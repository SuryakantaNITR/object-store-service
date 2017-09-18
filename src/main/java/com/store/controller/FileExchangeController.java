package com.store.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.store.dao.FileSystems;
import com.store.dto.DocumentDto;
import com.store.dto.DocumentMetadataDto;
import com.store.dto.MongoMetadata;
import com.store.service.ExchangeService;
import com.store.utils.MetadataUtils;

@RestController
@RequestMapping(value = "/file")
public class FileExchangeController {

  private static final Logger logger = Logger.getLogger(FileExchangeController.class);

  @Autowired
  private ExchangeService exchangeService;

  @Autowired
  private FileSystems fileSystems;


  @RequestMapping(value = "/upload", method = RequestMethod.POST,
      consumes = {"multipart/form-data"})
  public @ResponseBody DocumentMetadataDto handleFileUpload(
      @RequestParam(value = "file", required = true) MultipartFile file,
      @RequestParam(value = "fileType", required = true) String fileType,
      @RequestParam(value = "userType", required = true) String userType) {

    try {
      byte[] originalBytes = file.getBytes();

      logger.info("creating DocumentDto and metadata for uploaded file");
      DocumentDto document = new DocumentDto(originalBytes, fileType, file.getOriginalFilename(),
          MetadataUtils.calculateDigest(originalBytes), new Date(), userType);

      return exchangeService.save(document);
    } catch (RuntimeException e) {
      logger.error("error while uploading", e);
      throw e;
    } catch (Exception e) {
      logger.error("error while uploading", e);
      throw new RuntimeException(e);
    }
  }

  @RequestMapping(value = "/download/{key}", method = RequestMethod.GET)
  public ResponseEntity getDocument(@PathVariable String key) {
    MongoMetadata mongoMetadata =
        new MongoMetadata("Invalid key", "Error: File not found", null, null, null, null);
    try {
      logger.info("Downloading file using key " + key);
      Resource file = null;
      file = exchangeService.getFile(key);
      if (file == null) {
        return new ResponseEntity(mongoMetadata, null, HttpStatus.NOT_FOUND);
      }
      String fileName = fileSystems.getFileNameFromKey(key);
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
          .body(file);

    } catch (Exception e) {
      logger.error("error while downloading", e);
      return new ResponseEntity(mongoMetadata, null, HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(value = "/list", method = RequestMethod.POST)
  public @ResponseBody List<MongoMetadata> getMetadataList(
      @RequestParam(value = "userType", required = true) String userType) {
    logger.info("Fetching metadata for list of files using user type " + userType);
    return exchangeService.getMetadataList(userType);
  }

  @RequestMapping(value = "/last_modified", method = RequestMethod.POST)
  public @ResponseBody List<MongoMetadata> getMetadataListByDate(
      @RequestParam(value = "userType", required = true) String userType,
      @RequestParam(value = "date",
          required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") Date date) {
    logger.info("Fetching metadata for list of files using specified date " + date);
    return exchangeService.getMetadataListByDate(date, userType);

  }
}
