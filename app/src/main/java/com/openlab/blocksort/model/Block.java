package com.openlab.blocksort.model;

import java.io.Serializable;

/**
 * Created by Bryam Soto on 13/10/2017.
 */

public class Block implements Serializable {

    private int id;         //identificador del bloque
    private int color;      //color del bloque
    private int clicks;     //variable que indica el numero de toques dados en el bloque
    private int lastUse;    //vriable que indica el orden del último bloque tocado
    private int position;  //variable que indica la posición el que se encuentra el bloque


    public Block(int id, int color) {
        this.id = id;
        this.color = color;
        clicks = 0;
        lastUse = 0;
    }

    public Block(int id, int color, int clicks, int lastUse, int position) {
        this.id = id;
        this.color = color;
        this.clicks = clicks;
        this.lastUse = lastUse;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getLastUse() {
        return lastUse;
    }

    public void setLastUse(int lastUse) {
        this.lastUse = lastUse;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", color=" + color +
                ", clicks=" + clicks +
                ", lastUse=" + lastUse +
                ", position=" + position +
                '}';
    }
}
