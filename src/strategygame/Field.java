/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.StrategyGame.*;
import static strategygame.StrategyGame.*;
import strategygame.Cell.*;

/**
 *
 * @author artyom
 */
public class Field {
    private Cell[][] cells;
    
    public Field() {
        int x, y;
        
        this.cells = new Cell[FLD_WIDTH][FLD_HEIGHT];
        
        for (x = 0; x < FLD_WIDTH; x++) {
            for (y = 0; y < FLD_HEIGHT; y++) {
                cells[x][y] = new Cell();
            }
        }
    }
    /*
    public Coord findUnitCell(Player player) {
        
    }*/
    
    public Cell findCell(Coord coord) {
        return cells[coord.getX()][coord.getY()];
    }
    
    public boolean setUnit(Coord coord, Player player) {
        Unit unit = new Unit(100, player);
        
        findCell(coord).setUnit(unit);
        
        return true;
    }
}
