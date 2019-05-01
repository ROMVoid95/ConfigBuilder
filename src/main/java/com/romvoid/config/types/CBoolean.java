package com.romvoid.config.types;

import com.romvoid.config.ICParser;

public class CBoolean implements ICParser<Boolean> {
    @Override
    public Boolean parse(String value) {
        return Boolean.parseBoolean(value);
    }
}
