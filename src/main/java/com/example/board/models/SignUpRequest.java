package com.example.board.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class SignUpRequest {
    private String email;
    private String password;

}
