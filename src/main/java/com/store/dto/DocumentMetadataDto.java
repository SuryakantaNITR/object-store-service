package com.store.dto;

import java.io.Serializable;
import java.util.Date;

import com.store.utils.MetadataUtils;

public class DocumentMetadataDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1830021056680864193L;

  protected String fileType;
  protected String key;
  protected String md5;
  protected String userType;
  protected Date lastModified;
  protected String fileName;
  protected String status;

  public DocumentMetadataDto() {
    super();
  }

  public DocumentMetadataDto(String fileType, String md5, String userType, Date lastModified,
      String fileName, String status) {
    super();
    this.fileType = fileType;
    this.md5 = md5;
    this.userType = userType;
    this.lastModified = lastModified;
    this.fileName = fileName;
    this.key = MetadataUtils.encodeParams(fileType, userType, lastModified, fileName);
    this.status = status;
  }

  public DocumentMetadataDto(String fileType, String key, String md5, String userType,
      Date lastModified, String fileName) {
    super();
    this.fileType = fileType;
    this.key = key;
    this.md5 = md5;
    this.userType = userType;
    this.lastModified = lastModified;
    this.fileName = fileName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getMd5() {
    return md5;
  }

  public void setMd5(String md5) {
    this.md5 = md5;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }



}
