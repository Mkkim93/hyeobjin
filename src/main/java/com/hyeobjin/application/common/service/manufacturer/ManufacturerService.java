package com.hyeobjin.application.common.service.manufacturer;

import com.hyeobjin.application.common.dto.manu.ManufactureDTO;
import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import com.hyeobjin.domain.repository.manu.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

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
                .sorted(Comparator.comparing(ManufactureDTO::getManuId)) // ✅ ID 기준 내림차순 정렬
                .toList();
    }
}
