package com.example.reportes.repository;


import com.example.reportes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long>{

    //User save(User user);

    //List<User> findAll();

    Optional<User> findUserByUserId(Long id);

    void deleteUserByUserId(Long id);

}
