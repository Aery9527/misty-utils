package org.misty.utils.combinatorics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CombinatoricsTest {

    public static void print(String title, int size, List<List<String>> list) {
        System.out.println(title + "(" + list.size() + ")");

        list.stream().reduce(new ArrayList<List<String>>(), (result, foreachResult) -> {
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
                        if (target.size() == size) {
                            target = nextTarget.get();
                        }
                    }

                    target.add(foreachResult.toString());

                    return result;
                }, (result1, result2) -> null)
                .forEach(result -> System.out.println(String.join(" ", result)));
    }

    public static void print(String title, Map<String, List<List<String>>> map) {
        String prefix = title + "(" + map.size() + ")";
        map.forEach((key, value) -> print(prefix + ":key{" + key + "}", 5, value));
    }

}
