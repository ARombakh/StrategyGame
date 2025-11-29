/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.StrategyGame.*;
import static strategygame.StrategyGame.*;
import java.util.Random;

/**
 *
 * @author artyom
 */
public class GameCell {

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
            //Debug simplify expression
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
            newResQty = actUnit.getPlayer().getResource(resourceType);
            newResQty += extracted;
            actUnit.getPlayer().setResource(resourceType, newResQty);

            return extracted;
        }
}
