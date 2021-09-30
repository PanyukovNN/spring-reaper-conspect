package com.panyukovnn.springreaper.quoter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Для выполнения метода после настройки прокси (трехфазный конструктор)
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PostProxy {
}
