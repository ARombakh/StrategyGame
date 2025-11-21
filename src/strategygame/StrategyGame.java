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

    public static final int FLD_WIDTH = 8;
    public static final int FLD_HEIGHT = 8;
    
    public static final int CELL_WIDTH = 5;
    public static final int CELL_HEIGHT = 4;
    public static final int CELL_MIDDLE = (CELL_HEIGHT % 2 == 0 ?
                                            CELL_HEIGHT / 2 :
                                                    (CELL_HEIGHT / 2) + 1);
    
    public static final int[] X_START_PL = {0, FLD_WIDTH - 1};
    public static final int[] Y_START_PL = {0, FLD_HEIGHT - 1};
    
    public static final int LIFE = 20;
    public static final int DAMAGE = 5;
    public static final int EXTRACT_CAPACITY = 5;
    
    public static final int LABEL_LEN = 3;
    
    public static final char[] PLAYER_SYMBOL = {'X', 'O'};
    
    public static final int PLAYERS_COUNT = 2;
    
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
    
    public enum ResourceType {
        LUMBER,
        GOLD,
        STONE
    }
    
    public enum ActionType {
        ATTACK,
        COLLECT
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

    // почему нужно обязательно указывать static??    
    public static class Field {
        public GameCell[][] cells;
        public Player[] player;

        public Field() {
            int x, y;
            // возможно, стоит также инициализировать каждого player??
            this.cells = new GameCell[FLD_WIDTH][FLD_HEIGHT];
            this.player = new Field.Player[PLAYERS_COUNT];
                        
            for (y = 0; y < FLD_HEIGHT; y++) {
                for (x = 0; x < FLD_WIDTH; x++) {
                    this.cells[x][y] = new GameCell(x, y, this);
                }
            }
        }
        
        class Player {
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
                    // drawing full graphical representation of the cell
                    cells[x][y].fillCellChars();
                }
            }
        }

        static class Building {
            private int life;
        }
        
        class Unit {
            private Player player;
            private GameCell cell;
            private int life;
            private int damage;
            private int resExtrCapacity;
            
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
            
            public Unit (Player player, GameCell cell) {
                this.setPlayer(player);
                this.setCell(cell);
                this.setLife(LIFE);
                this.setDamage(DAMAGE);
                this.setResExtrCapacity(EXTRACT_CAPACITY);
            }

            // Насколько разумно возвращать метод ту же GameCell в случае
            // неудачи??
            public GameCell destCell(Direction direction) {
                GameCell dest;

                int x = getCell().getxCell(), y = getCell().getyCell();

                switch (direction) {
                    case UP -> {
                        x += 0;
                        y += -1;
                    }
                    case DOWN -> {
                        x += 0;
                        y += 1;
                    }
                    case LEFT -> {
                        x += -1;
                        y += 0;
                    }
                    case RIGHT -> {
                        x += 1;
                        y += 0;
                    }
                }
                
                if (!(x >= 0 && x < FLD_WIDTH) ||
                        !(y >= 0 && y < FLD_HEIGHT)) {
                    System.out.print("Target cell of ");
                    System.out.printf("%s is out of the field\n",
                                        this.getPlayer());
                    System.out.println("Choose another direction.");
                    dest = getCell();
                }
                else dest = getCell().getField().cells[x][y];

                return dest;
            }

            public boolean move(Direction direction) {
                boolean isSuccess;
                GameCell source = getCell();
                GameCell dest;  // Destination cell

                dest = destCell(direction);
                if (dest == source) {
                    isSuccess = false;
                } else {
                    if (dest.getBuilding() == null
                            && dest.getUnit() == null
                            && dest.getResource() == null
                            && dest.getTerrainType() == TerrainType.PLATEAU) {
                        dest.setUnit(source.getUnit());
                        source.setUnit(null);
                        setCell(dest);
                        isSuccess = true;
                    }
                    else {
                        System.out.println("The destination cell is taken");
                        isSuccess = false;
                    }
                }

                return isSuccess;
            }

            public boolean action(Direction direction) {
                boolean isSuccess;
                GameCell source = getCell();
                GameCell dest;  // Destination cell

                dest = destCell(direction);
                if (dest == source) {
                    isSuccess = false;
                } else {
                    if (dest.getBuilding() == null
                            && dest.getUnit() == null
                            && dest.getResource() == null) {
                        System.out.println(
                                "Nothing to act upon in the target cell");
                        isSuccess = false;
                    }
                    else {
                        if (dest.getUnit() != null ||
                                dest.getResource() != null) {
                            dest.actUpon(source.getUnit());
                        }
                        isSuccess = true;
                    }
                }

                return isSuccess;
            }

            public void attacked(int damage) {
                setLife(getLife() > damage ? getLife() - damage : 0);
            }
        }

        // почему нужно обязательно указывать static??
        class GameCell {
            private TerrainType terrainType;
            private Unit unit;
            private Building building;
            private Field field;
            private Resource resource;
            private final int xCell;
            private final int yCell;     // coordinates of the cell in the field
            // array of symbols of graphic representation of the cell
            public char[][] cellChars = new char[CELL_WIDTH][CELL_HEIGHT];

            // Нужно ли инициализировать ячейку??
            public void initCell() {
                int x, y;

                for (x = 0; x < CELL_WIDTH; x++) {
                    for (y = 0; y < CELL_HEIGHT; y++) {
                        cellChars[x][y] = ' ';
                    }
                }
            }
            
            private void validateCellIsEmpty() {
                if (getTerrainType() != TerrainType.PLATEAU) {
                    throw new IllegalStateException(
                            "Terrain type is "+ this.terrainType +" not Plateau"
                    );
                }
                
                if (getBuilding() != null) {
                    throw new IllegalStateException(
                            "Cell already has a Building"
                    );
                }
                
                if (getResource() != null) {
                    throw new IllegalStateException(
                            "Cell already has a Resource"
                    );
                }
                
                if (getUnit()!= null) {
                    throw new IllegalStateException(
                            "Cell already has a Unit"
                    );
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
                if (this.getResource() != null &&
                    this.getResource().getResourceQty() == 0) {
                    this.setResource(null);
                }
                
                if (this.getUnit() != null && this.getUnit().getLife() == 0) {
                    this.setUnit(null);
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

            public char terrainFiller() {
                char terrain = ' ';
                // Debug стоит вынести в константы
                switch (this.getTerrainType()) {
                    case PLATEAU -> terrain = ' ';
                    case MOUNTAIN -> terrain = 'X';
                    case WATER -> terrain = 'O';
                }
                return terrain;
            }

            public char playerFiller() {
                return this.getUnit().getPlayer().getSymbol();
            }

            public void fillTerrain() {
                char terrain;
                int x, y;

                terrain = terrainFiller();

                for (y = 0; y < CELL_HEIGHT; y++) {
                    for (x = 0; x < CELL_WIDTH; x++) {
                        cellChars[x][y] = terrain;
                    }
                }
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
                // Debug убрать fillCellChars, перенести в Drawing
                fillCellChars();
            }

            public GameCell() {
                this(-1, -1, null);
            }

            public void fillBorder(boolean thick) {
                int x, y, i;

                int[] bordX = {0, CELL_WIDTH - 1};
                int[] bordY = {0, CELL_HEIGHT - 1};
                // Как это лучше организовать??
                cellChars[bordX[0]][bordY[0]] = thick ? (char) 0x250F :
                                                        (char) 0x250C;
                cellChars[bordX[1]][bordY[0]] = thick ? (char) 0x2513 :
                                                        (char) 0x2510;
                cellChars[bordX[0]][bordY[1]] = thick ? (char) 0x2517 :
                                                        (char) 0x2514;
                cellChars[bordX[1]][bordY[1]] = thick ? (char) 0x251B :
                                                        (char) 0x2518;
                for (y = 1; y < bordY[1]; y++) {
                    for (i = 0; i < bordX.length; i++) {
                        x = bordX[i];
                        cellChars[x][y] = thick ? (char) 0x2503 : (char) 0x2502;
                    }
                }

                for (x = 1; x < bordX[1]; x++) {
                    for (i = 0; i < bordY.length; i++) {
                        y = bordY[i];
                        cellChars[x][y] = thick ? (char) 0x2501 : (char) 0x2500;
                    }
                }
            }

            public void fillCellChars() {
                initCell();            
                fillTerrain();
                if (this.getBuilding() != null) {
                    fillBorder(true);
                    cellChars[0][0] = 'B';
                }
                else {
                    if (this.getUnit() != null ||
                            this.getResource() != null) {
                        fillBorder(false);
                    }
                }
                fillPlayerMark();
                fillCellLabel();
            }
            
            public void fillPlayerMark() {
                if (this.getUnit() != null) {
                    cellChars[0][0] = this.playerFiller();
                }
            }
            
            public void fillCellLabel() {
                String label = "NUL";
                int indent;
                if (this.getResource() != null) {
                    switch (this.getResource().getResourceType()) {
                        case GOLD -> label = "GLD";
                        case LUMBER -> label = "LMB";
                        case STONE -> label = "STN";
                        default ->
                            throw new AssertionError("Incorrect resource type");
                    }

                    for (int i = 0; i < LABEL_LEN; i++) {
                        cellChars[i + 1][CELL_MIDDLE] = label.charAt(i);
                    }
                }
                
                if (this.getResource() != null || this.getUnit() != null) {
                    if (this.getResource() != null) {
                        label = Integer.toString(
                                this.getResource().getResourceQty()
                        );
                    }
                    
                    if (this.getUnit() != null) {
                        label = Integer.toString(this.getUnit().getLife());
                    }
                    
                    indent = LABEL_LEN - label.length();
                    // The quantity of resource is ought to be aligned at
                    // the right margin
                    for (int i = 0; i < label.length(); i++) {
                        cellChars[i + 1 + indent][CELL_MIDDLE - 1] =
                                label.charAt(i);
                    }
                }
            }
            // Debug удалить метод, не пригодился
            public void printCell() {
                int x, y;
                for (y = 0; y < CELL_HEIGHT; y++) {
                    for (x = 0; x < CELL_WIDTH; x++) {
                        System.out.print(this.cellChars[x][y]);
                    }
                    System.out.print("\n");
                }
            }
            
            public boolean actUpon(Unit actUnit) {
                boolean isSuccess = false;
                
                if (this.getUnit() != null) {
                    this.getUnit().attacked(actUnit.getDamage());
                    isSuccess = true;
                }
                else {
                    if (this.getResource() != null) {
                        this.ExtractResource(actUnit);
                        isSuccess = true;
                    }
                }
                
                return isSuccess;
            }
            
            public int ExtractResource(Unit actUnit) {
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

        class Screen {
            public char[][] screen = new char[FLD_WIDTH * CELL_WIDTH]
                                            [FLD_HEIGHT * CELL_HEIGHT];
            private Field field;

            public Field getField() {
                return field;
            }

            public void setField(Field field) {
                this.field = field;
            }

            public Screen(Field field) {
                int x, y;
                this.setField(field);
                assignAllCells();
                for (y = 0; y < screen[0].length; y++) {
                    for (x = 0; x < screen.length; x++) {
                        this.screen[x][y] = ' ';
                    }
                }
            }

            public void assignCell(GameCell cell) {
                int strtX = cell.getxCell() * CELL_WIDTH;
                int strtY = cell.getyCell() * CELL_HEIGHT; // Offset coordinates in
                // the screen
                int x, y; // Coordinates to loop in the screen

                for (y = 0; y < CELL_HEIGHT; y++) {
                    for (x = 0; x < CELL_WIDTH; x++) {
                        this.screen[x + strtX][y + strtY]
                                = cell.cellChars[x][y];
                    }
                }
            }

            public void assignAllCells() {
                int x, y;
                this.getField().recalcCells();
                for (y = 0; y < FLD_HEIGHT; y++) {
                    for (x = 0; x < FLD_WIDTH; x++) {
                        assignCell(getField().cells[x][y]);
                    }
                }
            }

            public void printScreen() {
                int x, y;
                assignAllCells();
                for (y = 0; y < FLD_HEIGHT * CELL_HEIGHT; y++) {
                    for (x = 0; x < FLD_WIDTH * CELL_WIDTH; x++) {
                        System.out.print(screen[x][y]);
                    }
                    System.out.println("");
                }
            }

            public void printSeparator() {
                char sep = (char) 0x2588;
                for (int i = 0; i < FLD_WIDTH * CELL_WIDTH; i++) {
                    System.out.print(sep);
                }
                System.out.println("");
            }
        }

        class Legend {
            private GameCell cell = new GameCell();
            private GameCell terrains = new GameCell();
            private GameCell units = new GameCell();
            private GameCell buildings = new GameCell();

            public void printLegend(Field field) {
                System.out.println("Legend:");
                int x = 0, y = 0;
                String annotation;

                System.out.print("\n");
                System.out.println("Terrains:");

                for (TerrainType terrain : TerrainType.values()) {
                    cell.setTerrainType(terrain);

                    cell.fillCellChars();

                    for (y = 0; y < CELL_HEIGHT; y++) {
                        for (x = 0; x < CELL_WIDTH; x++) {
                            System.out.print(cell.cellChars[x][y]);
                        }
                        annotation =
                                (y == CELL_MIDDLE - 1 ? "\t" + terrain : "");
                        System.out.print(annotation + "\n");
                    }
                    System.out.println("");
                }

                for (Player Player_iter : field.player) {
                    System.out.print("\n\n");
                    System.out.printf("Player %c:\n", Player_iter.getSymbol());
                    System.out.print("\n");
                    System.out.printf("Damage:\t%d\n", Player_iter.getUnit().getDamage());
                    System.out.print("\n");
                    
                    for (Map.Entry<ResourceType, Integer> entry :
                            Player_iter.resources.entrySet()) {
                        ResourceType key = entry.getKey();
                        Integer value = entry.getValue();
                        System.out.printf("%s: %d\n", key, value);                        
                    }
                }
            }
        }
        
        public void updateScreen(Legend legend, Screen screen) {
            screen.printSeparator();
            legend.printLegend(this);
            screen.printScreen();
            screen.printSeparator();
        }
    }
}