/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starchaser;

import java.awt.Graphics2D;

/**
 *
 * @author regen
 */
public class Star {
    
    Animation m_ani;
    int m_x, m_y;
    int m_width, m_height;
    
    public Star(int x, int y){
        
        m_x = x;
        m_y = y;
        
        m_width = 32;
        m_height = 32;
        
        m_ani = new Animation();
        m_ani.setFrames(AssetHandler.getStar());
        m_ani.setDelay(100);
    }
    
    public void update(){
        m_ani.update();
    }
    
    public void draw(Graphics2D g){
        g.drawImage(
            m_ani.getImage(),
            (int) (m_x - m_width / 2 + m_width),
            (int) (m_y - m_height / 2),
            -m_width,
            m_height,
            null
        );
    }
}
