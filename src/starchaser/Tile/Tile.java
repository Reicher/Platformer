/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package starchaser.Tile;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import starchaser.Animation;

/**
 *
 * @author regen
 */
public abstract class Tile {
    protected Animation m_animation;
    protected int m_x, m_y;
    protected int m_width, m_height;
    
    protected Tile(int x, int y, int width, int height, BufferedImage[] image){
        m_x = x;
        m_y = y;
        m_width = width;
        m_height = height;
        
        m_animation = new Animation();
        m_animation.setFrames(image);
        
        if(image.length == 1)
            m_animation.setDelay(-1);
        else
            m_animation.setDelay(100);
    }
    
    public void draw(Graphics2D g){
        g.drawImage(
            m_animation.getImage(),
            m_x - m_width/2,
            m_y - m_height/2,
            m_width,
            m_height,
            null);
    }   
    
    public void update(){
        m_animation.update();
    }
    
    public Rectangle getBounds() {
        return new Rectangle(m_x, m_y, m_width, m_height);
    }
}
