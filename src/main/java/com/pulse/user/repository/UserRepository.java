package com.pulse.user.repository;

import com.pulse.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    public boolean existsByUsername(String username);

    public boolean existsByEmail(String email);

}
