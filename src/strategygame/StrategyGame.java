/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package strategygame;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

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
        CELL_OCCUPIED,
        BUILDING_BUILT,
        BUILDING_SUCCESSFUL
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
        public EnumMap<ResourceType, Integer> resources =
                new EnumMap<>(ResourceType.class);

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

        public Player(char symbol) {
            this.setSymbol(symbol);

            for (ResourceType Res : ResourceType.values()) {
                resources.put(Res, 0);
            }
        }
    }
    
    // почему нужно обязательно указывать static??
    static class GameCell {
        private TerrainType terrainType;
        private Unit unit;
        private Building building;
        private Field field;
        private Resource resource;
        private final int xCell;
        private final int yCell;     // coordinates of the cell in the field

        public CellFillType whatInCell() {
            CellFillType inCell = null;
            if (getBuilding() != null) inCell = CellFillType.BUILDING;

            if (getResource() != null) inCell = CellFillType.RESOURCE;

            if (getUnit()!= null) inCell = CellFillType.UNIT;
            
            return inCell;
        }

        private void validateCellIsEmpty() {
            
            if (getTerrainType() != TerrainType.PLATEAU) {
                throw new IllegalStateException(
                        "Terrain type is "+ this.terrainType +" not Plateau"
                );
            }

            if (whatInCell() != null) {
                switch (whatInCell()) {
                    case BUILDING -> throw new IllegalStateException(
                                "Cell already has a Building"
                        );
                    case RESOURCE -> throw new IllegalStateException(
                                "Cell already has Resource"
                        );
                    case UNIT -> throw new IllegalStateException(
                                "Cell already has Unit"
                        );
                }
            }
        }

        public TerrainType getTerrainType() {
            return this.terrainType;
        }

        public void setTerrainType(TerrainType terrainType) {
            this.terrainType = terrainType;
        }

        public Unit getUnit() {
            return this.unit;
        }

        public void setUnit(Unit unit) {
            if (unit != null) {
                validateCellIsEmpty();
            }

            this.unit = unit;
        }

        public Building getBuilding() {
            return this.building;
        }

        public void setBuilding(Building building) {
            if (building != null) {
                validateCellIsEmpty();
            }

            this.building = building;
        }

        public Field getField() {
            return this.field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        public Resource getResource() {
            return resource;
        }

        public void setResource(Resource resource) {
            if (resource != null) {
                validateCellIsEmpty();
            }

            this.resource = resource;
        }

        public int getxCell() {
            return xCell;
        }

        public int getyCell() {
            return yCell;
        }

        public void checkCell() {
            if (whatInCell() == CellFillType.RESOURCE &&
                    this.getResource().getResourceQty() == 0) {
                this.setResource(null);
            }

            if (whatInCell() == CellFillType.UNIT &&
                    this.getUnit().getLife() == 0) {
                this.setUnit(null);
            }
            
            if (whatInCell() == CellFillType.BUILDING &&
                    this.getBuilding().getLife() == 0) {
                this.setBuilding(null);
            }
        }

        public TerrainType terrRand() {
            double plateauPrb = 0.5;
            double waterPrb = 0.3;
            double mountainPrb = 0.2;

            TerrainType terr = TerrainType.PLATEAU;

            Random rand = new Random();
            double r = rand.nextDouble();

            if (r <= plateauPrb) {
                terr = TerrainType.PLATEAU;
            }
            else {
                if (r <= plateauPrb + waterPrb) {
                    terr = TerrainType.WATER;
                }
                else {
                    if (r <= plateauPrb + waterPrb + mountainPrb) {
                        terr = TerrainType.MOUNTAIN;
                    }
                }
            }
            return terr;
        }

        public GameCell(int xCell, int yCell, Field field) {
            this.setField(field);
            this.xCell = xCell;
            this.yCell = yCell;
            this.setTerrainType(TerrainType.PLATEAU); //terrRand());
            this.setUnit(null);
            this.setBuilding(null);
            // в чём проблема в overridable методе в конструкторе??
            // Почему выскакивает предупреждение??
        }

        public GameCell() {
            this(-1, -1, null);
        }

        public boolean actUpon(Unit actUnit) {
            boolean isSuccess = false;

            switch (whatInCell()) {
                case UNIT -> {
                    this.getUnit().attacked(actUnit.getDamage());
                    isSuccess = true;
                }
                case RESOURCE -> {
                    this.extractResource(actUnit);
                    isSuccess = true;
                }
                case BUILDING -> {
                    this.getBuilding().attacked(actUnit.getDamage());
                }
            }

            return isSuccess;
        }

        public int extractResource(Unit actUnit) {
            int extracted;
            int newResQty;
            ResourceType resourceType;
            resourceType = this.getResource().getResourceType();

            if(this.getResource().getResourceQty() >
                    actUnit.getResExtrCapacity())
                extracted = actUnit.getResExtrCapacity();
            else
                extracted = this.getResource().getResourceQty();
            this.getResource().addResourceQty(-extracted);
            newResQty = actUnit.getPlayer().resources.get(resourceType);
            newResQty += extracted;
            actUnit.getPlayer().resources.put(resourceType, newResQty);

            return extracted;
        }
    }

    static class Building {
        private int life;
        private int maxLife;

        public int getLife() {
            return life;
        }

        public void setLife(int life) {
            this.life = life;
        }
        
        public Building(int life) {
            this.life = life;
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