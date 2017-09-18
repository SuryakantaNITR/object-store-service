package com.store.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.store.dto.MongoMetadata;

@Repository
public interface MetadataRepository extends MongoRepository<MongoMetadata, String> {

  List<MongoMetadata> findByMd5(String md5);

  List<MongoMetadata> findByFileType(String fileType);

  List<MongoMetadata> findByUserType(String userType);

  MongoMetadata findByKey(String key);

  @Query("{'$and':[ { 'lastModified' : { $gt: ?0, $lt: ?1 } }, {'userType': ?2} ] }")
  List<MongoMetadata> findByLastModifiedDateBetween(Date startDate, Date endDate, String userType);

}
