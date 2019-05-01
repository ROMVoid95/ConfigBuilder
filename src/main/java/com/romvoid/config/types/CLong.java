package com.romvoid.config.types;

import com.romvoid.config.ICParser;

public class CLong implements ICParser<Long> {
    @Override
    public Long parse(String value) {
        return Long.parseLong(value);
    }
}
