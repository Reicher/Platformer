/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starchaser.level;

import java.awt.Graphics2D;
import starchaser.Player;

/**
 *
 * @author regen
 */
public class Level2 extends Level{
    
    public Level2(int width, int heigt){
        super("Level2.txt", width, heigt);
        
    }
    
    @Override
    public void init(Player player){
        super.init(player);
        
        addStars();
        
        m_exitDoor = new Door(m_levelMap.gridToScreenX(18), 
                m_levelMap.gridToScreenY(5), 
                m_levelMap.getTileSize());
    }
    
    private void addStars(){
        m_stars = new Star[1];
        m_stars[0] = new Star(m_levelMap.gridToScreenX(10), m_levelMap.gridToScreenY(5), m_levelMap.getTileSize());
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        
        for(Star s : m_stars)
            s.draw(g);
        
        m_exitDoor.draw(g);
    }

    @Override
    public void update() {
        super.update();
        
        for(Star s : m_stars)
            s.update();
        
        m_exitDoor.update();
    }
    
}
