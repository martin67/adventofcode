package aoc.aoc2018;

import aoc.common.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
class Day11ChronalCharge {

    // OK!
    int getPowerLevel(Position position, int serial) {
        FuelGrid fuelGrid = new FuelGrid(300, 300, serial);
        fuelGrid.init();
        fuelGrid.setPowerLevel();

        return fuelGrid.fuelCells.get(position).getPowerLevel();
    }

    // OK!
    Position findLargestTotalPower(int serial) {
        FuelGrid fuelGrid = new FuelGrid(300, 300, serial);
        fuelGrid.init();
        fuelGrid.setPowerLevel();

        int maxPower = 0;
        FuelCell maxFuelCell = null;
        for (FuelCell fuelCell : fuelGrid.fuelCells.values()) {
            int gridPower = fuelGrid.computePowerGrid(fuelCell, 3);
            if (gridPower > maxPower) {
                maxPower = gridPower;
                maxFuelCell = fuelCell;
            }
        }

        return maxFuelCell.position;
    }

    String findLargestTotalPowerAnySize(int serial) {
        FuelGrid fuelGrid = new FuelGrid(300, 300, serial);
        fuelGrid.init();
        fuelGrid.setPowerLevel();

        int maxPower = 0;
        int maxGridSize = 0;
        FuelCell maxFuelCell = null;
        for (FuelCell fuelCell : fuelGrid.fuelCells.values()) {
            //for (int gridSize = 1; gridSize < 301; gridSize++) {
                //int gridPower = fuelGrid.computePowerGrid(fuelCell, gridSize);
            int gridSize = 300;
                int gridPower = fuelGrid.computePowerGrid2(fuelCell, gridSize);
                if (gridPower > maxPower) {
                    maxPower = gridPower;
                    maxFuelCell = fuelCell;
                    maxGridSize = gridSize;
                    log.info("Found max: {} at {} size {}", maxPower, maxFuelCell.position, maxGridSize);
              //  }
            }
        }
        return maxFuelCell.position.toString() + "," + maxGridSize;
    }

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
    static class FuelCell {
        Position position;
        int powerLevel;
        int maxPowerGridSize;
    }

    @Data
    static class FuelGrid {
        Map<Position, FuelCell> fuelCells = new HashMap<>();
        int xsize;
        int ysize;
        int serial;

        public FuelGrid(int xsize, int ysize, int serial) {
            this.xsize = xsize;
            this.ysize = ysize;
            this.serial = serial;
        }

        void init() {
            for (int x = 0; x < xsize; x++) {
                for (int y = 0; y < ysize; y++) {
                    Position position = new Position(x, y);
                    int maxWidth = xsize - x;
                    int maxHeight = ysize - y;
                    int maxSize = Math.min(maxWidth, maxHeight);
                    fuelCells.put(position, new FuelCell(position, 0, maxSize));
                }
            }
        }

        void setPowerLevel() {
            for (FuelCell fuelCell : fuelCells.values()) {
                int rackId = fuelCell.position.getX() + 10;
                fuelCell.setPowerLevel(rackId * fuelCell.position.getY());
                fuelCell.setPowerLevel(fuelCell.getPowerLevel() + serial);
                fuelCell.setPowerLevel(fuelCell.getPowerLevel() * rackId);
                String powerLevel = String.valueOf(fuelCell.getPowerLevel());
                int hundredDigit;
                if (powerLevel.length() < 3) {
                    hundredDigit = 0;
                } else {
                    hundredDigit = Integer.parseInt(powerLevel.substring(powerLevel.length() - 3, powerLevel.length() - 2));
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
            for (int x = fuelCell.position.getX(); x < fuelCell.position.getX() + gridSize; x++) {
                for (int y = fuelCell.position.getY(); y < fuelCell.position.getY() + gridSize; y++) {
                    gridPower += fuelCells.get(new Position(x, y)).getPowerLevel();
                }
            }
            return gridPower;
        }

        int computePowerGrid2(FuelCell fuelCell, int gridSize) {
            int maximumGridSize = Math.min(gridSize - fuelCell.position.getX(), gridSize - fuelCell.position.getY());
            int maxGridPower = 0;
            Position maxPosition;

            int previousGridPower = 0;
            for (int i = 1; i < maximumGridSize; i++) {
                int xPower = 0;
                for (int x = 0; x < i; x++) {
                    xPower += fuelCells.get(new Position(fuelCell.position.getX() + x, fuelCell.position.getY())).powerLevel;
                }
                int yPower = 0;
                for (int y = 0; y < i - 1; y++) {
                    yPower += fuelCells.get(new Position(fuelCell.position.getX(), fuelCell.position.getY() + y)).powerLevel;
                }
                int power = xPower + yPower + previousGridPower;
                if (power > maxGridPower) {
                    maxGridPower = power;
                }
                previousGridPower = power;
            }
            return maxGridPower;
        }
    }

}
