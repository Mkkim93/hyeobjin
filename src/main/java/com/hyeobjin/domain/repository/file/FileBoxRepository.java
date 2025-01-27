package com.hyeobjin.domain.repository.file;

import com.hyeobjin.domain.entity.file.FileBox;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FileBoxRepository extends JpaRepository<FileBox, Long> {

    Boolean existsByBoardId(Long boardId);

    void deleteById(Long fileBoxId);

    FileBox findByBoardIdAndId(Long boardId, Long id);

    @EntityGraph(attributePaths = {"item"})
    @Query("select f.isMain from FileBox f where f.item.id = :itemId and f.isMain = TRUE")
    Boolean existsByIsMain(@Param("itemId") Long itemId);

    @EntityGraph(attributePaths = {"item"})
    @Query("select f.id from FileBox f where f.item.id = :itemId and f.isMain = TRUE")
    Long findByDeleteFileBoxId(@Param("itemId") Long itemId);

    @Query("SELECT f.id FROM FileBox f WHERE f.item.id IN :itemIds")
    List<Long> findFileBoxIdsByItemIdIn(@Param("itemIds") List<Long> itemIds);
}
