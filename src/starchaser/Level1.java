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
public class Level1 extends Level{
    
    Level1(int width, int heigt){
        super("src/TileMaps/TestMap.txt", width, heigt);
        
        m_stars = new Star[3];
        m_stars[0] = new Star(300, 300);
        m_stars[1] = new Star(700, 200);
        m_stars[2] = new Star(400, 100);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        
        for(Star s : m_stars)
            s.draw(g);
    }

    @Override
    public void update() {
        super.update();
        
        for(Star s : m_stars)
            s.update();
    }
    
}
