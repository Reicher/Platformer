/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starchaser;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author regen
 */
public class Background {
    private int m_width, m_height;
    private BufferedImage m_background; 
    
    private Animation m_stars;
    private long m_starStartTime;
    private long m_starDelay;
    private int[] m_starPos;
    
    Background(int width, int height){
        m_width = width;
        m_height = height;               
    
        m_background =  AssetHandler.getBackground();    

        // Load star animation
        m_starPos = new int[2];
        m_starDelay = (int)(Math.random() * 4000.0);

        m_stars = new Animation();
        m_stars.setFrames(AssetHandler.getBackgroundStar());
        m_stars.setDelay(-1);
  
    }
    
    public void draw(Graphics2D g) {
        g.drawImage(m_background, 0, 0, m_width, m_height, null);

        if(m_stars.isRunning())
            g.drawImage(m_stars.getImage()
                , m_starPos[0]
                , m_starPos[1]
                , 32, 32, null );
    }
    
    public void update(){        
        long elapsed = (System.nanoTime() - m_starStartTime) / 1000000;
        if(elapsed > m_starDelay) {
            m_starStartTime = System.nanoTime();
            m_starDelay = (int)(Math.random() * 2000.0);
            m_starPos[0] = (int)(Math.random() * m_width);
            m_starPos[1] = (int)(Math.random() * m_height/2);
            m_stars.DoOnceWithDelay(50);
        }
        
        m_stars.update();
    }
        
}
