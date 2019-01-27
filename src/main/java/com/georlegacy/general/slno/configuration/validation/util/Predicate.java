package com.georlegacy.general.slno.configuration.validation.util;

public abstract class Predicate<T> implements java.util.function.Predicate<T> {

    public abstract boolean test(T t, String arg);

}
