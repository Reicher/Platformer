package starchaser;

import java.awt.image.BufferedImage;

public class Tile {
    
    public Tile(BufferedImage image, boolean blocked){
        m_image = image;
        m_blocked = blocked;
    }
    
    public BufferedImage getImage(){
        return m_image;
    }
    
    public enum TileType { DIRT_BLOCK_1, DIRT_BLOCK_2 };
    
    private BufferedImage m_image;
    public final boolean m_blocked;
}
