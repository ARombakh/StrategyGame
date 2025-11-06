/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package strategygame;

import java.util.Random;

/**
 *
 * @author artyom
 */
public class StrategyGame {

    final public static int FLD_WIDTH = 15;
    final public static int FLD_HEIGHT = 15;
    final public static int CELL_WIDTH = 5;
    final public static int CELL_HEIGHT = 4;
    
    public enum TerrainType {
        PLATEAU,
        WATER,
        MOUNTAIN
    }
    
    public enum Player {
        PLAYER1,
        PLAYER2
    }
    
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    
    static class Unit {
        public Player player;
        public int Life;
        public int Damage;
        GameCell Cell;
        
        public Unit (Player player, GameCell Cell) {
            this.player = player;
            this.Cell = Cell;
            this.Life = 100;
            this.Damage = 5;
        }

        public boolean moveUnit(Direction direction) {
            boolean isSuccess;
            GameCell source = this.Cell;
            GameCell dest = this.Cell;  // Destination cell
            int x = source.xCell, y = source.yCell;

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
            
            if (!(x >= 0 && x < CELL_WIDTH) ||
                    !(y >= 0 && y < CELL_HEIGHT)) {
                System.out.printf("%s moves out of the field\n",
                                    source.unit.player);
                System.out.println("Choose another direction.");
                isSuccess = false;
            } else {
                dest = source.field.cells[x][y];
                
                if (source.unit == null) {
                    System.out.println("There is no unit in this cell");
                    isSuccess = false;
                }
                else {
                    if (dest.building == null
                            && dest.unit == null
                            && dest.terrainType == TerrainType.PLATEAU) {
                        dest.unit = source.unit;
                        source.unit = null;
                        isSuccess = true;
                    }
                    else {
                        System.out.println("The destination cell is taken");
                        isSuccess = false;
                    }
                }
            }
            this.Cell = dest;
            return isSuccess;
        }
    }
    
    static class Building {
        public int Life;
    }
    
    // почему нужно обязательно указывать static??
    static class GameCell {
        public TerrainType terrainType;
        public Unit unit;
        public Building building;
        public Field field;
        final public int xCell;     // компилятор выдаёт предупреждение по xCell
                                    // но не по yCell
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
            char player = ' ';
            switch (this.unit.player) {
                case PLAYER1 -> player = 'X';
                case PLAYER2 -> player = 'O';
            }
            return player;
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
                if (this.unit != null) {
                    fillBorder(false);
                    cellChars[0][0] = this.playerFiller();
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
    }

    // почему нужно обязательно указывать static??    
    public static class Field {
        public int width;
        public int height;
        public GameCell[][] cells;
        
        public Field() {
            int x, y;
            
            this.width = FLD_WIDTH;
            this.height = FLD_HEIGHT;
            this.cells = new GameCell[this.width][this.height];
            
            for (y = 0; y < FLD_HEIGHT; y++) {
                for (x = 0; x < FLD_WIDTH; x++) {
                    this.cells[x][y] = new GameCell(x, y, this);
                }
            }
        }
        
        public void randomize() {
            int x, y, randTerrain, randomizer;
            for (y = 0; y < this.height; y++) {
                for (x = 0; x < this.width; x++) {
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
                    cells[x][y].fillCellChars();
                }
            }
        }
    }
    
    static class Screen {
        public char[][] screen = new char[FLD_WIDTH * CELL_WIDTH]
                                        [FLD_HEIGHT * CELL_HEIGHT];
        public Field field;

        public Screen() {
            int x, y;

            for (y = 0; y < screen[0].length; y++) {
                for (x = 0; x < screen.length; x++) {
                    this.screen[x][y] = ' ';
                }
            }
        }
        
        public void assignCell(GameCell Cell, int cellX, int cellY) {
            int strtX = cellX * CELL_WIDTH;
            int strtY = cellY * CELL_HEIGHT; // Offset coordinates in the screen
            int x, y; // Coordinates to loop in the screen

            for (y = 0; y < CELL_HEIGHT; y++) {
                for (x = 0; x < CELL_WIDTH; x++) {
                    this.screen[x + strtX][y + strtY] = Cell.cellChars[x][y];
                }
            }
        }
        
        public void assignAllCells(Field field) {
            int x, y;
            field.recalcCells();
            for (y = 0; y < FLD_HEIGHT; y++) {
                for (x = 0; x < FLD_WIDTH; x++) {
                    assignCell(field.cells[x][y], x, y);
                }                
            }
        }
        
        public void printScreen() {
            int x, y;
            assignAllCells(field);
            
            for (y = 0; y < FLD_HEIGHT * CELL_HEIGHT; y++) {
                for (x = 0; x < FLD_WIDTH * CELL_WIDTH; x++) {
                    System.out.print(screen[x][y]);
                }
                System.out.println("");
            }
        }
    }
    
    static class Legend {
        GameCell Cell = new GameCell();
        GameCell terrains = new GameCell();
        GameCell units = new GameCell();
        GameCell buildings = new GameCell();
        
        public void printLegend() {
            System.out.println("Legend:");
            int x = 0, y = 0;
            int middle = (CELL_HEIGHT % 2 == 0 ? CELL_HEIGHT / 2 :
                                                (CELL_HEIGHT / 2) + 1);
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
                    annotation = (y == middle - 1 ? "\t" + terrain : "");
                    System.out.print(annotation + "\n");
                }
                System.out.println("");
            }
        }
    }
}