package fr.chess.model;

public class Tour extends Piece {

    public Tour(String color, int x, int y) {
        super(color, "Tour", x, y);
    }

    @Override
    public Tour clone() {
        return new Tour(this.color, getX(), getY());
    }

}