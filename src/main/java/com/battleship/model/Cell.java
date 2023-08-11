package com.battleship.model;

public class Cell {
    private boolean isHit;
    private Ship ship;

    public Cell() {
        isHit = false;
        ship = null;
    }

    public boolean isHit() {
        return isHit;
    }

    public void hit() {
        if(ship!=null){
            isHit = true;
            ship.hit();
        }
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean hasShip() {
        return ship != null;
    }

    public Ship getShip() {
        return ship;
    }

    @Override
    public String toString() {
        if (isHit) {
            if (hasShip()) {
                return "X";
            } else {
                return "O";
            }
        } else {
            return ".";
        }
    }
}