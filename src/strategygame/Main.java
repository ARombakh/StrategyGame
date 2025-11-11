/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.StrategyGame.*;
import strategygame.Map.*;
import strategygame.Game.*;

/**
 *
 * @author artyom
 */
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Field field = game.field;
        int i = 0;  // Number of player who plays now
        
        while (field.Player[0].unit.Life != 0
                && field.Player[1].unit.Life != 0) {
            while (!game.turn(i)) {
                System.out.println("Turn unsuccessfull, repeat the turn!");
            }
            
            i = (i == StrategyGame.PLAYERS_COUNT - 1 ? 0 : i + 1);
            field.updateScreen(game.legend, game.screen);
        }

        if (field.Player[0].unit.Life == 0) {
            System.out.println("Player2 won!");
        }
        else {
            if (field.Player[1].unit.Life == 0) {
                System.out.println("Player1 won!");
            }
        }
    }
}