/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.StrategyGame.*;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author artyom
 */
public class Drawing {
    
    public final static char PLATEAU_SYM = ' '; 
    public final static char MOUNTAIN_SYM = 'X';
    public final static char WATER_SYM = 'O';

    static class GameCellDrawn {
        // The array of characters within the cell
        private StrategyGame.GameCell cell;
        private char[][] cellChars;

        public GameCellDrawn(GameCell cell) {
            setCell(cell);
            cellChars = new char[StrategyGame.CELL_WIDTH]
                                [StrategyGame.CELL_HEIGHT];
        }
        
        public void setCell(GameCell cell) {
            this.cell = cell;
        }

        public GameCell getCell() {
            return cell;
        }          

        public void initCell() {
            int x = 0, y = 0;
            
            for (x = 0; x < StrategyGame.CELL_WIDTH; x++) {
                for (y = 0; y < StrategyGame.CELL_HEIGHT; y++) {
                    cellChars[x][y] = ' ';
                }
            }
        }
        
        public char terrainFiller() {
            char terrain = ' ';
            // Debug стоит вынести в константы
            switch (cell.getTerrainType()) {
                case PLATEAU -> terrain = PLATEAU_SYM;
                case MOUNTAIN -> terrain = MOUNTAIN_SYM;
                case WATER -> terrain = WATER_SYM;
            }
            return terrain;
        }

        public void fillTerrain() {
            char terrain;
            int x, y;

            terrain = terrainFiller();

            for (y = 0; y < StrategyGame.CELL_HEIGHT; y++) {
                for (x = 0; x < StrategyGame.CELL_WIDTH; x++) {
                    cellChars[x][y] = terrain;
                }
            }
        }

        public void fillBorder(boolean thick) {
            int x, y, i;

            int[] bordX = {0, StrategyGame.CELL_WIDTH - 1};
            int[] bordY = {0, StrategyGame.CELL_HEIGHT - 1};
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
    
        public void fillSymbol(char symb) {
            cellChars[0][0] = symb;
        }

        public char playerFiller() {
            return cell.getUnit().getPlayer().getSymbol();
        }

        public void fillPlayerMark() {
            if (cell.getUnit() != null) {
                cellChars[0][0] = this.playerFiller();
            }
        }
        
        public void fillLabel(String label, int offset) {
            if (label.length() > StrategyGame.CELL_WIDTH - 2) {
                throw new IllegalArgumentException("The length of label " +
                                                label + " is too long");
            }
            int indent = StrategyGame.LABEL_LEN - label.length();
            for (int i = 0; i < label.length(); i++) {
                cellChars[i + 1 + indent][StrategyGame.CELL_MIDDLE + offset] =
                        label.charAt(i);
            }
        }
        
        public void fillIntLabel(int label) {
            String strLabel = Integer.toString(label);
            fillLabel(strLabel, -1);
        }
    
        public void fillStrLabel(String label) {
            fillLabel(label, 0);
        }
        
        public void fillAll(boolean thick) {
            initCell();
            fillBorder(thick);
        }
        
        public String getResName(ResourceType resType) {
            switch (resType) {
                case GOLD:
                    return "GLD";
                case LUMBER:
                    return "LMB";
                case STONE:
                    return "STN";
                default:
                    throw new AssertionError("Incorrect resource type");
            }
        }
        
        public void fillCellChars() {
            initCell();
            switch (cell.whatInCell()) {
                case UNIT:
                    fillAll(false);
                    fillSymbol(playerFiller());
                    fillIntLabel(cell.getUnit().getLife());
                    break;
                case BUILDING:
                    char symbol = 'C';
                    switch (cell.getBuilding().getBuildingType()) {
                        case BRIDGE:
                            symbol = 'B';
                            break;
                        case HOUSE:
                            symbol = 'H';
                            break;
                    }
                    fillAll(true);
                    fillSymbol(symbol);
                    fillIntLabel(cell.getBuilding().getLife());
                    break;
                case RESOURCE:
                    String label = getResName(cell.getResource().
                            getResourceType());
                    fillAll(false);
                    fillStrLabel(label);
                    fillIntLabel(cell.getResource().getResourceQty());
                    break;
                case null:
                    fillTerrain();
                    break;
            }
        }
        
        // Debug отрисовка ячейки для проверки
        public void printCell() {
            int x, y;
            
            for (y = 0; y < StrategyGame.CELL_HEIGHT; y++) {
                for (x = 0; x < StrategyGame.CELL_WIDTH; x++) {
                    System.out.print(cellChars[x][y]);
                }
                System.out.println("");
            }
        }
    }
    
