package com.togetor_renewal.togetor.domain.repository;

import com.togetor_renewal.togetor.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndName(String email, String name);

    @Transactional
    @Modifying
    @Query("update User u set u.email= :email, u.pass= :pass, u.name= :name, u.nickname= :nickname, u.phone= :phone, u.postcode= :postcode, u.address= :address, u.detailAddress= :detailAddress, u.extraAddress= :extraAddress where u.id= :userId")
    void updateUser(@Param("email") String email, @Param("pass") String pass, @Param("name") String name,
                    @Param("nickname") String nickname, @Param("phone") String phone, @Param("postcode") String postcode,
                    @Param("address") String address, @Param("detailAddress") String detailAddress,
                    @Param("extraAddress") String extraAddress, @Param("userId") Long userId);


    @Transactional
    @Modifying
    @Query("update User u set u.pass= :pass where u.name= :name and u.email= :email")
    void updatePassword(@Param("pass") String pass, @Param("name") String name, @Param("email") String email);

}
