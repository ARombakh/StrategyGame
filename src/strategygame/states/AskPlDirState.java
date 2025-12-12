/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame.states;

import java.util.Scanner;
import strategygame.ActionController;
import strategygame.GameState;
import strategygame.StrategyGame.*;
import strategygame.TestAction;
/**
 *
 * @author artyom
 */
public class AskPlDirState implements GameState {
    private ActionController context;
    
    public DirType askDirection() {
        Scanner sc = new Scanner(System.in);
        String input = null;
        DirType dir = null;
        boolean dirAssigned = false;
        
        while (!dirAssigned) {        
            System.out.println("Enter direction[UP/DOWN/LEFT/RIGHT]:");
            input = sc.next().toUpperCase();

            switch (input) {
                case "UP":
                    dir = DirType.UP;
                    dirAssigned = true;
                    break;
                case "DOWN":
                    dir = DirType.DOWN;
                    dirAssigned = true;
                    break;
                case "LEFT":
                    dir = DirType.LEFT;
                    dirAssigned = true;
                    break;
                case "RIGHT":
                    dir = DirType.RIGHT;
                    dirAssigned = true;
                    break;
                default:
                    System.out.println("Wrong name of direction.");
                    dirAssigned = false;
                    break;
            }
        }
        
        return dir;
    }

    @Override
    public void handle(ActionController context) {
        context.getData().setDir(askDirection());
        
        TestAction test = new TestAction(context.getData());
        
        switch (test.testActDir()) {
            case OK:
                context.setState(new EndState());
                break;
            case COORDS:
                System.out.println("The dest cell is out of field bounds.");
                context.setState(this);
                break;
            case ACTION_PROHIB:
                System.out.println("The action cannot be taken. "
                                    + "Choose another action.");
                context.setState(new AskPlActionState());
                break;            
        }
        
        
    }
}
