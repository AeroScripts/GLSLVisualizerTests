/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glvis.renderers;

import glvis.AudioProvider;
import glvis.VisCore;
import glvis.VisRender;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author root
 */
public class DemandingLightningTriangleStrip extends VisRender{

    public DemandingLightningTriangleStrip(AudioProvider provider, int shader) {
        super(provider, shader);
    }

    @Override
    public void render() {
        if(initial){GL11.glClear(16384);initial=false;}
        

        int LIGHT_POINTS = 288; // 1152
        LIGHT_POINTS = 2;
//        int LIGHT_POINTS = 1152; // 1152
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
        
        boolean chswch = false;
        
        GL11.glEnable(GL11.GL_BLEND); //Enable blending.
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); 
        if(initial){
            initial = false;
            GL11.glColor4f(0f,0f,0f,1f);
        }else GL11.glColor4f(0f,0f,0f,0.01f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(0,0);
        GL11.glVertex2f(1280,0);
        GL11.glVertex2f(1280,720);
        GL11.glVertex2f(0,720);
        short[] samp = provider.getCurrentSample();
        int samplen = provider.getCurrentSampleLength();
        GL11.glDisable(GL11.GL_BLEND); //Enable blending.
        GL11.glColor4f(1f,1f,1f,1f);
//        GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_LINE );
        
        for (int ie = 0; ie < LIGHT_POINTS; ie++) {GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
            //GL11.glColor4f(((float)ie)/LIGHT_POINTS,((float)ie)/LIGHT_POINTS,((float)ie)/LIGHT_POINTS,1f);
           
            itra ++;
            itra %= samplen;
            raw = samp[itra];
            rraw = samp[samplen-(itra+1)];
            
            phys_x[ie]/=2f;
            phys_y[ie]/=2f;
            
            distx = -loc_x[ie];
            disty = -loc_y[ie];
//            phys_x[ie] += distx + raw/64f;
//            phys_y[ie] += disty + rraw/64f;
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
            LIGHT_X = loc_x[ie]*2+1280/2;
            LIGHT_Y = loc_y[ie]*2+720/2;
//            GL11.glColor3f(lR9[ie], lG9[ie], lB9[ie]);
//            GL11.glVertex2f(lastx9[ie], lasty9[ie]);
//            GL11.glColor3f(lR8[ie], lG8[ie], lB8[ie]);
//            GL11.glVertex2f(lastx8[ie], lasty8[ie]);
//            GL11.glColor3f(lR7[ie], lG7[ie], lB7[ie]);
//            GL11.glVertex2f(lastx7[ie], lasty7[ie]);
            
            
            GL11.glColor3f(lR6[ie], lG6[ie], lB6[ie]);
            GL11.glVertex2f(lastx6[ie], lasty6[ie]);
            GL11.glColor3f(lR5[ie], lG5[ie], lB5[ie]);
            GL11.glVertex2f(lastx5[ie], lasty5[ie]);
            GL11.glColor3f(lR4[ie], lG4[ie], lB4[ie]);
            GL11.glVertex2f(lastx4[ie], lasty4[ie]);
            GL11.glColor3f(lR3[ie], lG3[ie], lB3[ie]);
            GL11.glVertex2f(lastx3[ie], lasty3[ie]);
            GL11.glColor3f(lR2[ie], lG2[ie], lB2[ie]);
            GL11.glVertex2f(lastx2[ie], lasty2[ie]);
            GL11.glColor3f(lR[ie], lG[ie], lB[ie]);
            GL11.glVertex2f(lastx[ie], lasty[ie]);
            GL11.glColor3f(R[ie]*2, G[ie]*2, B[ie]*2);
            GL11.glVertex2f(LIGHT_X, LIGHT_Y);
            
            
            
            
            
            
            
            lastx9[ie] = lastx8[ie];
            lasty9[ie] = lasty8[ie];
            lastx8[ie] = lastx7[ie];
            lasty8[ie] = lasty7[ie];
            lastx7[ie] = lastx6[ie];
            lasty7[ie] = lasty6[ie];
            
            lastx6[ie] = lastx5[ie];
            lasty6[ie] = lasty5[ie];
            lastx5[ie] = lastx4[ie];
            lasty5[ie] = lasty4[ie];
            lastx4[ie] = lastx3[ie];
            lasty4[ie] = lasty3[ie];
            lastx3[ie] = lastx2[ie];
            lasty3[ie] = lasty2[ie];
            lastx2[ie] = lastx[ie];
            lasty2[ie] = lasty[ie];
//            lastx[ie] = LIGHT_X;
//            lasty[ie] = LIGHT_Y;
            lastx[ie] = LIGHT_X;
            lasty[ie] = LIGHT_Y;
            
            lR9[ie]=lR8[ie];lG9[ie]=lG8[ie];lB9[ie]=lB8[ie];
            lR8[ie]=lR7[ie];lG8[ie]=lG7[ie];lB8[ie]=lB7[ie];
            lR7[ie]=lR6[ie];lG7[ie]=lG6[ie];lB7[ie]=lB6[ie];
            
            
            lR6[ie]=lR5[ie]/1.2f;lG6[ie]=lG5[ie]/1.2f;lB6[ie]=lB5[ie]/1.2f;
            lR5[ie]=lR4[ie]/1.2f;lG5[ie]=lG4[ie]/1.2f;lB5[ie]=lB4[ie]/1.2f;
            lR4[ie]=lR3[ie]/1.2f;lG4[ie]=lG3[ie]/1.2f;lB4[ie]=lB3[ie]/1.2f;
            lR3[ie]=lR2[ie]/1.2f;lG3[ie]=lG2[ie]/1.2f;lB3[ie]=lB2[ie]/1.2f;
            lR2[ie]=lR[ie]/1.2f;lG2[ie]=lG[ie]/1.2f;lB2[ie]=lB[ie]/1.2f;
            lR[ie]=R[ie]*2;lG[ie]=G[ie]*2;lB[ie]=B[ie]*2;GL11.glEnd();
        }
        
        for (int ie = 0; ie < LIGHT_POINTS; ie++) {
            LIGHT_X = loc_x[ie]*2+1280/2;
            LIGHT_Y = loc_y[ie]*2+720/2;
            
            GL11.glColorMask(true, true, true, true);
            GL20.glUseProgram(this.shaderProgram);
            GL20.glUniform2f(GL20.glGetUniformLocation(this.shaderProgram, (CharSequence) "location"), LIGHT_X, 720.0F - LIGHT_Y);
            GL20.glUniform3f(GL20.glGetUniformLocation(this.shaderProgram, (CharSequence) "colour"), R[ie]/2, G[ie]/2, B[ie]/2);
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

        }
        //
        Display.sync(600);
    }
    
}
