package com.store.exception.handler;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.omg.CORBA.SystemException;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.store.model.ErrorMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
  
  private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);
  
  @ExceptionHandler(MultipartException.class)
  @ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
  @ResponseBody
  public ErrorMessage handleMultipartException(MultipartException exception) {
    logger.error("File size exceeded while uploading", exception);
    return new ErrorMessage(com.store.config.ResponseStatus.FILE_SIZE_LIMIT.getMessage(), 
        com.store.config.ResponseStatus.FILE_SIZE_LIMIT.getErrorCode());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = {HttpMessageNotReadableException.class,
      MethodArgumentTypeMismatchException.class, ServletRequestBindingException.class, MissingServletRequestPartException.class})
  public ErrorMessage badRequestExceptionHandler(Exception e) {
    return new ErrorMessage(HttpStatus.BAD_REQUEST.getReasonPhrase(), 400);
  }
  
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = {NoHandlerFoundException.class, NotFoundException.class, ResourceNotFoundException.class})
  public ErrorMessage resourceNotFoundExceptionHandler(Exception e) {
    return new ErrorMessage(HttpStatus.NOT_FOUND.getReasonPhrase(), 404);
  }
  
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ErrorMessage methodNotSupportedException(Exception e) {
    return new ErrorMessage(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), 405);
  }
  
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = {SQLException.class,JsonMappingException.class, JsonParseException.class,
      IOException.class, SystemException.class})
  public ErrorMessage sqlException(Exception e) {
    return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), 500);
  }
}
