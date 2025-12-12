/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame.states;

import java.util.Scanner;
import strategygame.ActionController;
import strategygame.GameState;
import strategygame.StrategyGame.*;
/**
 *
 * @author artyom
 */
public class AskPlActionState implements GameState {
    
    private ActionController context;
    
    public ActionType askAction() {
        Scanner sc = new Scanner(System.in);
        String input;
        ActionType actType = null;
        boolean actAssigned = false;

        while (!actAssigned) {
            System.out.println("Enter action of the player [Act/Move]:");
            input = sc.next().toUpperCase();

            switch (input) {
                case "ACT":
                    actType = ActionType.ACT;
                    actAssigned = true;
                    break;
                case "MOVE":
                    actType = ActionType.MOVE;
                    actAssigned = true;
                    break;
                default:
                    actType = null;
                    actAssigned = false;
                    System.out.println("Wrong name of an action.");
                    break;
            }   
        }
        
        return actType;
    }

    @Override
    public void handle(ActionController context) {
        context.getData().setAct(askAction());
        
        context.setState(new AskPlActDirState());
    }
}