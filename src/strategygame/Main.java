/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.StrategyGame.*;
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
        boolean playerIsDead = false;
        char deadPlayerSymb = 'Z';
        
        Outer:
        while (!playerIsDead) {
            while (!game.turn(i)) {
                System.out.println("Turn unsuccessfull, repeat the turn!");
            }
            
            field.updateScreen(game.legend, game.screen);
            i = (i == StrategyGame.PLAYERS_COUNT - 1 ? 0 : i + 1);
            
            for (Field.Player player : field.Player) {
                if (player.unit.life == 0) {
                    playerIsDead = true;
                    deadPlayerSymb = player.symbol;
                    break Outer;
                }
            }
        }

        System.out.printf("Player %c lost!", deadPlayerSymb);
    }
}