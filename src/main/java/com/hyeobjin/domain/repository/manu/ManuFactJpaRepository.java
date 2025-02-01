package com.hyeobjin.domain.repository.manu;

import com.hyeobjin.application.admin.dto.manu.FindManufacturerDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 관리자 전용 리포지토리
 */
@Repository
public class ManuFactJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public List<FindManufacturerDTO> findAll() {

        return em.createQuery(
                "select new com.hyeobjin.application.admin.dto.manu.FindManufacturerDTO(m.id, m.manuName, COUNT(i.id), m.manuYN) " +
                        "from Manufacturer m left join Item i on m.id = i.manufacturer.id " +
                        "group by m.id, m.manuName, m.manuYN",
                FindManufacturerDTO.class
        ).getResultList();
    }

    public List<Long> selectItemId(Long manuId) {

        return em.createQuery("select new com.hyeobjin.application.admin.dto.manu.FindManufacturerDTO(i.id) " +
                "from Manufacturer m left join Item i on m.id = i.manufacturer.id " +
                "where m.id = :manuId")
                .setParameter("manuId", manuId).getResultList();
    }
}
