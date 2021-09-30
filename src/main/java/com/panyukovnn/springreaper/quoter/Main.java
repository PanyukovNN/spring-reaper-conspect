package com.panyukovnn.springreaper.quoter;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        // Важно искать по интерфейсу, а не по классу, т.к. может быть не найден прокси класс
        context.getBean(Quoter.class).sayQuote();
    }
}
