/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package strategygame;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import strategygame.GameCell.*;
import strategygame.Building.*;

/**
 *
 * @author artyom
 */
public class StrategyGame {

    public static final int FLD_WIDTH = 15;
    public static final int FLD_HEIGHT = 15;
    
    public static final int CELL_WIDTH = 5;
    public static final int CELL_HEIGHT = 4;
    public static final int CELL_MIDDLE = (CELL_HEIGHT % 2 == 0 ?
                                            CELL_HEIGHT / 2 :
                                                    (CELL_HEIGHT / 2) + 1);
    
    public static final int[] X_START_PL = {0, FLD_WIDTH - 1};
    public static final int[] Y_START_PL = {0, FLD_HEIGHT - 1};
    
    public static final int LIFE = 20;
    public static final int DAMAGE = 35;
    public static final int EXTRACT_CAPACITY = 5;
    
    public static final int LABEL_LEN = 3;
    
    public static final char[] PLAYER_SYMBOL = {'X', 'O'};
    
    public static final int PLAYERS_COUNT = 2;
    public static final int BUILD_CAPACITY = 7;
    
    public static final int BUILDING_MAX_LIFE = 100;
    
    public enum TerrainType {
        PLATEAU,
        WATER,
        MOUNTAIN
    }
    
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    
    public enum ActionType {
        MOVE,
        INTERACT,
        BUILD
    }
    
    public enum CellFillType {
        RESOURCE,
        UNIT,
        BUILDING
    }
    
    public enum ResourceType {
        LUMBER,
        GOLD,
        STONE
    }

    public enum BuildResultType {
        WRONG_TERRAIN,
        OTHER_BUILDING,
        CELL_OCCUPIED,
        BUILDING_BUILT,
        BUILDING_SUCCESSFUL
    }
    
    public enum BuildingType {
        BRIDGE,
        HOUSE
    }
    
    static class ResourceList {
        private EnumMap<ResourceType, Integer> resources =
                new EnumMap<>(ResourceType.class);
        
        public ResourceList() {
            for(ResourceType Res: ResourceType.values()) {
                resources.put(Res, 0);
            }
        }
        
        public int getResource(ResourceType resType) {
            return resources.get(resType);
        }
        
        public void setResource(ResourceType resType, int qty) {
            resources.put(resType, qty);
        }
        
        public Map<ResourceType, Integer> getResources() {
            return Collections.unmodifiableMap(resources);
        }
    }

    static class Resource {
        private ResourceType resourceType;
        private int resourceQty;

        public ResourceType getResourceType() {
            return this.resourceType;
        }
        
        public void setResourceType(ResourceType resourceType) {
            this.resourceType = resourceType;
        }

        public int getResourceQty() {
            return resourceQty;
        }

        public void setResourceQty(int resourceQty) {
            this.resourceQty = resourceQty;
        }
        
        public void addResourceQty(int resourceQty) {
            int newResQty;
            newResQty = getResourceQty();
            newResQty += resourceQty;
            setResourceQty(newResQty);
        }

        public Resource(ResourceType resourceType, int resourceQty) {
            this.setResourceType(resourceType);
            this.setResourceQty(resourceQty);
        }

    }
        
    static class Player {
        private char symbol;
        private Unit unit;
        private ResourceList res = new ResourceList();

        public char getSymbol() {
            return this.symbol;
        }

        public void setSymbol(char symbol) {
            this.symbol = symbol;
        }

        public Unit getUnit() {
            return this.unit;
        }

        public void setUnit(Unit unit) {
            this.unit = unit;
        }

        public int getResource(ResourceType resType) {
            return res.getResource(resType);
        }
        
        public Map<ResourceType, Integer> getResources() {
            return Collections.unmodifiableMap(res.resources);
        }
        
        public void setResource(ResourceType resType, int qty) {
            res.setResource(resType, qty);
        }
        
        public Player(char symbol) {
            this.setSymbol(symbol);
        }
    }

    static class Unit {
        private Player player;
        private GameCell cell;
        private int life;
        private int damage;
        private int resExtrCapacity;
        private int buildCapacity;

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public GameCell getCell() {
            return cell;
        }

        public void setCell(GameCell cell) {
            this.cell = cell;
        }

        public int getLife() {
            return life;
        }

        public void setLife(int life) {
            this.life = life;
        }

        public int getDamage() {
            return damage;
        }

        public void setDamage(int damage) {
            this.damage = damage;
        }

        public int getResExtrCapacity() {
            return resExtrCapacity;
        }

        public void setResExtrCapacity(int resExtrCapacity) {
            this.resExtrCapacity = resExtrCapacity;
        }

        public int getBuildCapacity() {
            return buildCapacity;
        }

        public void setBuildCapacity(int buildCapacity) {
            this.buildCapacity = buildCapacity;
        }

        public Unit (Player player, GameCell cell) {
            this.setPlayer(player);
            this.setCell(cell);
            this.setLife(LIFE);
            this.setDamage(DAMAGE);
            this.setResExtrCapacity(EXTRACT_CAPACITY);
            setBuildCapacity(BUILD_CAPACITY);
        }

        public void attacked(int damage) {
            setLife(getLife() > damage ? getLife() - damage : 0);
        }
    }

    // почему нужно обязательно указывать static??    
    public static class Field {
        public GameCell[][] cells;
        public Player[] player;

        public Field() {
            int x, y;
            // возможно, стоит также инициализировать каждого player??
            this.cells = new GameCell[FLD_WIDTH][FLD_HEIGHT];
            this.player = new Player[PLAYERS_COUNT];
                        
            for (y = 0; y < FLD_HEIGHT; y++) {
                for (x = 0; x < FLD_WIDTH; x++) {
                    this.cells[x][y] = new GameCell(x, y, this);
                }
            }
        }
        
        public void randomize() {
            int x, y, randTerrain, randomizer;
            for (y = 0; y < FLD_HEIGHT; y++) {
                for (x = 0; x < FLD_WIDTH; x++) {
                    randomizer = (int)(Math.random() * 9);
                    randTerrain = switch (randomizer) {
                        case 1, 2, 3, 4, 5 -> 0;
                        case 6, 7, 8 -> 1;
                        default -> 2;
                    };

                    cells[x][y] = new GameCell(x, y, this);
                }
            }
        }

        public void recalcCells() {
            int x, y;
            
            for (y = 0; y < FLD_HEIGHT; y++) {
                for (x = 0; x < FLD_WIDTH; x++) {
                    // check that resource or unit still exists in the cell
                    cells[x][y].checkCell();
                }
            }
        }
    }
}