package org.iesalixar.daw2.dvm.dwese_dvm_api.repositories;

import org.iesalixar.daw2.dvm.dwese_dvm_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}