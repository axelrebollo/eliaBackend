package com.axel.notebook.application.services.producers;

public interface IYearProducer {
    //Send token to kafka
    public int sendToken(String token);
}
