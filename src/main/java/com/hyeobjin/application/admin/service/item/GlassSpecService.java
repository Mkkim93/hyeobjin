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

        GlassSpec glassSpec = new GlassSpec();
        glassSpec.setGlassSpec(newGlassSpec);

        glassSpecRepository.save(glassSpec);
    }

    public List<GlassSpecDTO> findGlassList() {

        List<GlassSpec> findList = glassSpecRepository.findAll();

      return findList.stream().map(glassSpec -> new GlassSpecDTO(
                glassSpec.getId(),
                glassSpec.getGlassSpec()
        )).collect(Collectors.toList());
    }

    public void update(UpdateGlassSpecDTO updateGlassSpecDTO) {

        GlassSpec glassSpec = glassSpecRepository.findById(updateGlassSpecDTO.getGlassSpecId()).orElseThrow(() -> new EntityNotFoundException("no entity"));

        glassSpec.updateGlassSpec(glassSpec.getId(), updateGlassSpecDTO.getUpdateGlassSize());
    }

    public void delete(List<Long> glassSpecId) {
        glassSpecRepository.deleteAllById(glassSpecId);
    }

    public GlassSpec findById(Long glassId) {
        return glassSpecRepository.findById(glassId).orElseThrow(() -> new EntityNotFoundException("해당 스펙 조회 도중 오류가 발생 했습니다."));
    }
}
