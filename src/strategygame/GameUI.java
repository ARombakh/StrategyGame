/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.StrategyGame.*;
import java.util.Scanner;

/**
 *
 * @author artyom
 */
public class GameUI {
    Scanner scanner = new Scanner(System.in);
    String yes_no;
    
    public boolean getIsAction() {
        do {            
            System.out.print("Will it be action [y/n]? ");
            yes_no = this.scanner.next();
            if (!yes_no.equals("y") && !yes_no.equals("n")) {
                System.out.println("Unrecognized input. Repeat the input.");
            }
        } while (!yes_no.equals("y") && !yes_no.equals("n"));
        
        return yes_no.equals("y");
    }
    
    public StrategyGame.Direction getDirection () {
        StrategyGame.Direction dir;
        String move;
                
        System.out.print("Enter direction: ");
        move = this.scanner.next();
        while ((dir = getDir(move)) == null) {
            System.out.print(
                    "Incorrect direction. Enter another direction: "
            );
            move = this.scanner.next();
        }
        
        return dir;
    }

    public StrategyGame.Direction getDir(String move) {
        StrategyGame.Direction dir = null;

        dir = switch (move.toLowerCase()) {
            case "up" -> StrategyGame.Direction.UP;
            case "down" -> StrategyGame.Direction.DOWN;
            case "left" -> StrategyGame.Direction.LEFT;
            case "right" -> StrategyGame.Direction.RIGHT;
            default -> null;
        };

        return dir;
    }
}
