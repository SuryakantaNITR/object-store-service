package com.store.service;

import java.util.Date;
import java.util.List;

import org.springframework.core.io.Resource;

import com.store.dto.DocumentDto;
import com.store.dto.DocumentMetadataDto;
import com.store.dto.MongoMetadata;

public interface ExchangeService {
  public DocumentMetadataDto save(DocumentDto documentDto);

  public Resource getFile(String key);

  public List<MongoMetadata> getMetadataList(String userType);

  public List<MongoMetadata> getMetadataListByDate(Date date, String userType);

  public List<MongoMetadata> getMetadataListByDateRange(Date startDate, Date endDate, String userType);
}
