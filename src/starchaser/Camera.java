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
public class Camera {
    
    public Camera(int x, int y, int width, int height){
        m_x = x; 
        m_y = y;
        m_width = width;
        m_height = height;        
        m_limitX = new int[]{0, 0};
        m_limitY = new int[]{0, 0};
        
        m_mode = CameraMode.STATIC;
    }
    
    public void setMap(TileMap map){
        m_limitX[0] = 0;
        m_limitY[0] = 0;
        
        m_limitX[1] = map.getGridColums() * map.getTileSize();
        m_limitY[1] = map.getGridRows() * map.getTileSize();
        
    }
    
    public void setFollow(Player player){
        m_player = player;
        m_mode = CameraMode.FOLLOW;
    }
    
    public void setup(Graphics2D g){
        
        int new_x = 0;
        int new_y = 0;
        
        switch(m_mode){
            case STATIC:   
                new_x = m_x;
                new_y = m_y;
                break;
            case FOLLOW:
                new_x = m_player.getX() - m_width/2;
                new_y = m_player.getY() - m_height/2;
                break;
            default:
                System.out.println("CAMERA MODE NOT IMPLEMENTED");
        }        
        
        // Check lower limits
        if(new_x < m_limitX[0]) new_x = m_limitX[0];
        if(new_y < m_limitY[0]) new_y = m_limitY[0];
       
        // Check upper limits
        if(new_x > m_limitX[1] - m_width) new_x = m_limitX[1] - m_width;
        if(new_y > m_limitY[1] - m_height) new_y = m_limitY[1] - m_height;
 
        g.translate(-new_x, -new_y);
    }
    
    
    public enum CameraMode{
        STATIC, FOLLOW, MARIO
    }
    
    private int m_x, m_y, m_width, m_height;
    private int[] m_limitX, m_limitY;
    private CameraMode m_mode;
    private Player m_player;
}
