package com.store.serviceimpl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.store.config.ResponseStatus;
import com.store.dao.FileSystems;
import com.store.dto.DocumentDto;
import com.store.dto.DocumentMetadataDto;
import com.store.dto.MongoMetadata;
import com.store.repository.MetadataRepository;
import com.store.service.ExchangeService;

@Service
public class ExchangeServiceImpl implements ExchangeService {

  private static final Logger logger = Logger.getLogger(ExchangeServiceImpl.class);

  @Autowired
  private FileSystems fileSystems;

  @Autowired
  private MetadataRepository metadataRepository;

  public DocumentMetadataDto save(DocumentDto documentDto) {
    logger.info("Inside save method to save document dto");
    DocumentMetadataDto metadataDto = new DocumentMetadataDto(documentDto.getFileType(),
        documentDto.getKey(), documentDto.getMd5(), documentDto.getUserType(),
        documentDto.getLastModified(), documentDto.getFileName());
    if(documentDto.getFileName() == null || documentDto.getFileName().trim().isEmpty()) {
      metadataDto.setKey(null);
      metadataDto.setStatus(ResponseStatus.BLANK_FILE_NAME.getMessage());
      return metadataDto;
    }
    boolean[] isPresent = fileSystems.isFilePresent(documentDto);
    if (isPresent[1]) {
      metadataDto.setKey(documentDto.getKey());
      metadataDto.setLastModified(documentDto.getLastModified());
      metadataDto.setStatus(ResponseStatus.FILE_ALREADY_PRESENT.getMessage());
      return metadataDto;
    } else if (isPresent[0]) {
      fileSystems.insertMetadata(documentDto);
      return documentDto.getMetadata();
    } else {
      fileSystems.insert(documentDto);
      fileSystems.insertMetadata(documentDto);
      return documentDto.getMetadata();
    }

  }

  public Resource getFile(String key) {
    Resource resource = null;
    logger.info("Searching data using key " + key);
    MongoMetadata mongoMetadata = metadataRepository.findByKey(key);
    if (mongoMetadata == null) {
      logger.info("No data found using the supplied key " + key);
      return resource;
    }
    try {
      Path file = Paths.get(fileSystems.getFileLocation() + mongoMetadata.getMd5());
      resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      }
    } catch (Exception e) {
      logger.error("Exception... File Not found in FileSystem", e);
    }
    return resource;

  }

  public List<MongoMetadata> getMetadataList(String userType) {
    logger.info("Searching meta data using user type " + userType);
    List<MongoMetadata> response = metadataRepository.findByUserType(userType);
    if (response == null) {
      logger.info("No data found using the supplied user type " + userType);
    }
    return response;
  }

  public List<MongoMetadata> getMetadataListByDate(Date date, String userType) {
    logger.info("Searching meta data using specified date " + date);
    SimpleDateFormat outputFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
    try {
      Date startDate = outputFormat.parse(date.toString());
      Date endDate =
          outputFormat.parse(new Date(startDate.getTime() + (1000 * 60 * 60 * 24)).toString());
      List<MongoMetadata> response =
          metadataRepository.findByLastModifiedDateBetween(startDate, endDate, userType);
      if (response == null) {
        logger.info("No data found using the supplied date " + date);
      }
      return response;
    } catch (ParseException e) {
      logger.error("error while parsing start date");
      return new ArrayList<MongoMetadata>();
    }
  }

  public List<MongoMetadata> getMetadataListByDateRange(Date startDate, Date endDate, String userType) {
    logger.info("Searching meta data between specified date " + startDate + " and " + endDate);
    List<MongoMetadata> response =
        metadataRepository.findByLastModifiedDateBetween(startDate, endDate, userType);
    if (response == null) {
      logger.info("No data found using the supplied date ");
    }
    return response;
  }

}
