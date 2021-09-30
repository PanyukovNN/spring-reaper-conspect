package com.panyukovnn.springreaper.quoter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Сеттит случайное число в поле в диапазоне между min и max
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRandomInt {
    int min();

    int max();
}
