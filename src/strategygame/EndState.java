/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

/**
 *
 * @author artyom
 */
public class EndState implements GameState {
    @Override
    public void handle(ActionController context) {
        System.out.println("Turn ended successfully");
    }
}
