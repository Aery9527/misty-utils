package org.misty.utils.combinatorics;

import org.misty.utils.Tracked;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CombinatoricsSorter<KeyType, ElementType> {

    private interface SortMap<KeyType, ElementType> extends Map<KeyType, List<List<ElementType>>> {
        void join(KeyType key, List<ElementType> result);

        Tracked getTracked();

        default SortMap<KeyType, ElementType> fillMissing(Set<KeyType> keys) {
            keys.forEach(key -> putIfAbsent(key, Collections.emptyList()));
            return this;
        }

        default Map<KeyType, List<List<ElementType>>> toMap() {
            return Collections.unmodifiableMap(
                    entrySet().stream().collect(
                            Collectors.toMap(Map.Entry::getKey,
                                    entry -> Collections.unmodifiableList(entry.getValue()),
                                    (u, v) -> {
                                        String errorMsg = getTracked().say("Duplicate key %s", u);
                                        throw new IllegalStateException(errorMsg);
                                    },
                                    LinkedHashMap::new)
                    )
            );
        }
    }

    private class SyncSortMap extends HashMap<KeyType, List<List<ElementType>>> implements SortMap<KeyType, ElementType> {
        @Override
        public void join(KeyType key, List<ElementType> result) {
            super.computeIfAbsent(key, k -> new ArrayList<>(DEFAULT_SORT_LIST_SIZE)).add(result);
        }

        @Override
        public Tracked getTracked() {
            return tracked;
        }
    }

    private class AsyncSortMap extends ConcurrentHashMap<KeyType, List<List<ElementType>>> implements SortMap<KeyType, ElementType> {
        @Override
        public void join(KeyType key, List<ElementType> result) {
            super.computeIfAbsent(key, k -> Collections.synchronizedList(new ArrayList<>(DEFAULT_SORT_LIST_SIZE))).add(result);
        }

        @Override
        public Tracked getTracked() {
            return tracked;
        }
    }

    private static final int DEFAULT_SORT_LIST_SIZE = 8;

    private final Tracked tracked;

    private final Combinatorics<ElementType, ?> combinatorics;

    private Map<KeyType, BiPredicate<Long, List<ElementType>>> filterMap;

    /**
     * 是否允許覆蓋已存在的分類器
     */
    private boolean allowFilterCover = false;

    public CombinatoricsSorter(Combinatorics<ElementType, ?> combinatorics) {
        this.tracked = combinatorics.getTracked().link("sorter");
        this.combinatorics = combinatorics;
        this.filterMap = new LinkedHashMap<>();
    }

    public CombinatoricsSorter(Combinatorics<ElementType, ?> combinatorics, String name) {
        this.tracked = combinatorics.getTracked().linkWithRandomId(name);
        this.combinatorics = combinatorics;
        this.filterMap = new LinkedHashMap<>();
    }

    public CombinatoricsSorter(Combinatorics<ElementType, ?> combinatorics, Map<KeyType, BiPredicate<Long, List<ElementType>>> filterMap) {
        this.tracked = combinatorics.getTracked().link("sorter");
        this.combinatorics = combinatorics;
        this.filterMap = filterMap;
    }

    public CombinatoricsSorter(Combinatorics<ElementType, ?> combinatorics, String name, Map<KeyType, BiPredicate<Long, List<ElementType>>> filterMap) {
        this.tracked = combinatorics.getTracked().linkWithRandomId(name);
        this.combinatorics = combinatorics;
        this.filterMap = filterMap;
    }

    public CombinatoricsSorter<KeyType, ElementType> addFilter(KeyType key, Predicate<List<ElementType>> filter) {
        return addFilter(key, (target, elements) -> filter.test(elements));
    }

    public CombinatoricsSorter<KeyType, ElementType> addFilter(KeyType key, BiPredicate<Long, List<ElementType>> filter) {
        BiPredicate<Long, List<ElementType>> old = this.filterMap.put(key, filter);
        if (!this.allowFilterCover && old != null) {
            String errorMsg = this.tracked.say("filter key(%s) already exists", key);
            throw new IllegalStateException(errorMsg);
        }
        return this;
    }

    /**
     * 同 {@link #sortInAll(int, boolean)}, 只是多了一個將沒有任何filter匹配的排列/組合收集到unsortList中的功能
     */
    public Map<KeyType, List<List<ElementType>>> sortInAll(int size,
                                                           boolean repeat,
                                                           List<List<ElementType>> unsortList) {
        SortMap<KeyType, ElementType> sortResult = buildSortUsedMap();

        List<List<ElementType>> unsortListWrap;
        if (this.combinatorics.getTaskCountExecutor().isParallelMode()) {
            unsortListWrap = Collections.synchronizedList(unsortList);
        } else {
            unsortListWrap = unsortList;
        }

        this.combinatorics.foreach(size, repeat, (times, resultTemp) -> {
            AtomicBoolean isMatch = new AtomicBoolean(false);
            this.filterMap.entrySet().stream()
                    .filter(entry -> entry.getValue().test(times, resultTemp))
                    .map(Map.Entry::getKey)
                    .peek(key -> isMatch.set(true))
                    .forEach(key -> sortResult.join(key, resultTemp));
            if (!isMatch.get()) {
                unsortListWrap.add(resultTemp);
            }
        });
        this.combinatorics.waitFinish();

        return sortResult.fillMissing(this.filterMap.keySet()).toMap();
    }

    /**
     * 將排列/組合依照filterMap條件分類, 一個排列/組合可以分類到多個key中, 若只能分類到一個key中請使用 {@link #sortInFirst(int, boolean)}
     *
     * @param size   排列/組合數量(k)
     * @param repeat 同個元素是否可以重複出現
     * @return 分類結果, key為分類名稱, value為符合該分類的排列/組合
     */
    public Map<KeyType, List<List<ElementType>>> sortInAll(int size, boolean repeat) {
        SortMap<KeyType, ElementType> sortResult = buildSortUsedMap();

        this.combinatorics.foreach(size, repeat, (times, resultTemp) -> {
            this.filterMap.entrySet().stream()
                    .filter(entry -> entry.getValue().test(times, resultTemp))
                    .map(Map.Entry::getKey)
                    .forEach(key -> sortResult.join(key, resultTemp));
        });
        this.combinatorics.waitFinish();

        return sortResult.fillMissing(this.filterMap.keySet()).toMap();
    }

    /**
     * 同 {@link #sortInFirst(int, boolean)}, 只是多了一個將沒有任何filter匹配的排列/組合收集到unsortList中的功能
     */
    public Map<KeyType, List<List<ElementType>>> sortInFirst(int size,
                                                             boolean repeat,
                                                             List<List<ElementType>> unsortList) {
        SortMap<KeyType, ElementType> sortResult = buildSortUsedMap();

        List<List<ElementType>> unsortListWrap;
        if (this.combinatorics.getTaskCountExecutor().isParallelMode()) {
            unsortListWrap = Collections.synchronizedList(unsortList);
        } else {
            unsortListWrap = unsortList;
        }

        this.combinatorics.foreach(size, repeat, (times, resultTemp) -> {
            AtomicBoolean isMatch = new AtomicBoolean(false);
            this.filterMap.entrySet().stream()
                    .filter(entry -> entry.getValue().test(times, resultTemp))
                    .map(Map.Entry::getKey)
                    .peek(key -> isMatch.set(true))
                    .findFirst()
                    .ifPresent(key -> sortResult.join(key, resultTemp));
            if (!isMatch.get()) {
                unsortListWrap.add(resultTemp);
            }
        });
        this.combinatorics.waitFinish();

        return sortResult.fillMissing(this.filterMap.keySet()).toMap();
    }

    /**
     * 將排列/組合依照filterMap條件分類, 一個排列/組合只會分類到一個key中, 若想要分類到多個key中請使用 {@link #sortInAll(int, boolean)}
     *
     * @param size   排列/組合數量(k)
     * @param repeat 同個元素是否可以重複出現
     * @return 分類結果, key為分類名稱, value為符合該分類的排列/組合
     */
    public Map<KeyType, List<List<ElementType>>> sortInFirst(int size, boolean repeat) {
        SortMap<KeyType, ElementType> sortResult = buildSortUsedMap();

        this.combinatorics.foreach(size, repeat, (times, resultTemp) -> {
            this.filterMap.entrySet().stream()
                    .filter(entry -> entry.getValue().test(times, resultTemp))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .ifPresent(key -> sortResult.join(key, resultTemp));
        });
        this.combinatorics.waitFinish();

        return sortResult.fillMissing(this.filterMap.keySet()).toMap();
    }

    private SortMap<KeyType, ElementType> buildSortUsedMap() {
        return this.combinatorics.getTaskCountExecutor().isParallelMode() ? new AsyncSortMap() : new SyncSortMap();
    }

    public boolean isAllowFilterCover() {
        return allowFilterCover;
    }

    public void setAllowFilterCover(boolean allowFilterCover) {
        this.allowFilterCover = allowFilterCover;
    }

    public Map<KeyType, BiPredicate<Long, List<ElementType>>> getFilterMap() {
        return filterMap;
    }

    public CombinatoricsSorter<KeyType, ElementType> setFilterMap(Map<KeyType, BiPredicate<Long, List<ElementType>>> filterMap) {
        this.filterMap = filterMap;
        return this;
    }

}
