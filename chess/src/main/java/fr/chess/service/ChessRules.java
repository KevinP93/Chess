package fr.chess.service;

import fr.chess.model.*;

public class ChessRules {

    // Valide si un mouvement est autorisé pour une pièce donnée
    public boolean isMoveValid(Piece piece, int newX, int newY, Board board) {
        if (piece instanceof Pion) {
            return isPionMoveValid((Pion) piece, newX, newY, board);
        } else if (piece instanceof Tour) {
            return isTourMoveValid((Tour) piece, newX, newY, board);
        }
        else if (piece instanceof Fou) {
            return isFouMoveValid((Fou) piece, newX, newY, board);
        }
        else if (piece instanceof Cavalier) {
            return isCavalierMoveValid((Cavalier) piece, newX, newY, board);
        }
        else if (piece instanceof Roi) {
            return isRoiMoveValid((Roi) piece, newX, newY, board);
        }
        else if (piece instanceof Dame) {
            return isDameMoveValid((Dame) piece, newX, newY, board);
        }

        return false;
    }

    // Validation pour les pions
    private boolean isPionMoveValid(Pion pion, int newX, int newY, Board board) {
       int direction = pion.getColor().equalsIgnoreCase("white") ? -1 : 1;

        // Avancer d'une case
        if (newX == pion.getX() && newY == pion.getY() + direction) {
            return board.getPieceAt(newX, newY) == null;
        }

        // Avancer de deux cases si en position initiale
        if (newX == pion.getX() && newY == pion.getY() + 2 * direction) {
            boolean isStartingPosition = (pion.getColor().equalsIgnoreCase("white") && pion.getY() == 6)
                    || (pion.getColor().equalsIgnoreCase("black") && pion.getY() == 1);
            return isStartingPosition && board.getPieceAt(newX, newY) == null;
        }

        // Capture en diagonale
        if (Math.abs(newX - pion.getX()) == 1 && newY == pion.getY() + direction) {
            Piece target = board.getPieceAt(newX, newY);
            return target != null && !target.getColor().equalsIgnoreCase(pion.getColor());
        }
        return false;
    }

    // Validation pour les tours
    private boolean isTourMoveValid(Tour tour, int newX, int newY, Board board) {

        // La tour se déplace en ligne droite (horizontalement ou verticalement)
        if (tour.getX() != newX && tour.getY() != newY) {
            return false;
        }

        // Vérification des obstacles sur le chemin
        if (tour.getX() == newX) { // Déplacement vertical
            int step = tour.getY() < newY ? 1 : -1;
            for (int y = tour.getY() + step; y != newY; y += step) {
                if (board.getPieceAt(newX, y) != null) {
                    return false;
                }
            }
        } else { // Déplacement horizontal
            int step = tour.getX() < newX ? 1 : -1;
            for (int x = tour.getX() + step; x != newX; x += step) {
                if (board.getPieceAt(x, tour.getY()) != null) {
                    return false;
                }
            }
        }

        // Vérifie la case cible (doit être vide ou contenir une pièce ennemie)
        Piece target = board.getPieceAt(newX, newY);
        return target == null || !target.getColor().equalsIgnoreCase(tour.getColor());
    }

    private boolean isFouMoveValid(Fou fou, int newX, int newY, Board board) {
        int startX = fou.getX();
        int startY = fou.getY();

        // Vérifier si le mouvement est bien en diagonale
        if (Math.abs(newX - startX) != Math.abs(newY - startY)) {
            return false; // Pas un déplacement en diagonale
        }

        // Vérifier s'il y a une pièce sur le chemin
        int deltaX = (newX > startX) ? 1 : -1; // Direction en X
        int deltaY = (newY > startY) ? 1 : -1; // Direction en Y

        int x = startX + deltaX;
        int y = startY + deltaY;

        while (x != newX && y != newY) {
            if (board.getPieceAt(x, y) != null) { // Il y a une pièce sur le chemin
                return false;
            }
            x += deltaX;
            y += deltaY;
        }

        // Vérifier si la case d'arrivée est occupée par une pièce de la même couleur
        Piece destinationPiece = board.getPieceAt(newX, newY);
        if (destinationPiece != null && destinationPiece.getColor().equals(fou.getColor())) {
            return false; // Ne peut pas capturer une pièce de la même couleur
        }

        return true; // Mouvement valide
    }

    private boolean isCavalierMoveValid(Cavalier cavalier, int newX, int newY, Board board){
        return false;
    }
    private boolean isRoiMoveValid(Roi roi, int newX, int newY, Board board){
        return false;
    }
    private boolean isDameMoveValid(Dame dame, int newX, int newY, Board board){
        return false;
    }

}
