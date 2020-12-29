package com.alian.ums.repository;

import com.alian.ums.entity.SystemLogs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ZZL
 */
@Repository
public interface LogsRepository extends MongoRepository<SystemLogs,String> {

}
