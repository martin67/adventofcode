package aoc2016;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day3SquaresWithThreeSides {

    @Data
    @AllArgsConstructor
    static class Triangle {
        int sideA;
        int sideB;
        int sideC;

        boolean validTriangle() {
            return (sideA + sideB > sideC) && (sideA + sideC > sideB) && (sideB + sideC > sideA);
        }
    }

    private Set<Triangle> triangles = new HashSet<>();
    private Set<Triangle> verticalTriangles = new HashSet<>();

    public Day3SquaresWithThreeSides(String fileName) throws IOException {
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        List<String> inputStrings = Files.readAllLines(Paths.get(fileName));

        int rows = 0;
        Triangle leftTriangle = null;
        Triangle centerTriangle = null;
        Triangle rightTriangle = null;

        for (String row : inputStrings) {
            String[] s = row.trim().split("\\s+");
            triangles.add(new Triangle(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])));

            switch (rows % 3) {
                case 0:
                    leftTriangle = new Triangle(Integer.parseInt(s[0]), 0, 0);
                    centerTriangle = new Triangle(Integer.parseInt(s[1]), 0, 0);
                    rightTriangle = new Triangle(Integer.parseInt(s[2]), 0, 0);
                    break;
                case 1:
                    leftTriangle.sideB = Integer.parseInt(s[0]);
                    centerTriangle.sideB = Integer.parseInt(s[1]);
                    rightTriangle.sideB = Integer.parseInt(s[2]);
                    break;
                case 2:
                    leftTriangle.sideC = Integer.parseInt(s[0]);
                    centerTriangle.sideC = Integer.parseInt(s[1]);
                    rightTriangle.sideC = Integer.parseInt(s[2]);
                    verticalTriangles.add(leftTriangle);
                    verticalTriangles.add(centerTriangle);
                    verticalTriangles.add(rightTriangle);
                    break;
            }
            rows++;
        }
    }

    long validTriangles() {
        return triangles.stream().filter(Triangle::validTriangle).count();
    }

    long validVerticalTriangles() {
        return verticalTriangles.stream().filter(Triangle::validTriangle).count();
    }

}
