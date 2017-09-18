package com.store.config;

public enum ResponseStatus {
  
  BLANK_FILE_NAME(01, "Error - Improper filename"), 
  FILE_ALREADY_PRESENT(02, "Error - File already present with same name"),
  FILE_SIZE_LIMIT(03, "Error - File size exceeded configured upload limit"),
  SUCCESS(03, "Success");
  
  private final int errorCode;
  private final String message;
  
  ResponseStatus(int errorCode, String message) {
    this.errorCode = errorCode;
    this.message = message;
  }
  
  public int getErrorCode() {
    return errorCode;
  }

  public String getMessage() {
    return message;
  }

}
