/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import java.util.Scanner;

/**
 *
 * @author artyom
 */
public class Player {
    private int num;
    private ActionData act;
    private boolean alive;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
    public Player(int num) {
        setNum(num);
        setAct(new ActionData());
        setAlive(alive);
    }

    public ActionData getAct() {
        return act;
    }

    public void setAct(ActionData act) {
        this.act = act;
    }
    
    public void printPlNum() {
        System.out.printf("Player no. %d\n", getNum());
    }
    
    public ActionData askAction() {
        Scanner sc = new Scanner(System.in);
        String input = null;
        
        System.out.println("Enter action:");
        
        input = sc.next().toUpperCase();

        getAct().setDir(ActionController.DirectionType.valueOf(input));
        
        return getAct();
    }
}
