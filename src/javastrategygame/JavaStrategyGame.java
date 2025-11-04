/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javastrategygame;

import java.util.Random;

/**
 *
 * @author artyom
 */
public class JavaStrategyGame {

    final private static int FLD_WIDTH = 20;
    final private static int FLD_HEIGHT = 4;
    final private static int CELL_WIDTH = 5;
    final private static int CELL_HEIGHT = 4;
    
    public enum TerrainType {
        PLATEAU,
        WATER,
        MOUNTAIN
    }
    
    static class Unit{
        public char Player;
        public int Type;
    }
    
    static class Building{}
    
    // почему нужно обязательно указывать static??
    static class GameCell {
        public TerrainType terrainType;
        public Unit unit;
        public Building building;
        
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
        
        public void fillTerrain() {
            /*char terrain[] = this.TerrainName().toCharArray();
            
            for (int i = 0; i < CELL_WIDTH - 2; i++) {
                cellChars[i + 1][1] = terrain[i];
            }*/
            char terrain = ' ';
            int x, y;
            
            switch (this.terrainType) {
                case PLATEAU:
                    terrain = ' ';
                    break;
                case MOUNTAIN:
                    terrain = 'X';
                    break;
                case WATER:
                    terrain = 'O';
                    break;
            }
            
            for (y = 0; y < CELL_HEIGHT; y++) {
                for (x = 0; x < CELL_WIDTH; x++) {
                    cellChars[x][y] = terrain;
                }
            }
        }
              
        public GameCell() {
            this.terrainType = terrRand();
            this.unit = null;
            this.building = null;
            // в чём проблема в overridable методе в конструкторе??
            // Почему выскакивает предупреждение??
            fillCellChars();
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
            int x, y;
            initCell();            
            fillTerrain();
            if (this.building != null) {
                fillBorder(true);
                cellChars[0][0] = 'B';
            }
            else {
                if (this.unit != null) {
                    fillBorder(false);
                    cellChars[0][0] = this.unit.Player;
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
            int x, y, randTerrain, randomizer;
            this.width = FLD_WIDTH;
            this.height = FLD_HEIGHT;
            this.cells = new GameCell[this.width][this.height];
            
            for (y = 0; y < this.height; y++) {
                for (x = 0; x < this.width; x++) {
                    randomizer = (int)(Math.random() * 9);
                    switch (randomizer) {
                        case 1, 2, 3, 4, 5:
                            randTerrain = 0;
                            break;
                        case 6, 7, 8:
                            randTerrain = 1;
                            break;
                        default:
                            randTerrain = 2;
                    }
                    // randTerrain = (int)(Math.random() * 3);
                    
                    cells[x][y] = new GameCell();
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
        Field field = new Field();
 
//        field.cells[0][0].building = new Building();
        field.cells[0][0].unit = new Unit();
        field.cells[0][0].unit.Player = 'X';
        field.cells[0][0].fillCellChars();
        
        Screen screen = new Screen();
        screen.assignAllCells(field);
        screen.printScreen();
    }
}