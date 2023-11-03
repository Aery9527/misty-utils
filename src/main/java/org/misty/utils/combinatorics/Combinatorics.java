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

    /**
     * 參考 {@link #foreachMostTimes(int, int, BiPredicate)}
     */
    public void foreachMostTimes(int size, int mostTimes, BiConsumer<Integer, List<ElementType>> tester) {
        foreachMostTimes(size, mostTimes, (times, result) -> {
            tester.accept(times, result);
            return FOREACH_CONTINUE;
        });
    }

    /**
     * 參考 {@link #foreachTimes(int, int, int, BiPredicate)}
     */
    public boolean foreachMostTimes(int size, int mostTimes, BiPredicate<Integer, List<ElementType>> tester) {
        return foreachTimes(size, mostTimes, 1, tester);
    }

    /**
     * 參考 {@link #foreachLeastTimes(int, int, BiPredicate)}
     */
    public void foreachLeastTimes(int size, int leastTimes, BiConsumer<Integer, List<ElementType>> tester) {
        foreachLeastTimes(size, leastTimes, (times, result) -> {
            tester.accept(times, result);
            return FOREACH_CONTINUE;
        });
    }

    /**
     * 參考 {@link #foreachTimes(int, int, int, BiPredicate)}
     */
    public boolean foreachLeastTimes(int size, int leastTimes, BiPredicate<Integer, List<ElementType>> tester) {
        return foreachTimes(size, size, leastTimes, tester);
    }

    /**
     * 參考 {@link #foreachTimes(int, int, int, BiPredicate)}
     */
    public void foreachTimes(int size, int mostTimes, int leastTimes, BiConsumer<Integer, List<ElementType>> tester) {
        foreachTimes(size, mostTimes, leastTimes, (times, result) -> {
            tester.accept(times, result);
            return FOREACH_CONTINUE;
        });
    }

    /**
     * @param size       排列/組合數量(k)
     * @param mostTimes  一個元素最多只能出現幾次
     * @param leastTimes 一個元素最少只能出現幾次
     * @param tester     排列/組合測試器, 回傳值參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     * @return 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public boolean foreachTimes(int size, int mostTimes, int leastTimes, BiPredicate<Integer, List<ElementType>> tester) {
        return foreach(size, (times, result) -> { // filter
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
        }, tester);
    }

    /**
     * 同 {@link #foreach(int, BiPredicate, BiPredicate)}, 只是預設全跑玩不會中斷, 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public void foreach(int size,
                        BiPredicate<Integer, List<ListElement<ElementType>>> filter,
                        BiConsumer<Integer, List<ElementType>> tester) {
        foreach(size, filter, (times, result) -> {
            tester.accept(times, result);
            return FOREACH_CONTINUE;
        });
    }

    /**
     * @param size   排列/組合數量(k)
     * @param filter 排列/組合過濾器, 先行過濾該排列/組合是否使用
     * @param tester 排列/組合測試器, 回傳值參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     * @return 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public boolean foreach(int size,
                           BiPredicate<Integer, List<ListElement<ElementType>>> filter,
                           BiPredicate<Integer, List<ElementType>> tester) {
        return foreach((times, result) -> { // tester
            boolean accept = filter.test(times, result);
            return accept ? tester.test(times, ListElement.unboxing(result)) : FOREACH_CONTINUE;
        }, size, true);
    }

    /**
     * 同 {@link #foreach(int, boolean, BiPredicate)}, 只是預設全跑玩不會中斷, 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public void foreach(int size, boolean repeat, BiConsumer<Integer, List<ElementType>> tester) {
        foreach(size, repeat, (times, result) -> { // tester
            tester.accept(times, result);
            return FOREACH_CONTINUE;
        });
    }

    /**
     * 跑出所有排列/組合
     *
     * @param size   排列/組合數量(k)
     * @param repeat 同個元素是否可以重複出現, true:H(n取k)=C(n+k-1取k)=(n+k-1)!/k!/(n-1)!|false:C(n取k)=n!/k!/(n-k)!
     * @param tester 排列/組合測試器, 回傳值參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     * @return 參考 {@link #FOREACH_CONTINUE} 跟 {@link #FOREACH_BREAK}
     */
    public boolean foreach(int size, boolean repeat, BiPredicate<Integer, List<ElementType>> tester) {
        return foreach((times, result) -> tester.test(times, ListElement.unboxing(result)), size, repeat);
    }

    protected abstract boolean foreach(BiPredicate<Integer, List<ListElement<ElementType>>> tester, int size, boolean repeat);

    protected Predicate<List<ListElement<ElementType>>> buildReceiver(BiPredicate<Integer, List<ListElement<ElementType>>> tester) {
        if (this.executorSwitch.isWithParallel()) {
            AtomicBoolean interruptFlag = new AtomicBoolean(false);
            Consumer<Boolean> interruptFlagUpdate = flag -> interruptFlag.compareAndSet(false, flag);

            return combinationTemp -> {
                if (interruptFlag.get()) {
                    return FOREACH_BREAK;
                }

                List<ListElement<ElementType>> combinationTempForParallel = Collections.unmodifiableList(new ArrayList<>(combinationTemp));

                boolean executed = this.executorSwitch.run(times -> { // 這邊開始是fork出去別條thread執行的部分
                    boolean interrupt = tester.test(times, combinationTempForParallel);
                    interruptFlagUpdate.accept(interrupt);
                });
                interruptFlagUpdate.accept(!executed);

                return interruptFlag.get() ? FOREACH_BREAK : FOREACH_CONTINUE;
            };

        } else {
            AtomicInteger times = new AtomicInteger(0);
            return combinationTemp -> tester.test(times.incrementAndGet(), combinationTemp);
        }
    }

    protected List<ListElement<ElementType>> getListElements() {
        return this.elements;
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
