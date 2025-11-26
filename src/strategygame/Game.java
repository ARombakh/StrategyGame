/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import java.util.Scanner;
//import static strategygame.StrategyGame.*;
//import strategygame.MapFactory.*;
// Debug
import strategygame.DebugUI.*;

/**
 *
 * @author artyom
 */
public class Game {
    private StrategyGame.Field field;
    private Drawing.Legend legend;
    private Drawing.Screen screen;

    public StrategyGame.Field getField() {
        return field;
    }

    public void setField(StrategyGame.Field field) {
        this.field = field;
    }

    public Drawing.Legend getLegend() {
        return legend;
    }

    public void setLegend(Drawing.Legend legend) {
        this.legend = legend;
    }

    public Drawing.Screen getScreen() {
        return screen;
    }

    public void setScreen(Drawing.Screen screen) {
        this.screen = screen;
    }
    
    public Game() {
        MapFactory theMap = new MapFactory();
        this.setField(theMap.createPlateau());
        this.setLegend(new Drawing.Legend());
        this.setScreen(new Drawing.Screen(this.getField()));
//        Drawing.updateScreen(this.getLegend(), this.getScreen(), field);
    }
    
    public boolean turn(int playerIndex) {
        boolean isSuccess;
        String move;
        String yes_no;
        boolean isMove;
        StrategyGame.Direction dir = null;
        StrategyGame.Unit unitToGo;

        unitToGo = getField().player[playerIndex].getUnit();

        System.out.printf("Turn of Player %c.\n",
                getField().player[playerIndex].getSymbol());
        // Debug
        System.out.printf("Turn no %d\n", DebugUI.getTurnNo());   // Debug

        StrategyGame.Action action = new StrategyGame.Action();
        GameUI ui = new GameUI();
        
        action.setUnit(unitToGo);
        action.setSrc(unitToGo.getCell());
        action.setAction(ui.getActionType());

        dir = ui.getDirection();
        action.setDir(dir);
        
        isSuccess = action.act();

        return isSuccess;
    }
}
