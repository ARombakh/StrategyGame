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

    final public static int FLD_WIDTH = 8;
    final public static int FLD_HEIGHT = 8;
    
    final public static int CELL_WIDTH = 5;
    final public static int CELL_HEIGHT = 4;
    final public static int CELL_MIDDLE = (CELL_HEIGHT % 2 == 0 ?
                                            CELL_HEIGHT / 2 :
                                                    (CELL_HEIGHT / 2) + 1);
    
    final public static int[] X_START_PL = {0, FLD_WIDTH - 1};
    final public static int[] Y_START_PL = {0, FLD_HEIGHT - 1};
    
    final public static int LIFE = 20;
    final public static int DAMAGE = 5;
    final public static int EXTRACT_CAPACITY = 5;
    
    final public static int LABEL_LEN = 3;
    
    final public static char[] PLAYER_SYMBOL = {'X', 'O'};
    
    final public static int PLAYERS_COUNT = 2;
    
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
        public ResourceType resourceType;
        public int resourceQty;

        public Resource(ResourceType resourceType, int resourceQty) {
            this.resourceType = resourceType;
            this.resourceQty = resourceQty;
        }
    }

    // почему нужно обязательно указывать static??    
    public static class Field {
        public GameCell[][] cells;
        Player[] Player;
        
        public Field() {
            int x, y;

            this.cells = new GameCell[FLD_WIDTH][FLD_HEIGHT];
            this.Player = new Field.Player[PLAYERS_COUNT];
                        
            for (y = 0; y < FLD_HEIGHT; y++) {
                for (x = 0; x < FLD_WIDTH; x++) {
                    this.cells[x][y] = new GameCell(x, y, this);
                }
            }
        }
        
        class Player {
            char symbol;
            Unit unit;
            public EnumMap<ResourceType, Integer> resources =
                    new EnumMap<>(ResourceType.class);
            
            public Player(char symbol) {
                this.symbol = symbol;
                
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
                    cells[x][y].checkCell();
                    cells[x][y].fillCellChars();
                }
            }
        }

        static class Building {
            public int life;
        }
        
        class Unit {
            public Player player;
            public GameCell cell;
            public int life;
            public int Damage;
            public int ResExtrCapacity;

            public Unit (Player player, GameCell cell) {
                this.player = player;
                this.cell = cell;
                this.life = LIFE;
                this.Damage = DAMAGE;
                this.ResExtrCapacity = EXTRACT_CAPACITY;
            }

            // Насколько разумно возвращать метод ту же GameCell в случае
            // неудачи??
            public GameCell destCell(Direction direction) {
                GameCell dest;

                int x = cell.xCell, y = cell.yCell;

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
                                        this.player);
                    System.out.println("Choose another direction.");
                    dest = this.cell;
                }
                else dest = this.cell.field.cells[x][y];

                return dest;
            }

            public boolean move(Direction direction) {
                boolean isSuccess;
                GameCell source = this.cell;
                GameCell dest;  // Destination cell

                dest = destCell(direction);
                if (dest == source) {
                    isSuccess = false;
                } else {
                    if (dest.building == null
                            && dest.unit == null
                            && dest.resource == null
                            && dest.terrainType == TerrainType.PLATEAU) {
                        dest.unit = source.unit;
                        source.unit = null;
			this.cell = dest;
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
                GameCell source = this.cell;
                GameCell dest;  // Destination cell

                dest = destCell(direction);
                if (dest == source) {
                    isSuccess = false;
                } else {
                    if (dest.building == null
                            && dest.unit == null
                            && dest.resource == null) {
                        System.out.println(
                                "No one to act upon in the target cell");
                        isSuccess = false;
                    }
                    else {
                        if (dest.unit != null ||
                                dest.resource != null) {
                            dest.actUpon(source.unit);
                        }
                        isSuccess = true;
                    }
                }

                return isSuccess;
            }

            public void attacked(int Damage) {
                this.life -= (this.life > Damage ? Damage : this.life);
            }
        }

        // почему нужно обязательно указывать static??
        class GameCell {
            public TerrainType terrainType;
            public Unit unit;
            public Building building;
            public Field field;
            public Resource resource;
            final public int xCell;
            final public int yCell;     // coordinates of the cell in the field

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
            
            public Field getField() {
                return this.field;
            }
            
            public void checkCell() {
                if (this.resource != null && this.resource.resourceQty == 0) {
                    this.resource = null;
                }
                
                if (this.unit != null && this.unit.life == 0) {
                    this.unit = null;
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
                switch (this.terrainType) {
                    case PLATEAU -> terrain = ' ';
                    case MOUNTAIN -> terrain = 'X';
                    case WATER -> terrain = 'O';
                }
                return terrain;
            }

            public char playerFiller() {
                return this.unit.player.symbol;
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
                this.field = field;
                this.xCell = xCell;
                this.yCell = yCell;
                this.terrainType = terrRand();
                this.unit = null;
                this.building = null;
                // в чём проблема в overridable методе в конструкторе??
                // Почему выскакивает предупреждение??
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
                if (this.building != null) {
                    fillBorder(true);
                    cellChars[0][0] = 'B';
                }
                else {
                    if (this.unit != null ||
                            this.resource != null) {
                        fillBorder(false);
                    }
                }
                fillPlayerMark();
                fillCellLabel();
            }
            
            public void fillPlayerMark() {
                if (this.unit != null) {
                    cellChars[0][0] = this.playerFiller();
                }
            }
            
            public void fillCellLabel() {
                String label = "NUL";
                int indent;
                if (this.resource != null) {
                    switch (this.resource.resourceType) {
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
                
                if (this.resource != null || this.unit != null) {
                    if (this.resource != null) {
                        label = Integer.toString(this.resource.resourceQty);
                    }
                    
                    if (this.unit != null) {
                        label = Integer.toString(this.unit.life);
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
                
                if (this.unit != null) {
                    this.unit.attacked(actUnit.Damage);
                    isSuccess = true;
                }
                else {
                    if (this.resource != null) {
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
                resourceType = this.resource.resourceType;
                
                if(this.resource.resourceQty > actUnit.ResExtrCapacity)
                    extracted = actUnit.ResExtrCapacity;
                else
                    extracted = this.resource.resourceQty;
                this.resource.resourceQty -= extracted;
                newResQty = actUnit.player.resources.get(resourceType);
                newResQty += extracted;
                actUnit.player.resources.put(resourceType, newResQty);

                return extracted;
            }
        }

        class Screen {
            public char[][] screen = new char[FLD_WIDTH * CELL_WIDTH]
                                            [FLD_HEIGHT * CELL_HEIGHT];
            public Field field;

            public Screen(Field field) {
                int x, y;
                this.field = field;
                assignAllCells(this.field);
                for (y = 0; y < screen[0].length; y++) {
                    for (x = 0; x < screen.length; x++) {
                        this.screen[x][y] = ' ';
                    }
                }
            }

            public void assignCell(GameCell Cell) {
                int strtX = Cell.xCell * CELL_WIDTH;
                int strtY = Cell.yCell * CELL_HEIGHT; // Offset coordinates in
                // the screen
                int x, y; // Coordinates to loop in the screen

                for (y = 0; y < CELL_HEIGHT; y++) {
                    for (x = 0; x < CELL_WIDTH; x++) {
                        this.screen[x + strtX][y + strtY]
                                = Cell.cellChars[x][y];
                    }
                }
            }

            public void assignAllCells(Field field) {
                int x, y;
                field.recalcCells();
                for (y = 0; y < FLD_HEIGHT; y++) {
                    for (x = 0; x < FLD_WIDTH; x++) {
                        assignCell(field.cells[x][y]);
                    }
                }
            }

            public void printScreen() {
                int x, y;
                assignAllCells(this.field);
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
            GameCell Cell = new GameCell();
            GameCell terrains = new GameCell();
            GameCell units = new GameCell();
            GameCell buildings = new GameCell();

            public void printLegend(Field field) {
                System.out.println("Legend:");
                int x = 0, y = 0;
                String annotation;

                System.out.print("\n");
                System.out.println("Terrains:");

                for (TerrainType terrain : TerrainType.values()) {
                    Cell.terrainType = terrain;

                    Cell.fillCellChars();

                    for (y = 0; y < CELL_HEIGHT; y++) {
                        for (x = 0; x < CELL_WIDTH; x++) {
                            System.out.print(Cell.cellChars[x][y]);
                        }
                        annotation =
                                (y == CELL_MIDDLE - 1 ? "\t" + terrain : "");
                        System.out.print(annotation + "\n");
                    }
                    System.out.println("");
                }

                for (Player Player_iter : field.Player) {
                    System.out.print("\n\n");
                    System.out.printf("Player %c:\n", Player_iter.symbol);
                    System.out.print("\n");
                    System.out.printf("Damage:\t%d\n", Player_iter.unit.Damage);
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