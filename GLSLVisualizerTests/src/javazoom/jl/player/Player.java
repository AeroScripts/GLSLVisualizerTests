package javazoom.jl.player;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;

public class Player {

   private int frame;
   private Bitstream bitstream;
   private Decoder decoder;
   private AudioDevice audio;
   private boolean closed;
   private boolean complete;
   private int lastPosition;
   public boolean pause;
   public boolean playi;
   public static short[] samp = new short[2326];
   public static int samplen = 2326;


   public Player(InputStream stream) throws JavaLayerException {
      this(stream, (AudioDevice)null);
   }

   public Player(InputStream stream, AudioDevice device) throws JavaLayerException {
      this.frame = 0;
      this.closed = false;
      this.complete = false;
      this.lastPosition = 0;
      this.pause = false;
      this.playi = true;
      this.bitstream = new Bitstream(stream);
      this.decoder = new Decoder();
      if(device != null) {
         this.audio = device;
      } else {
         FactoryRegistry r = FactoryRegistry.systemRegistry();
         this.audio = r.createAudioDevice();
      }

      this.audio.open(this.decoder);
   }

   public void play() throws JavaLayerException {
      this.play(Integer.MAX_VALUE);
   }

   public boolean play(int frames) throws JavaLayerException {
      boolean ret;
      for(ret = true; frames-- > 0 && ret && this.playi; ret = this.decodeFrame()) {
         while(this.pause) {
            try {
               Thread.sleep(100L);
            } catch (InterruptedException var7) {
               Logger.getLogger(Player.class.getName()).log(Level.SEVERE, (String)null, var7);
            }
         }
      }

      if(!this.playi) {
         return true;
      } else {
         if(!ret) {
            AudioDevice out = this.audio;
            if(out != null) {
               out.flush();
               synchronized(this) {
                  this.complete = !this.closed;
                  this.close();
               }
            }
         }

         return ret;
      }
   }

   public synchronized void close() {
      AudioDevice out = this.audio;
      if(out != null) {
         this.closed = true;
         this.audio = null;
         out.close();
         this.lastPosition = out.getPosition();

         try {
            this.bitstream.close();
         } catch (BitstreamException var3) {
            ;
         }
      }

   }

   public synchronized boolean isComplete() {
      return this.complete;
   }

   public int getPosition() {
      int position = this.lastPosition;
      AudioDevice out = this.audio;
      if(out != null) {
         position = out.getPosition();
      }

      return position;
   }

   protected boolean decodeFrame() throws JavaLayerException {
      try {
         AudioDevice ex = this.audio;
         if(ex == null) {
            return false;
         } else {
            Header h = this.bitstream.readFrame();
            if(h == null) {
               return false;
            } else if(!this.playi) {
               return false;
            } else {
               SampleBuffer output = (SampleBuffer)this.decoder.decodeFrame(h, this.bitstream);
               synchronized(this) {
                  ex = this.audio;
                  if(ex != null) {
                     if(!this.playi) {
                        return false;
                     }

                     ex.write(samp = output.getBuffer(), 0, samplen = output.getBufferLength());
//                     System.out.println(samplen);
                  }
               }

               this.bitstream.closeFrame();
               return true;
            }
         }
      } catch (RuntimeException var7) {
         throw new JavaLayerException("Exception decoding audio frame", var7);
      }
   }

}
