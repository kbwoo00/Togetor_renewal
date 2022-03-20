package com.togetor_renewal.togetor.web.service.user;

import com.togetor_renewal.togetor.domain.entity.User;
import com.togetor_renewal.togetor.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public User login(String email, String pass){
        return userRepository.findByEmail(email).filter(
                u -> u.getPass().equals(pass)).orElse(null);
    }


}
