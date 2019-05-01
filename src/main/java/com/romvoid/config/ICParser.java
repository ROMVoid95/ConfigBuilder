package com.romvoid.config;

public interface ICParser<Type> {

    Type parse(String value);

    default String toStringValue(Object o) {
        return String.valueOf(o);
    }
}
