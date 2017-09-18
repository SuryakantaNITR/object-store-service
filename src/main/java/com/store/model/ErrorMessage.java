package com.store.model;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

  private static final long serialVersionUID = -5344766945009913903L;

  private String errorMessage;
  private int errorCode;

  public ErrorMessage() {}

  public ErrorMessage(String status, int errorCode) {
    this.errorMessage = status;
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String status) {
    this.errorMessage = status;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

}
