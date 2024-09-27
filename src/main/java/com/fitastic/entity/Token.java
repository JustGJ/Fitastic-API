package com.fitastic.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id
    private String id;

    private String accessToken;

    private String refreshToken;

    private boolean loggedOut;

    private User user;

}