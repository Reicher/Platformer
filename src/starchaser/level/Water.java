/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package starchaser.level;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import starchaser.Animation;
import starchaser.AssetHandler;

/**
 *
 * @author regen
 */
// Is almost identical to star, should inherit from some class/Interface

public class Water {
    
    private Animation m_ani;
    private int m_x, m_y;
    private int m_width, m_height;
    
    public Water(int x, int y, int tileSize){
        m_x = x;
        m_y = y;                
        m_width = (int)(tileSize);
        m_height = (int)(tileSize);
        
        // Not really needed now
        m_ani = new Animation();
        m_ani.setFrames(AssetHandler.getWater());
        m_ani.setDelay(100);
    }
    
        
    public Rectangle getBounds() {
        return new Rectangle(m_x, m_y, m_width, m_height);
    }
    
    public void update(){
        m_ani.update();
    }
    
    public void draw(Graphics2D g){
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
