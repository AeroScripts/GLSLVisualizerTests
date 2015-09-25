/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glvis;

import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.player.Player;
import javazoom.jl.player.jlp;

/**
 *
 * @author root
 */
public class JLayerProvider implements AudioProvider {

    {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                                
                    Thread.sleep(4000);
//                    jlp.main(new String[]{"/Users/aero/Downloads/Kraftwerk - Numbers (1981) [mp3clan.com].mp3"});
                    jlp.main(new String[]{"/Users/aero/Downloads/Madeon - The City.mp3.mp3"});
//                    jlp.main(new String[]{"/Users/aero/Documents/F10/BQYX.mp3"});
//                    jlp.main(new String[]{"/Users/aero/Documents/F10/BQYX.mp3"});
//                    jlp.main(new String[]{"/Users/aero/Downloads/'Sleepless' Chillout Mix (Rameses B).mp3"});
                } catch (InterruptedException ex) {
                    Logger.getLogger(VisCore.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }).start();
    }
    
    @Override
    public short[] getCurrentSample() {
        return Player.samp;
    }

    @Override
    public int getCurrentSampleLength() {
        return Player.samplen;
    }
    
}
