package edu.mum.sonet.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DomainErrors {

    private String errorType;
    private String message;

    private List<DomainError> errors = new ArrayList<DomainError>();

    public void addError(DomainError error) {
        errors.add(error);
    }
}
