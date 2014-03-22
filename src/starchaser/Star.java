/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starchaser;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author regen
 */
public class Star {
    
    private Animation m_ani;
    private int m_x, m_y;
    private int m_width, m_height;
    private boolean m_taken;
    
    public void setTaken(){
        m_taken = true;
    }
    public boolean isTaken(){ return m_taken; }
    
    public Star(int x, int y, int tileSize){
        m_x = x;
        m_y = y;                
        m_width = (int)(tileSize*0.5);
        m_height = (int)(tileSize*0.5);
        
        m_taken = false;
        
        m_ani = new Animation();
        m_ani.setFrames(AssetHandler.getStar());
        m_ani.setDelay(100);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(m_x, m_y, m_width, m_height);
    }
    
    public void update(){
        m_ani.update();
    }
    
    public void draw(Graphics2D g){
        if(m_taken)
            return;
        
        g.drawImage(
            m_ani.getImage(),
            m_x - m_width/2,
            m_y - m_height/2,
            m_width,
            m_height,
            null
        );
    }
}
