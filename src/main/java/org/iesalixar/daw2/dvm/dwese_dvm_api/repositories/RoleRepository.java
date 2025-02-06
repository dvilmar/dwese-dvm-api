package org.iesalixar.daw2.dvm.dwese_dvm_api.repositories;

import org.iesalixar.daw2.dvm.dwese_dvm_api.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}