/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.StrategyGame.*;
import strategygame.Drawing.*;

/**
 *
 * @author artyom
 */
public class TestDrawing {
    public static void main(String[] args) {
        int x, y;
        Field field = new Field();
        Field.GameCell cell;
        Resource stone = new Resource(ResourceType.STONE, 20);

        x = 0;
        y = 0;
        
        Drawing drawing = new Drawing();

        cell = field.cells[0][0];
        cell.setResource(stone);
        
        GameCellDrawn gCellDrawn = drawing.new GameCellDrawn(cell);
        
        gCellDrawn.fillCellChars();
        
        gCellDrawn.printCell();
        
        Field.Player playerX = field.new Player('X');
        Field.Unit unitX = field.new Unit(playerX, cell);
        
        cell.
    }
}
