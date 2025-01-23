package com.hyeobjin.application.admin.service.manu;

import com.hyeobjin.application.admin.dto.manu.FindManufacturerDTO;
import com.hyeobjin.application.admin.service.item.AdminItemService;
import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import com.hyeobjin.domain.repository.manu.ManuFactJpaRepository;
import com.hyeobjin.domain.repository.manu.ManufacturerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminManuService {

    private final ManuFactJpaRepository manuFactJpaRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final AdminItemService adminItemService;

    /**
     * 관리자 페이지에서 현재 등록된 제조사와 해당 제조사에 등록된 제품을 수를 조회
     */
    public List<FindManufacturerDTO> findAll() {
        return manuFactJpaRepository.findAll();
    }

    /**
     *
     * # 관리자가 제조사를 삭제
     * - 해당 제조사의 모든 제품과 파일 정보가 영구적으로 삭제된다.
     * @return
     */
    public synchronized void delete(Long manuId) {
        Manufacturer manufacturer = manufacturerRepository.findById(manuId)
                .orElseThrow(() -> new EntityNotFoundException("해당 제조사가 존재하지 않습니다."));

        List<Long> byItemIds = manufacturerRepository.findByItemIds(manuId);

        if (byItemIds == null || byItemIds.isEmpty()) {
            manufacturerRepository.delete(manufacturer);
            log.info("데이터가 없어서 제조사 삭제 성공!");
            return;
        }

        // 데이터가 존재하는 경우
        adminItemService.deleteItemIds(byItemIds);
        manufacturerRepository.delete(manufacturer);
        log.info("제품 데이터 삭제 후 제조사 삭제 성공!");
    }
}
