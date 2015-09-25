/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glvis.renderers;

import glvis.AudioProvider;
import glvis.VisRender;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author root
 */
public class LineTestNoLight extends VisRender {

    public LineTestNoLight(AudioProvider provider, int shader) {
        super(provider, shader);
    }

    @Override
    public void render() {
        
        GL11.glClear(16384);

        int LIGHT_POINTS = 288; // 1152
        float LIGHT_POINT_SEPARATION = 4.42f;

        float LIGHT_Y = 256f;
        float LIGHT_X = 0f;

        short raw = 0;
        float fraw = 0;
        float calc = 0;

        float distx = 0;
        float disty = 0;
        boolean swch = false;
        int hak = 0;
        
        short rraw = 0;
        
        float vdist = 0f;
        short[] samp = provider.getCurrentSample();
        int samplen = provider.getCurrentSampleLength();
        boolean chswch = false;
        for (int ie = 0; ie < LIGHT_POINTS; ie++) {
        
            itra ++;
            itra %= samplen;
            raw = samp[itra];
            rraw = samp[samplen-(itra+1)];
            
            phys_x[ie]/=2f;
            phys_y[ie]/=2f;
            
            distx = -loc_x[ie];
            disty = -loc_y[ie];
            phys_x[ie] += distx + raw/64f;
            phys_y[ie] += disty + rraw/64f;
            
            loc_x[ie] += phys_x[ie]/16f;
            loc_y[ie] += phys_y[ie]/16f;
            
            LIGHT_X = loc_x[ie]*4+1280/2;
            LIGHT_Y = loc_y[ie]*4+720/2;
            
            vdist = Math.abs(distx)+Math.abs(disty);
            vdist/=4f;
            
            R[ie]/=1.08;
            G[ie]/=1.08;
            B[ie]/=1.08;
            R[ie] += ((Math.abs(loc_y[ie]%2f) + Math.abs(loc_x[ie]%2f))*vdist)/1024f;
            G[ie] += ((loc_x[ie]%2f + Math.abs(loc_y[ie]%2f))*vdist)/512f;
            B[ie] += ((Math.abs(loc_x[ie]%2f) + loc_y[ie]%2f)*vdist)/512f;
       

        }
        GL11.glColor3f(1f,1f,1f);;
        GL11.glBegin(GL11.GL_LINES);
        for (int ie = 0; ie < LIGHT_POINTS; ie++) {
            LIGHT_X = loc_x[ie]*4+1280/2;
            LIGHT_Y = loc_y[ie]*4+720/2;
            GL11.glVertex2f(LIGHT_X, LIGHT_Y);
        }
        GL11.glEnd();

    }
    
}
