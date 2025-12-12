/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import static strategygame.StrategyGame.*;
import strategygame.Cell.*;

/**
 *
 * @author artyom
 */
public class TestField {
    public void DrawField(Field field) {
        int x = 0;
        int y = 0;
        Coord coord = new Coord();
        Cell cell = new Cell();
        
        for (x = 0; x < FLD_WIDTH; x++) {
            for (y = 0; y < FLD_HEIGHT; y++) {
                coord.setX(x);
                coord.setY(y);
                cell = field.findCell(coord);
                if (cell.getUnit() != null) {
                    System.out.printf("%c", cell.getUnit().
                                                getPlayer().getSym());
                }
                else {
                    System.out.printf(" ");
                }
            }
            System.out.printf("\n");
        }
    }

    
    public static void main(String[] args) {
        TestField test = new TestField();
        Field field = new Field();
        Coord coord = new Coord();
        Player player0 = new Player(0, 'X');
        Player player1 = new Player(1, 'O');
        
        coord.setX(0);
        coord.setY(0);
        
        field.findCell(coord).setUnit(new Unit(100, player0));
        
        coord.setX(FLD_WIDTH - 1);
        coord.setY(FLD_HEIGHT - 1);
        
        field.findCell(coord).setUnit(new Unit(100, player1));
        
        test.DrawField(field);
    }
}
