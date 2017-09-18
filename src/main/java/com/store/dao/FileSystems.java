package com.store.dao;

import com.store.dto.DocumentDto;


public interface FileSystems {

  public void insert(DocumentDto document);

  public void insertMetadata(DocumentDto document);

  public boolean[] isFilePresent(DocumentDto document);

  public String getFileLocation();

  public String getFileNameFromKey(String key);

}
