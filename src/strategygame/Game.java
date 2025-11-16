/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import java.util.Scanner;
//import static strategygame.StrategyGame.*;
//import strategygame.MapFactory.*;

/**
 *
 * @author artyom
 */
public class Game {
    StrategyGame.Field field;
    StrategyGame.Field.Legend legend;
    StrategyGame.Field.Screen screen;
    Scanner scanner = new Scanner(System.in);

    public Game() {
        MapFactory theMap = new MapFactory();
        this.field = theMap.createPlateau();
        this.legend = field.new Legend();
        this.screen = field.new Screen(this.field);
        field.updateScreen(this.legend, this.screen);
    }
    
    public boolean turn(int playerIndex) {
        boolean isSuccess;
        String move;
        String yes_no;
        boolean action;
        StrategyGame.Direction dir = null;
        StrategyGame.Field.Unit unitToGo;

        unitToGo = field.Player[playerIndex].unit;

        System.out.printf("Turn of Player %c.\n",
                field.Player[playerIndex].symbol);
        
        GameUI ui = new GameUI();
        action = ui.getIsAction();

        if (action) {
            System.out.println("Action chosen. ");
        }
        else {
            System.out.println("Move chosen. ");
        }

        dir = ui.getDirection();

        if (action) {
            isSuccess = unitToGo.action(dir);
        }
        else {
            isSuccess = unitToGo.move(dir);
        }

        return isSuccess;
    }
}
