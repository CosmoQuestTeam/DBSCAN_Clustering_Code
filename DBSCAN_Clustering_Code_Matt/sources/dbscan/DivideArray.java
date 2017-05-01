package dbscan;

import java.util.Vector;

//divide the total sorted data into 5 part
public class DivideArray {
    private int num_part1;
    private int num_part2;
    private int num_part3;
    private int num_part4;
    private int num_part5;
    private Point[] part1, part2, part3, part4, part5;

    public void divideArray(Point[] totalPart) {
        int totalLength = totalPart.length;
        int average = totalLength / 5;
        num_part1 = average;
        while (totalPart[num_part1 + 1].getX() - totalPart[num_part1].getX() < 1)
            num_part1++;

        num_part2 = average * 2;
        while (totalPart[num_part2 + 1].getX() - totalPart[num_part2].getX() < 1)
            num_part2++;

        num_part3 = average * 3;
        while (totalPart[num_part3 + 1].getX() - totalPart[num_part3].getX() < 1)
            num_part3++;

        num_part4 = average * 4;
        while (totalPart[num_part4 + 1].getX() - totalPart[num_part4].getX() < 1)
            num_part4++;

        num_part5 = totalLength - num_part4;
        num_part4 = num_part4 - num_part3;
        num_part3 = num_part3 - num_part2;
        num_part2 = num_part2 - num_part1;
        part1 = new Point[num_part1];
        part2 = new Point[num_part2];
        part3 = new Point[num_part3];
        part4 = new Point[num_part4];
        part5 = new Point[num_part5];


        for (int i = 0; i < num_part1; i++) {

            part1[i] = totalPart[i];               //first part
        }

        for (int i = 0; i < num_part2; i++) {

            part2[i] = totalPart[i + num_part1];               //second part
        }

        for (int i = 0; i < num_part3; i++) {

            part3[i] = totalPart[i + num_part1 + num_part2];               //third part
        }

        for (int i = 0; i < num_part4; i++) {

            part4[i] = totalPart[i + num_part1 + num_part2 + num_part3];               //fourth part
        }
        for (int i = 0; i < num_part5; i++) {

            part5[i] = totalPart[i + num_part1 + num_part2 + num_part3 + num_part4];               //fifth part
        }

    }

    public Vector<Point> getPart1() {
        Vector<Point> result = new Vector<Point>();
        for (int i = 0; i < part1.length; i++) {
            result.add(part1[i]);
        }
        return result;
    }


    public Vector<Point> getPart2() {
        Vector<Point> result = new Vector<Point>();
        for (int i = 0; i < part2.length; i++) {
            result.add(part2[i]);
        }
        return result;
    }

    public Vector<Point> getPart3() {
        Vector<Point> result = new Vector<Point>();
        for (int i = 0; i < part3.length; i++) {
            result.add(part3[i]);
        }
        return result;
    }

    public Vector<Point> getPart4() {
        Vector<Point> result = new Vector<Point>();
        for (int i = 0; i < part4.length; i++) {
            result.add(part4[i]);
        }
        return result;
    }

    public Vector<Point> getPart5() {
        Vector<Point> result = new Vector<Point>();
        for (int i = 0; i < part5.length; i++) {
            result.add(part5[i]);
        }
        return result;
    }
}
