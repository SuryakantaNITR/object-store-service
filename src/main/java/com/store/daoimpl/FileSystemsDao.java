package com.store.daoimpl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.store.config.FileSystemsConfig;
import com.store.config.MongoKeyConfig;
import com.store.controller.FileExchangeController;
import com.store.dao.FileSystems;
import com.store.dto.DocumentDto;
import com.store.dto.MongoMetadata;
import com.store.repository.MetadataRepository;
import com.store.utils.MetadataUtils;

@Service
public class FileSystemsDao implements FileSystems {

  private static final Logger logger = Logger.getLogger(FileExchangeController.class);

  @Autowired
  FileSystemsConfig fileSystemsConstant;

  @Autowired
  MetadataRepository metadataRepository;


  @PostConstruct
  public void init() {
    createDirectory(getDirectoryPath());
  }

  @Override
  public void insert(DocumentDto documentDto) {
    try {
      createDirectory(getDirectoryPath());
      saveFileData(documentDto);
    } catch (IOException e) {
      String message = "Error while inserting document";
      logger.error(message, e);
    }
  }

  @Async
  private void saveFileData(DocumentDto documentDto) throws IOException {
    logger.info("Writing file into memory");
    BufferedOutputStream stream = new BufferedOutputStream(
        new FileOutputStream(new File(new File(getDirectoryPath()), documentDto.getMd5())));
    stream.write(documentDto.getFileData());
    stream.close();
  }

  @Async
  public void insertMetadata(DocumentDto documentDto) {
    MongoMetadata mongoMetadata = new MongoMetadata(documentDto.getKey(), documentDto.getMd5(),
        documentDto.getFileType(), documentDto.getUserType(), documentDto.getLastModified(), documentDto.getFileName());
    logger.info("Writing metadata into database");
    metadataRepository.insert(mongoMetadata);
  }

  private String getDirectoryPath() {
    return fileSystemsConstant.getBaseDir().toString();
  }

  private void createDirectory(String path) {
    logger.info("Creating directory " + path);
    File file = new File(path);
    file.mkdirs();
  }

  public boolean[] isFilePresent(DocumentDto documentDto) {
    boolean[] isPresent = new boolean[] {false, false};
    List<MongoMetadata> mongoMetadataList = metadataRepository.findByMd5(documentDto.getMd5());
    if (!mongoMetadataList.isEmpty()) {
      isPresent[0] = true;
    }
    for (MongoMetadata mongoMetadata : mongoMetadataList) {
      if (documentDto.getFileName().equals(getFileNameFromKey(mongoMetadata.getKey())) && 
          documentDto.getUserType().equals(mongoMetadata.getUserType())) {
        isPresent[1] = true;
        documentDto.setLastModified(mongoMetadata.getLastModified());
        documentDto.setKey(mongoMetadata.getKey());
        logger.info("File is present in store");
        break;
      }
    }
    return isPresent;
  }

  public String getFileNameFromKey(String key) {
    String decodedString = MetadataUtils.decodeString(key);
    String fileName = decodedString.split(MongoKeyConfig.KEY_DELIMITER)[3];
    logger.info("Filename for the key is " + fileName);
    return fileName;
  }

  public String getFileLocation() {
    logger.info("Fetching file location");
    StringBuilder fileLocation = new StringBuilder();
    fileLocation.append(fileSystemsConstant.getBaseDir());
    fileLocation.append(File.separator);
    return fileLocation.toString();
  }
}
