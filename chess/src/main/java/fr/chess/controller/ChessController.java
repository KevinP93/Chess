package fr.chess.controller;

import fr.chess.model.Board;
import fr.chess.model.Piece;
import fr.chess.service.ChessRules;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chess")
public class ChessController {

    private final Board board;
    private final ChessRules chessRules;

    public ChessController() {
        this.board = new Board();
        this.chessRules = new ChessRules();
    }

    @GetMapping("/board")
    public List<Piece> getBoard() {
        return board.getPieces();
    }

    @PostMapping("/move")
    public String movePiece(@RequestBody MoveRequest moveRequest) {
        Piece piece = board.getPieceAt(moveRequest.getX(), moveRequest.getY());
        if (piece != null && chessRules.isMoveValid(piece, moveRequest.getNewX(), moveRequest.getNewY(), board)) {
            board.movePiece(piece, moveRequest.getNewX(), moveRequest.getNewY());
            return "Mouvement effectué.";
        } else {
            return "Mouvement invalide.";
        }
    }

    @GetMapping("/check/{color}")
    public boolean isKingInCheck(@PathVariable String color) {
        return chessRules.isKingInCheck(color, board);
    }

    @GetMapping("/checkmate/{color}")
    public boolean isCheckmate(@PathVariable String color) {
        return chessRules.isCheckmate(color, board);
    }

    @Setter
    @Getter
    public static class MoveRequest {
        private int x;
        private int y;
        private int newX;
        private int newY;

    }
}
