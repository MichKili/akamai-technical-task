package com.akamai.technical.task.util;

import com.akamai.technical.task.exception.PropertyNotExistException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@UtilityClass //not good idea -> it is better to create it like a bean and there is less pain in unit test because static is pain with unit testing etc.
//+ there is a problem with fields declaration. If we create public final field @UtilityClass made it static with bad naming convention
// (Because there is no constant naming convention with uppercase) + there is automatically created logger as const with bad naming convention
// Little thing bad it is a little destroy to readability of code. + thia is experimental feature fom lombok so it is not stable
public class ValidationUtil {

    public static <T> void checkSortProperty(String property, Class<T> clazz) {
        String s = Objects.requireNonNullElse(property, "id");//if property is null then set default value to id
        Optional<String> first = Arrays
                .stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .filter(e -> e.equals(property))
                .findFirst();
        if (first.isEmpty()) {
            throw new PropertyNotExistException("Given property: " + property + " does not exists in " + clazz.getSimpleName() + " class");
        }
    }
}
