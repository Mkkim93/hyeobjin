package com.hyeobjin.application.admin.service.fix;

import com.hyeobjin.domain.entity.manufacturer.Manufacturer;
import com.hyeobjin.domain.repository.manu.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * adminManuService 와 adminItemService 순환 참조로 인한 중간 서비스 레이어 구현
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminItemManuService {

    private final ManufacturerRepository manufacturerRepository;

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
}
