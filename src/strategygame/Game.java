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
    private StrategyGame.Field field;
    private StrategyGame.Field.Legend legend;
    private StrategyGame.Field.Screen screen;
    private Scanner scanner = new Scanner(System.in);

    public StrategyGame.Field getField() {
        return field;
    }

    public void setField(StrategyGame.Field field) {
        this.field = field;
    }

    public StrategyGame.Field.Legend getLegend() {
        return legend;
    }

    public void setLegend(StrategyGame.Field.Legend legend) {
        this.legend = legend;
    }

    public StrategyGame.Field.Screen getScreen() {
        return screen;
    }

    public void setScreen(StrategyGame.Field.Screen screen) {
        this.screen = screen;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    
    public Game() {
        MapFactory theMap = new MapFactory();
        this.setField(theMap.createPlateau());
        this.setLegend(getField().new Legend());
        this.setScreen(getField().new Screen(this.getField()));
        getField().updateScreen(this.getLegend(), this.getScreen());
    }
    
    public boolean turn(int playerIndex) {
        boolean isSuccess;
        String move;
        String yes_no;
        boolean action;
        StrategyGame.Direction dir = null;
        StrategyGame.Field.Unit unitToGo;

        unitToGo = getField().player[playerIndex].getUnit();

        System.out.printf("Turn of Player %c.\n",
                getField().player[playerIndex].getSymbol());
        
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
