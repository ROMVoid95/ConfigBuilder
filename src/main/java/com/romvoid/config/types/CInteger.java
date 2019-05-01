package com.romvoid.config.types;

import com.romvoid.config.ICParser;

public class CInteger implements ICParser<Integer> {
    @Override
    public Integer parse(String value) {
        return Integer.parseInt(value);
    }
}
