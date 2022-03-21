package com.example.echecfxml.classe;

import com.example.echecfxml.Main_Controller;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.security.cert.PolicyNode;

public class Chessboard {
    private final int h_tab = 8, w_tab = 8;
    private final GridPane tableau_jeu_gridpane = new GridPane(), dead_pawn_P1_Gridpane = new GridPane(), dead_pawn_P2_Gridpane = new GridPane();
    private final Rectangle[][] rectangles_board = new Rectangle[h_tab][w_tab], rectangles_players = new Rectangle[h_tab][w_tab];
    private Player player1, player2;
    private Case[][] chessboard = new Case[h_tab][w_tab];
    private Log log;

    public Chessboard() {
        log = new Log();
    }


    /**
    * Create a dead section for the pawn with the isout statut true
    *
     * @param dead_pawn_P2_HBox
     * @param dead_pawn_P1_HBox*/
    public void create_dead_place(HBox dead_pawn_P1_HBox, HBox dead_pawn_P2_HBox) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle rectangle_P1 = new Rectangle(40, 40), rectangle_P2 = new Rectangle(40, 40);
                rectangle_P1.setFill(Color.RED);
                rectangle_P2.setFill(Color.RED);

                dead_pawn_P1_Gridpane.add(rectangle_P1, i, j);
                dead_pawn_P2_Gridpane.add(rectangle_P2, i, j);
            }
        }
        dead_pawn_P1_Gridpane.setAlignment(Pos.CENTER);
        dead_pawn_P1_Gridpane.setGridLinesVisible(true);
        dead_pawn_P1_Gridpane.setHgap(5);
        dead_pawn_P1_Gridpane.setVgap(5);

        dead_pawn_P2_Gridpane.setAlignment(Pos.CENTER);
        dead_pawn_P2_Gridpane.setGridLinesVisible(true);
        dead_pawn_P2_Gridpane.setHgap(5);
        dead_pawn_P2_Gridpane.setVgap(5);

        dead_pawn_P1_HBox.getChildren().add(dead_pawn_P1_Gridpane);
        dead_pawn_P2_HBox.getChildren().add(dead_pawn_P2_Gridpane);
    }


    /**
     *
     * @param echecTabPane
     * @param p1_name
     * @param p2_name
     * @param dead_pawn_P1_HBox
     * @param dead_pawn_P2_HBox
     *
     *>Create 2 players
     * set nickname
     * create there pawns
     * create chessboard
     * set all the pawn per players
     *
     */
    public void create_chessboard(Pane echecTabPane, String p1_name, String p2_name, HBox dead_pawn_P1_HBox, HBox dead_pawn_P2_HBox) {
        player1 = new Player();
        player2 = new Player();

        player1.setName(p1_name);
        player2.setName(p2_name);

        player1.create_pawns(1);
        player2.create_pawns(2);

        create_dead_place(dead_pawn_P1_HBox,dead_pawn_P2_HBox);

        int h_tab = 8;
        int w_tab = 8;

        chessboard = new Case[h_tab][w_tab];
        int e = 0;

        //fill tab White Black
        for (int i = 0; i < chessboard.length; i++) {
            e++;
            for (int j = 0; j < chessboard.length; j++) {
                chessboard[i][j] = new Case();
                e++;
                Rectangle rectangle = new Rectangle(40, 40);

                rectangle.setOnDragOver(event -> {
                    rectangle.setFill(Color.GREEN);
                    event.acceptTransferModes(TransferMode.MOVE);
                    event.consume();
                });

                if (e % 2 == 0) {
                    rectangle.setOnDragExited(event -> {
                        rectangle.setFill(Color.WHITE);
                        event.consume();
                    });
                    rectangle.setFill(Color.WHITE);
                } else {
                    rectangle.setOnDragExited(event -> {
                        rectangle.setFill(Color.BLACK);
                        event.consume();
                    });
                    rectangle.setFill(Color.BLACK);
                }
                rectangles_board[i][j] = rectangle;
                tableau_jeu_gridpane.add(rectangle, i, j);
            }
        }

        for (Pawn pawn : player1.getTab_player()) {
            chessboard[pawn.getX()][pawn.getY()].setPawn(pawn);
            chessboard[pawn.getX()][pawn.getY()].setIs_occupied(true);

            Rectangle rectangle = new Rectangle(20, 40);

            GridPane.setValignment(rectangle, VPos.CENTER);
            GridPane.setHalignment(rectangle, HPos.CENTER);

            rectangle.setFill(new ImagePattern(pawn.getImage()));

            tableau_jeu_gridpane.add(rectangle, pawn.getX(), pawn.getY());
            rectangles_players[pawn.getX()][pawn.getY()] = rectangle;
        }

        for (Pawn pawn : player2.getTab_player()) {
            chessboard[pawn.getX()][pawn.getY()].setPawn(pawn);
            chessboard[pawn.getX()][pawn.getY()].setIs_occupied(true);

            Rectangle rectangle = new Rectangle(20, 40);

            GridPane.setValignment(rectangle, VPos.CENTER);
            GridPane.setHalignment(rectangle, HPos.CENTER);

            rectangle.setFill(new ImagePattern(pawn.getImage()));

            tableau_jeu_gridpane.add(rectangle, pawn.getX(), pawn.getY());
            rectangles_players[pawn.getX()][pawn.getY()] = rectangle;
        }

        tableau_jeu_gridpane.setGridLinesVisible(true);
        tableau_jeu_gridpane.setAlignment(Pos.CENTER);
        tableau_jeu_gridpane.setMinSize(echecTabPane.getPrefWidth(), echecTabPane.getPrefHeight());

        echecTabPane.getChildren().add(tableau_jeu_gridpane);
    }

    /**
     * enable the movement to a player
     * drag and drop system
     * check if the move is "possible"
     */

    public void enable_move(Player player, VBox vBox, int nbr_tour) {
        for (Pawn pawn : player.getTab_player()) {
            rectangles_players[pawn.getX()][pawn.getY()].setOnDragDetected(event_top -> {
                Dragboard dragboard = rectangles_board[pawn.getX()][pawn.getY()].startDragAndDrop(TransferMode.ANY);
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putImage(pawn.getImage());
                dragboard.setContent(clipboardContent);

                for (Rectangle[] rectangle_tab : rectangles_board) {
                    for (Rectangle rectangle : rectangle_tab) {
                        rectangle.setOnDragDropped(event -> {
                            //get coord of the rect
                            int x = GridPane.getColumnIndex(event.getPickResult().getIntersectedNode()),
                                    y = GridPane.getRowIndex(event.getPickResult().getIntersectedNode());
                            if (check_move(x, y, pawn, vBox)) {
                                check_eat(x, y, pawn);
                                move(x, y, pawn);
                                //log write
                                //System.out.println(nbr_tour + " " + player + " " + x + " " + y + " " + pawn.getValue());
                                log.write_log(nbr_tour, player, x, y, pawn.getValue());

                                //clear bottom
                                vBox.getChildren().clear();
                                Main_Controller.setPlayer_play(true);
                            }
                            event.setDropCompleted(true);
                            event.consume();
                        });
                    }
                }
                event_top.consume();
            });

            rectangles_players[pawn.getX()][pawn.getY()].setOnDragOver(event -> {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            });
        }
    }

    /**
     *
     * @param x
     * @param y
     * @param pawn
     *
     * check if there is some pawn between the actual position of the pawn and his futur position
     */
    public void check_eat(int x, int y, Pawn pawn) {

    }

    /**
     *
     * @param x
     * @param y
     * @param pawn
     *
     * move the pawn
     */
    public void move(int x, int y, Pawn pawn) {
        chessboard[pawn.getX()][pawn.getY()].setPawn(null);
        chessboard[pawn.getX()][pawn.getY()].setIs_occupied(false);

        //System.out.println("x pawn : " + pawn.getX() + " y pawn :" + pawn.getY()+"\nx : "+x + " y "+ y);

        //move the pawn to the rect
        rectangles_players[x][y] = rectangles_players[pawn.getX()][pawn.getY()];
        chessboard[x][y].setPawn(pawn);
        chessboard[x][y].setIs_occupied(true);
        Platform.runLater(() -> {
            tableau_jeu_gridpane.getChildren().remove(rectangles_players[pawn.getX()][pawn.getY()]);
            tableau_jeu_gridpane.add(rectangles_players[pawn.getX()][pawn.getY()], x, y);
        });
    }


    /**
     *
     * @param player
     *
     * remove the ability to move
     */
    public void disable_move(Player player) {
        for (Pawn pawn : player.getTab_player()) {
            rectangles_players[pawn.getX()][pawn.getY()].setOnDragDetected(Event::consume);
        }
    }

    /**
     *
     * @param x
     * @param y
     * @param pawn_player
     * @param vBox
     * @return
     *
     * check the move of the player
     *      * check if the pawn move on another pawn
     */
    private boolean check_move(int x, int y, Pawn pawn_player, VBox vBox) {
        if (!chessboard[x][y].isIs_occupied()) {
            if (pawn_player.getLegal_move() != null) {

                //check legal move
                boolean valid_move = false;
                /*for (Integer[] pos : pawn_player.getLegal_move(pawn_player)) {
                    if (pos[0] == x && pos[1] == y) {
                        valid_move = true;
                        break;
                    }
                }
                if (!valid_move) {
                    Label label = new Label("Impossible to reach");
                    label.setStyle("-fx-text-fill: red");
                    vBox.getChildren().clear();
                    vBox.getChildren().add(label);
                    return false;
                }*/
            }
            return true;
        } else {
            Label label = new Label("Can't place a pawn  on a pawn");
            label.setStyle("-fx-text-fill: red");
            vBox.getChildren().clear();
            vBox.getChildren().add(label);
            return false;
        }
    }


    /**
     *
     * @return
     *
     * check if the game is finished when one player has 0 pawn
     */
    public int is_game_over() {
        if (player1.getNombre_pawn() == 0) {
            return 1;
        } else if (player2.getNombre_pawn() == 0) {
            return 2;
        } else {
            return 0;
        }

    }


    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Case[][] getChessboard() {
        return chessboard;
    }

    public void setChessboard(Case[][] chessboard) {
        this.chessboard = chessboard;
    }
}
