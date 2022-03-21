package com.example.echecfxml;

import com.example.echecfxml.classe.Chessboard;
import com.example.echecfxml.classe.Log;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Main_Controller implements Initializable {
    public static int nbr_tour = 1;
    public static Chessboard chessboard = new Chessboard();
    public static boolean player_play = false;
    public static int selected_player = 1;
    public Log log = new Log();
    public String p2_name = "P2", p1_name = "P1";

    public BorderPane main_BorderPane;
    public Pane echecTabPane;
    public HBox dead_pawn_P2_Hbox, dead_pawn_P1_Hbox;
    public VBox p1_Vbox, tour_Vbox, p2_Vbox, bottom_Vbox;
    public Label point_P1_Label, tour_Label, point_p2_Label;

    public static int getNbr_tour() {
        return nbr_tour;
    }

    public static void setNbr_tour(int nbr_tour) {
        Main_Controller.nbr_tour = nbr_tour;
    }

    public static Chessboard getChessboard() {
        return chessboard;
    }

    public static boolean isPlayer_play() {
        return player_play;
    }

    public static void setPlayer_play(boolean player_play) {
        Main_Controller.player_play = player_play;
    }

    public static int getSelected_player() {
        return selected_player;
    }

    public static void setSelected_player(int selected_player) {
        Main_Controller.selected_player = selected_player;
    }

    public VBox getBottom_Vbox() {
        return bottom_Vbox;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Init
        chessboard.create_chessboard(echecTabPane, p1_name, p2_name, dead_pawn_P1_Hbox, dead_pawn_P2_Hbox);

        //if log exist
        if (log.is_game_log()) {
            bottom_Vbox.setVisible(true);
            Label label = new Label("Game available");

            HBox hBox = new HBox();
            Button button_Y = new Button("Yes"), button_N = new Button("No");

            button_N.setOnAction(event -> {
                bottom_Vbox.setVisible(false);
                bottom_Vbox.getChildren().clear();
                log.clear_log();
            });
            button_Y.setOnAction(event -> {
                log.read_log();
                Platform.runLater(() -> tour_Label.setText(String.valueOf(nbr_tour)));
                bottom_Vbox.setVisible(false);
                bottom_Vbox.getChildren().clear();
            });

            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(10);

            hBox.getChildren().addAll(button_Y, button_N);

            bottom_Vbox.getChildren().addAll(label, hBox);
        }

        //thread that run out of the main thread
        //give a player the ability to move with a tictoc system
        Task<Void> task = new Task<>() {
            int winner;

            @Override
            protected Void call() {
                while ((winner = chessboard.is_game_over()) == 0) {
                    while (!player_play) {
                        switch (selected_player) {
                            case 1:
                                p2_Vbox.setStyle("-fx-background-color: black;");
                                p1_Vbox.setStyle("-fx-background-color: green;");
                                chessboard.enable_move(chessboard.getPlayer1(), bottom_Vbox, nbr_tour);
                                break;
                            case 2:
                                p1_Vbox.setStyle("-fx-background-color: black;");
                                p2_Vbox.setStyle("-fx-background-color: green;");
                                chessboard.enable_move(chessboard.getPlayer2(), bottom_Vbox, nbr_tour);
                                break;
                        }
                    }


                    //change to the other player
                    switch (selected_player) {
                        case 1:
                            //disable pawn current player
                            chessboard.disable_move(chessboard.getPlayer1());
                            selected_player = 2;
                            break;
                        case 2:
                            //disable pawn current player
                            chessboard.disable_move(chessboard.getPlayer2());
                            selected_player = 1;
                            break;
                    }
                    player_play = false;
                    nbr_tour++;
                    Platform.runLater(() -> tour_Label.setText(String.valueOf(nbr_tour)));
                }

                if (winner == 1) {
                    p1_Vbox.setStyle("-fx-background-color: Yellow;");
                } else if (winner == 2) {
                    p2_Vbox.setStyle("-fx-background-color: Yellow;");
                }
                return null;
            }
        };
        new Thread(task).start();
    }

    /*
     * Bonjour monsieur , desole de ne pas avoir bosser plus ce projet la j'ai fais deux trois truc
     * mais j'ai pas mal de projet derriere et je vous avoue que je connais assez le javafx.
     * Je bosse actuellement sur un systeme de voiture controlable via un site internet pour pouvoir
     * rouler comme dans un jeu dans la vrai vie, j'ai mis en place un front complet avec un back pas encore
     * fini tant que je n'ai pas fais l'electronique de la voiture ce qui me prend le plus de temps etant donné
     * que je dois trouver les composants compatible entre eux et je dois ecrire un driver pour la camera
     * non standart qui me donnera une vue de la voiture pendant qu'elle roule , enfin bref je ne donne qu'un de
     * mes gros projet mais voila j'espere que mon travail est suffisant.
     * */
}