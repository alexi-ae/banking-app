package com.alexiae.banking.repository;

import com.alexiae.banking.model.entity.User;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<User, Long> {

  Optional<User> findByEmail(String email);
}
