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
            GameCell cell = plateau.cells[
                    StrategyGame.X_START_PL[i]
            ][
                    StrategyGame.Y_START_PL[i]
            ];
            
            plateau.player[i] =
                    new Player(StrategyGame.PLAYER_SYMBOL[i]);
            cell.setUnit(new Unit(plateau.player[i], cell));
            plateau.player[i].setUnit(cell.getUnit());
            cell.getUnit().setPlayer(plateau.player[i]);
        }
        
        return plateau;
    }
    
    public Field createRiver() {
        // Можно ли наполнить поле без создания нового объекта "карта"??
        Maps map = new Maps();
        Field river = map.River();
        
        for (int i = 0; i < StrategyGame.PLAYERS_COUNT; i++) {
            GameCell cell = river.cells[
                                        StrategyGame.X_START_PL[i]
                                        ][
                                        StrategyGame.Y_START_PL[i]
                                        ];
            
            river.player[i] =
                    new Player(StrategyGame.PLAYER_SYMBOL[i]);
            cell.setUnit(new Unit(river.player[i], cell));
            river.player[i].setUnit(cell.getUnit());
            cell.getUnit().setPlayer(river.player[i]);
        }
        
        return river;
    }
}