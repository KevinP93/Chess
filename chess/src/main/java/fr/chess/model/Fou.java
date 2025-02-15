package fr.chess.model;

public class Fou extends Piece{

    public Fou(String color, int x, int y) {
        super(color, "Fou", x, y);
    }

    @Override
    public Fou clone() {
        return new Fou(this.color, getX(), getY());
    }
}
