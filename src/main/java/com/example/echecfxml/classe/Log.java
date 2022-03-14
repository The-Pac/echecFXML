package com.example.echecfxml.classe;


import com.example.echecfxml.Main_Controller;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;

public class Log {
    private FileWriter fileWriter;
    private FileReader fileReader;

    public Log() {
    }

    /**
     * write a mouvement in a file text in format json
     */
    public void write_log(int tour, Player player, int x, int y, String pawn_value) {
        //les libs de ne joignent pas sur les arrays
        /*JsonObject jsonObject = Json.createObjectBuilder()
                .add("tour", tour)
                .add("move", Json.createObjectBuilder()
                        .add("player_name", player.getName())
                        .add("player_color", player.getColor())
                        .add("player_pawn_nbr", player.getNombre_pawn())
                        .add("x", x)
                        .add("y", y)
                        .build())
                .build();*/
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("tour", tour)
                .add("player_name", player.getName())
                .add("player_pawn_nbr", player.getNombre_pawn())
                .add("player_pawn", pawn_value)
                .add("x", x)
                .add("y", y)
                .build();

        try {
            fileWriter = new FileWriter("last_sav.txt", true);
            fileWriter.write(jsonObject.toString() + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * clear the file
     */
    public void clear_log() {
        try {
            fileWriter = new FileWriter("last_sav.txt");
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * return if a log file exist or not
     */
    public boolean is_game_log() {
        try {
            fileReader = new FileReader("last_sav.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            if (bufferedReader.readLine() != null) {
                return true;
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Read all the game log data and simulate each move
     */
    public void read_log() {
        try {
            fileReader = new FileReader("last_sav.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            JsonReader jsonReader;
            JsonObject game_log_obj;

            while ((s = bufferedReader.readLine()) != null) {
                jsonReader = Json.createReader(new StringReader(s));
                game_log_obj = jsonReader.readObject();
                Main_Controller.setNbr_tour(game_log_obj.getInt("tour"));
                switch (game_log_obj.getString("player_name")) {
                    case "P1":
                        //set nbr player pawn
                        Main_Controller.getChessboard().getPlayer1().setNombre_pawn(game_log_obj.getInt("player_pawn_nbr"));

                        //find the pawn with a value in string
                        for (Pawn pawn_player : Main_Controller.getChessboard().getPlayer1().getTab_player()) {
                            if (pawn_player.getValue().equals(game_log_obj.getString("player_pawn"))) {
                                Main_Controller.getChessboard().move(game_log_obj.getInt("x"), game_log_obj.getInt("y"), pawn_player);
                                break;
                            }
                        }
                        Main_Controller.setSelected_player(2);
                        break;
                    case "P2":
                        Main_Controller.getChessboard().getPlayer1().setNombre_pawn(game_log_obj.getInt("player_pawn_nbr"));
                        for (Pawn pawn_player : Main_Controller.getChessboard().getPlayer2().getTab_player()) {
                            if (pawn_player.getValue().equals(game_log_obj.getString("player_pawn"))) {
                                Main_Controller.getChessboard().move(game_log_obj.getInt("x"), game_log_obj.getInt("y"), pawn_player);
                                break;
                            }
                        }
                        Main_Controller.setSelected_player(1);
                        break;
                    default:
                        break;
                }
                jsonReader.close();
            }
            Main_Controller.setNbr_tour(Main_Controller.getNbr_tour() + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
