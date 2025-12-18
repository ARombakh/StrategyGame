/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

/**
 *
 * @author artyom
 */
public class Unit {
    private boolean alive;
    private int health;

    public boolean isAlive() {
        return alive;
    }

    public int getHealth() {
        return health;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
