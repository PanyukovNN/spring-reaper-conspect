package com.panyukovnn.springreaper.quoter;

public class RickQuoter {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

//    @Override
    public void sayQuote() {
        System.out.println("Message: " + message);
    }
}
