package com.dekagames.dongle;

/**
 * Class for drawing primitives
 */
public class Primitives {
    // осмысленные имена индексов в массиве координат
    static final int	X1=0, 	Y1=1, 	R1=2,  G1=3,  B1=4,  A1=5, 	S1=6, 	T1=7,
                        X2=8, 	Y2=9, 	R2=10, G2=11, B2=12, A2=13,	S2=14, 	T2=15,
                        X3=16, 	Y3=17, 	R3=18, G3=19, B3=20, A3=21,	S3=22, 	T3=23,
                        X4=24, 	Y4=25, 	R4=26, G4=27, B4=28, A4=29,	S4=30, 	T4=31,
                        X5=32, 	Y5=33, 	R5=34, G5=35, B5=36, A5=37,	S5=38, 	T5=39,
                        X6=40, 	Y6=41, 	R6=42, G6=43, B6=44, A6=45,	S6=46, 	T6=47;

    private  static float r, g, b, a;   // color components

    static {
        b = 1.0f;
        a = 1.0f;
    }

    private static float vertices[];

    public static void setColor(float red, float green, float blue, float alpha){
        r = red;
        g = green;
        b = blue;
        a = alpha;
    }
    public static void drawPoint(Graphics gr, float x, float y){
        // place appropriate data into the buffer
        vertices = new float[Graphics.VERTEX_SIZE * 6];
        vertices[X1]=x-1; vertices[Y1]=y-1; vertices[R1]=r; vertices[G1]=g; vertices[B1]=b; vertices[A1]=a;
        vertices[X2]=x+1; vertices[Y2]=y-1; vertices[R2]=r; vertices[G2]=g; vertices[B2]=b; vertices[A2]=a;
        vertices[X3]=x-1; vertices[Y3]=y+1; vertices[R3]=r; vertices[G3]=g; vertices[B3]=b; vertices[A3]=a;
        vertices[X4]=x+1; vertices[Y4]=y-1; vertices[R4]=r; vertices[G4]=g; vertices[B4]=b; vertices[A4]=a;
        vertices[X5]=x+1; vertices[Y5]=y+1; vertices[R5]=r; vertices[G5]=g; vertices[B5]=b; vertices[A5]=a;
        vertices[X6]=x-1; vertices[Y6]=y+1; vertices[R6]=r; vertices[G6]=g; vertices[B6]=b; vertices[A6]=a;
        gr.draw(null, vertices);
    }
}
