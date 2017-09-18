package com.store.dto;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.store.config.ResponseStatus;

public class DocumentDto extends DocumentMetadataDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -8440229432126853702L;

  private byte[] fileData;

  public DocumentDto(byte[] fileData, String fileType, String fileName, String md5,
      Date lastModified, String userType) throws NoSuchAlgorithmException {
    super(fileType, md5, userType, lastModified, fileName, "OBJECT_CREATED");
    this.fileData = fileData;

  }

  public DocumentDto(DocumentMetadataDto documentMetadataDto) {
    super(documentMetadataDto.fileType, documentMetadataDto.key, documentMetadataDto.md5,
        documentMetadataDto.userType, documentMetadataDto.lastModified,
        documentMetadataDto.fileName);
  }

  public byte[] getFileData() {
    return fileData;
  }

  public void setFileData(byte[] fileData) {
    this.fileData = fileData;
  }

  public DocumentMetadataDto getMetadata() {
    return new DocumentMetadataDto(getFileType(), getMd5(), getUserType(), getLastModified(),
        getFileName(), ResponseStatus.SUCCESS.getMessage());
  }


}
