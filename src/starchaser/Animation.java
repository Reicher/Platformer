/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starchaser;

import java.awt.image.BufferedImage;

public class Animation {

    private BufferedImage[] m_frames;
    private int m_currentFrame;

    private long m_startTime;
    private long m_delay;
    private boolean m_doOnce;

    public Animation() {
        m_currentFrame = 0;
        m_doOnce = false;
    }

    public void setFrames(BufferedImage[] images) {
        m_frames = images;
        if(m_currentFrame >= m_frames.length) 
            m_currentFrame = 0;
    }

    public void setDelay(long d) {
        m_delay = d;
    }
    
    public void DoOnceWithDelay(long d){
        setDelay(d);
        m_doOnce = true;
    }
    
    public boolean isRunning(){
        return m_delay != -1;
    }
    
    public void update() {
        if(m_delay == -1 ) return;

        long elapsed = (System.nanoTime() - m_startTime) / 1000000;
        if(elapsed > m_delay) {
            m_currentFrame++;
            m_startTime = System.nanoTime();
        }
        if(m_currentFrame == m_frames.length) {
            m_currentFrame = 0;
            if(m_doOnce){
                m_doOnce = false;
                setDelay(-1);
            }
        }
    }

    public BufferedImage getImage() {
        return m_frames[m_currentFrame];
    }
}