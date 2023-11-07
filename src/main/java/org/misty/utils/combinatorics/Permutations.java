package org.misty.utils.combinatorics;

import org.misty.utils.Tracked;
import org.misty.utils.collection.ListElement;
import org.misty.utils.ex.MathEx;
import org.misty.utils.flag.BitFlag;
import org.misty.utils.flag.DecimalFlag;
import org.misty.utils.flag.LongFlag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 排序暴力測試器, 根據給定的資訊跑出所有排序狀況
 */
public class Permutations<ElementType> extends Combinatorics<ElementType, Permutations<ElementType>> {

    protected interface IndexUsed {
        void use(int index);

        void unuse(int index);

        boolean isUsing(int index);
    }

    protected static class In62IndexUsed implements IndexUsed {

        private final LongFlag flag = BitFlag.useLong();

        @Override
        public void use(int index) {
            this.flag.add(1L << index);
        }

        @Override
        public void unuse(int index) {
            this.flag.remove(1L << index);
        }

        @Override
        public boolean isUsing(int index) {
            return this.flag.has(1L << index);
        }
    }

    protected static class Over62IndexUsed implements IndexUsed {

        private final DecimalFlag flag = BitFlag.useDecimal();

        @Override
        public void use(int index) {
            this.flag.add(index);
        }

        @Override
        public void unuse(int index) {
            this.flag.remove(index);
        }

        @Override
        public boolean isUsing(int index) {
            return this.flag.has(index);
        }
    }

    protected final static IndexUsed ALLOWED_REPEAT = new IndexUsed() {

        @Override
        public void use(int index) {
        }

        @Override
        public void unuse(int index) {
        }

        @Override
        public boolean isUsing(int index) {
            return false;
        }
    };

    public static <ElementType> Permutations<ElementType> of(ElementType... elements) {
        return new Permutations<>(Tracked.create(), Arrays.asList(elements), false);
    }

    public static <ElementType> Permutations<ElementType> shuffle(ElementType... elements) {
        return new Permutations<>(Tracked.create(), Arrays.asList(elements), true);
    }

    public static <ElementType> Permutations<ElementType> of(Collection<ElementType> elements) {
        return new Permutations<>(Tracked.create(), new ArrayList<>(elements), false);
    }

    public static <ElementType> Permutations<ElementType> shuffle(Collection<ElementType> elements) {
        return new Permutations<>(Tracked.create(), new ArrayList<>(elements), true);
    }

    public static <ElementType> Permutations<ElementType> of(Tracked tracked, ElementType... elements) {
        return new Permutations<>(tracked, Arrays.asList(elements), false);
    }

    public static <ElementType> Permutations<ElementType> shuffle(Tracked tracked, ElementType... elements) {
        return new Permutations<>(tracked, Arrays.asList(elements), true);
    }

    public static <ElementType> Permutations<ElementType> of(Tracked tracked, Collection<ElementType> elements) {
        return new Permutations<>(tracked, new ArrayList<>(elements), false);
    }

    public static <ElementType> Permutations<ElementType> shuffle(Tracked tracked, Collection<ElementType> elements) {
        return new Permutations<>(tracked, new ArrayList<>(elements), true);
    }

    /**
     * 參考 {@link #numberOfRepeat(int, int)} 跟 {@link #numberOfUnique(int, int)}
     */
    public static long numberOf(int n, int k, boolean repeat) {
        return repeat ? numberOfRepeat(n, k) : numberOfUnique(n, k);
    }

    /**
     * 重複排列的數量, 公式 n^r
     *
     * @param n elementSize
     * @param r permutationSize
     * @return n^r
     */
    public static long numberOfRepeat(int n, int r) {
        return IntStream.range(0, r)
                .mapToLong(i -> n)
                .reduce(1, (a, b) -> a * b);
    }

    /**
     * 不重複排列的數量, 公式 P(n取k) = n!/(n-k)!
     *
     * @param n elementSize
     * @param k permutationSize
     * @return P(n取k)
     */
    public static long numberOfUnique(int n, int k) {
        return MathEx.factorial(n) / MathEx.factorial(n - k);
    }

    protected Permutations(Tracked tracked, List<ElementType> elements, boolean shuffle) {
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
    protected boolean foreach(BiPredicate<Integer, List<ListElement<ElementType>>> receiver, int permutationSize, boolean repeat) {
        List<ListElement<ElementType>> elements = super.getListElements();

        Predicate<List<ListElement<ElementType>>> permutationReceiver = super.buildReceiver(receiver);

        List<ListElement<ElementType>> temp = IntStream.rangeClosed(1, permutationSize)
                .mapToObj(i -> (ListElement<ElementType>) null)
                .collect(Collectors.toList());

        Permutations.IndexUsed indexUsed;
        if (repeat) {
            indexUsed = ALLOWED_REPEAT;
        } else if (elements.size() <= LongFlag.MAX_BIT) {
            indexUsed = new In62IndexUsed();
        } else {
            indexUsed = new Over62IndexUsed();
        }

        return foreach(permutationReceiver, temp, indexUsed, 0);
    }

    protected boolean foreach(Predicate<List<ListElement<ElementType>>> permutationReceiver,
                              List<ListElement<ElementType>> permutationTemp,
                              Permutations.IndexUsed indexUsed,
                              int tempIndex) {
        int permutationSize = permutationTemp.size();
        boolean lastPosition = tempIndex == permutationSize;
        if (lastPosition) {
            return permutationReceiver.test(permutationTemp);
        }

        List<ListElement<ElementType>> elements = super.getListElements();
        for (int index = 0; index < elements.size(); index++) {
            if (indexUsed.isUsing(index)) {
                continue;
            }

            indexUsed.use(index);

            ListElement<ElementType> element = elements.get(index);
            permutationTemp.set(tempIndex, element);
            boolean isBreak = foreach(permutationReceiver, permutationTemp, indexUsed, tempIndex + 1);

            indexUsed.unuse(index);

            if (isBreak) {
                return FOREACH_BREAK;
            }
        }

        return FOREACH_CONTINUE;
    }

}
