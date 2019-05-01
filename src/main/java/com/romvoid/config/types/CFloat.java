package com.romvoid.config.types;

import com.romvoid.config.ICParser;

public class CFloat implements ICParser<Float> {
    @Override
    public Float parse(String value) {
        return Float.parseFloat(value);
    }
}
