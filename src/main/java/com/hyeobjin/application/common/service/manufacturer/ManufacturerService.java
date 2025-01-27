package com.hyeobjin.application.common.service.manufacturer;

import com.hyeobjin.application.admin.dto.manu.FindManufacturerDTO;
import com.hyeobjin.application.common.dto.manu.ManufactureDTO;
import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import com.hyeobjin.domain.repository.manu.ManuFactJpaRepository;
import com.hyeobjin.domain.repository.manu.ManufacturerRepository;
import com.hyeobjin.exception.DuplicateManufacturerException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final ManuFactJpaRepository manuFactJpaRepository;







    /**
     * 모든 제조사 조회 (user view)
     * # 관리자가 삭제한 제조사 (manuYN = N) 제외
     */
    public List<ManufactureDTO> findAll() {

        List<Manufacturer> manufacturerList = manufacturerRepository.findAll();

        return manufacturerList.stream()
                .map(manufacturer -> new ManufactureDTO(
                        manufacturer.getId(),
                        manufacturer.getManuName(),
                        manufacturer.getManuYN()))
                .filter(manufactureDTO -> "Y".equals(manufactureDTO.getManuYN()))
                .toList();
    }





    /**
     * # 관리자가 삭제했던 제조사 복구 (manuYN : Y -> N)
     */
    public Integer restore(ManufactureDTO manufactureDTO) {

        return manufacturerRepository.restoreManufacturer(
                manufactureDTO.getManuId());
    }
}
