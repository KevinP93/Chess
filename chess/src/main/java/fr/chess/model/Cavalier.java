package fr.chess.model;

public class Cavalier extends Piece{

    public Cavalier(String color, int x, int y) {
        super(color, "Cavalier", x, y);
    }

    @Override
    public Cavalier clone() {
        return new Cavalier(this.color, getX(), getY());
    }
}
