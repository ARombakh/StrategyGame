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
    private Scanner scanner = new Scanner(System.in);
    private String yes_no;

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getYes_no() {
        return yes_no;
    }

    public void setYes_no(String yes_no) {
        this.yes_no = yes_no;
    }
    
    public boolean getIsAction() {
        do {            
            System.out.print("Will it be action [y/n]? ");
            setYes_no(this.getScanner().next());
            if (!getYes_no().equals("y") && !getYes_no().equals("n")) {
                System.out.println("Unrecognized input. Repeat the input.");
            }
        } while (!getYes_no().equals("y") && !getYes_no().equals("n"));
        
        return getYes_no().equals("y");
    }
    
    public StrategyGame.Direction getDirection () {
        StrategyGame.Direction dir;
        String move;
                
        System.out.print("Enter direction: ");
        move = this.getScanner().next();
        while ((dir = getDir(move)) == null) {
            System.out.print(
                    "Incorrect direction. Enter another direction: "
            );
            move = this.getScanner().next();
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
