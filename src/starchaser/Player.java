/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starchaser;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author regen
 */
public class Player extends MovingTile{
    private boolean m_dead;
    private boolean m_isUsing;
    
    private double m_moveSpeed, m_maxSpeed, m_stopSpeed;
    private double m_jumpStart;
    
    private boolean m_facingLeft;
    
    private boolean m_bottomLeft;
    private boolean m_bottomRight;
    private boolean m_topLeft;
    private boolean m_topRight;

    public boolean isDead(){ return m_dead; }
        
    public Player(int width, int height) {
        super(0, 0, width, height, AssetHandler.getPlayerIdle());
        
        m_isUsing = false;
        m_facingLeft = false;
        
        m_moveSpeed = 0.6;
        m_maxSpeed = 7.0;
        m_stopSpeed = 0.5;
        m_jumpStart = -height * 0.16;

        init();
    }
    
    public void init(){
        m_dead = false;
    }
    
    public void update() {
        
        // Moving left and right
        if(m_left) {
                m_dx -= m_moveSpeed;
                m_dx = m_dx < -m_maxSpeed ? -m_maxSpeed : m_dx;
        }
        else if(m_right) {
                m_dx += m_moveSpeed;
                m_dx = m_dx > m_maxSpeed ? m_maxSpeed : m_dx;
        }
        else { // Not waling, Slowing down
                if(m_dx > 0) {
                        m_dx -= m_stopSpeed;
                        m_dx = m_dx < 0 ? 0 : m_dx;
                }
                else if(m_dx < 0) {
                        m_dx += m_stopSpeed;
                        m_dx = m_dx > 0 ? 0 : m_dx;
                }
        }
        
        // Jumping!
        if(m_jumping) {
            m_dy = m_jumpStart;
            m_inAir = true;
            m_jumping = false;
        }
        // Air time!
        if(m_inAir) {
                m_dy += m_gravity;
                if(m_dy > m_maxFallingSpeed) 
                        m_dy = m_maxFallingSpeed;
        }
        else 
            m_dy = 0;
        
       if(haveFallenOffMap())
           m_dead = true;
       else
            CheckForObstacles();
        
        setAnimation();
        
        // Update pos
        m_x += m_dx;                 
        m_y += m_dy;
    }
    
    private boolean haveFallenOffMap(){
        if( m_y >= (m_tileMap.getTileSize() * m_tileMap.getGridRows() - m_height))
            return true;
        
        return false;
    }
    
    private void CheckForObstacles(){
         
        double toX = m_x + m_dx;
        double toY = m_y + m_dy;

        calculateCorners(m_x, toY);   

        // Check for ground
        if(!m_bottomLeft && !m_bottomRight)
                m_inAir = true;
        else{
            m_inAir = false;
            m_y = (m_tileMap.getRowTile(m_y) + 1) // +1 to get the one to stand on
                    * m_tileMap.getTileSize() - m_height/2;
        }
        
        // Check head!
        if(m_topRight || m_topLeft){
            m_y = (m_tileMap.getRowTile(m_y)) // +1 to get the one to stand on
                    * m_tileMap.getTileSize() + m_height/2;
            m_dy = 0;
        }
        
        
        // Check for walls
        // is not 100%, when player hits the ceilning m_dx = 0.   
        calculateCorners(toX, m_y-1); 
        
        // Check Left wall
        if(m_dx < 0 && (m_topLeft || m_bottomLeft))
                m_dx = 0;
        // Check Right wall   
        else if(m_dx > 0 && (m_topRight || m_bottomRight))
                m_dx = 0;
                      
        // check for right edge of screen
        if( m_x >= (m_tileMap.getTileSize() * m_tileMap.getGridColums())){
            m_dx = 0;
            m_x = (m_tileMap.getTileSize() * m_tileMap.getGridColums()) - 1;
        }
        // check for Left edge of screen
        else if(m_x <= 0){
            m_dx = 0;
            m_x = 1;
        }
    }
    
    void calculateCorners(double tx, double ty){
        // Bottom Left Corner
        int cx = m_tileMap.getColTile((int)tx - m_width/2);
        int cy = m_tileMap.getRowTile((int)ty + m_height/2);   
        m_bottomLeft = m_tileMap.isBlocked(cx, cy);
        
        // Bottom Right Corner
        cx = m_tileMap.getColTile((int)tx + m_width/2);
        cy = m_tileMap.getRowTile((int)ty + m_height/2);
        m_bottomRight = m_tileMap.isBlocked(cx, cy);
        
        // Top left corner
        cx = m_tileMap.getColTile((int)tx - m_width/2);
        cy = m_tileMap.getRowTile((int)ty - m_height/2);
        m_topLeft = m_tileMap.isBlocked(cx, cy);
        
        // Top Right Corner
        cx = m_tileMap.getColTile((int)tx + m_width/2);
        cy = m_tileMap.getRowTile((int)ty - m_height/2);
        m_topRight = m_tileMap.isBlocked(cx, cy);
    }
    
    private void setAnimation(){
                // sprite animation
        if(m_left || m_right) {
                m_animation.setFrames(AssetHandler.getPlayerWalk());
                m_animation.setDelay(100); 
        }
        else {
                m_animation.setFrames(AssetHandler.getPlayerIdle());
                m_animation.setDelay(-1);
        }
        if(m_dy < 0) {
                m_animation.setFrames(AssetHandler.getPlayerJump());
                m_animation.setDelay(-1);
        }
        if(m_dy > 0) {
                m_animation.setFrames(AssetHandler.getPlayerFall());
                m_animation.setDelay(-1);
        }
        m_animation.update();
        
        // Update facing
        if(m_dx < 0) {
            m_facingLeft = true;
        }
        if(m_dx > 0) {
            m_facingLeft = false;
        }
    }

    public void draw(Graphics2D g) {
        
        if(m_facingLeft){
            g.drawImage(
                m_animation.getImage(),
                (int) (m_x - m_width / 2 + m_width),
                (int) (m_y - m_height / 2),
                -m_width,
                m_height,
                null
            );
        }else
        {
            g.drawImage(
                m_animation.getImage(),
                (int) (m_x - m_width / 2),
                (int) (m_y - m_height / 2),
                m_width,
                m_height,
                null
            );
        }
    }

    public void setUsing(boolean b) {        
        m_isUsing = b;
    }
    
    public boolean isUsing(){
        return m_isUsing;
    }
}

