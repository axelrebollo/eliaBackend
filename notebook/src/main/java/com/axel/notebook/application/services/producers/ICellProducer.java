package com.axel.notebook.application.services.producers;

import java.util.Map;

public interface ICellProducer {
    public Map<String, String> sendToken(String token);

    public Map<String, String> sendIdProfile(int idProfile);
}
