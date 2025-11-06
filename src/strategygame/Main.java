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
        Unit player1;
        Unit player2;
        
        // Можно ли наполнить поле без создания нового объекта "карта"??
        Map map = new Map();
        Field field = map.River();

        Cell = field.cells[0][0];
        Cell.unit = new Unit(Player.PLAYER1, Cell);
        player1 = Cell.unit;
        
        Cell = field.cells[StrategyGame.FLD_WIDTH - 1]
                [StrategyGame.FLD_HEIGHT - 1];
        Cell.unit = new Unit(Player.PLAYER2, Cell);
        player2 = Cell.unit;

        Legend legend = new Legend();
        legend.printLegend();
        
        Screen screen = new Screen();
        screen.field = field;
        screen.printScreen();
        
        player1.moveUnit(Direction.DOWN);

        screen.printScreen();
        
        player1.moveUnit(Direction.LEFT);
        
        screen.printScreen();
//        player2.moveUnit(Direction.UP);
//        screen.printScreen();
    }
}
