package com.hyeobjin.application.common.service.board;

import com.hyeobjin.application.common.dto.board.BoardListDTO;
import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardReplyService {

    private final BoardRepository boardRepository;

    public Page<BoardListDTO> searchKeywordList(String searchKeyword, Pageable pageable, String boardType) {

        String boardYN = "Y";

        Page<Board> keyWordList = boardRepository.findByBoardYNAndBoardTitleContainingAndBoardType(boardYN, searchKeyword, boardType, pageable);

        return keyWordList.map(board -> new BoardListDTO(
                board.getId(),
                board.getBoardTitle(),
                board.getBoardViewCount(),
                board.getBoardRegDate(),
                board.getUsers().getName()
        ));
    }
}
