package com.hyeobjin.application.service.manufacturer;

import com.hyeobjin.application.dto.item.CreateItemDTO;
import com.hyeobjin.application.dto.manu.ManufactureDTO;
import com.hyeobjin.domain.entity.Manufacturer;
import com.hyeobjin.domain.repository.ManufacturerRepository;
import com.hyeobjin.exception.DuplicateManufacturerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.management.monitor.StringMonitor;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    /**
     * @param menuName
     * 제조사명을 입력하여 해당 제조사의 pk 를 반환하여 제품등록 테이블에서 저장
     * @return menuEntity id(pk)
     */
    public Long findIdByManuName(String menuName) {
        return manufacturerRepository.findManufacturerByManuName(menuName);
    }

    /**
     * 새로운 제조사 추가
     */
    public void saveManu(ManufactureDTO manufactureDTO) {

        Boolean exists = manufacturerRepository.existsByManuName(manufactureDTO.getManuName());

        if (exists) {
            // TODO alert()
            log.info("이미 존재하는 제조사 입니다.");
            throw new DuplicateManufacturerException("이미 존재하는 제조사명입니다.");
        }
            Manufacturer manufacturer = Manufacturer.builder()
                    .manuName(manufactureDTO.getManuName())
                    .build();

            manufacturerRepository.save(manufacturer);
            log.info("새로운 제조사가 등록 되었습니다. {}", manufacturer.getManuName());
    }

    /**
     * 모든 제조사 조회 (user view)
     * # 관리자가 삭제한 제조사 (manuYN = Y) 제외
     */
    public List<ManufactureDTO> findAll() {

        List<Manufacturer> manufacturerList = manufacturerRepository.findAll();

        return manufacturerList.stream()
                .map(manufacturer -> new ManufactureDTO(
                        manufacturer.getId(),
                        manufacturer.getManuName(),
                        manufacturer.getManuYN()))
                .filter(manufactureDTO -> "N".equals(manufactureDTO.getManuYN()))
                .toList();
    }

    /**
     *
     * @param manufactureDTO
     * # 관리자가 기존 제조사명 변경
     * @return
     */
    public Integer update(ManufactureDTO manufactureDTO) {

        return manufacturerRepository.updateManuName(
                manufactureDTO.getManuId(),
                manufactureDTO.getManuName());
    }

    /**
     *
     * @param manufactureDTO
     * # 관리자가 제조사를 삭제 (manuYN : N -> Y)
     * @return
     */
    public Integer delete(ManufactureDTO manufactureDTO) {

        return manufacturerRepository.deleteManufacturer(
                manufactureDTO.getManuId());
    }

    /**
     * # 관리자가 삭제했던 제조사 복구 (manuYN : Y -> N)
     */
    public Integer restore(ManufactureDTO manufactureDTO) {

        return manufacturerRepository.restoreManufacturer(
                manufactureDTO.getManuId());
    }
}
