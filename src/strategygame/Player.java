/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import static strategygame.StrategyGame.*;

/**
 *
 * @author artyom
 */
public class Player {
    private int num;
    private char sym;

    public int getNum() {
        return num;
    }

    public char getSym() {
        return sym;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setSym(char sym) {
        this.sym = sym;
    }
    
    public Player(int num, char sym) {
        this.setNum(num);
        this.setSym(sym);
    }
}
