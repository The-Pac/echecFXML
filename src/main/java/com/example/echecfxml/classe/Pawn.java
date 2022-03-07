package com.example.echecfxml.classe;


import javafx.scene.image.Image;

import java.util.ArrayList;

public class Pawn {
    private String value;
    private Image image;
    private int x, y;
    private boolean isOut;
    private ArrayList<Integer[]> legal_move;

    /**
     * Empty constructeur
     */
    public Pawn() {
    }

    /**
     * pawn constructeur
     */
    public Pawn(int y, int x, String value, boolean isOut, Image image) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.isOut = isOut;
        this.image = image;
    }

    private ArrayList<Integer[]> square(Pawn pawn) {
        return new ArrayList<>() {
            {
                //1 row
                add(new Integer[]{pawn.getY() + 1, pawn.getX() - 1});
                add(new Integer[]{pawn.getY() + 1, pawn.getX()});
                add(new Integer[]{pawn.getY() + 1, pawn.getX() + 1});

                //2 row
                add(new Integer[]{pawn.getY(), pawn.getX() - 1});
                add(new Integer[]{pawn.getY(), pawn.getX() + 1});

                //3 row
                add(new Integer[]{pawn.getY() - 1, pawn.getX() - 1});
                add(new Integer[]{pawn.getY() - 1, pawn.getX()});
                add(new Integer[]{pawn.getY() - 1, pawn.getX() + 1});
            }
        };
    }

    private ArrayList<Integer[]> diag(Pawn pawn) {
        return new ArrayList<>() {
            {
                //diag max
                for (int i = 0; i < 16; i++) {
                    if (pawn.getX() - i > -1 && pawn.getX() + i < 8 || pawn.getY() - i > -1 && pawn.getY() + i < 8) {

                        //Left up
                        add(new Integer[]{pawn.getY() + i, pawn.getX() - i});

                        //Right up
                        add(new Integer[]{pawn.getY() + i, pawn.getX() + i});

                        //Left down
                        add(new Integer[]{pawn.getY() - i, pawn.getX() - i});

                        //Right down
                        add(new Integer[]{pawn.getY() - i, pawn.getX() + i});
                    }
                }
            }
        };
    }

    private ArrayList<Integer[]> line(Pawn pawn) {
        return new ArrayList<>() {
            {
                int start_x = pawn.getX(), start_y = pawn.getY();

                //lines max
                for (int i = 0; i < 16; i++) {
                    if (start_x - i > -1 && start_x + i < 8 && start_y - i > -1 && start_y + i < 8) {
                        //Left
                        add(new Integer[]{start_x - i, start_y});

                        //Right
                        add(new Integer[]{start_x + i, start_y});

                        //Up
                        add(new Integer[]{start_x, start_y + i});

                        //Down
                        add(new Integer[]{start_x, start_y - i});
                    }
                }
            }
        };
    }


    public ArrayList<Integer[]> getLegal_move(Pawn pawn) {
        switch (this.value) {
            case "R":
                setLegal_move(square(pawn));
                break;
            case "D":
                setLegal_move(new ArrayList<>() {
                    {
                        this.addAll(diag(pawn));
                        this.addAll(line(pawn));
                    }
                });
                break;
            case "F":
                setLegal_move(diag(pawn));
                break;
            case "T":
                setLegal_move(line(pawn));
                break;
        }

        return legal_move;
    }

    public void setLegal_move(ArrayList<Integer[]> legal_move) {
        this.legal_move = legal_move;
    }

    public Image getImage() {
        return image;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    public String getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
