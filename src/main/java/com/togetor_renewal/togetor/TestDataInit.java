package com.togetor_renewal.togetor;

import com.togetor_renewal.togetor.domain.entity.User;
import com.togetor_renewal.togetor.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

//@RequiredArgsConstructor
//@Component
public class TestDataInit {
//    private final UserRepository userRepository;

    @PostConstruct
    public void testData() {
        LocalDateTime now = LocalDateTime.now();

        User user = new User(
                LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute(), now.getSecond()),
                "kbw@na", "123", "123", "홍길동", "전우치입니다.", "01012345678",
                "51265", "서우린머", "구로구로구로", null);

//        userRepository.save(user);
    }
}
