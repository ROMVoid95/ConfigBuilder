package com.romvoid.config.types;

import com.romvoid.config.ICParser;

public class CDouble implements ICParser<Double> {
    @Override
    public Double parse(String value) {
        return Double.parseDouble(value);
    }
}
