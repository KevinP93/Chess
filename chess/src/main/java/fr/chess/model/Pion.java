package fr.chess.model;

import lombok.Getter;

@Getter
public class Pion extends Piece {

    public Pion(String color, int x, int y) {
        super(color, "Pion", x, y);
    }
}
