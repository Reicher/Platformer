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
public class Background {
    private int m_width, m_height;
    private BufferedImage m_background; 
    
    
    private Animation m_stars;
    
    private BGTheme m_theme;
    
    private File m_backgroundImageFile;
    
    public enum BGTheme{
        ORIGINAL
    }
    
    Background(BGTheme theme, int width, int height){
        m_theme = theme;
        m_width = width;
        m_height = height;               
    
        switch(theme){
            case ORIGINAL:
                m_backgroundImageFile = new File("src/images/Background.png");
                break;
            default:
                System.out.println("Unknown theme");
        }
            
        BufferedImage wholeStar = null;
        try{
            m_background = ImageIO.read(m_backgroundImageFile);            
            wholeStar = ImageIO.read(new File("src/images/StarAni.png"));
        }catch(Exception e){
            System.out.println("Something wrong with file " 
                    + m_backgroundImageFile.getAbsolutePath());                    
        }
        
        // Load star animation
        BufferedImage[] starImages = new BufferedImage[4]; 
        for(int i = 0; i < starImages.length; i++)
            starImages[i] = wholeStar.getSubimage(
            i * 30 + i, 0, 30, 30);

        m_stars = new Animation();
        m_stars.setFrames(starImages);
        m_stars.setDelay(50);
  
    }
    
    public void draw(Graphics2D g) {
        g.drawImage(m_background, 0, 0, m_width, m_height, null);

        g.drawImage(
            m_stars.getImage(),
            400, 100,
            32, 32,
            null
        );
    }
    
    public void update(){
        
        m_stars.update();
    }
        
}
