/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

/**
 *
 * @author artyom
 */
public class FieldObject {
    private int life;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
    
    public FieldObject(int life) {
        setLife(life);
    }
}
