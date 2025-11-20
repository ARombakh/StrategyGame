/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.StrategyGame.*;

/**
 *
 * @author artyom
 */
public class Drawing {

    class GameCellDrawn {
        // The array of characters within the cell
        private StrategyGame.Field.GameCell cell;
        private char[][] cellChars;

        public GameCellDrawn(Field.GameCell cell) {
            setCell(cell);
            cellChars = new char[StrategyGame.CELL_WIDTH]
                                [StrategyGame.CELL_HEIGHT];
        }
        
        public void setCell(Field.GameCell cell) {
            this.cell = cell;
        }

        public Field.GameCell getCell() {
            return cell;
        }
        
        // There are two types of the cell:
            // empty
                // terrain
            // resource, 3 layers:
                // border
                // label, 2 lines:
                    // 
                // symbol (for player)
            

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
                case PLATEAU -> terrain = ' ';
                case MOUNTAIN -> terrain = 'X';
                case WATER -> terrain = 'O';
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

        public void fillCellChars() {
            initCell();            
            fillTerrain();
            if (cell.getBuilding() != null) {
                fillBorder(true);
                cellChars[0][0] = 'B';
            }
            else {
                if (cell.getUnit() != null ||
                        cell.getResource() != null) {
                    fillBorder(false);
                }
            }
            fillPlayerMark();
            fillCellLabel();
        }

        public char playerFiller() {
            return cell.getUnit().getPlayer().getSymbol();
        }

        public void fillPlayerMark() {
            if (cell.getUnit() != null) {
                cellChars[0][0] = this.playerFiller();
            }
        }

        public void fillCellLabel() {
            String label = "NUL";
            int indent;
            if (cell.getResource() != null) {
                switch (cell.getResource().getResourceType()) {
                    case GOLD -> label = "GLD";
                    case LUMBER -> label = "LMB";
                    case STONE -> label = "STN";
                    default ->
                        throw new AssertionError("Incorrect resource type");
                }

                for (int i = 0; i < StrategyGame.LABEL_LEN; i++) {
                    cellChars[i + 1][StrategyGame.CELL_MIDDLE] =
                            label.charAt(i);
                }
            }

            if (cell.getResource() != null || cell.getUnit() != null) {
                if (cell.getResource() != null) {
                    label = Integer.toString(
                            cell.getResource().getResourceQty()
                    );
                }

                if (cell.getUnit() != null) {
                    label = Integer.toString(cell.getUnit().getLife());
                }

                indent = StrategyGame.LABEL_LEN - label.length();
                // The quantity of resource is ought to be aligned at
                // the right margin
                for (int i = 0; i < label.length(); i++) {
                    cellChars[i + 1 + indent][StrategyGame.CELL_MIDDLE - 1] =
                            label.charAt(i);
                }
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
    
    class Screen {
        // Debug нужен ли массив ячеек??
        private GameCellDrawn[][] cells = new
                    GameCellDrawn[StrategyGame.FLD_WIDTH]
                            [StrategyGame.FLD_HEIGHT];
        
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
        
        // Field coordinates of the cell are passed
        public void drawCell(int xF, int yF) {
            int x, y;   // coordinates in the screen
            int xC, yC; // coordinates in the cell
            cells[xF][yF].fillCellChars();

            for (xC = 0; xC < StrategyGame.CELL_WIDTH; xC++) {
                for (yC = 0; yC < StrategyGame.CELL_HEIGHT; yC++) {
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
    }
}
