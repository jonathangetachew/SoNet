package edu.mum.sonet.exception;

import edu.mum.sonet.models.dto.DomainError;
import edu.mum.sonet.models.dto.DomainErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ExceptionAdvice {

    MessageSourceAccessor messageSourceAccessor;

    @Autowired
    public ExceptionAdvice(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public DomainErrors handleException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        DomainErrors errors = new DomainErrors();
        errors.setErrorType("validation");
        for (FieldError fieldError : fieldErrors) {
            DomainError error = new DomainError(fieldError.getField(),
                    messageSourceAccessor.getMessage(fieldError));
            errors.addError(error);
        }

        return errors;
    }

}
