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
public abstract class Level {
    
    Background m_background;
    Map m_levelMap;
    Player m_player;
    Camera m_levelCam;
    Star[] m_stars;
    
    public Level(String tileMap, int width, int height){
        m_levelMap = new Map(tileMap, width, height);
        m_background = new Background(width, height);
        m_levelCam = new Camera(0, 0, width, height);
    }
    
    public Map getMap(){
        return m_levelMap;
    }
    
    public void addPlayer(Player player){
        m_player = player;
        
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
    }
}
