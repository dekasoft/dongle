package com.dekagames.dongle;

/**
 * This class represents vertex data transferred to vertex shader.
 */
public class Vertex {
    public static int VERTEX_SIZE = 8 * 4;
    public float x;         // screen coordinates x,y
    public float y;
    public float r;         // color elements r, g, b, a
    public float g;
    public float b;
    public float a;
    public float s;         // texture coordinates s,t
    public float t;


    public Vertex(float x, float y, float r, float g, float b, float a, float s, float t) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.s = s;
        this.t = t;
    }

    public Vertex(Vertex origin){
        this.x = origin.x;
        this.y = origin.y;
        this.r = origin.r;
        this.g = origin.g;
        this.b = origin.b;
        this.a = origin.a;
        this.s = origin.s;
        this.t = origin.t;
    }

}
