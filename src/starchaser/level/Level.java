/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starchaser.level;

import java.awt.Graphics2D;
import starchaser.Background;
import starchaser.Camera;
import starchaser.Player;
import starchaser.Star;

/**
 *
 * @author regen
 */
public abstract class Level {
    
    protected Background m_background;
    protected Map m_levelMap;
    protected Player m_player;
    protected Camera m_levelCam;
    protected Star[] m_stars;
    
    protected int m_starsCollected;
    
    public Level(String tileMap, int width, int height){
        m_levelMap = new Map(tileMap, width, height);
        m_background = new Background(width, height);
        m_levelCam = new Camera(0, 0, width, height);
    }
    
    public Map getMap(){
        return m_levelMap;
    }
    
    public void init(Player player){
        m_player = player;
        m_player.init();
        m_player.setPos(200, 100);
        
        m_starsCollected = 0;
        
        m_levelCam.setFollow(m_player);
        m_levelCam.setMap(m_levelMap);
        
        player.setMap(m_levelMap);
    }
    
    public void draw(Graphics2D g){
        m_background.draw(g);
        m_levelCam.setup(g);
        m_levelMap.draw(g);
    }
    
    public void update(){
        m_background.update();
        
        if(m_player.isDead()){
            init(m_player);
            System.out.println("DEAD!");
        }
        
        // Check for star being taken!
        for(Star s : m_stars)
            if(!s.isTaken() && s.getBounds().intersects(m_player.getBounds())){
                s.setTaken();
                m_starsCollected++;
                System.out.println("Stars collected: " + m_starsCollected);
            }
    }
}
