package fr.chess.model;

import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Board {


    private List<Piece> pieces;

    public Board() {
        pieces = new ArrayList<>();
        initializeBoard();
    }
    public void initializeBoard() {
        // Ajouter les pions
        for (int i = 0; i < 8; i++) {
            pieces.add(new Pion("white", i, 6));  // Pions blancs
            pieces.add(new Pion("black", i, 1));  // Pions noirs
        }
        // Ajouter les Tours
        pieces.add(new Tour("white", 0, 7));
        pieces.add(new Tour("white", 7, 7));
        pieces.add(new Tour("black", 0, 0));
        pieces.add(new Tour("black", 7, 0));


        // Ajouter les Cavaliers
        pieces.add(new Cavalier("white", 1, 7));
        pieces.add(new Cavalier("white", 6, 7));
        pieces.add(new Cavalier("black", 1, 0));
        pieces.add(new Cavalier("black", 6, 0));

        // Ajouter les Fous
        pieces.add(new Fou("white", 2, 7));
        pieces.add(new Fou("white", 5, 7));
        pieces.add(new Fou("black", 2, 0));
        pieces.add(new Fou("black", 5, 0));

        // Ajouter les Dames
        pieces.add(new Dame("white", 3, 7));
        pieces.add(new Dame("black", 3, 0));

        // Ajouter les Rois
        pieces.add(new Roi("white", 4, 7));
        pieces.add(new Roi("black", 4, 0));
    }


    public Piece getPieceAt(int x, int y) {
        return pieces.stream()
                .filter(piece -> piece.getX() == x && piece.getY() == y)
                .findFirst()
                .orElse(null);
    }


    public boolean movePiece(Piece piece, int newX, int newY) {
        if (piece != null) {
            piece.move(newX, newY);
            return true;
        }
        return false;
    }

}
