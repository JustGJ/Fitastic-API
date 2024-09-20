package com.fitastic.repository;

import com.fitastic.entity.DefaultExercise;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DefaultExerciseRepository extends MongoRepository<DefaultExercise, String> {
}