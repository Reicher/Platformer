/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starchaser.level;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import starchaser.AssetHandler;

/**
 *
 * @author regen
 */
public class Map {
    
    private int[] m_mapGridSize;
    private int[] m_screenSize;
    private int m_tileSize;
    private int[][] m_solidMap;
    private int[][] m_decorMap;   

    public int getTileSize(){
        return m_tileSize;
    }
    
    public int getColTile(int x) { return x /  m_tileSize; }
    public int getRowTile(int y) { return y /  m_tileSize; }
    
    public int getGridColums(){ return m_mapGridSize[0]; }
    public int getGridRows(){ return m_mapGridSize[1]; }
    
    public int gridToScreenX(int x){ return (x * m_tileSize) + m_tileSize/2; }
    public int gridToScreenY(int y){ return (y * m_tileSize) + m_tileSize/2; }
    
    public Map(String tileMapFile, int width, int height){
        m_screenSize = new int[] {width, height};
        m_mapGridSize = new int[2];
        
        try {   

            InputStreamReader isReader = new InputStreamReader(
                    ClassLoader.getSystemResourceAsStream("TileMaps/" + tileMapFile));
            BufferedReader br = new BufferedReader(isReader);
            
            m_mapGridSize[0] = Integer.parseInt(br.readLine());
            m_mapGridSize[1] = Integer.parseInt(br.readLine());  
            
            m_solidMap = new int[m_mapGridSize[0]][m_mapGridSize[1]];
            m_decorMap = new int[m_mapGridSize[0]][m_mapGridSize[1]];

            m_tileSize = m_screenSize[1] / m_mapGridSize[1];

            String delimiters = "\\s+";
            
            // Fill solid map
            for(int row = 0; row < m_mapGridSize[1]; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delimiters);
                for(int col = 0; col < m_mapGridSize[0]; col++) {
                        m_solidMap[col][row] = Integer.parseInt(tokens[col]);
                }
            }
            
            // map seperator line
            br.readLine();
            
            // Fill decoration map
            for(int row = 0; row < m_mapGridSize[1]; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delimiters);
                for(int col = 0; col < m_mapGridSize[0]; col++) {
                        m_decorMap[col][row] = Integer.parseInt(tokens[col]);
                }
            }
        }
        catch(Exception e) { e.printStackTrace(); }    
    }

    public boolean isBlocked(int row, int col) {    
        int rc = m_solidMap[row][col];
        return rc != 0;
    }

    public void draw(Graphics2D g) {
        // Draw solids and decor
        for(int row = 0; row < m_mapGridSize[1]; row++) {
            for(int col = 0; col < m_mapGridSize[0]; col++) {
                int solidNum = m_solidMap[col][row];
                int decorNum = m_decorMap[col][row];
                
                if(solidNum != 0)
                    g.drawImage(
                        AssetHandler.getSolidTile(solidNum),
                        col * m_tileSize,
                        row * m_tileSize,
                        m_tileSize, 
                        m_tileSize,
                        null);
                
                 if(decorNum != 0)
                    g.drawImage(
                        AssetHandler.getDecorTile(decorNum),
                        col * m_tileSize,
                        row * m_tileSize,
                        m_tileSize, 
                        m_tileSize,
                        null);
            }
        }
    }
}