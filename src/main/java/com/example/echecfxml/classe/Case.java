package com.example.echecfxml.classe;

public class Case {
    private Pawn pawn;
    private boolean is_occupied;

    public Case() {
        this.pawn = null;
        this.is_occupied = false;
        
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public boolean isIs_occupied() {
        return is_occupied;
    }

    public void setIs_occupied(boolean is_occupied) {
        this.is_occupied = is_occupied;
    }
}
