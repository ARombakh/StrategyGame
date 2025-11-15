/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.StrategyGame.*;

/**
 *
 * @author artyom
 */
public class MapFactory {
    public Field createPlateau() {
        // Можно ли наполнить поле без создания нового объекта "карта"??
        Maps map = new Maps();
        Field plateau = map.Plateau();
        
        for (int i = 0; i < StrategyGame.PLAYERS_COUNT; i++) {
            // как оптимальнее разбить эту строку??
            Field.GameCell cell = plateau.cells[
                    StrategyGame.X_START_PL[i]
            ][
                    StrategyGame.Y_START_PL[i]
            ];
            
            plateau.Player[i] =
                    plateau.new Player(StrategyGame.PLAYER_SYMBOL[i]);
            cell.unit = plateau.new Unit(plateau.Player[i], cell);
            plateau.Player[i].unit = cell.unit;
            cell.unit.player = plateau.Player[i];
        }
        
        return plateau;
    }
}