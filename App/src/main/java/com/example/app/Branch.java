package com.example.app;

import java.util.Arrays;

public class Branch {
private String dst;

private int points[];

    @Override
    public String toString() {
        return "Branch{" +
                "dst='" + dst + '\'' +
                ", points=" + Arrays.toString(points) +
                '}';
    }

    Branch(String dst, int[] points){
        this.dst = dst;
        this.points = points;
    }
    public String getDst() {
        return dst;
    }

    public int[] getPoints() {
        return points;
    }
}
