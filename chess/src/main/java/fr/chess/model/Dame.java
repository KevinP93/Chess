package fr.chess.model;

public class Dame extends Piece{

    public Dame(String color, int x, int y) {
        super(color, "Dame", x, y);
    }

    @Override
    public Dame clone() {
        return new Dame(this.color, getX(), getY());
    }
}
