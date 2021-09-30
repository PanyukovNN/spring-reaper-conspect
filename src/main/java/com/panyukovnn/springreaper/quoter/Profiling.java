package com.panyukovnn.springreaper.quoter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Выводит в лог время работы каждого из методов класса
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Profiling {
}
