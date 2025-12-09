/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.FieldObject.*;

/**
 *
 * @author artyom
 */
public class Unit extends FieldObject {
    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public Unit(int life, Player player) {
        super(life);
        
        setPlayer(player);
    }
}
