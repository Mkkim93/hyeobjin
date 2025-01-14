package com.hyeobjin.domain.repository.file;

import com.hyeobjin.domain.entity.file.FileBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileBoxRepository extends JpaRepository<FileBox, Long> {

    Boolean existsByBoardId(Long boardId);

    void deleteById(Long fileBoxId);

    FileBox findByBoardIdAndId(Long boardId, Long id);

}