    static class Screen {
        // Debug нужен ли массив ячеек??
        private GameCellDrawn[][] cells;
        
        private char[][] screen = new char[StrategyGame.FLD_WIDTH *
                                        StrategyGame.CELL_WIDTH]
                                        [StrategyGame.FLD_HEIGHT *
                                        StrategyGame.CELL_HEIGHT];
        
        private Field field;

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }
        
        public Screen(Field field) {
            int x, y;
            this.field = field;
            this.cells = new
                    GameCellDrawn[StrategyGame.FLD_WIDTH]
                            [StrategyGame.FLD_HEIGHT];
            
            for (y = 0; y < StrategyGame.FLD_HEIGHT; y++) {
                for (x = 0; x < StrategyGame.FLD_WIDTH; x++) {
                    this.cells[x][y] =
                            new GameCellDrawn(this.field.cells[x][y]);
                }
            }
        }
        
        // Field coordinates of the cell are passed
        public void drawCell(int xF, int yF) {
            int x, y;   // coordinates in the screen
            int xC, yC; // coordinates in the cell
            this.field.recalcCells();
            cells[xF][yF].fillCellChars();

            for (yC = 0; yC < StrategyGame.CELL_HEIGHT; yC++) {
                for (xC = 0; xC < StrategyGame.CELL_WIDTH; xC++) {
                    x = StrategyGame.CELL_WIDTH * xF + xC;
                    y = StrategyGame.CELL_HEIGHT * yF + yC;
                    
                    screen[x][y] = cells[xF][yF].cellChars[xC][yC];
                }
            }
        }
        
        public void drawAllCells() {
            int x, y;   // coordinates in the field
            
            for (x = 0; x < StrategyGame.FLD_WIDTH; x++) {
                for (y = 0; y < StrategyGame.FLD_HEIGHT; y++) {
                    drawCell(x, y);
                }
            }
        }
        
        public void printScreen() {
            int x, y;
            int xMax, yMax;
                        
            xMax = StrategyGame.CELL_WIDTH * StrategyGame.FLD_WIDTH;
            yMax = StrategyGame.CELL_HEIGHT * StrategyGame.FLD_HEIGHT;
            
            drawAllCells();

            for (y = 0; y < yMax; y++) {
                for (x = 0; x < xMax; x++) {
                    System.out.print(screen[x][y]);
                }                
                System.out.println("");
            }
        }
        
        public void printSeparator() {
            char sep = (char) 0x2588;
            for (int i = 0; i < StrategyGame.FLD_WIDTH *
                    StrategyGame.CELL_WIDTH;
                    i++) {
                System.out.print(sep);
            }
            System.out.println("");
        }
    }

    static class Legend {
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

                GameCellDrawn cellDrawn = new GameCellDrawn(cell);
                cellDrawn.fillCellChars();

                for (y = 0; y < StrategyGame.CELL_HEIGHT; y++) {
                    for (x = 0; x < StrategyGame.CELL_WIDTH; x++) {
                        System.out.print(cellDrawn.cellChars[x][y]);
                    }
                    annotation =
                            (y == StrategyGame.CELL_MIDDLE - 1 ?
                                        "\t" + terrain : "");
                    System.out.print(annotation + "\n");
                }
                System.out.println("");
            }

            for (Player Player_iter : field.player) {
                System.out.print("\n\n");
                System.out.printf("Player %c:\n", Player_iter.getSymbol());
                System.out.print("\n");
                System.out.printf("Damage:\t%d\n", Player_iter.
                        getUnit().getDamage());
                System.out.print("\n");

                for (Map.Entry<ResourceType, Integer> entry :
                        Player_iter.getResources().entrySet()) {
                    ResourceType key = entry.getKey();
                    Integer value = entry.getValue();
                    System.out.printf("%s: %d\n", key, value);                        
                }
            }
        }
    }

    public static void updateScreen(Legend legend, Screen screen, Field field) {
        screen.printSeparator();
        legend.printLegend(field);
        screen.printScreen();
        screen.printSeparator();
    }
}