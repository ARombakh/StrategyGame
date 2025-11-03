/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javastrategygame;

/**
 *
 * @author artyom
 */
public class JavaStrategyGame {

    final private static int FLD_WIDTH = 3;
    final private static int FLD_HEIGHT = 3;
    final private static int CELL_WIDTH = 5;
    final private static int CELL_HEIGHT = 4;
    
    static class Unit{
        public int Player;
        public int Type;
    }
    static class Building{}
    
    // почему нужно обязательно указывать static?
    static class GameCell {
        public int terrainType;    // Type of terrain 0 - plateau, 1 - water
                                    // 2 - mountain
        public Unit unit;
        public Building building;
        
        public char[][] cellChars = new char[CELL_WIDTH][CELL_HEIGHT];
        
        // Нужно ли инициализировать ячейку?
        public void initCell() {
            int x, y;
            
            for (x = 0; x < CELL_WIDTH; x++) {
                for (y = 0; y < CELL_HEIGHT; y++) {
                    cellChars[x][y] = ' ';
                }
            }
        }
        
        public GameCell(int terrainType) {
            this.terrainType = terrainType;
            this.unit = null;
            this.building = new Building();
            // в чём проблема в overridable методе в конструкторе?
            // Почему выскакивает предупреждение?
            initCell();
        }
        
        public String TerrainName() {
            String terrName = "";
            
            switch(this.terrainType) {
                case 0 -> terrName = "PLT";
                case 1 -> terrName = "WTR";
                case 2 -> terrName = "MNT";
            }
            return terrName;
        }

        
        public void fillBorder(boolean thick) {
            int x, y, i;
            
            int[] bordX = {0, CELL_WIDTH - 1};
            int[] bordY = {0, CELL_HEIGHT - 1};
            
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
            int x, y;
            if (this.building != null) {
                fillBorder(true);
            }
            else {
                if (this.unit != null) {
                    fillBorder(false);
                }
            }
        }
        
        public void printCell() {
            int x, y;
            for (y = 0; y < CELL_HEIGHT; y++) {
                for (x = 0; x < CELL_WIDTH; x++) {
                    System.out.print(this.cellChars[x][y]);
                }
                System.out.println("");
            }
        }
    }

    // почему нужно обязательно указывать static?    
    public static class Field {
        public int width;
        public int height;
        public GameCell[][] cells;
        
        public Field(int width, int height) {
            int x, y, randTerrain;
            this.width = width;
            this.height = height;
            this.cells = new GameCell[this.width][this.height];
            
            for (y = 0; y < this.height; y++) {
                for (x = 0; x < this.width; x++) {
                    randTerrain = (int)(Math.random() * 3);
                    cells[x][y] = new GameCell(randTerrain);
                }
            }
        }
        
        public void DrawField() {
            int x, y;
            String spc = " ";
            
            for (y = 0; y < 10; y++) {
                for (x = 0; x < 10; x++) {
                    spc = (x == this.width - 1 ? "" : " ");
                    System.out.printf("%d%s",
                                        this.cells[x][y].terrainType, spc);
                }
                System.out.print("\n");
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
            
            for (y = 0; y < FLD_HEIGHT; y++) {
                for (x = 0; x < FLD_WIDTH; x++) {
                    assignCell(field.cells[x][y], x, y);
                }                
            }
        }
        
        public void printScreen() {
            int x, y;
            
            for (y = 0; y < FLD_HEIGHT * CELL_HEIGHT; y++) {
                for (x = 0; x < FLD_WIDTH * CELL_WIDTH; x++) {
                    System.out.print(screen[x][y]);
                }
                System.out.println("");
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Field field = new Field(10, 10);
        field.DrawField();
                
        field.cells[0][0].fillBorder(true);
        field.cells[1][1].fillBorder(false);
        
        Screen screen = new Screen();
        screen.assignAllCells(field);
        screen.printScreen();
    }   
}