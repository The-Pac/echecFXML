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
        chessboard.create_chessboard(echecTabPane, p1_name, p2_name);

        if (log.is_game_log()) {
            bottom_Vbox.setVisible(true);
            Label label = new Label("Game available");

            HBox hBox = new HBox();
            Button button_Y = new Button("Yes"), button_N = new Button("No");

            button_N.setOnAction(event -> {
                bottom_Vbox.setVisible(false);
                bottom_Vbox.getChildren().clear();
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
}