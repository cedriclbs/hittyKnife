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
public class GameView extends JPanel{

    private Game game;
    private HomeMenu homeMenu;
    private EntityDisplay entityDisplay;
    private EntityDisplay entityDisplay2;
    private Knife knife[];
    private boolean isSolo;

    private Image backgroundImage;
    private static double bgImgWidth;
    private static double bgImgHeight;

    /**
     * Constructeur de la classe SoloMode.
     *
     * @param homeMenu Le menu principal de l'application.
     */
    public GameView(HomeMenu homeMenu, boolean isSolo)  {
        this.isSolo = isSolo;
        initBg("src/main/ressources/background/bgForet.png");
        this.game = new Game(isSolo);
        Main.loop = new Loop(game);

        Main.loop.startTickFunction();

        this.homeMenu = homeMenu;
        this.knife = game.knife;
        //this.cible = new Cible("Cible", 100, KnifeDisplay.getBgImgWidth() / 2, KnifeDisplay.getBgImgHeight() / 2, 0);
        if (isSolo) {
            this.entityDisplay = new EntityDisplay(knife[0], "src/main/ressources/background/bgForet.png", (ArrayList<Cible>) game.getListeCible(),isSolo);
        }
        else{
            this.entityDisplay = new EntityDisplay(knife[0], "src/main/ressources/background/fond1v1.jpg", (ArrayList<Cible>) game.getListeCible(),isSolo);
            this.entityDisplay2 = new EntityDisplay(knife[1], "src/main/ressources/background/fond1v1.jpg", (ArrayList<Cible>) game.getListeCible2(),isSolo);
        }
    }

    /**
     * Retourne le couteau utilisé dans la partie solo.
     *
     * @return Le couteau utilisé dans la partie solo.
     */
    /*public Knife getKnife() {
        return knife;
    }*/

    /**
     * Initialise l'interface utilisateur et démarre la partie solo.
     */
    public void initialize() {
        if (isSolo) {
            setStates(SOLOMODE);
            homeMenu.getContentPane().removeAll();
            homeMenu.getContentPane().add(entityDisplay);
            homeMenu.getContentPane().revalidate();
            homeMenu.getContentPane().repaint();
        }
        else{
            setLayout(new BorderLayout());
            setStates(SOLOMODE);
            homeMenu.getContentPane().removeAll();

            JPanel playersPanel = new JPanel(new GridLayout(1, 2));
            playersPanel.add(entityDisplay);
            playersPanel.add(entityDisplay2);
            add(playersPanel, BorderLayout.CENTER);
            homeMenu.getContentPane().add(this);
            homeMenu.getContentPane().revalidate();
            homeMenu.getContentPane().repaint();
        }
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
