package com.panyukovnn.springreaper.quoter;

import javax.annotation.PostConstruct;

@Profiling
public class TerminatorQuoter implements Quoter {

    @InjectRandomInt(min = 2, max = 7)
    private int repeat;

    private String message;

    public TerminatorQuoter() {
        System.out.println("Phase 1");

        // будет выведен 0, поскольку инъкция  спринга на данном этапе ещё не сработала
        // спринг сначала создает объект и только потом настраивает
        System.out.println(repeat);
    }

    @PostConstruct
    public void init() {
        System.out.println("Phase 2");

        // здесь переменная уже будет проинициализирована, поскольку @PostConstruct метод выполняется после
        // postProcessBeforeInitialization метода BeanPostProcessor'а
        System.out.println(repeat);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    @PostProxy
    public void sayQuote() {
        System.out.println("Phase 3");

        for (int i = 0; i < repeat; i++) {
            System.out.println("Message: " + message);
        }
    }
}
