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
        setAlive(alive);
    }
    
    public void printPlNum() {
        System.out.printf("Player no. %d\n", getNum());
    }
    
    public void askAction(ActionController ac) {
        Scanner sc = new Scanner(System.in);
        String input = null;
        boolean isCorrInput = false;
        
        while (!isCorrInput) {            
            System.out.println("Enter action:");

            input = sc.next().toUpperCase();

            try {
                ac.getAct().setDir(
                        ActionController.DirectionType.valueOf(input)
                );
                isCorrInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Incorrect action!");
                isCorrInput = false;
            }
        }
    }
}
