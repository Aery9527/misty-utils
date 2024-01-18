package org.misty.utils.collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ListElementTest {

    @Test
    public void boxing() {
        List<String> list = Arrays.asList("A", "B", "C");
        List<ListElement<String>> boxingList = ListElement.boxing(list);
        boxingList.forEach(System.out::println);

        IntStream.range(0, list.size()).forEach(index -> {
            Assertions.assertThat(boxingList.get(index).index).isEqualTo(index);
            Assertions.assertThat(boxingList.get(index).content).isEqualTo(list.get(index));
        });
    }

    @Test
    public void unboxing() {
        List<String> list = Arrays.asList("A", "B", "C");
        List<ListElement<String>> boxingList = ListElement.boxing(list);
        List<String> unboxingList = ListElement.unboxing(boxingList);

        Assertions.assertThat(unboxingList).containsExactlyElementsOf(list);
    }

}
