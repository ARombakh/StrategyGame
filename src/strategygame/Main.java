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
//        Field field = new Field();
        
        // Можно ли наполнить поле без создания нового объекта "карта"??
        Map map = new Map();
        Field field = map.River();
//        field.randomize();
 
//        field.cells[0][0].building = new Building();
        field.cells[0][0].unit = new Unit();
        field.cells[0][0].unit.player = Player.PLAYER1;
        field.cells[0][0].fillCellChars();
        
        Legend legend = new Legend();
        legend.printLegend();
        
        Screen screen = new Screen();
        screen.assignAllCells(field);
        screen.printScreen();
        
        System.out.println(
                field.moveUnit(
                        field.cells[0][0], field.cells[0][1]
                )
        );

        screen.assignAllCells(field);
        screen.printScreen();
    }
}
