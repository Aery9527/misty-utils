package org.misty.utils.combinatorics;

import org.misty.utils.ExecutorSwitch;
import org.misty.utils.Tracked;
import org.misty.utils.collection.ListElement;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;
import java.util.stream.Collectors;

public abstract class Combinatorics<ElementType, SelfType extends Combinatorics<ElementType, SelfType>> {

    private interface SortMap<KeyType, ElementType> extends Map<KeyType, List<List<ElementType>>> {
        void join(KeyType key, List<ElementType> result);

        default SortMap<KeyType, ElementType> fillMissing(Set<KeyType> keys) {
            keys.forEach(key -> putIfAbsent(key, Collections.emptyList()));
            return this;
        }

        default Map<KeyType, List<List<ElementType>>> toMap() {
            return Collections.unmodifiableMap(
                    entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> Collections.unmodifiableList(entry.getValue())))
            );
        }
    }

    private class SyncSortMap<KeyType> extends HashMap<KeyType, List<List<ElementType>>> implements SortMap<KeyType, ElementType> {
        @Override
        public void join(KeyType key, List<ElementType> result) {
            super.computeIfAbsent(key, k -> new ArrayList<>(DEFAULT_SORT_LIST_SIZE)).add(result);
        }
    }

    private class AsyncSortMap<KeyType> extends ConcurrentHashMap<KeyType, List<List<ElementType>>> implements SortMap<KeyType, ElementType> {
        @Override
        public void join(KeyType key, List<ElementType> result) {
            super.computeIfAbsent(key, k -> Collections.synchronizedList(new ArrayList<>(DEFAULT_SORT_LIST_SIZE))).add(result);
        }
    }

    /**
     * 控制{@link #foreach(int, boolean, BiPredicate)}要中斷
     */
    public static final boolean FOREACH_BREAK = true;

    /**
     * 同 {@link #FOREACH_BREAK} 的反意
     */
    public static final boolean FOREACH_CONTINUE = false;

    private static final int DEFAULT_COLLECT_LIST_SIZE = 16;

    private static final int DEFAULT_SORT_LIST_SIZE = DEFAULT_COLLECT_LIST_SIZE / 2;

    private final Tracked tracked;

    /**
     * 待排列/組合的物件母體
     */
    private final List<ListElement<ElementType>> elements;

    /**
     * {@link #elements}是否已經洗牌過
     */
    private final boolean shuffle;

    /**
     * 執行緒切換器(用於切換使用單緒或多緒環境)
     */
    private final ExecutorSwitch executorSwitch;

    /**
     * 是否強制使用單執行緒執行foreach, 主要使用在collect系列上, 由於他們都只是要將元素收集成List,
     * 若{@link #executorSwitch}原本就設定了單緒則沒啥問題, 但若設定了使用多緒將會為了維持List的同步而造成效能損失及程式碼雜亂,
     * 因此收集成List沒有額外的判斷時, 則強制採用單緒執行, 提高效能與程式碼維護性.
     */
    private boolean forceSerial = false;

    protected Combinatorics(Tracked tracked, List<ElementType> elements, boolean shuffle) {
        if (shuffle) {
            Collections.shuffle(elements);
        }

        this.tracked = tracked;
        this.elements = ListElement.boxing(elements);
        this.shuffle = shuffle;

        this.executorSwitch = ExecutorSwitch.create(tracked.link(ExecutorSwitch.class.getSimpleName()));
    }

    /**
     * 參考 {@link ExecutorSwitch#withParallel()}
     */
    public SelfType withParallel() {
        this.executorSwitch.withParallel();
        return (SelfType) this;
    }

    /**
     * 參考 {@link ExecutorSwitch#withParallel(int)}
     */
    public SelfType withParallel(int threadNumber) {
        this.executorSwitch.withParallel(threadNumber);
        return (SelfType) this;
    }

    /**
     * 參考 {@link ExecutorSwitch#withParallel(ExecutorService)}
     */
    public SelfType withParallel(ExecutorService executorService) {
        this.executorSwitch.withParallel(executorService);
        return (SelfType) this;
    }

    /**
     * 參考 {@link ExecutorSwitch#withSerial()}
     */
    public SelfType withSerial() {
        this.executorSwitch.withSerial();
        return (SelfType) this;
    }

    /**
     * 參考 {@link ExecutorSwitch#waitFinish()}
     */
    public SelfType waitFinish() {
        this.executorSwitch.waitFinish();
        return (SelfType) this;
    }

    /**
     * 同 {@link #sortInAll(int, boolean, Map)}, 只是多了一個將沒有任何filter匹配的排列/組合收集到unsortList中的功能
     */
    public <KeyType> Map<KeyType, List<List<ElementType>>> sortInAll(int size,
                                                                     boolean repeat,
                                                                     Map<KeyType, BiPredicate<Integer, List<ElementType>>> filterMap,
                                                                     List<List<ElementType>> unsortList) {
        SortMap<KeyType, ElementType> sortResult = buildSortUsedMap();

        List<List<ElementType>> unsortListWrap = this.executorSwitch.isWithParallel() ? Collections.synchronizedList(unsortList) : unsortList;

        foreach(size, repeat, (times, resultTemp) -> {
            AtomicBoolean isMatch = new AtomicBoolean(false);
            filterMap.entrySet().stream()
                    .filter(entry -> entry.getValue().test(times, resultTemp))
                    .map(Map.Entry::getKey)
                    .peek(key -> isMatch.set(true))
                    .forEach(key -> sortResult.join(key, resultTemp));
            if (!isMatch.get()) {
                unsortListWrap.add(resultTemp);
            }
        });
        waitFinish();

        return sortResult.fillMissing(filterMap.keySet()).toMap();
    }

    /**
     * 將排列/組合依照filterMap條件分類, 一個排列/組合可以分類到多個key中, 若只能分類到一個key中請使用 {@link #sortInFirst(int, boolean, Map)}
     *
     * @param size      排列/組合數量(k)
     * @param repeat    同個元素是否可以重複出現
     * @param filterMap 分類條件, key為分類名稱, value為分類條件
     * @param <KeyType> 分類名稱型態
     * @return 分類結果, key為分類名稱, value為符合該分類的排列/組合
     */
    public <KeyType> Map<KeyType, List<List<ElementType>>> sortInAll(int size, boolean repeat, Map<KeyType, BiPredicate<Integer, List<ElementType>>> filterMap) {
        SortMap<KeyType, ElementType> sortResult = buildSortUsedMap();

        foreach(size, repeat, (times, resultTemp) -> {
            filterMap.entrySet().stream()
                    .filter(entry -> entry.getValue().test(times, resultTemp))
                    .map(Map.Entry::getKey)
                    .forEach(key -> sortResult.join(key, resultTemp));
        });
        waitFinish();

        return sortResult.fillMissing(filterMap.keySet()).toMap();
    }

    /**
     * 同 {@link #sortInFirst(int, boolean, Map)}, 只是多了一個將沒有任何filter匹配的排列/組合收集到unsortList中的功能
     */
    public <KeyType> Map<KeyType, List<List<ElementType>>> sortInFirst(int size,
                                                                       boolean repeat,
                                                                       Map<KeyType, BiPredicate<Integer, List<ElementType>>> filterMap,
                                                                       List<List<ElementType>> unsortList) {
        SortMap<KeyType, ElementType> sortResult = buildSortUsedMap();

        List<List<ElementType>> unsortListWrap = this.executorSwitch.isWithParallel() ? Collections.synchronizedList(unsortList) : unsortList;

        foreach(size, repeat, (times, resultTemp) -> {
            AtomicBoolean isMatch = new AtomicBoolean(false);
            filterMap.entrySet().stream()
                    .filter(entry -> entry.getValue().test(times, resultTemp))
                    .map(Map.Entry::getKey)
                    .peek(key -> isMatch.set(true))
                    .findFirst()
                    .ifPresent(key -> sortResult.join(key, resultTemp));
            if (!isMatch.get()) {
                unsortListWrap.add(resultTemp);
            }
        });
        waitFinish();

        return sortResult.fillMissing(filterMap.keySet()).toMap();
    }

    /**
     * 將排列/組合依照filterMap條件分類, 一個排列/組合只會分類到一個key中, 若想要分類到多個key中請使用 {@link #sortInAll(int, boolean, Map)}
     *
     * @param size      排列/組合數量(k)
     * @param repeat    同個元素是否可以重複出現
     * @param filterMap 分類條件, key為分類名稱, value為分類條件
     * @param <KeyType> 分類名稱型態
     * @return 分類結果, key為分類名稱, value為符合該分類的排列/組合
     */
    public <KeyType> Map<KeyType, List<List<ElementType>>> sortInFirst(int size, boolean repeat, Map<KeyType, BiPredicate<Integer, List<ElementType>>> filterMap) {
        SortMap<KeyType, ElementType> sortResult = buildSortUsedMap();

        foreach(size, repeat, (times, resultTemp) -> {
            filterMap.entrySet().stream()
                    .filter(entry -> entry.getValue().test(times, resultTemp))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .ifPresent(key -> sortResult.join(key, resultTemp));
        });
        waitFinish();

        return sortResult.fillMissing(filterMap.keySet()).toMap();
    }

    /**
     * 同{@link #foreachMostTimes(int, int, BiConsumer)}, 只是將結果收集成List回傳, 強制採用單緒執行
     */
    public List<List<ElementType>> collectMostTimes(int size, int mostTimes) {
        return forceSerial(() -> {
            List<List<ElementType>> result = buildCollectUsedList();
            foreachMostTimes(size, mostTimes, (times, resultTemp) -> {
                result.add(resultTemp);
            });
            return result;
        });
    }

    /**
     * 同{@link #foreachLeastTimes(int, int, BiConsumer)}, 只是將結果收集成List回傳, 強制採用單緒執行
     */
    public List<List<ElementType>> collectLeastTimes(int size, int leastTimes) {
        return forceSerial(() -> {
            List<List<ElementType>> result = buildCollectUsedList();
            foreachLeastTimes(size, leastTimes, (times, resultTemp) -> {
                result.add(resultTemp);
            });
            return result;
        });
    }

    /**
     * 同{@link #foreachTimes(int, int, int, BiConsumer)}, 只是將結果收集成List回傳, 強制採用單緒執行
     */
    public List<List<ElementType>> collectTimes(int size, int mostTimes, int leastTimes) {
        return forceSerial(() -> {
            List<List<ElementType>> result = buildCollectUsedList();
            foreachTimes(size, mostTimes, leastTimes, (times, resultTemp) -> {
                result.add(resultTemp);
            });
            return result;
        });
    }

    /**
     * 同{@link #collectFirst(int, boolean, BiPredicate)}, 只是filter永遠回傳true(表示全收集), 因此沒有任何判斷邏輯(不耗時)所以強制採用單執行緒
     */
    public Optional<List<ElementType>> collectFirst(int size, boolean repeat) {
        return forceSerial(() -> collectFirst(size, repeat, (times, result) -> true));
    }

    /**
     * 同{@link #foreach(int, boolean, BiPredicate, BiPredicate)}, 只是回傳第一個找到的符合filter的組合或排序.
     * 不強制採用單緒是因為這邊的filter是由外部執行, 它的動作耗時是不可預期的, 因此維持此實例當前{@link #executorSwitch}的設定.
     */
    public Optional<List<ElementType>> collectFirst(int size, boolean repeat, BiPredicate<Integer, List<ListElement<ElementType>>> filter) {
        AtomicReference<List<ElementType>> pickRef = new AtomicReference<>();
        foreach(size, repeat, filter, (times, resultTemp) -> {
            pickRef.compareAndSet(null, resultTemp);
            return Combinatorics.FOREACH_BREAK;
        });
        waitFinish();
        return Optional.ofNullable(pickRef.get());
    }

    /**
     * 同{@link #collectMostAmount(int, boolean, int, BiPredicate)}, 只是filter永遠回傳true(表示全收集), 因此沒有任何判斷邏輯(不耗時)所以強制採用單執行緒
     */
    public List<List<ElementType>> collectMostAmount(int size, boolean repeat, int mostAmount) {
        return forceSerial(() -> collectMostAmount(size, repeat, mostAmount, (times, result) -> true));
    }

    /**
     * 同{@link #foreach(int, boolean, BiPredicate, BiPredicate)}, 只是回傳最多指定數量的符合filter的組合或排序.
     * 不強制採用單緒是因為這邊的filter是由外部執行, 它的動作耗時是不可預期的, 因此維持此實例當前{@link #executorSwitch}的設定.
     */
    public List<List<ElementType>> collectMostAmount(int size, boolean repeat, int mostAmount, BiPredicate<Integer, List<ListElement<ElementType>>> filter) {
        List<List<ElementType>> result = buildCollectUsedList();
        AtomicInteger amountCounter = new AtomicInteger(0);
        foreach(size, repeat, filter, (times, resultTemp) -> {
            int count = amountCounter.incrementAndGet();
            if (count <= mostAmount) {
                result.add(resultTemp);
                return Combinatorics.FOREACH_CONTINUE;
            } else {
                return Combinatorics.FOREACH_BREAK;
            }
        });
        waitFinish();
        return result;
    }

    /**
     * 同{@link #collect(int, boolean, BiPredicate)}, 只是filter永遠回傳true(表示全收集), 因此沒有任何判斷邏輯(不耗時)所以強制採用單執行緒
     */
    public List<List<ElementType>> collect(int size, boolean repeat) {
        return forceSerial(() -> collect(size, repeat, (times, result) -> true));
    }

    /**
     * 同{@link #foreach(int, boolean, BiPredicate, BiConsumer)}, 只是回傳所有符合filter的組合或排序.
     */
    public List<List<ElementType>> collect(int size, boolean repeat, BiPredicate<Integer, List<ListElement<ElementType>>> filter) {
        List<List<ElementType>> result = buildCollectUsedList();
        foreach(size, repeat, filter, (times, resultTemp) -> {
            result.add(resultTemp);
        });
        waitFinish();
        return result;
    }

    /**
     * 參考 {@link #foreachMostTimes(int, int, BiPredicate)}
     */
    public SelfType foreachMostTimes(int size, int mostTimes, BiConsumer<Integer, List<ElementType>> receiver) {
        foreachMostTimes(size, mostTimes, (times, result) -> {
            receiver.accept(times, result);
            return FOREACH_CONTINUE;
        });
        return (SelfType) this;
    }

    /**
     * 參考 {@link #foreachTimes(int, int, int, BiPredicate)}
     */
    public boolean foreachMostTimes(int size, int mostTimes, BiPredicate<Integer, List<ElementType>> receiver) {
        return foreachTimes(size, mostTimes, 1, receiver);
    }

    /**
     * 參考 {@link #foreachLeastTimes(int, int, BiPredicate)}
     */
    public SelfType foreachLeastTimes(int size, int leastTimes, BiConsumer<Integer, List<ElementType>> receiver) {
        foreachLeastTimes(size, leastTimes, (times, result) -> {
            receiver.accept(times, result);
            return FOREACH_CONTINUE;
        });
        return (SelfType) this;
    }

    /**
     * 參考 {@link #foreachTimes(int, int, int, BiPredicate)}
     */
    public boolean foreachLeastTimes(int size, int leastTimes, BiPredicate<Integer, List<ElementType>> receiver) {
        return foreachTimes(size, size, leastTimes, receiver);
    }

    /**
     * 參考 {@link #foreachTimes(int, int, int, BiPredicate)}
     */
    public SelfType foreachTimes(int size, int mostTimes, int leastTimes, BiConsumer<Integer, List<ElementType>> receiver) {
        foreachTimes(size, mostTimes, leastTimes, (times, result) -> {
            receiver.accept(times, result);
            return FOREACH_CONTINUE;
        });
        return (SelfType) this;
    }

    /**
     * @param size       排列/組合數量(k)
     * @param mostTimes  一個元素最多只能出現幾次
     * @param leastTimes 一個元素最少只能出現幾次
     * @param receiver   排列/組合接受器, 回傳值參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     * @return 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public boolean foreachTimes(int size, int mostTimes, int leastTimes, BiPredicate<Integer, List<ElementType>> receiver) {
        return foreach(size, true, (times, result) -> { // filter
            return result.stream()
                    .map(element -> element.index)
                    .reduce(new HashMap<Integer, AtomicInteger>(), (map, index) -> {
                        map.computeIfAbsent(index, i -> new AtomicInteger()).incrementAndGet();
                        return map;
                    }, (map1, map2) -> {
                        map2.forEach((index, count) -> {
                            map1.computeIfAbsent(index, i -> new AtomicInteger()).addAndGet(count.get());
                        });
                        return map1;
                    })
                    .values().stream()
                    .mapToInt(AtomicInteger::get)
                    .allMatch(count -> count >= leastTimes && count <= mostTimes);
        }, receiver);
    }

    /**
     * 同 {@link #foreach(int, boolean, BiPredicate, BiPredicate)}, 只是預設全跑玩不會中斷, 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public SelfType foreach(int size,
                            boolean repeat,
                            BiPredicate<Integer, List<ListElement<ElementType>>> filter,
                            BiConsumer<Integer, List<ElementType>> receiver) {
        foreach(size, repeat, filter, (times, result) -> {
            receiver.accept(times, result);
            return FOREACH_CONTINUE;
        });
        return (SelfType) this;
    }

    /**
     * @param size     排列/組合數量(k)
     * @param filter   排列/組合過濾器, 先行過濾該排列/組合是否使用
     * @param receiver 排列/組合接受器, 回傳值參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     * @return 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public boolean foreach(int size,
                           boolean repeat,
                           BiPredicate<Integer, List<ListElement<ElementType>>> filter,
                           BiPredicate<Integer, List<ElementType>> receiver) {
        return preForeach((times, result) -> { // receiver
            boolean accept = filter.test(times, result);
            return accept ? receiver.test(times, ListElement.unboxing(result)) : FOREACH_CONTINUE;
        }, size, repeat);
    }

    /**
     * 同 {@link #foreach(int, boolean, BiPredicate)}, 只是預設全跑玩不會中斷, 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public SelfType foreach(int size, boolean repeat, BiConsumer<Integer, List<ElementType>> receiver) {
        foreach(size, repeat, (times, result) -> { // receiver
            receiver.accept(times, result);
            return FOREACH_CONTINUE;
        });
        return (SelfType) this;
    }

    /**
     * 跑出所有排列/組合
     *
     * @param size     排列/組合數量(k)
     * @param repeat   同個元素是否可以重複出現, true:H(n取k)=C(n+k-1取k)=(n+k-1)!/k!/(n-1)!|false:C(n取k)=n!/k!/(n-k)!
     * @param receiver 排列/組合接受器, 回傳值參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     * @return 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public boolean foreach(int size, boolean repeat, BiPredicate<Integer, List<ElementType>> receiver) {
        return preForeach((times, result) -> receiver.test(times, ListElement.unboxing(result)), size, repeat);
    }

    public long numberOf(int k, boolean repeat) {
        return repeat ? numberOfRepeat(k) : numberOfUnique(k);
    }

    public abstract long numberOfRepeat(int k);

    public abstract long numberOfUnique(int k);

    protected boolean preForeach(BiPredicate<Integer, List<ListElement<ElementType>>> receiver, int size, boolean repeat) {
        return doForeach(buildReceiveController(receiver), size, repeat);
    }

    /**
     * 實際跑出所有排列/組合的動作
     */
    protected abstract boolean doForeach(Predicate<List<ListElement<ElementType>>> receiver, int size, boolean repeat);

    /**
     * 主要用來控制是多緒還是單緒執行任務
     */
    protected Predicate<List<ListElement<ElementType>>> buildReceiveController(BiPredicate<Integer, List<ListElement<ElementType>>> receiver) {
        if (this.executorSwitch.isWithParallel() && !this.forceSerial) {
            AtomicBoolean interruptFlag = new AtomicBoolean(false);
            Consumer<Boolean> interruptFlagUpdate = flag -> interruptFlag.compareAndSet(false, flag);

            return resultTemp -> {
                if (interruptFlag.get()) {
                    return FOREACH_BREAK;
                }

                List<ListElement<ElementType>> resultTempForParallel = Collections.unmodifiableList(new ArrayList<>(resultTemp));

                boolean executed = this.executorSwitch.run(times -> { // 這邊開始是fork出去別條thread執行的部分
                    if (interruptFlag.get()) {
                        return;
                    }

                    boolean interrupt = receiver.test(times, resultTempForParallel);
                    interruptFlagUpdate.accept(interrupt);
                });
                interruptFlagUpdate.accept(!executed);

                return interruptFlag.get() ? FOREACH_BREAK : FOREACH_CONTINUE;
            };

        } else {
            AtomicInteger times = new AtomicInteger(0);
            return resultTemp -> receiver.test(times.incrementAndGet(), resultTemp);
        }
    }

    protected List<ListElement<ElementType>> getListElements() {
        return this.elements;
    }

    private <ReturnType> ReturnType forceSerial(Supplier<ReturnType> action) {
        try {
            this.forceSerial = true;
            return action.get();
        } finally {
            this.forceSerial = false;
        }
    }

    private <KeyType> SortMap<KeyType, ElementType> buildSortUsedMap() {
        return this.executorSwitch.isWithParallel() ? new AsyncSortMap<>() : new SyncSortMap<>();
    }

    private List<List<ElementType>> buildCollectUsedList() {
        List<List<ElementType>> list = new ArrayList<>(DEFAULT_COLLECT_LIST_SIZE);
        return this.executorSwitch.isWithParallel() && !this.forceSerial ? Collections.synchronizedList(list) : list;
    }

    protected ExecutorSwitch getExecutorSwitch() {
        return executorSwitch;
    }

    public Tracked getTracked() {
        return tracked;
    }

    public List<ElementType> getElements() {
        return ListElement.unboxing(elements);
    }

    public boolean isShuffle() {
        return shuffle;
    }

}
