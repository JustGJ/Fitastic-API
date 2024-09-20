package com.fitastic.repository;

import com.fitastic.entity.UserExercise;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserExerciseRepository extends MongoRepository<UserExercise, String>{
}
