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
public class Building {
    private int life;
    private int maxLife;
    private TerrainType terrain;
    private BuildingType buildingType;

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType building) {
        this.buildingType = building;
    }

    public TerrainType getTerrain() {
        return terrain;
    }

    public void setTerrain(TerrainType terrain) {
        this.terrain = terrain;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Building(int life, TerrainType terrain, BuildingType building) {
        setLife(life);
        setTerrain(terrain);
        setBuildingType(building);
    }

    public BuildResultType build(Unit unit) {
        BuildResultType result = null;
        int new_life;

        new_life = getLife();

        if (new_life == BUILDING_MAX_LIFE) {
            result = BuildResultType.BUILDING_BUILT;
            return result;
        }

        if(new_life + unit.getBuildCapacity() > BUILDING_MAX_LIFE) 
            new_life = BUILDING_MAX_LIFE;
        else
            new_life += unit.getBuildCapacity();
        setLife(new_life);

        result = BuildResultType.BUILDING_SUCCESSFUL;
        return result;
    }

    public void attacked(int damage) {
        if (getLife() - damage < 0) {
            setLife(0);
        }
        else {
            setLife(getLife() - damage);
        }
    }
}
