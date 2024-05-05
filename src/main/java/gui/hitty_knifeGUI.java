package gui;

import javax.swing.JPanel;

import config.Game;

import java.awt.Dimension;
import java.awt.Graphics;

/**
 * La classe HittyKnifeGUI représente le panneau graphique où le jeu Hitty Knife est affiché.
 * Elle étend JPanel pour permettre l'affichage des éléments graphiques du jeu.
 */
public class hitty_knifeGUI extends JPanel {

    private Game game;
    private Dimension gameScreenDimension = new Dimension (520*2,800);

    /**
     * Constructeur de la classe HittyKnifeGUI.
     * @param game Le jeu Hitty Knife à afficher dans le panneau graphique.
     */
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

