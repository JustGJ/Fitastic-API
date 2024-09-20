package com.fitastic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "defaultExercises")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultExercise {

    @Id
    private String id;
    private String name;
    private String target;
    private String description;
}