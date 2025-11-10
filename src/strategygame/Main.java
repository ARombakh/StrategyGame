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
        StrategyGame.Game game = new StrategyGame.Game();
        
        while (!game.field.Player1.isKilled && !game.field.Player2.isKilled) {
            while (!game.turn()) {                
                System.out.println("Turn unsuccessfull, repeat the turn!");
            }
            
            game.field.updateScreen(game.legend, game.screen);
        }

        if (game.field.Player1.isKilled) {
            System.out.println("Player2 won!");
        }
        else {
            if (game.field.Player2.isKilled) {
                System.out.println("Player1 won!");
            }
        }
    }
}