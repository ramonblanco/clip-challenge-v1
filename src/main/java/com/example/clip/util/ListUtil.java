package com.example.clip.util;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ListUtil {

    public static <T> List<T> getListFromIterable(Iterable<T> iterable) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable.iterator(),
                Spliterator.ORDERED), false)
                .collect(Collectors.toList());
    }
}
