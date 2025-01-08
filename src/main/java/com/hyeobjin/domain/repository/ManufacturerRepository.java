package com.hyeobjin.domain.repository;

import com.hyeobjin.domain.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    Manufacturer findManufacturerByManuName(String manuName);

    Boolean existsByManuName(String manuName);
}
