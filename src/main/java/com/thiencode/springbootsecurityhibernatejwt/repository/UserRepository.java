package com.thiencode.springbootsecurityhibernatejwt.repository;

import com.thiencode.springbootsecurityhibernatejwt.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
