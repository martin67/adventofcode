import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
class Day11ChronalCharge {

    @Data
    @AllArgsConstructor
    public static class Coordinate {
        int x;
        int y;

        public String toString() {
            return x + "," + y;
        }
    }

    @Data
    @AllArgsConstructor
    class FuelCell {
        Coordinate coordinate;
        int powerLevel;
        int maxPowerGridSize;
    }

    @Data
    @AllArgsConstructor
    class FuelGrid {
        int xsize;
        int ysize;
        int serial;
        final List<FuelCell> fuelCells = new ArrayList<>();

        void init() {
            for (int x = 0; x < xsize; x++) {
                for (int y = 0; y < ysize; y++) {
                    Coordinate coordinate = new Coordinate(x, y);
                    int maxWidth = xsize - x;
                    int maxHeight = ysize - y;
                    int maxSize = maxWidth < maxHeight ? maxWidth : maxHeight;
                    fuelCells.add(new FuelCell(coordinate, 0, maxSize));
                }
            }
        }

        FuelCell getFuelCell(Coordinate coordinate) {
            return fuelCells.get(coordinate.getX() * this.getXsize() + coordinate.getY());
        }

        void setPowerLevel() {
            for (FuelCell fuelCell : fuelCells) {
                int rackId = fuelCell.getCoordinate().getX() + 10;
                fuelCell.setPowerLevel(rackId * fuelCell.getCoordinate().getY());
                fuelCell.setPowerLevel(fuelCell.getPowerLevel() + serial);
                fuelCell.setPowerLevel(fuelCell.getPowerLevel() * rackId);
                String powerLevel = String.valueOf(fuelCell.getPowerLevel());
                int hundredDigit;
                if (powerLevel.length() < 3) {
                    hundredDigit = 0;
                } else {
                    hundredDigit = Integer.valueOf(powerLevel.substring(powerLevel.length() - 3, powerLevel.length() - 2));
                }
                fuelCell.setPowerLevel(hundredDigit - 5);
            }
        }

        int computePowerGrid(FuelCell fuelCell, int gridSize) {

            // check so that the grid will fit
            if (gridSize > fuelCell.getMaxPowerGridSize()) {
                return 0;
            }

            int gridPower = 0;
            for (int x = fuelCell.getCoordinate().getX(); x < fuelCell.getCoordinate().getX() + gridSize; x++) {
                for (int y = fuelCell.getCoordinate().getY(); y < fuelCell.getCoordinate().getY() + gridSize; y++) {
                    gridPower += getFuelCell(new Coordinate(x, y)).getPowerLevel();
                }
            }
            return gridPower;
        }

    }

    // OK!
    int getPowerLevel(Coordinate coordinate, int serial) {
        FuelGrid fuelGrid = new FuelGrid(300, 300, serial);
        fuelGrid.init();
        fuelGrid.setPowerLevel();

        return fuelGrid.getFuelCell(coordinate).getPowerLevel();
    }

    // OK!
    Coordinate findLargestTotalPower(int serial) {
        FuelGrid fuelGrid = new FuelGrid(300, 300, serial);
        fuelGrid.init();
        fuelGrid.setPowerLevel();

        int maxPower = 0;
        FuelCell maxFuelCell = null;
        for (FuelCell fuelCell : fuelGrid.getFuelCells()) {
            int gridPower = fuelGrid.computePowerGrid(fuelCell, 3);
            if (gridPower > maxPower) {
                maxPower = gridPower;
                maxFuelCell = fuelCell;
            }
        }

        return maxFuelCell.getCoordinate();
    }


    String findLargestTotalPowerAnySize(int serial) {
        FuelGrid fuelGrid = new FuelGrid(300, 300, serial);
        fuelGrid.init();
        fuelGrid.setPowerLevel();

        int maxPower = 0;
        int maxGridSize = 0;
        FuelCell maxFuelCell = null;
        for (FuelCell fuelCell : fuelGrid.getFuelCells()) {
            for (int gridSize = 1; gridSize < 301; gridSize++) {
                int gridPower = fuelGrid.computePowerGrid(fuelCell, gridSize);
                if (gridPower > maxPower) {
                    maxPower = gridPower;
                    maxFuelCell = fuelCell;
                    maxGridSize = gridSize;
                    log.info("Found max: " + maxPower + " at " + maxFuelCell.getCoordinate() + " size " + maxGridSize);
                }
            }
        }
        return maxFuelCell.getCoordinate().toString() + "," + maxGridSize;
    }

}
