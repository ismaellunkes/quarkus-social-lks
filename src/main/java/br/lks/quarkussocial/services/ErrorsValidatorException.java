package br.lks.quarkussocial.services;

import jakarta.json.bind.JsonbBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.Set;
import java.util.stream.Collectors;

public class ErrorsValidatorException extends ConstraintViolationException {

    private final String errorsValidator;

    public ErrorsValidatorException(String message) {
        super(message, null);
        this.errorsValidator = message;
    }

    public ErrorsValidatorException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
        errorsValidator = JsonbBuilder.create().toJson(constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));
    }

    public String getErrors() {
        return errorsValidator;
    }
}
