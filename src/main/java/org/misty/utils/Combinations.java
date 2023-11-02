package org.misty.utils;

import org.misty.utils.ex.MathEx;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 組合暴力測試器, 根據給定的資訊跑出所有組合(不管排序),
 * 例如一副撲克牌(52張)發5張牌, 有多少組合全部列舉出來(52取5不重複或者重複).
 */
public class Combinations<ElementType> {

    interface ForeachAction {
        int elementStopIndex(int elementSize, int combinationSize, int tempStartIndex);

        int nextElementStartIndex(int elementSize, int elementStartIndex);
    }

    private static final ForeachAction FOREACH_REPEAT_ACTION = new ForeachAction() {
        @Override
        public int elementStopIndex(int elementSize, int combinationSize, int tempStartIndex) {
            return elementSize;
        }

        @Override
        public int nextElementStartIndex(int elementSize, int elementStartIndex) {
            return elementStartIndex;
        }
    };

    private static final ForeachAction FOREACH_UNIQUE_ACTION = new ForeachAction() {
        @Override
        public int elementStopIndex(int elementSize, int combinationSize, int tempStartIndex) {
            return elementSize - combinationSize + tempStartIndex + 1;
        }

        @Override
        public int nextElementStartIndex(int elementSize, int elementStartIndex) {
            return elementStartIndex + 1;
        }
    };

    public class Element {
        public final int index;

        public final ElementType content;

        public Element(int index, ElementType content) {
            this.index = index;
            this.content = content;
        }

