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
        Field field = game.getField();
        int i = 0;  // Number of player who plays now
        boolean playerIsDead = false;
        char deadPlayerSymb = 'Z';
        
        Outer:
        while (!playerIsDead) {
            while (!game.turn(i)) {
                System.out.println("Turn unsuccessfull, repeat the turn!");
            }
            
            field.updateScreen(game.getLegend(), game.getScreen());
            i = (i == StrategyGame.PLAYERS_COUNT - 1 ? 0 : i + 1);
            
            for (Field.Player player : field.player) {
                if (player.getUnit().getLife() == 0) {
                    playerIsDead = true;
                    deadPlayerSymb = player.getSymbol();
                    break Outer;
                }
            }
        }

        System.out.printf("Player %c lost!", deadPlayerSymb);
    }
}