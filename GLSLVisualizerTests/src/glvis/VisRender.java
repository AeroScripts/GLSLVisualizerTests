/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glvis;

/**
 *
 * @author root
 */
public abstract class VisRender {
    
    public float[] R = new float[4096], G = new float[4096], B = new float[4096];
    public int itra = 0;

    public float[] phys_x = new float[4096];
    public float[] phys_y = new float[4096];

    public float[] loc_x = new float[4096];
    public float[] loc_y = new float[4096];
    
    public float[] rand = new float[4096];

    public float[] vel = new float[4096];
    
    public float[] lastx = new float[4096];
    public float[] lasty = new float[4096];
    
    public float[] lastx2 = new float[4096];
    public float[] lasty2 = new float[4096];
    
    public float[] lastx3 = new float[4096];
    public float[] lasty3 = new float[4096];
    
    public float[] lastx4 = new float[4096];
    public float[] lasty4 = new float[4096];
    
    public float[] lastx5 = new float[4096];
    public float[] lasty5 = new float[4096];
    
    public float[] lastx6 = new float[4096];
    public float[] lasty6 = new float[4096];
    
    public float[] lastx7 = new float[4096];
    public float[] lasty7 = new float[4096];
    
    public float[] lastx8 = new float[4096];
    public float[] lasty8 = new float[4096];
    
    public float[] lastx9 = new float[4096];
    public float[] lasty9 = new float[4096];
    
    
    public boolean initial = true;
    
    public float[] lR = new float[4096], lG = new float[4096], lB = new float[4096];
    public float[] lR2 = new float[4096], lG2 = new float[4096], lB2 = new float[4096];
    public float[] lR3 = new float[4096], lG3 = new float[4096], lB3 = new float[4096];
    public float[] lR4 = new float[4096], lG4 = new float[4096], lB4 = new float[4096];
    public float[] lR5 = new float[4096], lG5 = new float[4096], lB5 = new float[4096];
    public float[] lR6 = new float[4096], lG6 = new float[4096], lB6 = new float[4096];
    
    public float[] lR7 = new float[4096], lG7 = new float[4096], lB7 = new float[4096];
    public float[] lR8 = new float[4096], lG8 = new float[4096], lB8 = new float[4096];
    public float[] lR9 = new float[4096], lG9 = new float[4096], lB9 = new float[4096];
    
    
    public AudioProvider provider;
    public int shaderProgram;
    
    public VisRender(AudioProvider provider, int shader){
        this.provider = provider;
        this.shaderProgram = shader;
    }
    public abstract void render();
}
