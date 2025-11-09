/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package strategygame;

import java.util.Random;
import strategygame.Map.*;

/**
 *
 * @author artyom
 */
public class StrategyGame {

    final public static int FLD_WIDTH = 15;
    final public static int FLD_HEIGHT = 15;
    final public static int CELL_WIDTH = 5;
    final public static int CELL_HEIGHT = 4;
    
    final public static int X_START_PL1 = 0;
    final public static int Y_START_PL1 = 0;
    
    final public static int X_START_PL2 = FLD_WIDTH - 1;
    final public static int Y_START_PL2 = FLD_HEIGHT - 1;
    
    public static boolean gameOver = false;
    
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
    
    Player playerTurn;

    // почему нужно обязательно указывать static??    
    public static class Field {
        public GameCell[][] cells;
        Unit Player1;
        Unit Player2;
        
        public Field() {
            int x, y;

            this.cells = new GameCell[FLD_WIDTH][FLD_HEIGHT];
            
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
                    cells[x][y].fillCellChars();
                }
            }
        }

        class Unit {
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
            
            public Unit (Player player, int x, int y) {
                GameCell Cell = cells[x][y];
                
                this(player, Cell);
            }

            // Насколько разумно возвращать метод ту же GameCell в случае
            // неудачи??
            public GameCell destCell(GameCell source, Direction direction) {
                GameCell dest;

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

                if (!(x >= 0 && x < FLD_WIDTH) ||
                        !(y >= 0 && y < FLD_HEIGHT)) {
                    System.out.printf("Target cell of %s is out of the field\n",
                                        source.unit.player);
                    System.out.println("Choose another direction.");
                    dest = source;
                }
                else dest = source.field.cells[x][y];

                return dest;
            }

            public boolean move(Direction direction) {
                boolean isSuccess;
                GameCell source = this.Cell;
                GameCell dest;  // Destination cell

                dest = destCell(source, direction);
                if (dest == source) {
                    isSuccess = false;
                } else {
                    if (dest.building == null
                            && dest.unit == null
                            && dest.terrainType == TerrainType.PLATEAU) {
                        dest.unit = source.unit;
                        source.unit = null;
                        isSuccess = true;
                    }
                    else {
                        System.out.println("The destination cell is taken");
                        dest = source;
                        isSuccess = false;
                    }
                }

                this.Cell = dest;
                return isSuccess;
            }

            public boolean action(Direction direction) {
                boolean isSuccess;
                GameCell source = this.Cell;
                GameCell dest;  // Destination cell

                dest = destCell(source, direction);
                if (dest == source) {
                    isSuccess = false;
                } else {
                    if (dest.building == null
                            && dest.unit == null) {
                        System.out.println("No one to attack in the target cell");
                        dest = source;
                        isSuccess = false;
                    }
                    else {
                        if (dest.unit != null) {
                            dest.unit.attacked(source.unit.Damage);
                        }
                        isSuccess = true;
                    }
                }

                return isSuccess;
            }

            public boolean attacked(int Damage) {
                this.Life -= (this.Life > Damage ? Damage : this.Life);
                if (this.Life == 0) {
                    gameOver = true;
                }
                return true;
            }
        }

        static class Building {
            public int Life;
        }

        // почему нужно обязательно указывать static??
        class GameCell {
            public TerrainType terrainType;
            public Unit unit;
            public Building building;
            public Field field;
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
                        this.screen[x + strtX][y + strtY] = Cell.cellChars[x][y];
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
        
        public void updateScreen(Legend legend, Screen screen) {
            legend.printLegend();
            screen.printScreen();
            screen.printSeparator();
        }
    }
    
    static class Game {
        Field field;
        Field.Legend legend;
        Field.Screen screen;
        
        public Game() {
            this.field = initField();
            this.legend = field.new Legend();
            this.screen = field.new Screen(this.field);

            field.updateScreen(this.legend, this.screen);
        }
        
        public static Field initField() {
            Field.GameCell Cell;

            // Можно ли наполнить поле без создания нового объекта "карта"??
            Map map = new Map();
            Field field = map.Plateau();

            Cell = field.cells[X_START_PL1][Y_START_PL1];
            Cell.unit = field.new Unit(Player.PLAYER1, Cell);
            field.Player1 = Cell.unit;
            
            Cell = field.cells[X_START_PL2][Y_START_PL2];
            Cell.unit = field.new Unit(Player.PLAYER2, Cell);
            field.Player2 = Cell.unit;
            return field;
        }
    }
}