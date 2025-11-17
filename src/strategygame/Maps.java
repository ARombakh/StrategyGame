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
public class Maps {
    private Field field;
    private int width;
    private int height;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    
    public Maps() {
        setField(new Field());
    }

    public Field River() {
        int x = 0, y = 0;
        
        if (StrategyGame.FLD_WIDTH == 15 && StrategyGame.FLD_HEIGHT == 15) {            
            for (y = 0; y < StrategyGame.FLD_HEIGHT; y++) {
                for (x = 0; x < StrategyGame.FLD_WIDTH; x++) {
                    if (
                            (y < 5 && x == 4) ||
                            (y >= 5 && y < 10 && x == 3) ||
                            (y >= 10 && y < 15 && x == 2)
                            ) {
                        getField().cells[x][y].setTerrainType(TerrainType.WATER);
                    }
                    else {
                        getField().cells[x][y].
                                setTerrainType(TerrainType.PLATEAU);
                        
                    }
                }                
            }
        }
        return getField();
    }
    
    public Field Plateau() {
        int x = 0, y = 0;
        
        if (StrategyGame.FLD_WIDTH == 8 && StrategyGame.FLD_HEIGHT == 8) {
            for (y = 0; y < StrategyGame.FLD_HEIGHT; y++) {
                for (x = 0; x < StrategyGame.FLD_WIDTH; x++) {
                    getField().cells[x][y].setTerrainType(TerrainType.PLATEAU);
                }
            }
        }

        getField().cells[0][2].setResource(new Resource(ResourceType.GOLD, 25));
        
        getField().cells[2][1].setResource(new Resource(ResourceType.LUMBER, 100));
        
        getField().cells[6][5].setResource(new Resource(ResourceType.STONE, 100));
        return getField();
    }
}