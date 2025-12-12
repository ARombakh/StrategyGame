/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import static strategygame.StrategyGame.*;
import static strategygame.Building.*;
import strategygame.ActionData.*;

/**
 *
 * @author artyom
 */
public class ActionController {

    private Player player;
    private Unit unit;

    private ActionData data;
    private GameState state;

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Player getPlayer() {
        return player;
    }

    public Unit getUnit() {
        return unit;
    }

    public ActionData getData() {
        return data;
    }

    public void setData(ActionData data) {
        this.data = data;
    }
    
    public ActionController(Field field) {
        ActionData data = new ActionData();
        setData(data);
        getData().setField(field);
    }
    
    public void run() {
        while(!(state instanceof EndState)) {
            state.handle(this);
        }
    }
}
