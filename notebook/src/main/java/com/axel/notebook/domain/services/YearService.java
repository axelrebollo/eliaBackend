package com.axel.notebook.domain.services;

import com.axel.notebook.domain.entities.Year;
import org.springframework.stereotype.Service;

@Service
public class YearService {
    public Year addYear(String nameYear, int idProfile) {
        return new Year(nameYear, idProfile);
    }
}
