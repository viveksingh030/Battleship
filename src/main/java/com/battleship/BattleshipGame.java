package com.battleship;

import com.battleship.model.Cell;
import com.battleship.model.Player;
import com.battleship.model.Ship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;


public class BattleshipGame {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter dimensions of battle area (width height):");
        String widthAndHeight[] = bufferedReader.readLine().split("\\s+");
        int width = Integer.parseInt(widthAndHeight[0]);
        int height = widthAndHeight[1].charAt(0) - 'A' + 1;
        System.out.println("Enter number of battleships each player has:");
        int numBattleships = Integer.parseInt(bufferedReader.readLine());
        Player[] players = new Player[2];
        for(int i=0;i<2;i++){
            System.out.println("Enter player " + (i + 1) + " name:");
            players[i]=new Player(bufferedReader.readLine(),width,height);
        }
        for (int i = 0; i < numBattleships; i++) {
            System.out.println("Enter battleship " + (i + 1) + " details");
            String battleshipDetails[] = bufferedReader.readLine().split("\\s+");
            char shipType = battleshipDetails[0].charAt(0);
            int shipWidth = battleshipDetails[1].charAt(0) - 48;
            int shipHeight = battleshipDetails[2].charAt(0) - 48;
            int y1 = battleshipDetails[3].charAt(0) - 'A';
            int x1 = battleshipDetails[3].charAt(1) - 49;
            int y2 = battleshipDetails[4].charAt(0) - 'A';
            int x2 = battleshipDetails[4].charAt(1) - 49;
            for (int row = y1; row < y1 + shipHeight; row++) {
                for (int col = x1; col < x1 + shipWidth; col++) {
                    Ship ship = new Ship(shipType == 'Q' ? 2 : 1);
                    players[0].setShip(row, col, ship);
                }
            }
            for (int row = y2; row < y2 + shipHeight; row++) {
                for (int col = x2; col < x2 + shipWidth; col++) {
                    Ship ship = new Ship(shipType == 'Q' ? 2 : 1);
                    players[1].setShip(row, col, ship);
                }
            }
        }

        for (int playerIndex = 0; playerIndex < 2; playerIndex++) {
            System.out.println("Enter missile sequence for " + players[playerIndex].getName() + ":");
            String missiles[] = bufferedReader.readLine().split("\\s+");
            for (int i = 0; i < missiles.length; i++) {
                players[playerIndex].addMissileToSequence(missiles[i]);
            }
        }

        bufferedReader.close();

        simulateGame(players);
    }

    private static void simulateGame(Player[] players) {
        Queue<Player> playerQueue = new LinkedList<>();
        playerQueue.add(players[0]);
        playerQueue.add(players[1]);

        Player currentPlayer;
        Player otherPlayer;

        while (true) {
            currentPlayer = playerQueue.poll();
            otherPlayer = playerQueue.peek(); //in case of multi player we should don't know what would be the target as it is not mentioned in the requirement
            // to handle this we can keep a Map of playerId with Player Object , once we will know which one is target we can get it from the map in O(1)
            //this solution needs some modification based on the missile input.

            if (currentPlayer.getMissileSequence().isEmpty()) {
                System.out.println(currentPlayer.getName() + " has no more missiles left to launch");
                playerQueue.offer(currentPlayer); // Put the current player at the end of the queue
                continue;
            }

            String target = currentPlayer.getMissileSequence().remove(0);
            int y = target.charAt(0) - 'A';
            int x = target.charAt(1) - '1';

            Cell targetCell = otherPlayer.getBattleArea()[y][x];
            if (!targetCell.hasShip() || targetCell.getShip().isDestroyed()) {
                System.out.println(currentPlayer.getName() + " fires a missile with target " + target + " which got miss");
            } else {
                targetCell.hit();
                if (targetCell.hasShip() && targetCell.getShip().isDestroyed()) {
                    otherPlayer.setRemainingShips(otherPlayer.getRemainingShips() - targetCell.getShip().getSize());
                    System.out.println(currentPlayer.getName() + " fires a missile with target " + target + " which got hit and ship destroyed");
                } else {
                    System.out.println(currentPlayer.getName() + " fires a missile with target " + target + " which got hit");
                }
            }

            if (isGameFinished(otherPlayer)) {
                System.out.println(currentPlayer.getName() + " won the battle");
                break;
            }

            playerQueue.offer(currentPlayer); // Put the current player at the end of the queue
        }
    }


    private static boolean isGameFinished(Player player) {
        return player.getRemainingShips() == 0;
    }

}
