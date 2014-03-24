/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starchaser;

import java.awt.Graphics2D;
import starchaser.Tile.Tile;

/**
 *
 * @author regen
 */
public class Star extends Tile{

    private boolean m_taken;
    
    public void setTaken(){ m_taken = true; }
    public boolean isTaken(){ return m_taken; }
    
    public Star(int x, int y, int tileSize){
        super(x, y
                , (int)(tileSize*0.5), (int)(tileSize*0.5)
                , AssetHandler.getStar());
        
        m_taken = false;
    }
 
    public void draw(Graphics2D g){
        if(!m_taken)
            super.draw(g);
    }
}
