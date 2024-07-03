package com.example.board.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomUserInfoDto {
    private Long id;

    private String email;
    private String username;
    private String password;
    private RoleType role;
}
