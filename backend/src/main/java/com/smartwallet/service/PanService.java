package com.smartwallet.service;

import com.smartwallet.repository.PanRepository;
import com.smartwallet.model.PanEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@Service
public class PanService {

    private final PanRepository panRepository;

    private static final Pattern PAN_PATTERN =
            Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[A-Za-z ]{3,100}$");

    public PanService(PanRepository panRepository) {
        this.panRepository = panRepository;
    }

    public void submitPan(String panName, String panNumber, LocalDate dob) {

        if (panNumber == null || !PAN_PATTERN.matcher(panNumber).matches()) {
            throw new IllegalArgumentException("Invalid PAN format");
        }

        if (panName == null || !NAME_PATTERN.matcher(panName).matches()) {
            throw new IllegalArgumentException("Invalid name on PAN");
        }

        if (dob == null) {
            throw new IllegalArgumentException("DOB is required");
        }

        int age = Period.between(dob, LocalDate.now()).getYears();
        if (age < 18) {
            throw new IllegalArgumentException("User must be 18 years or older");
        }

        if (panRepository.existsByPanNumber(panNumber)) {
            throw new IllegalArgumentException("PAN already exists");
        }

        PanEntity pan = new PanEntity();
        pan.setPanName(panName);
        pan.setPanNumber(panNumber);
        pan.setDob(dob);
        pan.setStatus("SUBMITTED");

        panRepository.save(pan);
    }
}
