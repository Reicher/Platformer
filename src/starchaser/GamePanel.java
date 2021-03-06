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
import java.util.Vector;
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

    private final int FPS = 30;
    private int updateInterval;
    private Timer gameTimer;
    private boolean m_loaded = false;
    private boolean m_releasedJump = true; // UGGGGLY
    
    private ActionListener m_listener;
    private Player m_player;
    
    private State m_gameState;
    
    Level m_currentLevel;
    Vector<Level> m_levels;
    
    private enum State{
        EXIT, MENU, GAME
    }

    
    public GamePanel() {
        super();
        this.updateInterval = 1000 / FPS;
        
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
        
        m_player = new Player(WIDTH/11, WIDTH/11); 
        
        m_levels = new Vector<Level>();
        m_levels.add(new Level1(WIDTH, HEIGHT));
        m_levels.add(new Level2(WIDTH, HEIGHT));
        
        m_currentLevel = m_levels.firstElement();
        m_levels.removeElement(m_currentLevel);
        m_currentLevel.init(m_player);
  
        gameTimer = new Timer(updateInterval, m_listener);
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
                m_currentLevel.update();
                m_player.update();
                
                if(m_currentLevel.isCompleted()){
                    m_currentLevel = m_levels.firstElement();
                    m_levels.removeElement(m_currentLevel);
                    m_currentLevel.init(m_player);
                }

                break;
            default:
                System.out.println("Invalid state");
        }

    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g; 
        m_currentLevel.draw(g2);
                
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
        if(code == KeyEvent.VK_SPACE && m_releasedJump) {
                m_player.setUsing(true);
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
        if(code == KeyEvent.VK_SPACE && m_releasedJump) {
                m_player.setUsing(false);
        }
    }
}
