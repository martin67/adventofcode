import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class Day11ChronalCharge {

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
        int powerGridLevel;
    }

    @Data
    @AllArgsConstructor
    class FuelGrid {
        int xsize;
        int ysize;
        int serial;
        final List<FuelCell> fuelCells = new ArrayList<FuelCell>();

        void init() {
            for (int x = 0; x < xsize; x++) {
                for (int y = 0; y < ysize; y++) {
                    Coordinate c = new Coordinate(x, y);
                    fuelCells.add(new FuelCell(new Coordinate(x, y), 0, 0));
                }
            }
        }

        FuelCell getFuelCell(Coordinate coordinate) {
            return fuelCells.get(coordinate.getX()*this.getXsize() + coordinate.getY());
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

        void computePowerGrid() {
            //for (FuelCell fuelCell : fuelCells) {
            for (int i = 0; i < fuelCells.size(); i++) {

                FuelCell fuelCell = fuelCells.get(i);

                // Skip the Fuel Cells that are on the edge
                if (((fuelCell.getCoordinate().getX() % (this.getXsize()-2)) == 0) ||
                        ((fuelCell.getCoordinate().getX() % (this.getXsize()-1)) == 0) ||
                        (fuelCell.getCoordinate().getY() > (this.getYsize() - 3))) {
                    //log.info("skipping border, " + fuelCell.getCoordinate());
                } else {
                    //log.info("Computing powergrid for cell at " + fuelCell.getCoordinate());
                    fuelCell.setPowerGridLevel(fuelCell.getPowerLevel() +
                            fuelCells.get(i + 1).getPowerLevel() +
                            fuelCells.get(i + 2).getPowerLevel() +
                            fuelCells.get(i + this.getXsize()).getPowerLevel() +
                            fuelCells.get(i + this.getXsize() + 1).getPowerLevel() +
                            fuelCells.get(i + this.getXsize() + 2).getPowerLevel() +
                            fuelCells.get(i + this.getXsize() * 2).getPowerLevel() +
                            fuelCells.get(i + this.getXsize() * 2 + 1).getPowerLevel() +
                            fuelCells.get(i + this.getXsize() * 2 + 2).getPowerLevel());
                }
            }
        }

        Coordinate getLargestPowerGrid() {
            FuelCell maxPower = fuelCells.stream().max(Comparator.comparingInt(FuelCell::getPowerGridLevel)).get();
            return maxPower.getCoordinate();
        }
    }


    Coordinate findLargestTotalPower(int serial) {

        FuelGrid fuelGrid = new FuelGrid(300, 300, serial);

        fuelGrid.init();
        fuelGrid.setPowerLevel();
        fuelGrid.computePowerGrid();
        Coordinate answer = fuelGrid.getLargestPowerGrid();

        return answer;
    }

    int getPowerLevel(Coordinate coordinate, int serial) {
        FuelGrid fuelGrid = new FuelGrid(300, 300, serial);
        fuelGrid.init();
        fuelGrid.setPowerLevel();
        return fuelGrid.getFuelCell(coordinate).getPowerLevel();
    }

}

