/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package strategygame;

import strategygame.ActionController.*;

/**
 *
 * @author artyom
 */
public interface GameState {
    public void handle(ActionController context);
}
