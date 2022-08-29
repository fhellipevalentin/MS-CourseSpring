package io.github.fhellipevalentin.hruser.repositories;

import io.github.fhellipevalentin.hruser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}