package com.romvoid.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.romvoid.config.types.*;

public class ConfigBuilder {
    private final File configFile;
    private final CProperties properties;
    private final Class<?> configclass;
    private final Map<Class<?>, ICParser> cParsers = new HashMap<>();

    public ConfigBuilder(Class<?> configclass, File configFile) {
        this.configFile = configFile;
        this.configclass = configclass;
        this.properties = new CProperties();
        loadParsers();
    }

    /**
     * loads the configuration parsers for each type
     */
    private void loadParsers() {
        ArrayList<Class<? extends ICParser<?>>> classes = new ArrayList<>();
        classes.add(CBoolean.class);
        classes.add(CFloat.class);
        classes.add(CInteger.class);
        classes.add(CLong.class);
        classes.add(CString.class);
        classes.add(CStringArray.class);
        classes.add(CDouble.class);
        cParsers.put(int.class, new CInteger());
        cParsers.put(boolean.class, new CBoolean());
        cParsers.put(double.class, new CDouble());
        cParsers.put(long.class, new CLong());
        cParsers.put(float.class, new CFloat());
        for (Class<? extends ICParser> parserclass : classes) {
            try {
                Class<?> parserType = (Class<?>) ((ParameterizedType) parserclass.getGenericInterfaces()[0]).getActualTypeArguments()[0];
                ICParser<?> parserInstance = parserclass.getConstructor().newInstance();
                cParsers.put(parserType, parserInstance);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the configClass's variables with the configFile's values
     *
     * @param cleanfile clear the File of all undefined variables
     * @throws IOException file can't be accessed
     */
    public void build(boolean cleanfile) throws Exception {
        if (configFile == null) throw new IllegalStateException("File not initialized");
        if (configFile.exists()) {
            properties.load(new FileInputStream(configFile));
        }
        CProperties cleanProperties = new CProperties();

        for (Field field : configclass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(COption.class)) {
                continue;
            }
            try {
                boolean isPrivate = !field.isAccessible();
                if (isPrivate) {
                    field.setAccessible(true);
                }
                String variableName = field.getName().toLowerCase();
                Object defaultValue = field.get(null);
                Object value = configFile.exists() ? properties.getOrDefault(variableName, defaultValue) : defaultValue;
                if (cParsers.containsKey(field.getType())) {
                    field.set(null, cParsers.get(field.getType()).parse(String.valueOf(value)));
                    properties.setProperty(variableName, cParsers.get(field.getType()).toStringValue(field.get(null)));
                    cleanProperties.setProperty(variableName, properties.getProperty(variableName));
                } else {
                    throw new Exception("Unknown Configuration Type. Variable name: '" + field.getName() + "'; Unknown Class: " + field.getType().getName());
                }
                if (isPrivate) {
                    field.setAccessible(false);
                }
            } catch (IllegalAccessException e) {
                System.out.println("Could not load configuration, IllegalAccessException");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        if (cleanfile) {
            cleanProperties.store(new FileOutputStream(configFile), null);
        } else {
            properties.store(new FileOutputStream(configFile), null);
        }
    }
}
