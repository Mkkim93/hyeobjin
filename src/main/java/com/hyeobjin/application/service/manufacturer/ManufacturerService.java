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

@Slf4j
@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    /**
     *
     * @param menuName
     * 제조사명을 입력하여 해당 제조사의 pk 를 반환하여 제품등록 테이블에서 저장
     * @return menuEntity id(pk)
     */
    public Long findIdByManuName(String menuName) {
        Manufacturer findByManuName = manufacturerRepository.findManufacturerByManuName(menuName);
        // TODO menuName NotFoundException..
        return new ManufactureDTO().findByIdToDTO(findByManuName);
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
}
