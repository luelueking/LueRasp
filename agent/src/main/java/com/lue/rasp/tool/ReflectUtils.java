package com.lue.rasp.tool;

import java.lang.reflect.Field;

public class ReflectUtils {

    public static void writeField(final Field field, final Object target, final Object value, final boolean forceAccess)
            throws IllegalAccessException {
        if (forceAccess && !field.isAccessible()) {
            field.setAccessible(true);
        }
        field.set(target, value);
    }
}
