package com.example.app;

import java.util.Arrays;

public class Line {
    private String src;

    private String dst;

    private int id;

    private int[] points;

    private Branch[] branches;

    private static int linesCount;

    @Override
    public String toString() {
        return "Line{" +
                "src='" + src + '\'' +
                ", dst='" + dst + '\'' +
                ", id=" + id +
                ", points=" + Arrays.toString(points) +
                ", branches=" + Arrays.toString(branches) +
                '}';
    }

    Line(String src, String dst, int[] points, Branch[] branches) {
    this.src = src;
    this.dst = dst;
    this.points = points;
    this.branches = branches;

    this.id = linesCount++;

    }
    Line(String src,int[] points,Branch[] branches) {
        this.src = src;
        this.points = points;
    this.branches = branches;

        this.id = linesCount++;
    }

    public String getSrc() {
        return src;
    }

    public String getDst() {
        return dst;
    }

    public int getId() {
        return id;
    }

    public int[] getPoints() {
        return points;
    }

    public Branch[] getBranches() {
        return branches;
    }
}
