import com.sun.xml.internal.ws.api.ha.StickyFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day10TheStarsAlign {

    @Data
    @AllArgsConstructor
    class Point {
        int xpos;
        int ypos;
        int xvel;
        int yvel;
    }

    @Data
    class Box {
        int startx;
        int starty;
        int width;
        int height;
    }

    List<Point> pointList = new ArrayList<>();

    void readData(String input) {
        // Split string into a list
        List<String> inputStrings = Arrays.stream(input.trim().split("\\n")).collect(Collectors.toList());


        for (String s : inputStrings) {
            String s1 = StringUtils.substringBetween(s, "position=<", ">");
            String s2 = StringUtils.substringBetween(s, "velocity=<", ">");
            pointList.add(new Point(
                    Integer.parseInt(StringUtils.substringBefore(s1, ",").trim()),
                    Integer.parseInt(StringUtils.substringAfter(s1, ",").trim()),
                    Integer.parseInt(StringUtils.substringBefore(s2, ",").trim()),
                    Integer.parseInt(StringUtils.substringAfter(s2, ",").trim())));
        }
    }

    Box computeSize() {
        // Loop and find the highest and lowest of xpos and ypos. This gives the box
        Box theSize = new Box();

        int lowX = pointList.stream().min(Comparator.comparingInt(Point::getXpos)).get().getXpos();
        int highX = pointList.stream().max(Comparator.comparingInt(Point::getXpos)).get().getXpos();
        int lowY = pointList.stream().min(Comparator.comparingInt(Point::getYpos)).get().getYpos();
        int highY = pointList.stream().max(Comparator.comparingInt(Point::getYpos)).get().getYpos();

        theSize.setStartx(lowX);
        theSize.setStarty(lowY);
        theSize.setWidth(highX - lowX + 1);
        theSize.setHeight(highY - lowY + 1);

        return theSize;
    }

    void movePoints() {
        // Move all points
        pointList.stream().forEach(p -> {
            p.setXpos(p.getXpos() + p.getXvel());
            p.setYpos(p.getYpos() + p.getYvel());
        })
        ;
    }

    void printPoints() {
        Box box = computeSize();
            char[][] = new char[box.getWidth()][box.getHeight()];

        }
    }

    String getMessage(String input) {
        readData(input);

        // Assume that the message is when the box is as small as possible

        for (int i = 0; i < 10; i++) {
            Box box = computeSize();
            log.info("Box " + box + " size: " + box.getHeight() * box.getWidth());
            movePoints();
        }


        return "xyzzy";
    }
}
