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
    
    private static String m_solidTileSetFile;
    private static String m_decorTileSetFile;
    private static String m_backgroundFile;
    private static String m_backgroundStarFile;
    
    private static String m_starFile;
    
    private static String m_playerIdleFile;
    private static String m_playerJumpFile;
    private static String m_playerFallFile;
    private static String m_playerWalkFile;
    
   
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
        String imagePath = "images/";
        
        switch(m_source){
            case ORIGINAL: 
                imagePath += "Original/";
                break;
            case TEST:
                imagePath += "Test/";
                break;
        }
        
        m_solidTileSetFile = imagePath + "SolidTiles.png";
        m_decorTileSetFile = imagePath + "DecorativeTiles.png";
        
        m_backgroundFile = imagePath + "Background.png";
        m_backgroundStarFile = imagePath + "StarAni.png";
        
        m_starFile = imagePath + "Star.png";
        
        m_playerIdleFile = imagePath + "PlayerIdle.png";
        m_playerJumpFile = imagePath + "PlayerJump.png";
        m_playerFallFile = imagePath + "PlayerFalling.png";
        m_playerWalkFile = imagePath + "PlayerWalking.png";
    } 
    
    private static void loadGraphics(){
        int tileSize = 32;
        try{
            m_background =  loadImage(m_backgroundFile);
            m_backgroundStar = toArray(loadImage(m_backgroundStarFile), tileSize);
            
            m_playerIdle = toArray(loadImage(m_playerIdleFile), tileSize);
            m_playerJump = toArray(loadImage(m_playerJumpFile), tileSize);
            m_playerFall = toArray(loadImage(m_playerFallFile), tileSize);
            m_playerWalk = toArray(loadImage(m_playerWalkFile), tileSize);
            
            m_star =  toArray(loadImage(m_starFile), tileSize);
            
            m_solidTiles = toArray(loadImage(m_solidTileSetFile), tileSize);
            m_decorTiles = toArray(loadImage(m_decorTileSetFile), tileSize);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private static BufferedImage loadImage(String path){
        BufferedImage tmp = null;
        try{
            tmp = ImageIO.read(ClassLoader.getSystemResource(path));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return tmp;
    }
    
    private static BufferedImage[]  toArray(BufferedImage source, int tileSize){
        BufferedImage[] tmp = new BufferedImage[source.getWidth() / tileSize];
        for(int i = 0; i < tmp.length; i++)
            tmp[i] = source.getSubimage( i * tileSize, 0, tileSize, tileSize);
        
        return tmp;
    }
}

