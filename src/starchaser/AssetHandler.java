/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starchaser;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author regen
 */
public class AssetHandler {    
    private static BufferedImage[] m_solidTiles;
    private static BufferedImage[] m_decorTiles;
    private static BufferedImage m_background;
    private static BufferedImage[] m_backgroundStar;
    
    private static BufferedImage[] m_star;
    
    private static BufferedImage[] m_playerIdle;
    private static BufferedImage[] m_playerJump;
    private static BufferedImage[] m_playerFall;
    private static BufferedImage[] m_playerWalk;
    
    private static Source m_source;
    
    private static File m_solidTileSetFile;
    private static File m_decorTileSetFile;
    private static File m_backgroundFile;
    private static File m_backgroundStarFile;
    
    private static File m_starFile;
    
    private static File m_playerIdleFile;
    private static File m_playerJumpFile;
    private static File m_playerFallFile;
    private static File m_playerWalkFile;
    
   
    public enum Source{
        ORIGINAL, TEST
    }    
    
    static{
        m_source = Source.ORIGINAL;
        createFilePaths();
        loadGraphics();
    }
    
    public static BufferedImage getSolidTile(int t){ return m_solidTiles[t]; }
    public static BufferedImage getDecorTile(int t){ return m_decorTiles[t]; }
    public static BufferedImage getBackground(){ return m_background; }
    public static BufferedImage[] getBackgroundStar(){ return m_backgroundStar; }
    
    public static BufferedImage[] getStar(){ return m_star; }
    
    public static BufferedImage[] getPlayerIdle(){ return m_playerIdle; }
    public static BufferedImage[] getPlayerJump(){ return m_playerJump; }
    public static BufferedImage[] getPlayerFall(){ return m_playerFall; }
    public static BufferedImage[] getPlayerWalk(){ return m_playerWalk; }
    
    public static void setSource(Source source){
        m_source = source;
        createFilePaths();
        loadGraphics();
    }
    
    private static void createFilePaths(){
        String imagePath = "src/images/";
        
        switch(m_source){
            case ORIGINAL: 
                imagePath += "Original/";
                break;
            case TEST:
                imagePath += "Test/";
                break;
        }
        
        m_solidTileSetFile = new File(imagePath + "SolidTiles.png");
        m_decorTileSetFile = new File(imagePath + "DecorativeTiles.png");
        
        m_backgroundFile = new File(imagePath + "Background.png");
        m_backgroundStarFile = new File(imagePath + "StarAni.png");
        
        m_starFile = new File(imagePath + "Star.png");
        
        m_playerIdleFile = new File(imagePath + "PlayerIdle.png");
        m_playerJumpFile = new File(imagePath + "PlayerJump.png");
        m_playerFallFile = new File(imagePath + "PlayerFalling.png");
        m_playerWalkFile = new File(imagePath + "PlayerWalking.png");
    } 
    
    private static void loadGraphics(){
        int tileSize = 32;
        try{
            m_background =  ImageIO.read(m_backgroundFile);
            m_backgroundStar = toArray(ImageIO.read(m_backgroundStarFile), tileSize);
            
            m_playerIdle = toArray(ImageIO.read(m_playerIdleFile), tileSize);
            m_playerJump = toArray(ImageIO.read(m_playerJumpFile), tileSize);
            m_playerFall = toArray(ImageIO.read(m_playerFallFile), tileSize);
            m_playerWalk = toArray(ImageIO.read(m_playerWalkFile), tileSize);
            
            m_star =  toArray(ImageIO.read(m_starFile), tileSize);
            
            m_solidTiles = toArray(ImageIO.read(m_solidTileSetFile), tileSize);
            m_decorTiles = toArray(ImageIO.read(m_decorTileSetFile), tileSize);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private static BufferedImage[]  toArray(BufferedImage source, int tileSize){
        BufferedImage[] tmp = new BufferedImage[source.getWidth() / tileSize];
        for(int i = 0; i < tmp.length; i++)
            tmp[i] = source.getSubimage( i * tileSize, 0, tileSize, tileSize);
        
        return tmp;
    }
}

