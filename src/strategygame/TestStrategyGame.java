/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.StrategyGame.*;
import strategygame.Game.*;
import strategygame.Drawing.*;
import strategygame.GameCell.*;

/**
 *
 * @author artyom
 */
public class TestStrategyGame {
    /*public static void main(String[] args) {
        Game game = new Game();
        
        game.getScreen().printScreen();
        
        Field field = game.getField();
        
        GameCell cell = field.cells[0][0];
        
        Action action = new Action();
        
        action.setUnit(cell.getUnit());
        
        action.setSrc(cell);
        action.setDir(Direction.RIGHT);
        System.out.println(action.getDest() == null); 
        action.calcDest();

        System.out.printf("x = %d, y = %d\n",
                action.getDest().getxCell(), action.getDest().getyCell());   // Debug
        
        action.build();
        
        game.getScreen().printScreen();
    }*/
}
