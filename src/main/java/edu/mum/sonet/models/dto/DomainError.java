package edu.mum.sonet.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DomainError {
    private String field;
    private String message;

    public DomainError(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
