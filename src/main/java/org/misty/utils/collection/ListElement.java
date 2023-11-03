package org.misty.utils.collection;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListElement<ContentType> {

    public static <ContentType> List<ListElement<ContentType>> boxing(List<ContentType> list) {
        int listSize = list.size();
        return Collections.unmodifiableList(IntStream.range(0, listSize)
                .mapToObj(i -> new ListElement<>(i, list.get(i)))
                .collect(Collectors.toList()));
    }

    public static <ContentType> List<ContentType> unboxing(List<ListElement<ContentType>> list) {
        return unboxing(list, false);
    }

    public static <ContentType> List<ContentType> unboxing(List<ListElement<ContentType>> list, boolean unmodifiable) {
        List<ContentType> result = list.stream()
                .map(element -> element.content)
                .collect(Collectors.toList());
        return unmodifiable ? Collections.unmodifiableList(result) : result;
    }

    public final int index;

    public final ContentType content;

    private ListElement(int index, ContentType content) {
        this.index = index;
        this.content = content;
    }

    @Override
    public String toString() {
        return "[" + index + "] " + content;
    }

}
