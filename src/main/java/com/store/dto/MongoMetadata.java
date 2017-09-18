package com.store.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "metadata")
public class MongoMetadata implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1224125181606462730L;

  @Id
  private String key;
  private String md5;
  private String fileType;
  private String userType;
  private Date lastModified;
  private String fileName;

  public MongoMetadata(String key, String md5, String fileType, String userType,
      Date lastModified, String fileName) {
    this.key = key;
    this.md5 = md5;
    this.fileType = fileType;
    this.userType = userType;
    this.lastModified = lastModified;
    this.fileName = fileName;
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

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
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

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileName() {
    return fileName;
  }

}
