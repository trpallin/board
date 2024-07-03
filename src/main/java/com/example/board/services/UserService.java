package com.example.board.services;

import com.example.board.models.CustomUserInfoDto;
import com.example.board.models.SignInRequestDto;
import com.example.board.models.User;
import com.example.board.repositories.UserRepository;
import com.example.board.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;



    @Transactional
    public boolean createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return false;
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public String authenticateUser(
            SignInRequestDto dto
    ) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Not found email");
        }

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }

        CustomUserInfoDto info = modelMapper.map(user, CustomUserInfoDto.class);

        return jwtUtil.createAccessToken(info);
    }
}
