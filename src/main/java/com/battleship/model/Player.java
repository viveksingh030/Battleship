package com.battleship.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private Cell[][] battleArea;
    private List<String> missileSequence;
    private int remainingShips;

    public Player(String name, int width, int height) {
        this.name = name;
        this.battleArea = new Cell[height][width];
        initializeBattleArea();
        this.missileSequence = new ArrayList<>();
        this.remainingShips=0;
    }

    private void initializeBattleArea() {
        for (int i = 0; i < battleArea.length; i++) {
            for (int j = 0; j < battleArea[i].length; j++) {
                battleArea[i][j] = new Cell();
            }
        }
    }

    public String getName() {
        return name;
    }

    public Cell[][] getBattleArea() {
        return battleArea;
    }

    public List<String> getMissileSequence() {
        return missileSequence;
    }

    public void addMissileToSequence(String target) {
        missileSequence.add(target);
    }

    public void setShip(int row, int col, Ship ship) {
        getBattleArea()[row][col].setShip(ship);
        remainingShips += ship.getSize();
    }

    public int getRemainingShips() {
        return remainingShips;
    }

    public void setRemainingShips(int remainingShips) {
        this.remainingShips = remainingShips;
    }
}