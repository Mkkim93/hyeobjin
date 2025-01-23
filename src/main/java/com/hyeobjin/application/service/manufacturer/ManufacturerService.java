package com.hyeobjin.application.service.manufacturer;

import com.hyeobjin.application.admin.dto.manu.FindManufacturerDTO;
import com.hyeobjin.application.dto.manu.ManufactureDTO;
import com.hyeobjin.application.service.item.ItemService;
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
     * 관리자 목록을 조회할 때 해당 제조사에 등록된 제품의 개수를 카운팅하여 조회
     * @return
     */
    public List<FindManufacturerDTO> findManuCountAll() {
        return manuFactJpaRepository.findAll();
    }

    /**
     * @param menuName
     * 제조사명을 입력하여 해당 제조사의 pk 를 반환하여 제품등록 테이블에서 저장
     * @return menuEntity id(pk)
     */
    public Manufacturer findIdByManuName(String menuName) {
        Manufacturer findManuFact = manufacturerRepository.findManufacturerByManuName(menuName);
        if (findManuFact == null) {
            log.info("존재하지 않는 제조사 입니다.");
            throw new RuntimeException("존재하지 않는 제조사"); // TODO CustomException 정의
        }
        return findManuFact;
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
                    .manuYN(manufactureDTO.getManuYN())
                    .build();

            manufacturerRepository.save(manufacturer);
            log.info("새로운 제조사가 등록 되었습니다. {}", manufacturer.getManuName());
    }

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



    /**
     * # 관리자가 삭제했던 제조사 복구 (manuYN : Y -> N)
     */
    public Integer restore(ManufactureDTO manufactureDTO) {

        return manufacturerRepository.restoreManufacturer(
                manufactureDTO.getManuId());
    }
}
