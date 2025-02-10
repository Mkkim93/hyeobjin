package com.hyeobjin.application.admin.service.item;

import com.hyeobjin.application.admin.dto.item.glass.GlassSpecDTO;
import com.hyeobjin.application.admin.dto.item.glass.UpdateGlassSpecDTO;
import com.hyeobjin.domain.entity.item.GlassSpec;
import com.hyeobjin.domain.repository.item.GlassSpecRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GlassSpecService {

    private final GlassSpecRepository glassSpecRepository;

    public void save(String newGlassSpec) {

        GlassSpec glassSpec = new GlassSpec(newGlassSpec);

        glassSpecRepository.save(glassSpec);
    }

    public List<GlassSpecDTO> findGlassList() {

        List<GlassSpec> findList = glassSpecRepository.findAll();

      return findList.stream().map(glassSpec -> new GlassSpecDTO(
                glassSpec.getId(),
                glassSpec.getGlassSpec()
        )).collect(Collectors.toList());
    }

    public void update(Long glassSpecId, String glassSpecSize) {

        GlassSpec glassSpec = glassSpecRepository.findById(glassSpecId).orElseThrow(() -> new EntityNotFoundException("no entity"));

        glassSpec.updateGlassSpec(glassSpecSize);
    }

    public void delete(Long glassSpecId) {

        if(!glassSpecRepository.existsById(glassSpecId)) {
            throw new EntityNotFoundException("삭제할 제품 유리 스펙 존재하지 않습니다: " + glassSpecId);
        }
        glassSpecRepository.deleteById(glassSpecId);
    }

}
