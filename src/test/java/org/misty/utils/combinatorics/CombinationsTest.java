package org.misty.utils.combinatorics;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty.utils.ex.ThreadEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CombinationsTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 測試重複組合數
     */
    @Test
    public void numberOfRepeat() {
        Assertions.assertThat(Combinations.numberOfRepeat(5, 3)).isEqualTo(35);
        Assertions.assertThat(Combinations.numberOfRepeat(9, 5)).isEqualTo(1287);

        Assertions.assertThat(Combinations.of(1, 2, 3, 4, 5).numberOfRepeat(3)).isEqualTo(35);
        Assertions.assertThat(Combinations.of(1, 2, 3, 4, 5, 6, 7, 8, 9).numberOfRepeat(5)).isEqualTo(1287);
    }

    /**
     * 測試不重複組合數
     */
    @Test
    public void numberOfUnique() {
        Assertions.assertThat(Combinations.numberOfUnique(5, 3)).isEqualTo(10);
        Assertions.assertThat(Combinations.numberOfUnique(9, 5)).isEqualTo(126);

        Assertions.assertThat(Combinations.of(1, 2, 3, 4, 5).numberOfUnique(3)).isEqualTo(10);
        Assertions.assertThat(Combinations.of(1, 2, 3, 4, 5, 6, 7, 8, 9).numberOfUnique(5)).isEqualTo(126);
    }

    /**
     * 測試建構
     */
    @Test
    public void of() {
        {
            Combinations<String> combinations = Combinations.of("A", "B", "C");
            Assertions.assertThat(combinations.getElements()).containsExactly("A", "B", "C");
            Assertions.assertThat(combinations.isShuffle()).isEqualTo(false);
        }

        {
            Combinations<String> combinations = Combinations.shuffle("A", "B", "C");
            Assertions.assertThat(combinations.getElements()).containsExactlyInAnyOrder("A", "B", "C");
            Assertions.assertThat(combinations.isShuffle()).isEqualTo(true);
        }

        {
            List<String> elements = new ArrayList<>(Arrays.asList("A", "B", "C"));
            Combinations<String> combinations = Combinations.of(elements);
            Assertions.assertThat(combinations.getElements()).containsExactly("A", "B", "C");
            Assertions.assertThat(combinations.getElements() == elements).isFalse();
            Assertions.assertThat(combinations.isShuffle()).isEqualTo(false);

            elements.add("D");
            Assertions.assertThat(combinations.getElements()).containsExactly("A", "B", "C");
        }

        {
            Set<String> elements = new HashSet<>(Arrays.asList("A", "B", "C"));
            Combinations<String> combinations = Combinations.shuffle(elements);
            Assertions.assertThat(combinations.getElements()).containsExactlyInAnyOrder("A", "B", "C");
            Assertions.assertThat(combinations.isShuffle()).isEqualTo(true);
        }
    }

    /**
     * 測試不中斷
     */
    @Test
    public void foreach_continue() {
        int combinationSize = 2;
        boolean repeat = true;

        Combinations<String> combinations = Combinations.of("A", "B", "C");
        List<List<String>> temp = new ArrayList<>();
        boolean flag = combinations.foreach(combinationSize, repeat, (times, combination) -> {
            temp.add(combination);
            return Combinatorics.FOREACH_CONTINUE;
        });

        Assertions.assertThat(temp.size()).isEqualTo(Combinations.numberOf(combinations.getElements().size(), combinationSize, repeat));
        Assertions.assertThat(flag).isEqualTo(Combinatorics.FOREACH_CONTINUE);
    }

    /**
     * 測試中斷
     */
    @Test
    public void foreach_break() {
        int combinationSize = 2;
        boolean repeat = true;
        int testCount = 2;

        Combinations<String> combinations = Combinations.of("A", "B", "C");
        List<List<String>> temp = new ArrayList<>();
        boolean flag = combinations.foreach(combinationSize, repeat, (times, combination) -> {
            temp.add(combination);
            return temp.size() < testCount ? Combinatorics.FOREACH_CONTINUE : Combinatorics.FOREACH_BREAK;
        });

        Assertions.assertThat(temp.size()).isEqualTo(testCount);
        Assertions.assertThat(flag).isEqualTo(Combinatorics.FOREACH_BREAK);
    }

    @Test
    public void collect_repeat() {
        int combinationSize = 2;
        boolean repeat = true;

        for (int testTimes = 0; testTimes < 10; testTimes++) {
            List<List<String>> expectedResult = Collections.synchronizedList(new ArrayList<>());
            List<List<String>> actualResult = null;
            try {
                Combinations<String> combinations = Combinations.of("A", "B", "C").withParallel();

                combinations.foreach(combinationSize, repeat, (times, combination) -> {
                    expectedResult.add(combination);
                });
                combinations.waitFinish();

                actualResult = combinations.collect(combinationSize, repeat);

                Assertions.assertThat(actualResult).containsExactlyInAnyOrderElementsOf(expectedResult);
            } catch (Throwable e) {
                CombinatoricsTest.print("expectedResult", combinationSize, expectedResult);
                CombinatoricsTest.print("actualResult", combinationSize, actualResult);
                throw e;
            }
        }
    }

    /**
     * 測試重複出現的組合
     */
    @Test
    public void foreach_repeat() {
        int combinationSize = 2;
        boolean repeat = true;

        Combinations<String> combinations = Combinations.of("A", "B", "C");
        List<List<String>> temp = new ArrayList<>();
        combinations.foreach(combinationSize, repeat, (times, combination) -> {
            temp.add(combination);
        });

        int index = 0;
        Assertions.assertThat(temp.get(index++)).containsExactly("A", "A");
        Assertions.assertThat(temp.get(index++)).containsExactly("A", "B");
        Assertions.assertThat(temp.get(index++)).containsExactly("A", "C");
        Assertions.assertThat(temp.get(index++)).containsExactly("B", "B");
        Assertions.assertThat(temp.get(index++)).containsExactly("B", "C");
        Assertions.assertThat(temp.get(index++)).containsExactly("C", "C");

        Assertions.assertThat(temp.size()).isEqualTo(Combinations.numberOf(combinations.getElements().size(), combinationSize, repeat));
    }

    @Test
    public void collect_unique() {
        int combinationSize = 2;
        boolean repeat = false;

        for (int testTimes = 0; testTimes < 10; testTimes++) {
            List<List<String>> expectedResult = Collections.synchronizedList(new ArrayList<>());
            List<List<String>> actualResult = null;
            try {
                Combinations<String> combinations = Combinations.of("A", "B", "C").withParallel();

                combinations.foreach(combinationSize, repeat, (times, combination) -> {
                    expectedResult.add(combination);
                });
                combinations.waitFinish();

                actualResult = combinations.collect(combinationSize, repeat);

                Assertions.assertThat(actualResult).containsExactlyInAnyOrderElementsOf(expectedResult);
            } catch (Throwable e) {
                CombinatoricsTest.print("expectedResult", combinationSize, expectedResult);
                CombinatoricsTest.print("actualResult", combinationSize, actualResult);
                throw e;
            }
        }
    }

    /**
     * 測試不重複出現的組合
     */
    @Test
    public void foreach_unique() {
        int combinationSize = 2;
        boolean repeat = false;

        Combinations<String> combinations = Combinations.of("A", "B", "C");
        List<List<String>> temp = new ArrayList<>();
        combinations.foreach(combinationSize, repeat, (times, combination) -> {
            temp.add(combination);
        });

        int index = 0;
        Assertions.assertThat(temp.get(index++)).containsExactly("A", "B");
        Assertions.assertThat(temp.get(index++)).containsExactly("A", "C");
        Assertions.assertThat(temp.get(index++)).containsExactly("B", "C");

        Assertions.assertThat(temp.size()).isEqualTo(Combinations.numberOf(combinations.getElements().size(), combinationSize, repeat));
    }

    /**
     * 測試過濾器
     */
    @Test
    public void foreach_filter() {
        Combinations<String> combinations = Combinations.of("A", "B", "C");
        List<List<String>> temp = new ArrayList<>();
        combinations.foreach(2, true, (times, combination) -> { // filter
            return combination.get(1).content.equals("B");
        }, (times, combination) -> { // tester
            temp.add(combination);
        });

        int index = 0;
        Assertions.assertThat(temp.get(index++)).containsExactly("A", "B");
        Assertions.assertThat(temp.get(index++)).containsExactly("B", "B");
    }

    @Test
    public void collectMostTimes() {
        int combinationSize = 3;
        int mostTimes = 2;

        for (int testTimes = 0; testTimes < 10; testTimes++) {
            List<List<String>> expectedResult = Collections.synchronizedList(new ArrayList<>());
            List<List<String>> actualResult = null;
            try {
                Combinations<String> combinations = Combinations.of("A", "B", "C", "D", "E").withParallel();

                combinations.foreachMostTimes(combinationSize, mostTimes, (times, combination) -> {
                    expectedResult.add(combination);
                });
                combinations.waitFinish();

                actualResult = combinations.collectMostTimes(combinationSize, mostTimes);

                Assertions.assertThat(actualResult).containsExactlyInAnyOrderElementsOf(expectedResult);
            } catch (Throwable e) {
                CombinatoricsTest.print("expectedResult", combinationSize, expectedResult);
                CombinatoricsTest.print("actualResult", combinationSize, actualResult);
                throw e;
            }
        }
    }

    /**
     * 測試一個元素最多只能出現次數
     */
    @Test
    public void foreach_mostTimes() {
        int combinationSize = 3;
        int mostTimes = 2; // 這個1就等於不重複組合

        Combinations<String> combinations = Combinations.of("A", "B", "C", "D", "E");

        List<List<String>> expectedResult = new ArrayList<>();
        combinations.foreach(combinationSize, true, (times, combination) -> {
            Map<String, AtomicInteger> countMap = new HashMap<>();
            combination.forEach(element -> countMap.computeIfAbsent(element, key -> new AtomicInteger()).incrementAndGet());
            boolean skip = countMap.values().stream().anyMatch(count -> count.get() > mostTimes);
            if (!skip) {
                expectedResult.add(combination);
            }
        });

        CombinatoricsTest.print("expectedResult", combinationSize, expectedResult);

        List<List<String>> actualResult = new ArrayList<>();
        combinations.foreachMostTimes(combinationSize, mostTimes, (times, combination) -> {
            actualResult.add(combination);
        });

        CombinatoricsTest.print("actualResult", combinationSize, actualResult);

        Assertions.assertThat(actualResult).containsExactlyElementsOf(expectedResult);
    }

    @Test
    public void collectLeastTimes() {
        int combinationSize = 4;
        int leastTimes = 2;

        for (int testTimes = 0; testTimes < 10; testTimes++) {
            List<List<String>> expectedResult = Collections.synchronizedList(new ArrayList<>());
            List<List<String>> actualResult = null;
            try {
                Combinations<String> combinations = Combinations.of("A", "B", "C", "D", "E").withParallel();

                combinations.foreachLeastTimes(combinationSize, leastTimes, (times, combination) -> {
                    expectedResult.add(combination);
                });
                combinations.waitFinish();

                actualResult = combinations.collectLeastTimes(combinationSize, leastTimes);

                Assertions.assertThat(actualResult).containsExactlyInAnyOrderElementsOf(expectedResult);

            } catch (Throwable e) {
                CombinatoricsTest.print("expectedResult", combinationSize, expectedResult);
                CombinatoricsTest.print("actualResult", combinationSize, actualResult);
                throw e;
            }
        }
    }

    /**
     * 測試一個元素最少只能出現次數
     */
    @Test
    public void foreachLeastTimes() {
        int combinationSize = 4;
        int leastTimes = 2;

        Combinations<String> combinations = Combinations.of("A", "B", "C", "D", "E");

        List<List<String>> expectedResult = new ArrayList<>();
        combinations.foreach(combinationSize, true, (times, combination) -> {
            Map<String, AtomicInteger> countMap = new HashMap<>();
            combination.forEach(element -> countMap.computeIfAbsent(element, key -> new AtomicInteger()).incrementAndGet());
            boolean skip = countMap.values().stream().anyMatch(count -> count.get() < leastTimes);
            if (!skip) {
                expectedResult.add(combination);
            }
        });

        CombinatoricsTest.print("expectedResult", combinationSize, expectedResult);

        List<List<String>> actualResult = new ArrayList<>();
        combinations.foreachLeastTimes(combinationSize, leastTimes, (times, combination) -> {
            actualResult.add(combination);
        });

        CombinatoricsTest.print("actualResult", combinationSize, actualResult);

        Assertions.assertThat(actualResult).containsExactlyElementsOf(expectedResult);
    }

    @Test
    public void collectTimes() {
        int combinationSize = 5;
        int mostTimes = 3;
        int leastTimes = 2;

        for (int testTimes = 0; testTimes < 10; testTimes++) {
            List<List<String>> expectedResult = Collections.synchronizedList(new ArrayList<>());
            List<List<String>> actualResult = null;
            try {
                Combinations<String> combinations = Combinations.of("A", "B", "C", "D", "E", "F").withParallel();

                combinations.foreachTimes(combinationSize, mostTimes, leastTimes, (times, combination) -> {
                    expectedResult.add(combination);
                });
                combinations.waitFinish();

                actualResult = combinations.collectTimes(combinationSize, mostTimes, leastTimes);

                Assertions.assertThat(actualResult).containsExactlyInAnyOrderElementsOf(expectedResult);
            } catch (Throwable e) {
                CombinatoricsTest.print("expectedResult", combinationSize, expectedResult);
                CombinatoricsTest.print("actualResult", combinationSize, actualResult);
                throw e;
            }
        }
    }

    /**
     * 測試一個元素同時限制"最多只能出現次數"跟"最少只能出現次數"
     */
    @Test
    public void foreachTimes() {
        int combinationSize = 5;
        int mostTimes = 3;
        int leastTimes = 2;

        Combinations<String> combinations = Combinations.of("A", "B", "C", "D", "E", "F");

        List<List<String>> expectedResult = new ArrayList<>();
        combinations.foreach(combinationSize, true, (times, combination) -> {
            Map<String, AtomicInteger> countMap = new HashMap<>();
            combination.forEach(element -> countMap.computeIfAbsent(element, key -> new AtomicInteger()).incrementAndGet());
            boolean skip = countMap.values().stream().anyMatch(count -> count.get() < leastTimes || count.get() > mostTimes);
            if (!skip) {
                expectedResult.add(combination);
            }
        });

        CombinatoricsTest.print("expectedResult", combinationSize, expectedResult);

        List<List<String>> actualResult = new ArrayList<>();
        combinations.foreachTimes(combinationSize, mostTimes, leastTimes, (times, combination) -> {
            actualResult.add(combination);
        });

        CombinatoricsTest.print("actualResult", combinationSize, actualResult);

        Assertions.assertThat(actualResult).containsExactlyElementsOf(expectedResult);
    }

    @Test
    public void collectFirst() {
        int combinationSize = 3;
        boolean repeat = false;

        Combinations<String> combinations = Combinations.shuffle("A", "B", "C", "D", "E", "F", "G", "H").withParallel();

        List<List<String>> resultTemp = new ArrayList<>();
        for (int testTimes = 0; testTimes < 20; testTimes++) {
            Optional<List<String>> resultOptional = combinations.collectFirst(combinationSize, repeat, (times, combination) -> { // filter
                return combination.stream().map(element -> element.content).anyMatch("A"::equals);
            });

            resultTemp.add(resultOptional.get());
        }
        CombinatoricsTest.print("resultTemp", 5, resultTemp);

        for (int testTimes = 0; testTimes < 20; testTimes++) {
            Optional<List<String>> resultOptional = combinations.collectFirst(combinationSize, repeat, (times, combination) -> { // filter
                return combination.stream().map(element -> element.content).anyMatch(""::equals);
            });

            Assertions.assertThat(resultOptional.isPresent()).isFalse();
        }
    }

    @Test
    public void collectMostNumber() {
        int combinationSize = 3;
        int collectNumber = 3;
        boolean repeat = false;

        Combinations<String> combinations = Combinations.shuffle("A", "B", "C", "D", "E", "F", "G", "H").withParallel();

        for (int testTimes = 0; testTimes < 20; testTimes++) {
            List<List<String>> result = combinations.collectMostNumber(combinationSize, repeat, collectNumber, (times, combination) -> { // filter
                return combination.stream().map(element -> element.content).anyMatch("A"::equals);
            });

            CombinatoricsTest.print("result[" + testTimes + "]", 5, result);

            Assertions.assertThat(result.size()).isLessThanOrEqualTo(collectNumber);
        }

        for (int testTimes = 0; testTimes < 20; testTimes++) {
            List<List<String>> result = combinations.collectMostNumber(combinationSize, repeat, collectNumber, (times, combination) -> { // filter
                return combination.stream().map(element -> element.content).anyMatch(""::equals);
            });

            Assertions.assertThat(result).isEmpty();
        }
    }

    /**
     * 測試多執行緒併發處理
     */
    @Test
    public void withParallel() {
        int combinationSize = 3;

        Combinations<String> combinations = Combinations.of("A", "B", "C", "D").withParallel();

        List<Thread> threads = new ArrayList<>();
        combinations.foreach(combinationSize, false, (times, combination) -> { // other thread execute
            threads.add(Thread.currentThread());
            this.logger.info("times: {}, combination: {}", times, combination);
            ThreadEx.restRandom(100, 200);
        });

        combinations.waitFinish(); // 這裡要等待fork出去的thread都執行完再往下

        Assertions.assertThat(threads).doesNotContain(Thread.currentThread());
    }

}
