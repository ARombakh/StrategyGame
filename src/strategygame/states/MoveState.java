/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame.states;

import strategygame.ActionController;
import strategygame.Cell;
import strategygame.GameState;
import strategygame.Unit;

/**
 *
 * @author artyom
 */
public class MoveState implements GameState {
    public void handle(ActionController context) {
        Unit unit;
        Cell src;
        Cell dest;
        
        src = context.getData().getField().findCell(
                                context.getData().getSource()
            );
        
//        System.out.printf("Dest is not null %b\n", context.getData().getDest() != null);   // Debug
        
        dest = context.getData().getDestCell();
        
        unit = src.getUnit();
        dest.setUnit(unit);
        src.setUnit(null);
        
        context.setState(new EndState());
    }
}
