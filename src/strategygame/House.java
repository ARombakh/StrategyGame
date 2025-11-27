/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.Building.*;
import strategygame.StrategyGame.*;
import static strategygame.StrategyGame.*;

/**
 *
 * @author artyom
 */
public class House extends Building {
    public House() {
        super(0, TerrainType.PLATEAU, BuildingType.HOUSE);
    }
}
