package com.store.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;

import com.store.config.MongoKeyConfig;

public class MetadataUtils {

  private static final Logger logger = Logger.getLogger(MetadataUtils.class);

  private MetadataUtils() {

  }

  public static String calculateDigest(byte[] fileData) throws NoSuchAlgorithmException {
    logger.info("Calculating message digest for the object");
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.reset();
    byte[] digest = md.digest(fileData);
    BigInteger bigInt = new BigInteger(1, digest);
    String hashtext = bigInt.toString(16);
    while (hashtext.length() < 32) {
      hashtext = "0" + hashtext;
    }
    return hashtext;
  }

  public static String encodeParams(String fileType, String userType, Date lastModified,
      String fileName) {
    logger.info("Encoding the passed parameters");
    String separator = MongoKeyConfig.KEY_DELIMITER;
    StringBuilder finalString = new StringBuilder();
    finalString.append(fileType).append(separator).append(userType).append(separator)
        .append(lastModified.getTime()).append(separator).append(fileName);
    byte[] bytesEncoded = Base64.encodeBase64(finalString.toString().getBytes());
    return new String(bytesEncoded);
  }

  public static String decodeString(String encodedString) {
    logger.info("Decoding the passed string");
    byte[] valueDecoded = Base64.decodeBase64(encodedString.getBytes());
    return new String(valueDecoded);
  }

}
