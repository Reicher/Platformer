/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starchaser;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author regen
 */

public class GamePanel extends JPanel implements KeyListener {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private int FPS = 30;
    private int targetTime = 1000 / FPS;
    private Timer gameTimer;
    private boolean m_loaded = false;
    private boolean m_releasedJump = true;
    
    private ActionListener m_listener;
    
    private Background m_background;
    private TileMap m_tileMap;
    private Player m_player;
    
    private State m_gameState;
    private Camera m_gameCam;
    
    private enum State{
        EXIT, MENU, GAME
    }

    
    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        
        init();
    }
    
    public void init(){        
        // Init Game loop
        m_listener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event)
            {   
                update();
                draw();
            }
        }; 
        
        m_tileMap = new TileMap("src/TileMaps/TestMap.txt", WIDTH, HEIGHT);
        m_tileMap.loadTileSet("src/images/MapTileSet.png");
        
        m_background = new Background(WIDTH, HEIGHT);
        
        m_player = new Player(m_tileMap);        
        m_player.setx(200);
        m_player.sety(100);
        
        m_gameCam = new Camera(0, 0, WIDTH, HEIGHT);
        m_gameCam.setFollow(m_player);
        m_gameCam.setMap(m_tileMap);
        
        gameTimer = new Timer(targetTime, m_listener);
        addKeyListener(this);
        
        m_gameState = State.GAME;
        
        m_loaded = true;
        gameTimer.start();
    }
    
    private void update(){
        if(!m_loaded)
            return;
        
        switch(m_gameState){
            case EXIT:
                JFrame parentJFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                parentJFrame.dispatchEvent(new WindowEvent(parentJFrame, WindowEvent.WINDOW_CLOSING));
                break;
            case MENU:
                
                break;
            case GAME:            
                m_background.update();
                m_player.update();
                break;
            default:
                System.out.println("Invalid state");
        }

    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g; 
                
        m_background.draw(g2);
        
        m_gameCam.setup(g2);
        
        m_tileMap.draw(g2);
        m_player.draw(g2);
    }
    

    private void draw(){
        if(!m_loaded)
            return;
        
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void keyPressed(KeyEvent key) {            
        int code = key.getKeyCode();
        
        if(code == KeyEvent.VK_LEFT) {
                m_player.setLeft(true);
        }
        if(code == KeyEvent.VK_RIGHT) {
                m_player.setRight(true);
        }
        if(code == KeyEvent.VK_UP && m_releasedJump) {
                m_player.setJumping(true);
                m_releasedJump = false;
        }
        if(code == KeyEvent.VK_ESCAPE)
            m_gameState = State.EXIT;
    }
 
    @Override
    public void keyReleased(KeyEvent key) {
        int code = key.getKeyCode();
        
        if(code == KeyEvent.VK_LEFT) {
            m_player.setLeft(false);
        }
        if(code == KeyEvent.VK_RIGHT) {
            m_player.setRight(false);
        }
        if(code == KeyEvent.VK_UP) {
            m_player.setJumping(false);
            m_releasedJump = true;
        }
    }
}
