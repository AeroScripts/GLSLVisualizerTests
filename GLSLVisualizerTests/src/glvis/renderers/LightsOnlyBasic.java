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
public class LightsOnlyBasic extends VisRender{

    public LightsOnlyBasic(AudioProvider provider, int shader) {
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
//            LIGHT_X = ((float)ie)*LIGHT_POINT_SEPARATION;
            itra ++;
            itra %= samplen;
            raw = samp[itra];
            rraw = samp[samplen-(itra+1)];
//            fraw = ((float) raw) / 65535f;
//            calc = ((float) raw) / 256f;
//            disty = loc_y[ie] + calc/2f;
//            LIGHT_Y = disty + (720 / 2);
//            loc_y[ie] = disty * .94f;
//            loc_y[ie] = loc_y[ie]+raw/6;
            
//            phys_x[ie]/=1.929f;
//            phys_y[ie]/=1.929f;
            
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
//            LIGHT_X = rand[ie]*1280;
//            LIGHT_Y = rand[1023-ie]*720;
            
//////////////            phys_x[ie]/=1.02f;
//////////////            phys_y[ie]/=1.02f;
//////////////            
//////////////            loc_y[ie]/=1.02f;
//////////////            loc_y[ie] +=raw/16 + phys_y[ie];
//////////////            loc_x[ie]/=1.02f;
//////////////            loc_x[ie] +=rraw/16 + phys_x[ie];
//////////////            
//////////////            distx = -loc_x[ie];
//////////////            disty = -loc_y[ie];
//////////////            
//////////////            phys_x[ie] += distx;
//////////////            phys_y[ie] += disty;
//////////////            
////////////////            loc_y[ie]/=1f+Math.abs(loc_y[ie]/548192f);
//////////////            raw = (short) loc_y[ie];
//////////////            LIGHT_Y = 720/2+loc_y[ie]/16f;
//////////////            LIGHT_X = 1280/2+loc_x[ie]/16f;
//            R[ie]*=.86f;
//            G[ie]*=.86f;
//            B[ie]*=.86f;
//            /*switch(itra%3){*/
//                /*case 0: */R[ie]+=Math.abs(loc_y[ie]%4)/56f;/*break;*/
//                /*case 1: */G[ie]+=Math.abs((4f+loc_y[ie])%4)/56f;/*break;*/
//                /*case 2: */B[ie]+=Math.abs((8f+loc_y[ie])%4)/56f;/*break;*/
//            /*}*/
//            R[ie]=G[ie]=B[ie]=(Math.abs(loc_y[ie])+Math.abs(loc_x[ie]))/4432f;

            vdist = Math.abs(distx)+Math.abs(disty);
            vdist/=4f;
            
            R[ie]/=1.08;
            G[ie]/=1.08;
            B[ie]/=1.08;
            R[ie] += ((Math.abs(loc_y[ie]%2f) + Math.abs(loc_x[ie]%2f))*vdist)/1024f;
            G[ie] += ((loc_x[ie]%2f + Math.abs(loc_y[ie]%2f))*vdist)/512f;
            B[ie] += ((Math.abs(loc_x[ie]%2f) + loc_y[ie]%2f)*vdist)/512f;
//            B[ie]/=2;
            
            
//            R[ie]=G[ie]=B[ie]=.1f;
            
            
            GL11.glColorMask(true, true, true, true);
            GL20.glUseProgram(this.shaderProgram);
            GL20.glUniform2f(GL20.glGetUniformLocation(this.shaderProgram, (CharSequence) "location"), LIGHT_X, 720.0F - LIGHT_Y);
            GL20.glUniform3f(GL20.glGetUniformLocation(this.shaderProgram, (CharSequence) "colour"), R[ie], G[ie], B[ie]);
            GL11.glEnable(3042);
            GL11.glBlendFunc(1, 1);
            GL11.glBegin(7);
            GL11.glVertex2f(0.0F, 0.0F);
            GL11.glVertex2f(0.0F, 720.0F);
            GL11.glVertex2f(1280.0F, 720.0F);
            GL11.glVertex2f(1280.0F, 0.0F);
            GL11.glEnd();
            GL11.glDisable(3042);
            GL20.glUseProgram(0);
            GL11.glClear(1024);

        }
    }
    
}
