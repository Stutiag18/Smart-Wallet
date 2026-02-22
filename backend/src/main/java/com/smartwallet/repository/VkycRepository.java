package com.smartwallet.repository;


import com.smartwallet.model.Vkyc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VkycRepository extends MongoRepository<Vkyc, String> {
    Optional<Vkyc> findByUserId(String userId);

}
