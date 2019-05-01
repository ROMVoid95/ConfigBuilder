package com.romvoid.config.types;

import com.romvoid.config.ICParser;

public class CString implements ICParser<String> {
    @Override
    public String parse(String value) {
        return value;
    }
}
