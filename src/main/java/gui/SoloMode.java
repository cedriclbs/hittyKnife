package gui;

import App.Loop;
import App.Main;
import config.Game;
import entity.Cible;
import entity.Knife;


import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import static config.States.*;

/**
 * La classe SoloMode représente le mode de jeu solo du jeu et gère l'initialisation et le démarrage d'une partie solo du jeu.
 */
public class SoloMode extends JPanel{

    private Game game;
    private GameView gameView;
    private EntityDisplay entityDisplay;
    private Knife knife;

    private Image backgroundImage;
    private static double bgImgWidth;
    private static double bgImgHeight;

    /**
     * Constructeur de la classe SoloMode.
     *
     * @param gameView Le menu principal de l'application.
     */
    public SoloMode(GameView gameView, boolean isSolo)  {

        initBg("src/main/ressources/background/bgForet.png");
        this.game = new Game();
        Main.loop = new Loop(game);
        Main.loop.startTickFunction();

        this.gameView = gameView;
        this.knife = game.getKnife();
        //this.cible = new Cible("Cible", 100, KnifeDisplay.getBgImgWidth() / 2, KnifeDisplay.getBgImgHeight() / 2, 0);
        this.entityDisplay = new EntityDisplay(knife,"src/main/ressources/background/bgForet.png", (ArrayList<Cible>) game.getListeCible());
    }

    /**
     * Retourne le couteau utilisé dans la partie solo.
     *
     * @return Le couteau utilisé dans la partie solo.
     */
    public Knife getKnife() {
        return knife;
    }

    /**
     * Initialise l'interface utilisateur et démarre la partie solo.
     */
    public void initialize() {
        setStates(SOLOMODE);
        gameView.getContentPane().removeAll();
        gameView.getContentPane().add(entityDisplay);
        gameView.getContentPane().revalidate();
        gameView.getContentPane().repaint();
    }

    /**
     * Initialise l'image de fond du jeu.
     *
     * @param backgroundPath Le chemin d'accès à l'image de fond.
     */
    private void initBg(String backgroundPath) {
        this.backgroundImage = new ImageIcon(backgroundPath).getImage();
        bgImgHeight = this.backgroundImage.getHeight(null);
        bgImgWidth = this.backgroundImage.getWidth(null);
    }

    /**
     * Démarre la partie solo.
     */
    public void startSoloGame() {
        initialize();
    }

}
