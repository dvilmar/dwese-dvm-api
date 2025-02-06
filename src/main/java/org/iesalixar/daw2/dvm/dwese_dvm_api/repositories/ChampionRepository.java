package org.iesalixar.daw2.dvm.dwese_dvm_api.repositories;

import org.iesalixar.daw2.dvm.dwese_dvm_api.entities.Champion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChampionRepository extends JpaRepository<Champion, Long> {

    boolean existsByName(String name);
    @Query("SELECT COUNT(c) > 0 FROM Champion c WHERE c.name = :name AND c.id != :id")
    boolean existsChampionByNameAndNotId(@Param("name") String code, @Param("id") Long id);

}
