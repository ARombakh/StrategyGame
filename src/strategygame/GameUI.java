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
    
    public ActionType getActionType() {
        String input;
        ActionType actType = null;
        boolean wrongInput = false;
        do {            
            System.out.println("What action do you choose? [Move/Build/Act]");
            input = this.getScanner().next().toLowerCase();
            switch (input) {
                case "move":
                    actType = ActionType.MOVE;
                    wrongInput = false;
                    break;
                case "build":
                    actType = ActionType.BUILD;
                    wrongInput = false;
                    break;
                case "act":
                    actType = ActionType.INTERACT;
                    wrongInput = false;
                    break;
                default:
                    System.out.println("Wrong action, enter another action");
                    wrongInput = true;
                    break;
            }
        } while (wrongInput);
        
        return actType;
    }
    
    public BuildingType getBuildingType() {
        String input;
        BuildingType buildingType = null;
        boolean wrongInput = false;
        do {            
            System.out.println("What building do you choose? [House/Bridge]");
            input = this.getScanner().next().toLowerCase();
            switch (input) {
                case "house":
                    buildingType = BuildingType.HOUSE;
                    wrongInput = false;
                    break;
                case "bridge":
                    buildingType = BuildingType.BRIDGE;
                    wrongInput = false;
                    break;
                default:
                    System.out.println("Wrong building, enter "
                            + "another building");
                    wrongInput = true;
                    break;
            }
        } while (wrongInput);
        return buildingType;
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
