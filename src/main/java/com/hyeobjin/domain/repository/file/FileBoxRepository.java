package com.hyeobjin.domain.repository.file;

import com.hyeobjin.domain.entity.file.FileBox;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileBoxRepository extends JpaRepository<FileBox, Long> {

    Boolean existsByBoardId(Long boardId);

    void deleteById(Long fileBoxId);

    FileBox findByBoardIdAndId(Long boardId, Long id);

    @Query("SELECT f.id FROM FileBox f WHERE f.item.id IN :itemIds")
    List<Long> findFileBoxIdsByItemIdIn(@Param("itemIds") List<Long> itemIds);

    @Query("select f.id from FileBox f where f.board.id in :boardIds")
    List<Long> findFileBoxIdsByBoardIdIn(@Param("boardIds") List<Long> boardIds);

    FileBox findByFileOrgName(String fileName);

    List<FileBox> findAllByInquiryId(Long inquiryId);
}
