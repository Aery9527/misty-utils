package org.misty.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty.utils.ex.ThreadEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

class CombinationsTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
     * 測試重複組合數
     */
    @Test
    public void numberOfRepeat() {
        Assertions.assertThat(Combinations.numberOfRepeat(5, 3)).isEqualTo(35);
        Assertions.assertThat(Combinations.numberOfRepeat(9, 5)).isEqualTo(1287);
    }

    /**
     * 測試不重複組合數
     */
    @Test
    public void numberOfUnique() {
        Assertions.assertThat(Combinations.numberOfUnique(5, 3)).isEqualTo(10);
        Assertions.assertThat(Combinations.numberOfUnique(9, 5)).isEqualTo(126);
    }

    /**
     * 測試不中斷
     */
    @Test
    public void foreach_continue() {
        Combinations<String> combinations = Combinations.of("A", "B", "C");
        List<List<String>> temp = new ArrayList<>();
        boolean flag = combinations.foreach(2, true, (times, combination) -> {
            temp.add(combination);
            return Combinations.FOREACH_CONTINUE;
        });

        Assertions.assertThat(temp.size()).isEqualTo(6);
        Assertions.assertThat(flag).isEqualTo(Combinations.FOREACH_CONTINUE);
    }

    /**
     * 測試中斷
     */
    @Test
    public void foreach_break() {
        int testCount = 2;

        Combinations<String> combinations = Combinations.of("A", "B", "C");
        List<List<String>> temp = new ArrayList<>();
        boolean flag = combinations.foreach(2, true, (times, combination) -> {
            temp.add(combination);
            return temp.size() < testCount ? Combinations.FOREACH_CONTINUE : Combinations.FOREACH_BREAK;
        });

        Assertions.assertThat(temp.size()).isEqualTo(testCount);
        Assertions.assertThat(flag).isEqualTo(Combinations.FOREACH_BREAK);
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

        Assertions.assertThat(temp.size()).isEqualTo(Combinations.numberOfRepeat(combinations.getElements().size(), combinationSize));
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

        Assertions.assertThat(temp.size()).isEqualTo(Combinations.numberOfUnique(combinations.getElements().size(), combinationSize));
    }

    /**
     * 測試過濾器
     */
    @Test
    public void foreach_filter() {
        Combinations<String> combinations = Combinations.of("A", "B", "C");
        List<List<String>> temp = new ArrayList<>();
        combinations.foreach(2, (times, combination) -> { // filter
            return combination.get(1).content.equals("B");
        }, (times, combination) -> { // tester
            temp.add(combination);
        });

        int index = 0;
        Assertions.assertThat(temp.get(index++)).containsExactly("A", "B");
        Assertions.assertThat(temp.get(index++)).containsExactly("B", "B");
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

        printCombinations("expectedResult", combinationSize, expectedResult);

        List<List<String>> actualResult = new ArrayList<>();
        combinations.foreachMostTimes(combinationSize, mostTimes, (times, combination) -> {
            actualResult.add(combination);
        });

        printCombinations("actualResult", combinationSize, actualResult);

        Assertions.assertThat(actualResult).containsExactlyElementsOf(expectedResult);
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

        printCombinations("expectedResult", combinationSize, expectedResult);

        List<List<String>> actualResult = new ArrayList<>();
        combinations.foreachLeastTimes(combinationSize, leastTimes, (times, combination) -> {
            actualResult.add(combination);
        });

        printCombinations("actualResult", combinationSize, actualResult);

        Assertions.assertThat(actualResult).containsExactlyElementsOf(expectedResult);
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

        printCombinations("expectedResult", combinationSize, expectedResult);

        List<List<String>> actualResult = new ArrayList<>();
        combinations.foreachTimes(combinationSize, mostTimes, leastTimes, (times, combination) -> {
            actualResult.add(combination);
        });

        printCombinations("actualResult", combinationSize, actualResult);

        Assertions.assertThat(actualResult).containsExactlyElementsOf(expectedResult);
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

    private void printCombinations(String title, int combinationSize, List<List<String>> list) {
        System.out.println(title + "(" + list.size() + "):");

        list.stream().reduce(new ArrayList<List<String>>(), (result, combination) -> {
                    Supplier<List<String>> nextTarget = () -> {
                        List<String> target = new ArrayList<>();
                        result.add(target);
                        return target;
                    };

                    List<String> target;
                    if (result.isEmpty()) {
                        target = nextTarget.get();
                    } else {
                        target = result.get(result.size() - 1);
                        if (target.size() == combinationSize) {
                            target = nextTarget.get();
                        }
                    }

                    target.add(combination.toString());

                    return result;
                }, (result1, result2) -> null)
                .forEach(result -> System.out.println(String.join(" ", result)));
        System.out.println();
    }

}
