package com.togetor_renewal.togetor.web.service.user;

import com.togetor_renewal.togetor.domain.DTO.user.FindPasswordForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {
    private final UserService userService;
    private final JavaMailSender emailSender;

    public void sendNewPass(String name, String email){
        String tempPassword = UUID.randomUUID().toString().replace("-", "");
        tempPassword = tempPassword.substring(0,8);

        String text = "";
        text += name + "님의 임시 비밀번호 입니다. 비밀번호를 변경하여 사용하세요.";
        text += "임시 비밀번호 : " + tempPassword;

       SimpleMailMessage message = new SimpleMailMessage();
       message.setFrom("togetor@gmail.com");
       message.setReplyTo("togetor@gmail.com");
       message.setTo(email);
       message.setSubject("Togetor 임시 비밀번호 입니다.");
       message.setText(text);
       try{
           emailSender.send(message);
           userService.updatePassword(name, email ,tempPassword);
       } catch (Exception e){
           e.printStackTrace();
       }

    }
}
