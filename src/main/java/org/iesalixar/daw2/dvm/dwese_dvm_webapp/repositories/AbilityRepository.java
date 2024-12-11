package org.iesalixar.daw2.dvm.dwese_dvm_webapp.repositories;

import org.iesalixar.daw2.dvm.dwese_dvm_webapp.entities.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AbilityRepository extends JpaRepository<Ability, Long> {

    boolean existsByCode(String code);
    @Query("SELECT COUNT(a) > 0 FROM Ability a WHERE a.code = :code AND a.id != :id")
    boolean existsAbilityByCodeAndNotId(@Param("code") String code, @Param("id") Long id);

}
