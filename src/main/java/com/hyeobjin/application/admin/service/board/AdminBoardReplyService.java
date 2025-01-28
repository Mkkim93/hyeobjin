package com.hyeobjin.application.admin.service.board;

import com.hyeobjin.application.admin.dto.board.FindAdminBoardDTO;
import com.hyeobjin.domain.entity.board.Board;
import com.hyeobjin.domain.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminBoardReplyService {

    private final BoardRepository boardRepository;

    public Page<FindAdminBoardDTO> searchKeywordList(String searchKeyword, Pageable pageable) {

        Page<Board> keyWordList = boardRepository.findByBoardTitleContaining(pageable, searchKeyword);

        return keyWordList.map(board -> new FindAdminBoardDTO(
                board.getId(),
                board.getBoardTitle(),
                board.getBoardViewCount(),
                board.getBoardRegDate(),
                board.getBoardUpdate(),
                board.getBoardType(),
                board.getBoardYN(),
                board.getUsers().getName()
        ));
    }

}
