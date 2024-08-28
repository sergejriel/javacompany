package com.exercise.javacompany.utils;

import java.util.Objects;

public class ValidationUtils {

    public static boolean checkFieldIsUpdatedAndValid(String oldValue, String newValue) {
        return (newValue != null
                && !newValue.isEmpty()
                && !Objects.equals(oldValue,newValue)
        );
    }
}
