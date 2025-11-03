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

    final private static int FLD_WIDTH = 10;
    final private static int FLD_HEIGHT = 10;
    final private static int CELL_WIDTH = 5;
    final private static int CELL_HEIGHT = 4;
    
    public static char[][] screen;
    
    class Unit{
        public int Player;
        public int Type;
    }
    class Building{}
    
    // почему нужно обязательно указывать static?
    static class GameCell {
        public int terrainType;    // Type of terrain 0 - plateau, 1 - water
                                    // 2 - mountain
        public Unit unit;
        public Building building;
        
        public char[][] cellChars;
        
        public GameCell(int terrainType) {
            this.terrainType = terrainType;
            this.unit = null;
            this.building = null;
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
        
        public void fillCellChars() {
            
        }
    }

    // почему нужно обязательно указывать static?    
    static class Field {
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Field field = new Field(10, 10);
        field.DrawField();
        System.out.println(field.cells[0][0].TerrainName());
    }   
}