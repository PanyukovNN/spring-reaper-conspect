package com.panyukovnn.springreaper.quoter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName);
        // берем все поля бина (сюда будут попадать все бины, даже не помеченные искомой аннотацией)
        Field[] declaredFields = bean.getClass().getDeclaredFields();

        // Пытаемся достать поле, помеченное аннотацией
        for (Field declaredField : declaredFields) {
             InjectRandomInt annotation = declaredField.getAnnotation(InjectRandomInt.class);
             if (annotation != null) {
                 int min = annotation.min();
                 int max = annotation.max();

                 // генерируем случайное число в диапазоне
                 int i = min + new Random().nextInt(max - min);

                 declaredField.setAccessible(true);
                 ReflectionUtils.setField(declaredField, bean, i);
             }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
