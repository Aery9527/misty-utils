package org.misty.utils.combinatorics;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

class CombinatoricsSorterTest {

    interface SortCollectAction {
        boolean collect(long times, List<String> result, Map<String, BiPredicate<Long, List<String>>> sortMap, Map<String, List<List<String>>> expectedMap);
    }

    @Test
    public void constructor() {
        CombinatoricsSorter<String, String> sorter1 = new CombinatoricsSorter<>(Combinations.of());
        Assertions.assertThat(sorter1.getFilterMap()).isExactlyInstanceOf(LinkedHashMap.class);

        Map<String, BiPredicate<Long, List<String>>> filterMap = new HashMap<>();
        CombinatoricsSorter<String, String> sorter2 = new CombinatoricsSorter<>(Permutations.of(), filterMap);
        Assertions.assertThat(sorter2.getFilterMap() == filterMap).isTrue();
    }

    @Test
    public void addFilter() {
        String key1 = "key1";
        BiPredicate<Long, List<String>> filter1 = (times, elements) -> true;
        String key2 = "key2";
        BiPredicate<Long, List<String>> filter2 = (times, elements) -> true;
        String key3 = "key3";
        BiPredicate<Long, List<String>> filter3 = (times, elements) -> true;

        CombinatoricsSorter<String, String> sorter = new CombinatoricsSorter<>(Combinations.of());
        sorter.addFilter(key1, filter1);
        sorter.addFilter(key2, filter2);
        sorter.addFilter(key3, filter3);

        Map<String, BiPredicate<Long, List<String>>> filterMap = sorter.getFilterMap();
        Assertions.assertThat(filterMap).hasSize(3);
        Assertions.assertThat(filterMap.get(key1) == filter1).isTrue();
        Assertions.assertThat(filterMap.get(key2) == filter2).isTrue();
        Assertions.assertThat(filterMap.get(key3) == filter3).isTrue();

        AssertionsEx.awareThrown(() -> sorter.addFilter(key1, filter1)).isInstanceOf(IllegalStateException.class);

        sorter.setAllowFilterCover(true);
        sorter.addFilter(key1, filter1);
    }

    @Test
    public void sortInAll() {
        testSort((times, result, sortMap, expectedMap) -> {
            AtomicBoolean isMatch = new AtomicBoolean(false);
            sortMap.forEach((key, filter) -> {
                if (filter.test(times, result)) {
                    expectedMap.get(key).add(result);
                    isMatch.set(true);
                }
            });
            return isMatch.get();
        }, true);
    }

    @Test
    public void sortInFirst() {
        testSort((times, result, sortMap, expectedMap) -> {
            Iterator<Map.Entry<String, BiPredicate<Long, List<String>>>> iterator = sortMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, BiPredicate<Long, List<String>>> entry = iterator.next();
                String key = entry.getKey();
                BiPredicate<Long, List<String>> filter = entry.getValue();

                if (filter.test(times, result)) {
                    expectedMap.get(key).add(result);
                    return true;
                }
            }
            return false;
        }, false);
    }

    private void testSort(SortCollectAction collectAction, boolean sortInAll) {
        Consumer<Combinatorics<String, ?>> tester = combinatorics -> {
            String term = combinatorics.getClass().getSimpleName();
            int size = 3; // combinationSize or permutationSize
            boolean repeat = true;

            String containsA = "containsA";
            String containsAorB = "containsAorB";
            String empty = "empty";
            Map<String, BiPredicate<Long, List<String>>> sortMap = new LinkedHashMap<>();
            sortMap.put(containsA, (times, combination) -> combination.contains("A"));
            sortMap.put(containsAorB, (times, combination) -> combination.contains("A") || combination.contains("B"));
            sortMap.put(empty, (times, combination) -> false);
            List<List<String>> unsortList = new ArrayList<>();

            Map<String, List<List<String>>> expectedMap = new LinkedHashMap<>();
            sortMap.keySet().forEach(key -> expectedMap.put(key, new ArrayList<>()));
            combinatorics.foreach(size, repeat, (times, result) -> {
                boolean isMatch = collectAction.collect(times, result, sortMap, expectedMap);
                if (!isMatch) {
                    unsortList.add(result);
                }
            });

            CombinatoricsTest.print("<" + term + "> expectedMap", expectedMap);
            CombinatoricsTest.print("<" + term + "> unsortList", 5, unsortList);
            System.out.println();

            Consumer<Boolean> testAction = testUnsort -> {
                CombinatoricsSorter<String, String> sorter = combinatorics.sorter(sortMap);

                List<List<String>> actualUnsortList = testUnsort ? new ArrayList<>() : null;
                Map<String, List<List<String>>> actualMap;
                if (testUnsort) {
                    if (sortInAll) {
                        actualMap = sorter.sortInAll(size, repeat, actualUnsortList);
                    } else {
                        actualMap = sorter.sortInFirst(size, repeat, actualUnsortList);
                    }
                } else {
                    if (sortInAll) {
                        actualMap = sorter.sortInAll(size, repeat);
                    } else {
                        actualMap = sorter.sortInFirst(size, repeat);
                    }
                }

                Assertions.assertThat(actualMap.get(containsA)).containsExactlyInAnyOrderElementsOf(expectedMap.get(containsA));
                Assertions.assertThat(actualMap.get(containsAorB)).containsExactlyInAnyOrderElementsOf(expectedMap.get(containsAorB));
                Assertions.assertThat(actualMap.get(empty)).containsExactlyInAnyOrderElementsOf(expectedMap.get(empty));

                if (testUnsort) {
                    Assertions.assertThat(actualUnsortList).containsExactlyInAnyOrderElementsOf(unsortList);
                }
            };

            combinatorics.withSerial();
            testAction.accept(true);
            testAction.accept(false);

            combinatorics.withParallel();
            testAction.accept(true);
            testAction.accept(false);
        };

        tester.accept(Combinations.of("A", "B", "C", "D", "E"));
        tester.accept(Permutations.of("A", "B", "C", "D", "E"));
    }

}
