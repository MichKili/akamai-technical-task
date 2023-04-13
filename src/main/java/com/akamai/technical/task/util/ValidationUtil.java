package com.akamai.technical.task.util;

import com.akamai.technical.task.exception.PropertyNotExistException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class ValidationUtil {
    private ValidationUtil() {
    }

    public static <T> void checkSortProperty(String property, Class<T> clazz) {
        Optional<String> first = Arrays
                .stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .filter(e -> e.equals(property))
                .findFirst();
        if (first.isEmpty()) {
            log.debug("Given property: " + property + " does not exists in " + clazz.getSimpleName() + " class");
            throw new PropertyNotExistException("Given property: " + property + " does not exists in " + clazz.getSimpleName() + " class");
        }
    }
}
