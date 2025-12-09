/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import static strategygame.StrategyGame.*;

/**
 *
 * @author artyom
 */
public class Player {
    private int num;
    
    public ActionType askAction() {
        return ActionType.MOVE;
    }
    
    public DirType askMove() {
        return DirType.UP;
    }
}
