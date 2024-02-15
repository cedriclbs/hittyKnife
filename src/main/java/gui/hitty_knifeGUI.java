package gui;

import javax.swing.JPanel;

import config.Game;

import java.awt.Dimension;
import java.awt.Graphics;

public class hitty_knifeGUI extends JPanel {

    private Game game;
    private Dimension gameScreenDimension = new Dimension (520*2,800);

    public hitty_knifeGUI(Game game){
        
        this.game = game;
        setMinimumSize(gameScreenDimension);
        setPreferredSize(gameScreenDimension);
        setMaximumSize(gameScreenDimension);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }

}

