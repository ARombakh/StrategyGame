/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.StrategyGame.*;
import static strategygame.StrategyGame.*;


/**
 *
 * @author artyom
 */
public class Bridge extends Building {
    public Bridge(int life) {
        super(life, TerrainType.WATER, BuildingType.BRIDGE, 'B');
    }
}
