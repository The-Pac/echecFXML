package com.example.echecfxml.classe;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Pawn> tab_player;
    private int nombre_pawn = 16;

    public Player() {
    }

    public Player(String name, ArrayList<Pawn> tab_player, int nombre_pawn) {
        this.name = name;
        this.tab_player = tab_player;
        this.nombre_pawn = nombre_pawn;
    }

    public void create_pawns(int pos) {
        ArrayList<Pawn> pawns = new ArrayList<>();

        if (pos == 1) {
            //list piont
            Pawn roi = new Pawn(4, 0, "R", false, new Image("roi_p1.png", 100, 100, true, true));
            pawns.add(roi);
            Pawn dame = new Pawn(3, 0, "D", false, new Image("dame_p1.png", 100, 100, true, true));
            pawns.add(dame);

            Pawn fou1 = new Pawn(2, 0, "F1", false, new Image("fou_p1.png", 100, 100, true, true));
            pawns.add(fou1);
            Pawn fou2 = new Pawn(5, 0, "F2", false, new Image("fou_p1.png", 100, 100, true, true));
            pawns.add(fou2);

            Pawn cavalier1 = new Pawn(1, 0, "C1", false, new Image("cavalier_p1.png", 100, 100, true, true));
            pawns.add(cavalier1);
            Pawn cavalier2 = new Pawn(6, 0, "C2", false, new Image("cavalier_p1.png", 100, 100, true, true));
            pawns.add(cavalier2);

            Pawn tour1 = new Pawn(0, 0, "T1", false, new Image("tour_p1.png", 100, 100, true, true));
            pawns.add(tour1);
            Pawn tour2 = new Pawn(7, 0, "T2", false, new Image("tour_p1.png", 100, 100, true, true));
            pawns.add(tour2);

            for (int i = 0; i < 8; i++) {
                Pawn soldat = new Pawn(i, 1, ("S" + i), false, new Image("soldat_p1.png", 100, 100, true, true));
                pawns.add(soldat);
            }
        } else {
            //list piont
            Pawn roi = new Pawn(3, 7, "R", false, new Image("roi_p2.png", 100, 100, true, true));
            pawns.add(roi);
            Pawn dame = new Pawn(4, 7, "D", false, new Image("dame_p2.png", 100, 100, true, true));
            pawns.add(dame);

            Pawn fou1 = new Pawn(2, 7, "F1", false, new Image("fou_p2.png", 100, 100, true, true));
            pawns.add(fou1);
            Pawn fou2 = new Pawn(5, 7, "F2", false, new Image("fou_p2.png", 100, 100, true, true));
            pawns.add(fou2);

            Pawn cavalier1 = new Pawn(1, 7, "C1", false, new Image("cavalier_p2.png", 100, 100, true, true));
            pawns.add(cavalier1);
            Pawn cavalier2 = new Pawn(6, 7, "C2", false, new Image("cavalier_p2.png", 100, 100, true, true));
            pawns.add(cavalier2);

            Pawn tour1 = new Pawn(0, 7, "T1", false, new Image("tour_p2.png", 100, 100, true, true));
            pawns.add(tour1);
            Pawn tour2 = new Pawn(7, 7, "T2", false, new Image("tour_p2.png", 100, 100, true, true));
            pawns.add(tour2);

            for (int i = 0; i < 8; i++) {
                Pawn soldat = new Pawn(i, 6, ("S" + i), false, new Image("soldat_p2.png", 100, 100, true, true));
                pawns.add(soldat);
            }
        }
        this.setTab_player(pawns);
    }


    public int getNombre_pawn() {
        return nombre_pawn;
    }

    public void setNombre_pawn(int nombre_pawn) {
        this.nombre_pawn = nombre_pawn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Pawn> getTab_player() {
        return tab_player;
    }

    public void setTab_player(ArrayList<Pawn> tab_player) {
        this.tab_player = tab_player;
    }
}
