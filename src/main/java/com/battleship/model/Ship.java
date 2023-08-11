package com.battleship.model;

public class Ship {
    private int size;
    private int hits;
    private boolean isDestroyed;

    public Ship(int size) {
        this.size = size;
        this.hits = 0;
        this.isDestroyed = false;
    }

    public void hit() {
        hits++;
        if (hits >= size) {
            isDestroyed = true;
        }
    }

    public int getSize() {
        return size;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}