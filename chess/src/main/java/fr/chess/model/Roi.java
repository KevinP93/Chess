package fr.chess.model;

public class Roi extends Piece{

    public Roi(String color, int x, int y) {
        super(color, "Roi", x, y);
    }

    @Override
    public Roi clone() {
        return new Roi(this.color, getX(), getY());
    }
}
