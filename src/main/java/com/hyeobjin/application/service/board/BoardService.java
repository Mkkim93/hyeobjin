package com.hyeobjin.application.service.board;

import com.hyeobjin.application.dto.board.CreateBoardDTO;
import com.hyeobjin.application.dto.board.FindBoardDTO;
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
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<FindBoardDTO> findAll(Pageable pageable) {

        Page<Board> boardList = boardRepository.findAll(pageable);

        Page<FindBoardDTO> results = boardList.map(board -> {
            return new FindBoardDTO(
                    board.getId(),
                    board.getBoardTitle(),
                    board.getBoardViewCount(),
                    board.getBoardRegDate(),
                    board.getUsers().getUsername());
        });
        return results;
    }

    public void save(CreateBoardDTO createBoardDTO) {
        Board board = new Board().saveToEntity(createBoardDTO);
        boardRepository.save(board);
    }

}
