package org.misty.utils.combinatorics;

import org.misty.utils.Tracked;
import org.misty.utils.collection.ListElement;
import org.misty.utils.ex.MathEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 組合暴力測試器, 根據給定的資訊跑出所有組合(不管排序),
 * 例如一副撲克牌(52張)發5張牌, 有多少組合全部列舉出來(52取5不重複或者重複).
 */
public class Combinations<ElementType> extends Combinatorics<ElementType, Combinations<ElementType>> {

    protected interface ForeachAction {
        int elementStopIndex(int elementSize, int combinationSize, int tempIndex);

        int nextElementStartIndex(int elementSize, int elementIndex);
    }

    protected static final ForeachAction FOREACH_REPEAT_ACTION = new ForeachAction() {
        @Override
        public int elementStopIndex(int elementSize, int combinationSize, int tempIndex) {
            return elementSize;
        }

        @Override
        public int nextElementStartIndex(int elementSize, int elementIndex) {
            return elementIndex;
        }
    };

    protected static final ForeachAction FOREACH_UNIQUE_ACTION = new ForeachAction() {
        @Override
        public int elementStopIndex(int elementSize, int combinationSize, int tempIndex) {
            return elementSize - combinationSize + tempIndex + 1;
        }

        @Override
        public int nextElementStartIndex(int elementSize, int elementIndex) {
            return elementIndex + 1;
        }
    };

    public static <ElementType> Combinations<ElementType> of(ElementType... elements) {
        return new Combinations<>(Tracked.create(), Arrays.asList(elements), false);
    }

    public static <ElementType> Combinations<ElementType> shuffle(ElementType... elements) {
        return new Combinations<>(Tracked.create(), Arrays.asList(elements), true);
    }

    public static <ElementType> Combinations<ElementType> of(Collection<ElementType> elements) {
        return new Combinations<>(Tracked.create(), new ArrayList<>(elements), false);
    }

    public static <ElementType> Combinations<ElementType> shuffle(Collection<ElementType> elements) {
        return new Combinations<>(Tracked.create(), new ArrayList<>(elements), true);
    }

    public static <ElementType> Combinations<ElementType> of(Tracked tracked, ElementType... elements) {
        return new Combinations<>(tracked, Arrays.asList(elements), false);
    }

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
     * 參考 {@link #numberOfRepeat(int, int)} 跟 {@link #numberOfUnique(int, int)}
     */
    public static long numberOf(int n, int k, boolean repeat) {
        return repeat ? numberOfRepeat(n, k) : numberOfUnique(n, k);
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

    private Combinations(Tracked tracked, List<ElementType> elements, boolean shuffle) {
        super(tracked, elements, shuffle);
    }

    @Override
    public long numberOfRepeat(int k) {
        return numberOfRepeat(super.getListElements().size(), k);
    }

    @Override
    public long numberOfUnique(int k) {
        return numberOfUnique(super.getListElements().size(), k);
    }

    @Override
    protected boolean foreach(BiPredicate<Integer, List<ListElement<ElementType>>> combinationTester, int combinationSize, boolean repeat) {
        Predicate<List<ListElement<ElementType>>> combinationReceiver = super.buildReceiver(combinationTester);

        List<ListElement<ElementType>> temp = IntStream.rangeClosed(1, combinationSize)
                .mapToObj(i -> (ListElement<ElementType>) null)
                .collect(Collectors.toList());

        ForeachAction foreachAction = repeat ? FOREACH_REPEAT_ACTION : FOREACH_UNIQUE_ACTION;

        return foreach(combinationReceiver, temp, foreachAction, 0, 0);
    }

    protected boolean foreach(Predicate<List<ListElement<ElementType>>> combinationReceiver,
                              List<ListElement<ElementType>> combinationTemp,
                              ForeachAction foreachAction,
                              int elementStartIndex,
                              int tempIndex) {
        int combinationSize = combinationTemp.size();
        boolean lastPosition = tempIndex == combinationSize;
        if (lastPosition) {
            return combinationReceiver.test(combinationTemp);
        }

        List<ListElement<ElementType>> elements = super.getListElements();

        int nextTempIndex = tempIndex + 1;
        int elementStopIndex = foreachAction.elementStopIndex(elements.size(), combinationSize, tempIndex);
        for (int elementIndex = elementStartIndex; elementIndex < elementStopIndex; elementIndex++) {
            ListElement<ElementType> element = elements.get(elementIndex);
            combinationTemp.set(tempIndex, element);
            int nextElementStartIndex = foreachAction.nextElementStartIndex(elements.size(), elementIndex);
            boolean isBreak = foreach(combinationReceiver, combinationTemp, foreachAction, nextElementStartIndex, nextTempIndex);
            if (isBreak) {
                return FOREACH_BREAK;
            }
        }

        return FOREACH_CONTINUE;
    }

}
