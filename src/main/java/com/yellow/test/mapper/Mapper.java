package com.yellow.test.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<F, T> {

    T map(F value);

    default List<T> map(Collection<F> domains) {
        return domains.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
