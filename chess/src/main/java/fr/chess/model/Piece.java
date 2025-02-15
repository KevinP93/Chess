package fr.chess.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract  class Piece {

    final String color;
    final String type;
    private int x;
    private int y;

    public Piece(String color, String type, int x, int y) {
        this.color = color;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    @Override
    public abstract Piece clone();


}
