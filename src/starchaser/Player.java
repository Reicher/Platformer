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
public class Player {
    
    private int m_x, m_y;
    private double m_dx, m_dy;
    private int m_width, m_height;
    private double m_moveSpeed, m_maxSpeed, m_stopSpeed;
    private double m_maxFallingSpeed;
    private double m_gravity;
    private double m_jumpStart;
    
    private boolean m_facingLeft;
    private boolean m_left;
    private boolean m_right;
    private boolean m_jumping;
    private boolean m_inAir;  
    
    private boolean m_bottomLeft;
    private boolean m_bottomRight;
    private boolean m_topLeft;
    private boolean m_topRight;
    
    private Animation m_animation;
    private BufferedImage[] m_idleSprites;
    private BufferedImage[] m_walkingSprites;
    private BufferedImage[] m_jumpingSprites;
    private BufferedImage[] m_fallingSprites;
    
    private TileMap m_tileMap;
    
    public void setx(int i) { m_x = i; }
    public void sety(int i) { m_y = i; }
    public void setLeft(boolean b) { m_left = b; }
    public void setRight(boolean b) { m_right = b; }
    public void setJumping(boolean b) {
        m_jumping = !m_inAir ? true : m_jumping;
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
    
    public Player(TileMap tileMap) {

        m_tileMap = tileMap;
        m_width = 60;
        m_height = 60;
        
        m_facingLeft = false;
        m_left = m_right = m_jumping = m_inAir = false;
        m_moveSpeed = 0.6;
        m_maxSpeed = 5.0;
        m_stopSpeed = 0.5;
        m_jumpStart = -9.0;
        
        m_maxFallingSpeed = 20.0;
        m_gravity = 0.4;
        
        m_idleSprites = new BufferedImage[1];
        m_jumpingSprites = new BufferedImage[1];
        m_fallingSprites = new BufferedImage[1];
        m_walkingSprites = new BufferedImage[4];
        
        // Load sprites
        try{
            int tileSize = 32;
            
            m_idleSprites[0] = ImageIO.read(new File("src/images/PlayerIdle.png"));
            m_jumpingSprites[0] = ImageIO.read(new File("src/images/PlayerJump.png"));
            m_fallingSprites[0] = ImageIO.read(new File("src/images/PlayerFalling.png"));
            
            BufferedImage image = ImageIO.read(new File("src/images/PlayerWalking.png"));
            for(int i = 0; i < m_walkingSprites.length; i++)
                m_walkingSprites[i] = image.getSubimage(
                    i * tileSize + i,
                    0,
                    tileSize,
                    tileSize);
       
        }catch(Exception e){
            e.printStackTrace();
        }

        m_animation = new Animation();
        m_animation.setFrames(m_idleSprites);
        m_animation.setDelay(-1);
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
        
        double toX = m_x + m_dx;
        double toY = m_y + m_dy;

        calculateCorners(m_x, toY);   

        // Check for ground
        if(!m_bottomLeft && !m_bottomRight)
                m_inAir = true;
        else{
            m_inAir = false;
            m_y = (m_tileMap.getRowTile(m_y) + 1) // +1 to get the one to stand on
                    * m_tileMap.getTileHeight() - m_height/2;
        }
        
        // Check head!
        if(m_topRight || m_topLeft){
            m_y = (m_tileMap.getRowTile(m_y)) // +1 to get the one to stand on
                    * m_tileMap.getTileHeight() + m_height/2;
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
        if( m_x >= (m_tileMap.getTileWidth() * m_tileMap.getGridColums())){
            m_dx = 0;
            m_x = (m_tileMap.getTileWidth() * m_tileMap.getGridColums()) - 1;
        }
        else if(m_x <= 0){
            m_dx = 0;
            m_x = 1;
        }
        
        // sprite animation
        if(m_left || m_right) {
                m_animation.setFrames(m_walkingSprites);
                m_animation.setDelay(100); 
        }
        else {
                m_animation.setFrames(m_idleSprites);
                m_animation.setDelay(-1);
        }
        if(m_dy < 0) {
                m_animation.setFrames(m_jumpingSprites);
                m_animation.setDelay(-1);
        }
        if(m_dy > 0) {
                m_animation.setFrames(m_fallingSprites);
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
        
        // Update pos
        m_x += m_dx;
        m_y += m_dy;
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
    

}