        @Override
        public String toString() {
            return "[" + index + "] " + content;
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

    @SafeVarargs
    public static <ElementType> Combinations<ElementType> of(ElementType... elements) {
        return new Combinations<>(Tracked.create(), Arrays.asList(elements), false);
    }

    @SafeVarargs
    public static <ElementType> Combinations<ElementType> shuffle(ElementType... elements) {
        return new Combinations<>(Tracked.create(), Arrays.asList(elements), true);
    }

    public static <ElementType> Combinations<ElementType> of(Collection<ElementType> elements) {
        return new Combinations<>(Tracked.create(), new ArrayList<>(elements), false);
    }

    public static <ElementType> Combinations<ElementType> shuffle(Collection<ElementType> elements) {
        return new Combinations<>(Tracked.create(), new ArrayList<>(elements), true);
    }

    @SafeVarargs
    public static <ElementType> Combinations<ElementType> of(Tracked tracked, ElementType... elements) {
        return new Combinations<>(tracked, Arrays.asList(elements), false);
    }

    @SafeVarargs
    public static <ElementType> Combinations<ElementType> shuffle(Tracked tracked, ElementType... elements) {
        return new Combinations<>(tracked, Arrays.asList(elements), true);
    }

    public static <ElementType> Combinations<ElementType> of(Tracked tracked, Collection<ElementType> elements) {
        return new Combinations<>(tracked, new ArrayList<>(elements), false);
    }

    public static <ElementType> Combinations<ElementType> shuffle(Tracked tracked, Collection<ElementType> elements) {
        return new Combinations<>(tracked, new ArrayList<>(elements), true);
    }

    /**
     * 重複組合的數量, 公式 H(n取k) = C(n+k-1取k) = (n+k-1)!/k!/(n-1)!
     *
     * @param n elementSize
     * @param k combinationSize
     * @return H(n取k)
     */
    public static long numberOfRepeat(int n, int k) {
        return numberOfUnique(n + k - 1, k);
    }

    /**
     * 不重複組合的數量, 公式 C(n取k) = n!/k!/(n-k)!
     *
     * @param n elementSize
     * @param k combinationSize
     * @return C(n取k)
     */
    public static long numberOfUnique(int n, int k) {
        return MathEx.factorial(n) / MathEx.factorial(k) / MathEx.factorial(n - k);
    }

    private final Tracked tracked;

    /**
     * 所有待測試物件集合
     */
    private final List<Element> elements;

    private final boolean shuffle;

    private final ExecutorSwitch executorSwitch;

    private Combinations(Tracked tracked, List<ElementType> elements, boolean shuffle) {
        if (shuffle) {
            Collections.shuffle(elements);
        }

        this.tracked = tracked;
        this.elements = Collections.unmodifiableList(IntStream.range(0, elements.size())
                .mapToObj(i -> new Element(i, elements.get(i)))
                .collect(Collectors.toList()));
        this.shuffle = shuffle;

        this.executorSwitch = ExecutorSwitch.create(tracked.link(ExecutorSwitch.class.getSimpleName()));
    }

    /**
     * 參考 {@link ExecutorSwitch#withParallel()}
     */
    public Combinations<ElementType> withParallel() {
        this.executorSwitch.withParallel();
        return this;
    }

    /**
     * 參考 {@link ExecutorSwitch#withParallel(int)}
     */
    public Combinations<ElementType> withParallel(int threadNumber) {
        this.executorSwitch.withParallel(threadNumber);
        return this;
    }

    /**
     * 參考 {@link ExecutorSwitch#withParallel(ExecutorService)}
     */
    public Combinations<ElementType> withParallel(ExecutorService executorService) {
        this.executorSwitch.withParallel(executorService);
        return this;
    }

    /**
     * 參考 {@link ExecutorSwitch#withSerial()}
     */
    public Combinations<ElementType> withSerial() {
        this.executorSwitch.withSerial();
        return this;
    }

    public Combinations<ElementType> waitFinish() {
        this.executorSwitch.waitFinish();
        return this;
    }

    /**
     * 參考 {@link #foreachMostTimes(int, int, BiPredicate)}
     */
    public void foreachMostTimes(int combinationSize, int mostTimes, BiConsumer<Integer, List<ElementType>> combinationTester) {
        foreachMostTimes(combinationSize, mostTimes, (times, combination) -> {
            combinationTester.accept(times, combination);
            return FOREACH_CONTINUE;
        });
    }

    /**
     * 參考 {@link #foreachTimes(int, int, int, BiPredicate)}
     */
    public boolean foreachMostTimes(int combinationSize, int mostTimes, BiPredicate<Integer, List<ElementType>> combinationTester) {
        return foreachTimes(combinationSize, mostTimes, 1, combinationTester);
    }

    /**
     * 參考 {@link #foreachLeastTimes(int, int, BiPredicate)}
     */
    public void foreachLeastTimes(int combinationSize, int leastTimes, BiConsumer<Integer, List<ElementType>> combinationTester) {
        foreachLeastTimes(combinationSize, leastTimes, (times, combination) -> {
            combinationTester.accept(times, combination);
            return FOREACH_CONTINUE;
        });
    }

    /**
     * 參考 {@link #foreachTimes(int, int, int, BiPredicate)}
     */
    public boolean foreachLeastTimes(int combinationSize, int leastTimes, BiPredicate<Integer, List<ElementType>> combinationTester) {
        return foreachTimes(combinationSize, combinationSize, leastTimes, combinationTester);
    }

    /**
     * 參考 {@link #foreachTimes(int, int, int, BiPredicate)}
     */
    public void foreachTimes(int combinationSize, int mostTimes, int leastTimes, BiConsumer<Integer, List<ElementType>> combinationTester) {
        foreachTimes(combinationSize, mostTimes, leastTimes, (times, combination) -> {
            combinationTester.accept(times, combination);
            return FOREACH_CONTINUE;
        });
    }

    /**
     * @param combinationSize   組合數量(k)
     * @param mostTimes         一個元素最多只能出現幾次
     * @param leastTimes        一個元素最少只能出現幾次
     * @param combinationTester 組合測試器, 回傳值參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     * @return 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public boolean foreachTimes(int combinationSize, int mostTimes, int leastTimes, BiPredicate<Integer, List<ElementType>> combinationTester) {
        return foreach(combinationSize, (times, combination) -> { // filter
            return combination.stream()
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
        }, combinationTester);
    }

    /**
     * 同 {@link #foreach(int, BiPredicate, BiPredicate)}, 只是預設全跑玩不會中斷, 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public void foreach(int combinationSize, BiPredicate<Integer, List<Element>> filter, BiConsumer<Integer, List<ElementType>> combinationTester) {
        foreach(combinationSize, filter, (times, combination) -> {
            combinationTester.accept(times, combination);
            return FOREACH_CONTINUE;
        });
    }

    /**
     * @param combinationSize   組合數量(k)
     * @param filter            組合過濾器, 先行過濾該組合是否使用
     * @param combinationTester 組合測試器, 回傳值參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     * @return 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public boolean foreach(int combinationSize, BiPredicate<Integer, List<Element>> filter, BiPredicate<Integer, List<ElementType>> combinationTester) {
        return foreach((times, combinations) -> { // combinationTester
            boolean accept = filter.test(times, combinations);
            return accept ? combinationTester.test(times, unboxing(combinations)) : FOREACH_CONTINUE;
        }, combinationSize, true);
    }

    /**
     * 同 {@link #foreach(int, boolean, BiPredicate)}, 只是預設全跑玩不會中斷, 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public void foreach(int combinationSize, boolean repeat, BiConsumer<Integer, List<ElementType>> combinationTester) {
        foreach(combinationSize, repeat, (times, combination) -> { // combinationTester
            combinationTester.accept(times, combination);
            return FOREACH_CONTINUE;
        });
    }

    /**
     * 跑出所有組合
     *
     * @param combinationSize   組合數量(k)
     * @param repeat            同個元素是否可以重複出現, true:H(n取k)=C(n+k-1取k)=(n+k-1)!/k!/(n-1)!|false:C(n取k)=n!/k!/(n-k)!
     * @param combinationTester 組合測試器, 回傳值參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     * @return 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public boolean foreach(int combinationSize, boolean repeat, BiPredicate<Integer, List<ElementType>> combinationTester) {
        return foreach((times, combinations) -> combinationTester.test(times, unboxing(combinations)), combinationSize, repeat);
    }

    private boolean foreach(BiPredicate<Integer, List<Element>> combinationTester, int combinationSize, boolean repeat) {
        Supplier<List<Element>> tempInitializer = () -> IntStream.rangeClosed(1, combinationSize).mapToObj(i -> (Element) null).collect(Collectors.toList());

        List<Element> temp = tempInitializer.get();

        ForeachAction foreachAction = repeat ? FOREACH_REPEAT_ACTION : FOREACH_UNIQUE_ACTION;

        Predicate<List<Element>> combinationReceiver;
        if (this.executorSwitch.isWithParallel()) {
            AtomicBoolean interruptFlag = new AtomicBoolean(false);
            Consumer<Boolean> interruptFlagUpdate = flag -> interruptFlag.compareAndSet(false, flag);

            combinationReceiver = combinationTemp -> {
                if (interruptFlag.get()) {
                    return FOREACH_BREAK;
                }

                List<Element> combinationTempForParallel = Collections.unmodifiableList(new ArrayList<>(combinationTemp));

                boolean executed = this.executorSwitch.run(times -> { // 這邊開始是fork出去別條thread執行的部分
                    boolean interrupt = combinationTester.test(times, combinationTempForParallel);
                    interruptFlagUpdate.accept(interrupt);
                });
                interruptFlagUpdate.accept(!executed);

                return interruptFlag.get() ? FOREACH_BREAK : FOREACH_CONTINUE;
            };
        } else {
            AtomicInteger times = new AtomicInteger(0);
            combinationReceiver = combinationTemp -> combinationTester.test(times.incrementAndGet(), combinationTemp);
        }

        return foreach(combinationReceiver, temp, 0, 0, foreachAction);
    }

    private boolean foreach(Predicate<List<Element>> combinationReceiver,
                            List<Element> combinationTemp,
                            int elementStartIndex,
                            int tempIndex,
                            ForeachAction foreachAction) {
        int combinationSize = combinationTemp.size();
        boolean lastPosition = tempIndex == combinationSize;
        if (lastPosition) {
            return combinationReceiver.test(combinationTemp);
        }

        int nextTempIndex = tempIndex + 1;
        int elementStopIndex = foreachAction.elementStopIndex(this.elements.size(), combinationSize, tempIndex);
        for (int elementIndex = elementStartIndex; elementIndex < elementStopIndex; elementIndex++) {
            Element element = this.elements.get(elementIndex);
            combinationTemp.set(tempIndex, element);
            int nextElementStartIndex = foreachAction.nextElementStartIndex(this.elements.size(), elementIndex);
            boolean isBreak = foreach(combinationReceiver, combinationTemp, nextElementStartIndex, nextTempIndex, foreachAction);
            if (isBreak) {
                return FOREACH_BREAK;
            }
        }

        return FOREACH_CONTINUE;
    }

    private List<ElementType> unboxing(List<Element> elements) {
        return Collections.unmodifiableList(elements.stream()
                .map(element -> element.content)
                .collect(Collectors.toList()));
    }

    public List<ElementType> getElements() {
        return unboxing(elements);
    }

    public Tracked getTracked() {
        return tracked;
    }

    public boolean isShuffle() {
        return shuffle;
    }

}
