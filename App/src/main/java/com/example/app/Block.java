package com.example.app;

import java.util.Arrays;

public class Block {
    private String blockType;
    private String name;

    private String SID;

    private int[] ports = {1,1};
    private int[] position;




    private String mirror;

    public Block(String blockType, String name, String SID, int[] ports, int[] position,String mirror) {
        this.blockType = blockType;
        this.name = name;
        this.SID = SID;
        this.ports = ports;
        this.position = position;
        this.mirror=mirror;
    }

    public Block(String blockType, String name, String SID, int[] position,String mirror) {
        this.blockType = blockType;
        this.name = name;
        this.SID = SID;
        this.position = position;;
        this.mirror=mirror;
    }


    //GETTERS
    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    @Override
    public String toString() {
        return "Block{" +
                "blockType='" + blockType + '\'' +
                ", name='" + name + '\'' +
                ", SID='" + SID + '\'' +
                ", ports=" + Arrays.toString(ports) +
                ", position=" + Arrays.toString(position) +
                ", mirror='" + mirror + '\'' +
                '}';
    }

    public int[] getPorts() {
        return ports;
    }

    public int[] getPosition() {
        return position;
    }

    public String getMirror() {
        return mirror;
    }
}
