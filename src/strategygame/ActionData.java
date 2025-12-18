/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import static strategygame.ActionController.*;
import strategygame.units.Unit;

/**
 *
 * @author artyom
 */
public class ActionData {
    private DirectionType dir;
    private Player player;
    private Unit unit;

    public DirectionType getDir() {
        return dir;
    }

    public void setDir(DirectionType dir) {
        this.dir = dir;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}