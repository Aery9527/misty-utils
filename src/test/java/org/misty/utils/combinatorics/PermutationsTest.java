package org.misty.utils.combinatorics;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty.utils.ex.ThreadEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class PermutationsTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void numberOfRepeat() {
        Assertions.assertThat(Permutations.numberOfRepeat(5, 3)).isEqualTo(5 * 5 * 5);
        Assertions.assertThat(Permutations.numberOfRepeat(9, 4)).isEqualTo(9 * 9 * 9 * 9);

        Assertions.assertThat(Permutations.of(1, 2, 3, 4, 5).numberOfRepeat(3)).isEqualTo(5 * 5 * 5);
        Assertions.assertThat(Permutations.of(1, 2, 3, 4, 5, 6, 7, 8, 9).numberOfRepeat(4)).isEqualTo(9 * 9 * 9 * 9);
    }

    @Test
    public void numberOfUnique() {
        Assertions.assertThat(Permutations.numberOfUnique(5, 3)).isEqualTo(60);
        Assertions.assertThat(Permutations.numberOfUnique(9, 4)).isEqualTo(3024);

        Assertions.assertThat(Permutations.of(1, 2, 3, 4, 5).numberOfUnique(3)).isEqualTo(60);
        Assertions.assertThat(Permutations.of(1, 2, 3, 4, 5, 6, 7, 8, 9).numberOfUnique(4)).isEqualTo(3024);
    }

    /**
     * 測試建構
     */
    @Test
    public void of() {
        {
            Permutations<String> permutations = Permutations.of("A", "B", "C");
            Assertions.assertThat(permutations.getElements()).containsExactly("A", "B", "C");
            Assertions.assertThat(permutations.isShuffle()).isEqualTo(false);
        }

        {
            Permutations<String> permutations = Permutations.shuffle("A", "B", "C");
            Assertions.assertThat(permutations.getElements()).containsExactlyInAnyOrder("A", "B", "C");
            Assertions.assertThat(permutations.isShuffle()).isEqualTo(true);
        }

        {
            List<String> elements = new ArrayList<>(Arrays.asList("A", "B", "C"));
            Permutations<String> permutations = Permutations.of(elements);
            Assertions.assertThat(permutations.getElements()).containsExactly("A", "B", "C");
            Assertions.assertThat(permutations.getElements() == elements).isFalse();
            Assertions.assertThat(permutations.isShuffle()).isEqualTo(false);

            elements.add("D");
            Assertions.assertThat(permutations.getElements()).containsExactly("A", "B", "C");
        }

        {
            Set<String> elements = new HashSet<>(Arrays.asList("A", "B", "C"));
            Permutations<String> permutations = Permutations.shuffle(elements);
            Assertions.assertThat(permutations.getElements()).containsExactlyInAnyOrder("A", "B", "C");
            Assertions.assertThat(permutations.isShuffle()).isEqualTo(true);
        }
    }

    /**
     * 測試不中斷
     */
    @Test
    public void foreach_continue() {
        int permutationSize = 2;
        boolean repeat = true;

        Permutations<String> permutations = Permutations.of("A", "B", "C");
        List<List<String>> temp = new ArrayList<>();
        boolean flag = permutations.foreach(permutationSize, repeat, (times, permutation) -> {
            temp.add(permutation);
            return Combinatorics.FOREACH_CONTINUE;
        });

        Assertions.assertThat(temp.size()).isEqualTo(Permutations.numberOf(permutations.getElements().size(), permutationSize, repeat));
        Assertions.assertThat(flag).isEqualTo(Combinatorics.FOREACH_CONTINUE);
    }

    /**
     * 測試中斷
     */
    @Test
    public void foreach_break() {
        int permutationSize = 2;
        boolean repeat = true;
        int testCount = 2;

        Permutations<String> permutations = Permutations.of("A", "B", "C");
        List<List<String>> temp = new ArrayList<>();
        boolean flag = permutations.foreach(permutationSize, repeat, (times, permutation) -> {
            temp.add(permutation);
            return temp.size() < testCount ? Combinatorics.FOREACH_CONTINUE : Combinatorics.FOREACH_BREAK;
        });

        Assertions.assertThat(temp.size()).isEqualTo(testCount);
        Assertions.assertThat(flag).isEqualTo(Combinatorics.FOREACH_BREAK);
    }

    @Test
    public void collect_repeat() {
        int permutationSize = 2;
        boolean repeat = true;

        for (int testTimes = 0; testTimes < 10; testTimes++) {
            List<List<String>> expectedResult = Collections.synchronizedList(new ArrayList<>());
            List<List<String>> actualResult = null;
            try {
                Permutations<String> permutations = Permutations.of("A", "B", "C").withParallel();

                permutations.foreach(permutationSize, repeat, (times, permutation) -> {
                    expectedResult.add(permutation);
                });
                permutations.waitFinish();

                actualResult = permutations.collect(permutationSize, repeat);

                Assertions.assertThat(actualResult).containsExactlyInAnyOrderElementsOf(expectedResult);
            } catch (Throwable e) {
                CombinatoricsTest.print("expectedResult", permutationSize, expectedResult);
                CombinatoricsTest.print("actualResult", permutationSize, actualResult);
                throw e;
            }
        }
    }

    /**
     * 測試重複出現的排列
     */
    @Test
    public void foreach_repeat() {
        int permutationSize = 2;
        boolean repeat = true;

        Permutations<String> permutations = Permutations.of("A", "B", "C");
        List<List<String>> temp = new ArrayList<>();
        permutations.foreach(permutationSize, repeat, (times, permutation) -> {
            temp.add(permutation);
        });

        int index = 0;
        Assertions.assertThat(temp.get(index++)).containsExactly("A", "A");
        Assertions.assertThat(temp.get(index++)).containsExactly("A", "B");
        Assertions.assertThat(temp.get(index++)).containsExactly("A", "C");
        Assertions.assertThat(temp.get(index++)).containsExactly("B", "A");
        Assertions.assertThat(temp.get(index++)).containsExactly("B", "B");
        Assertions.assertThat(temp.get(index++)).containsExactly("B", "C");
        Assertions.assertThat(temp.get(index++)).containsExactly("C", "A");
        Assertions.assertThat(temp.get(index++)).containsExactly("C", "B");
        Assertions.assertThat(temp.get(index++)).containsExactly("C", "C");

        Assertions.assertThat(temp.size()).isEqualTo(Permutations.numberOf(permutations.getElements().size(), permutationSize, repeat));
    }

    @Test
    public void collect_unique() {
        int permutationSize = 2;
        boolean repeat = false;

        for (int testTimes = 0; testTimes < 10; testTimes++) {
            List<List<String>> expectedResult = Collections.synchronizedList(new ArrayList<>());
            List<List<String>> actualResult = null;
            try {
                Permutations<String> permutations = Permutations.of("A", "B", "C").withParallel();

                permutations.foreach(permutationSize, repeat, (times, permutation) -> {
                    expectedResult.add(permutation);
                });
                permutations.waitFinish();

                actualResult = permutations.collect(permutationSize, repeat);

                Assertions.assertThat(actualResult).containsExactlyInAnyOrderElementsOf(expectedResult);
            } catch (Throwable e) {
                CombinatoricsTest.print("expectedResult", permutationSize, expectedResult);
                CombinatoricsTest.print("actualResult", permutationSize, actualResult);
                throw e;
            }
        }
    }

    /**
     * 測試不重複出現的排列
     */
    @Test
    public void foreach_unique() {
        int permutationSize = 2;
        boolean repeat = false;

        Permutations<String> permutations = Permutations.of("A", "B", "C");
        List<List<String>> temp = new ArrayList<>();
        permutations.foreach(permutationSize, repeat, (times, permutation) -> {
            temp.add(permutation);
        });

        int index = 0;
        Assertions.assertThat(temp.get(index++)).containsExactly("A", "B");
        Assertions.assertThat(temp.get(index++)).containsExactly("A", "C");
        Assertions.assertThat(temp.get(index++)).containsExactly("B", "A");
        Assertions.assertThat(temp.get(index++)).containsExactly("B", "C");
        Assertions.assertThat(temp.get(index++)).containsExactly("C", "A");
        Assertions.assertThat(temp.get(index++)).containsExactly("C", "B");

        Assertions.assertThat(temp.size()).isEqualTo(Permutations.numberOf(permutations.getElements().size(), permutationSize, repeat));
    }

    /**
     * 測試過濾器
     */
    @Test
    public void foreach_filter() {
        Permutations<String> permutations = Permutations.of("A", "B", "C");
        List<List<String>> temp = new ArrayList<>();
        permutations.foreach(2, true, (times, permutation) -> { // filter
            return permutation.get(1).content.equals("B");
        }, (times, permutation) -> { // tester
            temp.add(permutation);
        });

        int index = 0;
        Assertions.assertThat(temp.get(index++)).containsExactly("A", "B");
        Assertions.assertThat(temp.get(index++)).containsExactly("B", "B");
        Assertions.assertThat(temp.get(index++)).containsExactly("C", "B");
    }

    @Test
    public void collectMostTimes() {
        int permutationSize = 3;
        int mostTimes = 2;

        for (int testTimes = 0; testTimes < 10; testTimes++) {
            List<List<String>> expectedResult = Collections.synchronizedList(new ArrayList<>());
            List<List<String>> actualResult = null;
            try {
                Permutations<String> permutations = Permutations.of("A", "B", "C", "D", "E").withParallel();

                permutations.foreachMostTimes(permutationSize, mostTimes, (times, permutation) -> {
                    expectedResult.add(permutation);
                });
                permutations.waitFinish();

                actualResult = permutations.collectMostTimes(permutationSize, mostTimes);

                Assertions.assertThat(actualResult).containsExactlyInAnyOrderElementsOf(expectedResult);
            } catch (Throwable e) {
                CombinatoricsTest.print("expectedResult", permutationSize, expectedResult);
                CombinatoricsTest.print("actualResult", permutationSize, actualResult);
                throw e;
            }
        }
    }

    /**
     * 測試一個元素最多只能出現次數
     */
    @Test
    public void foreach_mostTimes() {
        int permutationSize = 3;
        int mostTimes = 2; // 這個1就等於不重複排序

        Permutations<String> permutations = Permutations.of("A", "B", "C", "D", "E");

        List<List<String>> expectedResult = new ArrayList<>();
        permutations.foreach(permutationSize, true, (times, permutation) -> {
            Map<String, AtomicInteger> countMap = new HashMap<>();
            permutation.forEach(element -> countMap.computeIfAbsent(element, key -> new AtomicInteger()).incrementAndGet());
            boolean skip = countMap.values().stream().anyMatch(count -> count.get() > mostTimes);
            if (!skip) {
                expectedResult.add(permutation);
            }
        });

        CombinatoricsTest.print("expectedResult", permutationSize, expectedResult);

        List<List<String>> actualResult = new ArrayList<>();
        permutations.foreachMostTimes(permutationSize, mostTimes, (times, permutation) -> {
            actualResult.add(permutation);
        });

        CombinatoricsTest.print("actualResult", permutationSize, actualResult);

        Assertions.assertThat(actualResult).containsExactlyElementsOf(expectedResult);
    }

    @Test
    public void collectLeastTimes() {
        int permutationSize = 4;
        int leastTimes = 2;

        for (int testTimes = 0; testTimes < 10; testTimes++) {
            List<List<String>> expectedResult = Collections.synchronizedList(new ArrayList<>());
            List<List<String>> actualResult = null;
            try {
                Permutations<String> permutations = Permutations.of("A", "B", "C", "D", "E").withParallel();

                permutations.foreachLeastTimes(permutationSize, leastTimes, (times, permutation) -> {
                    expectedResult.add(permutation);
                });
                permutations.waitFinish();

                actualResult = permutations.collectLeastTimes(permutationSize, leastTimes);

                Assertions.assertThat(actualResult).containsExactlyInAnyOrderElementsOf(expectedResult);

            } catch (Throwable e) {
                CombinatoricsTest.print("expectedResult", permutationSize, expectedResult);
                CombinatoricsTest.print("actualResult", permutationSize, actualResult);
                throw e;
            }
        }
    }

    /**
     * 測試一個元素最少只能出現次數
     */
    @Test
    public void foreachLeastTimes() {
        int permutationSize = 4;
        int leastTimes = 2;

        Permutations<String> permutations = Permutations.of("A", "B", "C", "D", "E");

        List<List<String>> expectedResult = new ArrayList<>();
        permutations.foreach(permutationSize, true, (times, permutation) -> {
            Map<String, AtomicInteger> countMap = new HashMap<>();
            permutation.forEach(element -> countMap.computeIfAbsent(element, key -> new AtomicInteger()).incrementAndGet());
            boolean skip = countMap.values().stream().anyMatch(count -> count.get() < leastTimes);
            if (!skip) {
                expectedResult.add(permutation);
            }
        });

        CombinatoricsTest.print("expectedResult", permutationSize, expectedResult);

        List<List<String>> actualResult = new ArrayList<>();
        permutations.foreachLeastTimes(permutationSize, leastTimes, (times, permutation) -> {
            actualResult.add(permutation);
        });

        CombinatoricsTest.print("actualResult", permutationSize, actualResult);

        Assertions.assertThat(actualResult).containsExactlyElementsOf(expectedResult);
    }

    @Test
    public void collectTimes() {
        int permutationSize = 5;
        int mostTimes = 3;
        int leastTimes = 2;

        for (int testTimes = 0; testTimes < 10; testTimes++) {
            List<List<String>> expectedResult = Collections.synchronizedList(new ArrayList<>());
            List<List<String>> actualResult = null;
            try {
                Permutations<String> permutations = Permutations.of("A", "B", "C", "D", "E", "F").withParallel();

                permutations.foreachTimes(permutationSize, mostTimes, leastTimes, (times, permutation) -> {
                    expectedResult.add(permutation);
                });
                permutations.waitFinish();

                actualResult = permutations.collectTimes(permutationSize, mostTimes, leastTimes);

                Assertions.assertThat(actualResult).containsExactlyInAnyOrderElementsOf(expectedResult);
            } catch (Throwable e) {
                CombinatoricsTest.print("expectedResult", permutationSize, expectedResult);
                CombinatoricsTest.print("actualResult", permutationSize, actualResult);
                throw e;
            }
        }
    }

    /**
     * 測試一個元素同時限制"最多只能出現次數"跟"最少只能出現次數"
     */
    @Test
    public void foreachTimes() {
        int permutationSize = 5;
        int mostTimes = 3;
        int leastTimes = 2;

        Permutations<String> permutations = Permutations.of("A", "B", "C", "D", "E", "F");

        List<List<String>> expectedResult = new ArrayList<>();
        permutations.foreach(permutationSize, true, (times, permutation) -> {
            Map<String, AtomicInteger> countMap = new HashMap<>();
            permutation.forEach(element -> countMap.computeIfAbsent(element, key -> new AtomicInteger()).incrementAndGet());
            boolean skip = countMap.values().stream().anyMatch(count -> count.get() < leastTimes || count.get() > mostTimes);
            if (!skip) {
                expectedResult.add(permutation);
            }
        });

        CombinatoricsTest.print("expectedResult", permutationSize, expectedResult);

        List<List<String>> actualResult = new ArrayList<>();
        permutations.foreachTimes(permutationSize, mostTimes, leastTimes, (times, permutation) -> {
            actualResult.add(permutation);
        });

        CombinatoricsTest.print("actualResult", permutationSize, actualResult);

        Assertions.assertThat(actualResult).containsExactlyElementsOf(expectedResult);
    }

    @Test
    public void collectFirst() {
        int permutationSize = 3;
        boolean repeat = false;

        Permutations<String> permutations = Permutations.shuffle("A", "B", "C", "D", "E", "F", "G", "H").withParallel();

        List<List<String>> resultTemp = new ArrayList<>();
        for (int testTimes = 0; testTimes < 20; testTimes++) {

            Optional<List<String>> resultOptional = permutations.collectFirst(permutationSize, repeat, (times, permutation) -> { // filter
                return permutation.get(0).content.equals("A");
            });

            resultTemp.add(resultOptional.get());
        }
        CombinatoricsTest.print("resultTemp", 5, resultTemp);

        for (int testTimes = 0; testTimes < 20; testTimes++) {
            Optional<List<String>> resultOptional = permutations.collectFirst(permutationSize, repeat, (times, permutation) -> { // filter
                return permutation.stream().map(element -> element.content).anyMatch(""::equals);
            });

            Assertions.assertThat(resultOptional.isPresent()).isFalse();
        }
    }

    @Test
    public void collectMostAmount() {
        int permutationSize = 3;
        int collectAmount = 3;
        boolean repeat = false;

        Permutations<String> permutations = Permutations.shuffle("A", "B", "C", "D", "E", "F", "G", "H").withParallel();

        for (int testTimes = 0; testTimes < 20; testTimes++) {
            List<List<String>> result = permutations.collectMostAmount(permutationSize, repeat, collectAmount, (times, permutation) -> { // filter
                return permutation.get(0).content.equals("A");
            });

            CombinatoricsTest.print("result[" + testTimes + "]", 5, result);

            Assertions.assertThat(result.size()).isLessThanOrEqualTo(collectAmount);
        }

        for (int testTimes = 0; testTimes < 20; testTimes++) {
            List<List<String>> result = permutations.collectMostAmount(permutationSize, repeat, collectAmount, (times, permutation) -> { // filter
                return permutation.stream().map(element -> element.content).anyMatch(""::equals);
            });

            Assertions.assertThat(result).isEmpty();
        }
    }

    /**
     * 測試多執行緒併發處理
     */
    @Test
    public void withParallel() {
        int permutationSize = 3;

        Permutations<String> permutations = Permutations.of("A", "B", "C", "D").withParallel();

        List<Thread> threads = new ArrayList<>();
        permutations.foreach(permutationSize, false, (times, permutation) -> { // other thread execute
            threads.add(Thread.currentThread());
            this.logger.info("times: {}, permutation: {}", times, permutation);
            ThreadEx.restRandom(100, 200);
        });

        permutations.waitFinish(); // 這裡要等待fork出去的thread都執行完再往下

        Assertions.assertThat(threads).doesNotContain(Thread.currentThread());
    }

}
