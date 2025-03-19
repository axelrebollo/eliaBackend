package com.axel.notebook.domain.services;

import com.axel.notebook.domain.entities.Year;

public class YearService {
    public Year addYear(String nameYear, int idProfile) {
        return new Year(nameYear, idProfile);
    }
}
