package fr.chess.service;

import fr.chess.model.*;

import java.util.ArrayList;
import java.util.List;

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
        return destinationPiece == null || !destinationPiece.getColor().equals(fou.getColor()); // Ne peut pas capturer une pièce de la même couleur
// Mouvement valide
    }

    private boolean isCavalierMoveValid(Cavalier cavalier, int newX, int newY, Board board) {
        int startX = cavalier.getX();
        int startY = cavalier.getY();

        // Liste des déplacements possibles en "L"
        int[][] possibleMoves = {
                { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 },
                { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }
        };

        // Vérifier si le mouvement est dans les possibilités
        boolean validMove = false;
        for (int[] move : possibleMoves) {
            if (startX + move[0] == newX && startY + move[1] == newY) {
                validMove = true;
                break;
            }
        }

        if (!validMove) return false; // Pas un déplacement en "L"

        // Vérifie si la case d'arrivée contient une pièce de la même couleur
        Piece destinationPiece = board.getPieceAt(newX, newY);
        return destinationPiece == null || !destinationPiece.getColor().equals(cavalier.getColor());
    }

    //TODO: Implementer une regle pour le roc
    private boolean isRoiMoveValid(Roi roi, int newX, int newY, Board board) {
        int startX = roi.getX();
        int startY = roi.getY();

        // Vérifie si le déplacement est de 1 case dans n'importe quelle direction
        if (Math.abs(newX - startX) > 1 || Math.abs(newY - startY) > 1) {
            return false;
        }

        // si la case d'arrivée est occupée par une pièce de la même couleur
        Piece destinationPiece = board.getPieceAt(newX, newY);
        return destinationPiece == null || !destinationPiece.getColor().equals(roi.getColor());
    }

    private boolean isDameMoveValid(Dame dame, int newX, int newY, Board board) {
        int startX = dame.getX();
        int startY = dame.getY();

        // Vérifie si le mouvement est en ligne droite (tour) ou en diagonale (fou)
        boolean isStraightMove = (startX == newX || startY == newY); // Mouvement de la tour
        boolean isDiagonalMove = (Math.abs(newX - startX) == Math.abs(newY - startY)); // Mouvement du fou

        if (!isStraightMove && !isDiagonalMove) {
            return false;
        }

        // Vérifier si le chemin est libre
        int deltaX = Integer.compare(newX, startX); // 1, -1 ou 0
        int deltaY = Integer.compare(newY, startY); // 1, -1 ou 0

        int x = startX + deltaX;
        int y = startY + deltaY;

        while (x != newX || y != newY) {
            if (board.getPieceAt(x, y) != null) { // Une pièce bloque le passage
                return false;
            }
            x += deltaX;
            y += deltaY;
        }

        // Si la case d'arrivée est occupée par une pièce de la même couleur
        Piece destinationPiece = board.getPieceAt(newX, newY);
        return destinationPiece == null || !destinationPiece.getColor().equals(dame.getColor());
    }

    public boolean isKingInCheck(String color, Board board) {
        Roi king = findKing(color, board);
        if (king == null) return false;

        int kingX = king.getX();
        int kingY = king.getY();

        // Si une pièce ennemie peut attaquer le roi
        for (Piece piece : board.getPieces()) {
            if (!piece.getColor().equalsIgnoreCase(color)) { // Pièce ennemie
                if (isMoveValid(piece, kingX, kingY, board)) {
                    return true; // Roi en échec
                }
            }
        }
        return false;
    }

    private Roi findKing(String color, Board board) {
        for (Piece piece : board.getPieces()) {
            if (piece instanceof Roi && piece.getColor().equalsIgnoreCase(color)) {
                return (Roi) piece;
            }
        }
        return null;
    }

    public boolean isCheckmate(String color, Board board) {
        if (!isKingInCheck(color, board)) return false;

        // Parcour de toutes les pièces
        for (Piece piece : board.getPieces()) {
            if (piece.getColor().equalsIgnoreCase(color)) {
                List<int[]> possibleMoves = getLegalMoves(piece, board);

                // Tester si un mouvement enlève l'échec
                for (int[] move : possibleMoves) {
                    int newX = move[0];
                    int newY = move[1];

                    // Simuler le mouvement
                    Board simulatedBoard = board.copyBoard();
                    simulatedBoard.movePiece(piece, newX, newY);

                    // Si après mouvement -> roi est encore en échec
                    if (!isKingInCheck(color, simulatedBoard)) {
                        return false; // Au moins un mouvement sauve le roi -> pas échec et mat
                    }
                }
            }
        }

        return true; // Aucun mouvement possible pour sortir de l’échec -> échec et mat
    }

    //Cette methode serviras à mettre en surbrillance les coups possible d'une piece ( un peu comme dans chess com)
    public List<int[]> getLegalMoves(Piece piece, Board board) {
        List<int[]> legalMoves = new ArrayList<>();
        int startX = piece.getX();
        int startY = piece.getY();

        // L'échiquier fait 8x8 cases
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (isMoveValid(piece, x, y, board)) {
                    legalMoves.add(new int[]{x, y});
                }
            }
        }
        return legalMoves;
    }

    public void promotePion(Pion pion, String newPieceType, Board board) {
        // Vérifie si le pion est arrivé sur la dernière rangée
        if ((pion.getColor().equalsIgnoreCase("white") && pion.getY() == 0) ||
                (pion.getColor().equalsIgnoreCase("black") && pion.getY() == 7)) {

            // Enlever le pion du plateau
            board.getPieces().remove(pion);

            // Créer la nouvelle pièce en fonction du type demandé
            Piece newPiece = null;
            switch (newPieceType.toLowerCase()) {
                case "dame":
                    newPiece = new Dame(pion.getColor(), pion.getX(), pion.getY());
                    break;
                case "tour":
                    newPiece = new Tour(pion.getColor(), pion.getX(), pion.getY());
                    break;
                case "fou":
                    newPiece = new Fou(pion.getColor(), pion.getX(), pion.getY());
                    break;
                case "cavalier":
                    newPiece = new Cavalier(pion.getColor(), pion.getX(), pion.getY());
                    break;
                default:
                    System.out.println("Type de promotion invalide.");
                    return;
            }

            // Ajout de la nouvelle pièce sur le plateau
            board.getPieces().add(newPiece);
        }
    }


}
