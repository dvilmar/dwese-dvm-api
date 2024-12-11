package org.iesalixar.daw2.dvm.dwese_dvm_webapp.repositories;

import org.iesalixar.daw2.dvm.dwese_dvm_webapp.entities.Champion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChampionRepository extends JpaRepository<Champion, Long> {

    boolean existsByCode(String code);
    @Query("SELECT COUNT(c) > 0 FROM Champion c WHERE c.code = :code AND c.id != :id")
    boolean existsChampionByCodeAndNotId(@Param("code") String code, @Param("id") Long id);

}
