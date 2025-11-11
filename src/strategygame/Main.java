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
        
        while (field.Player1.Life != 0 && field.Player2.Life != 0) {
            while (!game.turn()) {
                System.out.println("Turn unsuccessfull, repeat the turn!");
            }
            
            field.updateScreen(game.legend, game.screen);
        }

        if (field.Player1.Life == 0) {
            System.out.println("Player2 won!");
        }
        else {
            if (field.Player2.Life == 0) {
                System.out.println("Player1 won!");
            }
        }
    }
}