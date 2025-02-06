package org.iesalixar.daw2.dvm.dwese_dvm_api.repositories;

import org.iesalixar.daw2.dvm.dwese_dvm_api.entities.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AbilityRepository extends JpaRepository<Ability, Long> {

    boolean existsByName(String code);
    @Query("SELECT COUNT(a) > 0 FROM Ability a WHERE a.name = :name AND a.id != :id")
    boolean existsAbilityByNameAndNotId(@Param("name") String code, @Param("id") Long id);

}
