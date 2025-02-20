package com.hyeobjin.application.admin.service.manu;

import com.hyeobjin.application.admin.dto.manu.FindManufacturerDTO;
import com.hyeobjin.application.admin.service.item.AdminItemService;
import com.hyeobjin.application.common.dto.manu.ManufactureDTO;
import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import com.hyeobjin.domain.repository.manu.ManuFactJpaRepository;
import com.hyeobjin.domain.repository.manu.ManufacturerRepository;
import com.hyeobjin.exception.DuplicateManufacturerException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AdminManuService {

    private final ManuFactJpaRepository manuFactJpaRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final AdminItemService adminItemService;

    @Autowired
    public AdminManuService(ManuFactJpaRepository manuFactJpaRepository,
                            ManufacturerRepository manufacturerRepository,
                            AdminItemService adminItemService) {
        this.manuFactJpaRepository = manuFactJpaRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.adminItemService = adminItemService;
    }

    /**
     * 관리자 목록을 조회할 때 해당 제조사에 등록된 제품의 개수를 카운팅하여 조회
     * @return
     */
    public List<FindManufacturerDTO> findManuCountAll() {
        return manuFactJpaRepository.findAll();
    }

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

    /**
     * 새로운 제조사 추가
     */
    public void saveManu(ManufactureDTO manufactureDTO) {

        Boolean exists = manufacturerRepository.existsByManuName(manufactureDTO.getManuName());

        if (exists) {
            log.info("이미 존재하는 제조사 입니다.");
            throw new DuplicateManufacturerException("이미 존재하는 제조사명입니다.");
        }

        Manufacturer manufacturer = Manufacturer.builder()
                .manuName(manufactureDTO.getManuName())
                .manuYN(manufactureDTO.getManuYN())
                .build();

        manufacturerRepository.save(manufacturer);
        log.info("새로운 제조사가 등록 되었습니다. {}", manufacturer.getManuName());
    }

    /**
     *
     * @param manufactureDTO
     * # 관리자가 기존 제조사와 등록/미등록 수정
     * @return
     */
    public void update(ManufactureDTO manufactureDTO) {
        Manufacturer manufacturer = manufacturerRepository
                .findById(manufactureDTO.getManuId())
                .orElseThrow(() -> new EntityNotFoundException("해당 제조사가 존재하지 않습니다."));

        if (manufactureDTO.getManuName() != null) {
            manufacturer.adminUpdateManuName(manufactureDTO.getManuName());
        }
        if (manufactureDTO.getManuYN() != null) {
            manufacturer.adminUpdateManuYN(manufactureDTO.getManuYN());
        }
        manufacturerRepository.save(manufacturer);
    }
}
