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
public interface AudioProvider {
    public short[] getCurrentSample();
    public int getCurrentSampleLength();
}
