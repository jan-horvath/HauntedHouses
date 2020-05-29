package cz.muni.fi.pa165.hauntedhouses.rest.controllers;

import cz.muni.fi.pa165.hauntedhouses.rest.ApiError;
import cz.muni.fi.pa165.hauntedhouses.rest.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.hauntedhouses.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.hauntedhouses.rest.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    ApiError handleResourceAlreadyExistingException(ResourceAlreadyExistingException ex) {
        ApiError apiError = new ApiError();
        apiError.setErrors(Arrays.asList("The requested resource already exists."));
        return apiError;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    ApiError handleInvalidParameterException(InvalidParameterException ex) {
        ApiError apiError = new ApiError();
        apiError.setErrors(Arrays.asList("The input is invalid."));
        return apiError;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ApiError handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.setErrors(Arrays.asList("The requested resource was not found."));
        return apiError;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (var error : ex.getBindingResult().getAllErrors()) {
            String message = error.getDefaultMessage();
            errors.add(message);
        }

        ApiError apiError = new ApiError();
        apiError.setErrors(errors);
        return apiError;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiError handleGeneralException(Exception ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ApiError apiError = new ApiError();
        apiError.setErrors(errors);
        return apiError;
    }
}