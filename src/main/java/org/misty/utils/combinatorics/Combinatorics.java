package org.misty.utils.combinatorics;

import org.misty.utils.ExecutorSwitch;
import org.misty.utils.Tracked;
import org.misty.utils.collection.ListElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Combinatorics<ElementType, SelfType extends Combinatorics<ElementType, SelfType>> {

    /**
     * 控制{@link #foreach(int, boolean, BiPredicate)}要中斷
     */
    public static final boolean FOREACH_BREAK = true;

    /**
     * 同 {@link #FOREACH_BREAK} 的反意
     */
    public static final boolean FOREACH_CONTINUE = false;

    private final Tracked tracked;

    /**
     * 所有待測試物件集合
     */
    private final List<ListElement<ElementType>> elements;

    private final boolean shuffle;

    private final ExecutorSwitch executorSwitch;

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

    public SelfType waitFinish() {
        this.executorSwitch.waitFinish();
        return (SelfType) this;
    }

    public List<List<ElementType>> collectMostTimes(int size, int mostTimes) {
        List<List<ElementType>> result = buildCollectUsedList();
        foreachMostTimes(size, mostTimes, (times, resultTemp) -> {
            result.add(resultTemp);
        });
        waitFinish();
        return result;
    }

    public List<List<ElementType>> collectLeastTimes(int size, int leastTimes) {
        List<List<ElementType>> result = buildCollectUsedList();
        foreachLeastTimes(size, leastTimes, (times, resultTemp) -> {
            result.add(resultTemp);
        });
        waitFinish();
        return result;
    }

    public List<List<ElementType>> collectTimes(int size, int mostTimes, int leastTimes) {
        List<List<ElementType>> result = buildCollectUsedList();
        foreachTimes(size, mostTimes, leastTimes, (times, resultTemp) -> {
            result.add(resultTemp);
        });
        waitFinish();
        return result;
    }

    public List<List<ElementType>> collect(int size, boolean repeat) {
        return collect(size, repeat, (times, result) -> true);
    }

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
     * @param size   排列/組合數量(k)
     * @param filter 排列/組合過濾器, 先行過濾該排列/組合是否使用
     * @param receiver 排列/組合測試器, 回傳值參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     * @return 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public boolean foreach(int size,
                           boolean repeat,
                           BiPredicate<Integer, List<ListElement<ElementType>>> filter,
                           BiPredicate<Integer, List<ElementType>> receiver) {
        return foreach((times, result) -> { // receiver
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
     * @param size   排列/組合數量(k)
     * @param repeat 同個元素是否可以重複出現, true:H(n取k)=C(n+k-1取k)=(n+k-1)!/k!/(n-1)!|false:C(n取k)=n!/k!/(n-k)!
     * @param receiver 排列/組合測試器, 回傳值參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     * @return 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public boolean foreach(int size, boolean repeat, BiPredicate<Integer, List<ElementType>> receiver) {
        return foreach((times, result) -> receiver.test(times, ListElement.unboxing(result)), size, repeat);
    }

    public long numberOf(int k, boolean repeat) {
        return repeat ? numberOfRepeat(k) : numberOfUnique(k);
    }

    public abstract long numberOfRepeat(int k);

    public abstract long numberOfUnique(int k);

    protected abstract boolean foreach(BiPredicate<Integer, List<ListElement<ElementType>>> receiver, int size, boolean repeat);

    protected Predicate<List<ListElement<ElementType>>> buildReceiver(BiPredicate<Integer, List<ListElement<ElementType>>> receiver) {
        if (this.executorSwitch.isWithParallel()) {
            AtomicBoolean interruptFlag = new AtomicBoolean(false);
            Consumer<Boolean> interruptFlagUpdate = flag -> interruptFlag.compareAndSet(false, flag);

            return resultTemp -> {
                if (interruptFlag.get()) {
                    return FOREACH_BREAK;
                }

                List<ListElement<ElementType>> resultTempForParallel = Collections.unmodifiableList(new ArrayList<>(resultTemp));

                boolean executed = this.executorSwitch.run(times -> { // 這邊開始是fork出去別條thread執行的部分
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

    private List<List<ElementType>> buildCollectUsedList() {
        List<List<ElementType>> list = new ArrayList<>(16);
        return this.executorSwitch.isWithParallel() ? Collections.synchronizedList(list) : list;
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
