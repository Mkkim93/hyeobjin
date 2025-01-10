package com.hyeobjin.domain.repository;

import com.hyeobjin.domain.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    @Query("select m.id from Manufacturer m where m.manuName = :manuName")
    Long findManufacturerByManuName(@Param("manuName") String manuName);

    Boolean existsByManuName(String manuName);

    @Transactional
    @Modifying
    @Query("update Manufacturer m set m.manuName = :manuName where m.id = :manuId")
    Integer updateManuName(@Param("manuId") Long manuId, @Param("manuName") String manuName);

    @Transactional
    @Modifying
    @Query("update Manufacturer m set m.manuYN = 'Y' where m.id = :manuId")
    Integer deleteManufacturer(@Param("manuId") Long manuId);

    @Transactional
    @Modifying
    @Query("update Manufacturer m set m.manuYN = 'N' where m.id = :manuId")
    Integer restoreManufacturer(@Param("manuId") Long manuId);
}
