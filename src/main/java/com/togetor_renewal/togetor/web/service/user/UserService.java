package com.togetor_renewal.togetor.web.service.user;

import com.togetor_renewal.togetor.domain.entity.User;
import com.togetor_renewal.togetor.domain.repository.UserRepository;
import com.togetor_renewal.togetor.domain.validation.user.UserJoinForm;
import com.togetor_renewal.togetor.domain.validation.user.UserModifyForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User confirmUser(String email, String pass) {
        return userRepository.findByEmail(email).filter(
                u -> u.getPass().equals(pass)).orElse(null);
    }

    public Optional<User> checkDuplicateEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void join(UserJoinForm form) {
        // 검증 성공시 User 객체에 담아서 DB에 저장해야 된다.
        LocalDateTime now = LocalDateTime.now();
        User user = new User(
                LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute(), now.getSecond()),
                form.getEmail(), form.getPass(), form.getPassConfirm(),
                form.getName(), form.getNickname(), form.getPhone(), form.getPostcode(),
                form.getAddress(), form.getDetailAddress(), form.getExtraAddress()
        );

        // 만든 User 객체를 Repository(DB)에 저장하자
        userRepository.save(user);
    }

    public User findUserById(Long id) {
        return userRepository.getById(id);
    }

    public void modifyUser(UserModifyForm form){
        userRepository.updateUser(
                form.getEmail(),
                form.getPass(),
                form.getName(),
                form.getNickname(),
                form.getPhone(),
                form.getPostcode(),
                form.getAddress(),
                form.getDetailAddress(),
                form.getExtraAddress(),
                form.getUserId()
        );
    }

    public void withDrawalUser(Long userId){
        userRepository.deleteById(userId);
    }

}