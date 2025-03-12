package com.axel.notebook.application.services.producers;

public interface ISubjectProducer {
    //Send token to kafka
    public int sendToken(String token);
}
