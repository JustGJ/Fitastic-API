package com.fitastic.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters")
    private String name;

    private String target;
    private String [] description;
    private String [] instructions;
    private String [] image;
    private String [] advices;
    private String video;
}