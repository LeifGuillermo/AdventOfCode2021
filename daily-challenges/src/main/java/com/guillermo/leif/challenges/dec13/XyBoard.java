package com.guillermo.leif.challenges.dec13;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Slf4j
@ToString
public class XyBoard {
    int width;
    int height;

    Set<Point> points;

    private XyBoard() {

    }

    public XyBoard(List<Point> pointList) {
        setBoardDimensions(pointList);
        points = new HashSet<>(pointList);
    }

    /**
     * Folds the current board, returning a new board that is the folded version of the current
     * board.
     *
     * @return a new board that is the folded version of the current board.
     */
    public XyBoard foldBoard(String axis, Integer indexToFold) throws Exception {
        switch (axis) {
            case "y":
                return foldHorizontallyInMiddle(indexToFold);
            case "x":
                return foldVerticallyInMiddle(indexToFold);
        }
        throw new Exception("Unknown axis to fold: " + axis);
    }

    // TODO: I am assuming row to fold is always in the middle. Will have to do additional
    //  calculations if that is not the case.
    private XyBoard foldHorizontallyInMiddle(int rowToFold) {
        XyBoard xyBoard = new XyBoard();
        xyBoard.setWidth(width);
        xyBoard.setHeight(rowToFold - 1);

        // Removing duplicate points for now because those are the instructions essentially.
        Set<Point> newPoints = new HashSet<>();

        for (Point point : points) {
            if (point.getY() > rowToFold) {
                int newY = height - point.y;
                Point newPoint = new Point(point.x, newY);
                newPoints.add(newPoint);
            } else if (point.getY() < rowToFold) {
                newPoints.add(point);
            }
        }
        xyBoard.setPoints(newPoints);

        log.info("Folded Row: " + rowToFold);
//        log.info("New Height:" + xyBoard.getHeight());
//        log.info("New Width:" + xyBoard.getWidth());
//        log.info("New Points: " + Arrays.toString(newPoints.toArray()));

        log.info("Total number of points: " + newPoints.size());

        return xyBoard;
    }

    private XyBoard foldVerticallyInMiddle(int columnToFold) {
        XyBoard xyBoard = new XyBoard();
        xyBoard.setWidth(columnToFold - 1);
        xyBoard.setHeight(height);

        // Removing duplicate points for now because those are the instructions essentially.
        Set<Point> newPoints = new HashSet<>();

        for (Point point : points) {
            if (point.getX() > columnToFold) {
                int newX = width - point.x;
                Point newPoint = new Point(newX, point.y);
                newPoints.add(newPoint);
            } else if (point.getX() < columnToFold) {
                newPoints.add(point);
            }
        }
        xyBoard.setPoints(newPoints);

        log.info("Folded Row: " + columnToFold);
//        log.info("New Height:" + xyBoard.getHeight());
//        log.info("New Width:" + xyBoard.getWidth());
//        log.info("New Points: " + Arrays.toString(newPoints.toArray()));

        log.info("Total number of points: " + newPoints.size());

        return xyBoard;
    }

    public void setBoardDimensions(List<Point> pointList) {
        findHeightFromMaxY(pointList);
        findWidthFromMaxX(pointList);
    }

    private void findWidthFromMaxX(List<Point> pointList) {
        int maxX = 0;

        for (Point point : pointList) {
            if (point.x > maxX) {
                maxX = point.x;
            }
        }
        this.width = maxX;
    }

    private void findHeightFromMaxY(List<Point> pointList) {
        int maxY = 0;

        for (Point point : pointList) {
            if (point.y > maxY) {
                maxY = point.y;
            }
        }
        this.height = maxY;
    }

    public int findMinX(Set<Point> pointList) {
        int minX = 0;

        for (Point point : pointList) {
            if (point.x < minX) {
                minX = point.x;
            }
        }
        return minX;
    }

    public int findMinY(Set<Point> pointList) {
        int minY = 0;

        for (Point point : pointList) {
            if (point.y < minY) {
                minY = point.y;
            }
        }
        return minY;
    }

    public void drawBoard() {
        int minX = findMinX(points);
        int minY = findMinY(points);

        log.info("Mins: (" + minX + "," + minY + ")");
        for (int y = 0; y <= height; y++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int x = 0; x <= width; x++) {
                Point point = new Point(x, y);
                if (points.contains(point)) {
                    stringBuilder.append("#");
                } else {
                    stringBuilder.append(".");
                }
            }
            log.info(stringBuilder.toString());
        }
    }
}
