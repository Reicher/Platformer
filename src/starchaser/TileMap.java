/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starchaser;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.imageio.ImageIO;

/**
 *
 * @author regen
 */
public class TileMap {
    
    private int[] m_mapGridSize;
    private int[] m_screenSize;
    private int[] m_tileSize;
    private int[][] m_map;   

    private BufferedImage m_tileset;
    private final int m_tileSetOriginalSize = 32;
    public Tile[][] m_tiles;
    
    public int getTileWidth(){
        return m_tileSize[0];
    }
    
    public int getTileHeight(){
        return m_tileSize[1];
    }
    
    public int getColTile(int x) {
        return x /  m_tileSize[0];
        
    }
    public int getRowTile(int y) {
        return y /  m_tileSize[1];
    }
    
    public int getGridColums(){
        return m_mapGridSize[0];
    }
    
    public int getGridRows(){
        return m_mapGridSize[1];
    }
    
    public TileMap(String tileMapFile, int width, int height){
        m_screenSize = new int[] {width, height};
        m_mapGridSize = new int[2];
        m_tileSize = new int[2];

        try {   
            BufferedReader br = new BufferedReader(new FileReader(tileMapFile));

            m_mapGridSize[0] = Integer.parseInt(br.readLine());
            m_mapGridSize[1] = Integer.parseInt(br.readLine());            
            m_map = new int[m_mapGridSize[0]][m_mapGridSize[1]];

            m_tileSize[0] = m_screenSize[0] / m_mapGridSize[0];
            m_tileSize[1] = m_screenSize[1] / m_mapGridSize[1];

            String delimiters = "\\s+";
            for(int row = 0; row < m_mapGridSize[1]; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delimiters);
                for(int col = 0; col < m_mapGridSize[0]; col++) {
                        m_map[col][row] = Integer.parseInt(tokens[col]);
                }
            }
        }
        catch(Exception e) { e.printStackTrace(); }    
    }

    public void loadTileSet(String TileSetImage) {
        File tmp = new File(TileSetImage);
        try{
            m_tileset = ImageIO.read(tmp);
        }catch(Exception e){ e.printStackTrace(); } 

        int numTileColums = (m_tileset.getWidth() + 1) / (m_tileSetOriginalSize + 1);
        int numTileRows = (m_tileset.getHeight() + 1) / (m_tileSetOriginalSize + 1);
        m_tiles = new Tile[numTileRows][numTileColums];

        BufferedImage subimage;
        for(int col = 0; col < numTileColums; col++) {
            for(int row = 0; row < numTileRows; row++) {
                subimage = m_tileset.getSubimage(
                    col * m_tileSetOriginalSize,
                    row * m_tileSetOriginalSize,
                    m_tileSetOriginalSize,
                    m_tileSetOriginalSize);
                if(col == 1 || col == 2)
                    m_tiles[row][col] = new Tile(subimage, true);
                else
                    m_tiles[row][col] = new Tile(subimage, false);
            }
        }
    }
    
    public boolean isBlocked(int row, int col) {
        if(row >= getGridRows() || col >= getGridColums())
            return false;
        
        int rc = m_map[row][col];
        
        int r = rc / m_tiles[0].length;
        int c = rc % m_tiles[0].length;
        return m_tiles[r][c].m_blocked;
    }

    public void draw(Graphics2D g) {

        for(int row = 0; row < m_mapGridSize[1]; row++) {
            for(int col = 0; col < m_mapGridSize[0]; col++) {

                int rc = m_map[col][row] ;

                int r = rc / m_tiles[0].length;
                int c = rc % m_tiles[0].length;

                g.drawImage(
                    m_tiles[r][c].getImage(),
                    col * m_tileSize[0],
                    row * m_tileSize[1],
                    m_tileSize[0], 
                    m_tileSize[1],
                    null
                );
            }
        }
    }
}