/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.StrategyGame.*;
import strategygame.Map.*;

/**
 *
 * @author artyom
 */
public class Main {
    public static void main(String[] args) {
        GameCell Cell;
        
        // Можно ли наполнить поле без создания нового объекта "карта"??
        Map map = new Map();
        Field field = map.River();

        Cell = field.cells[0][0];
        Cell.unit = new Unit();
        Cell.unit.player = Player.PLAYER1;
        
        Cell = field.cells[StrategyGame.FLD_WIDTH - 1]
                [StrategyGame.FLD_HEIGHT - 1];
        Cell.unit = new Unit();
        Cell.unit.player = Player.PLAYER2;

        Legend legend = new Legend();
        legend.printLegend();
        
        Screen screen = new Screen();
        screen.field = field;
        screen.printScreen();
        
        field.moveUnit(field.cells[0][0], Direction.DOWN);

        screen.printScreen();
        
        field.moveUnit(field.cells[0][1], Direction.LEFT);
        
        screen.printScreen();
        
        field.moveUnit(field.cells[0][0], Direction.DOWN);
        
        screen.printScreen();
    }
}
