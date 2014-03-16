/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starchaser;

import javax.swing.JFrame;

/**
 *
 * @author regen
 */
public class StarChaser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("Platformer");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new GamePanel());
        window.pack();
        window.setVisible(true);
    }
}
