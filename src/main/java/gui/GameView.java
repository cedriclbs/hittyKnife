package gui;

import App.Loop;
import App.Main;
import config.Game;
import entity.Cible;
import entity.Knife;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;


import static config.States.*;

/**
 * La classe SoloMode représente le mode de jeu solo du jeu et gère l'initialisation et le démarrage d'une partie solo du jeu.
 */
public class GameView extends JPanel{

    private Game game;
    private HomeMenu homeMenu;
    private EntityDisplay entityDisplay;
    private EntityDisplay entityDisplay2;
    //private Knife knife[];
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
        //this.knife = game.knife1;
        //this.cible = new Cible("Cible", 100, KnifeDisplay.getBgImgWidth() / 2, KnifeDisplay.getBgImgHeight() / 2, 0);
        if (isSolo) {
            this.entityDisplay = new EntityDisplay(game.knife1, "src/main/ressources/background/bgForet.png", (ArrayList<Cible>) game.getListeCible(),isSolo);
        }
        else{
            this.entityDisplay = new EntityDisplay(game.knife1, "src/main/ressources/background/fond1v1.jpg", (ArrayList<Cible>) game.getListeCible(),isSolo);
            this.entityDisplay2 = new EntityDisplay(game.knife2, "src/main/ressources/background/fond1v1.jpg", (ArrayList<Cible>) game.getListeCible2(),isSolo);
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
        setStates(SOLOMODE);
        homeMenu.getContentPane().removeAll();
        if (isSolo) {
            homeMenu.getContentPane().add(entityDisplay);
            homeMenu.getContentPane().addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {

                    if (!game.knife1.throwing && !game.knife1.isInTheAir) {
                        game.knife1.jump();
                        System.out.println(game.knife1.getY());
                    }
                    else if (!game.knife1.throwing && game.knife1.isInTheAir){
                        game.knife1.throwKnife();
                    }
                }
            }
            );
            homeMenu.getContentPane().revalidate();
            homeMenu.getContentPane().repaint();
        }
        else{
            setLayout(new BorderLayout());

            JPanel playersPanel = new JPanel(new GridLayout(1, 2));
            playersPanel.add(entityDisplay);
            playersPanel.add(entityDisplay2);
            // Assurez-vous que le panel peut obtenir le focus
            playersPanel.setFocusable(true);
// Demandez le focus pour le panneau
            playersPanel.requestFocusInWindow();
            playersPanel.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {

                    if (!game.knife2.throwing && !game.knife2.isInTheAir) {
                        game.knife2.jump();
                        //System.out.println(game.knife1.getY());
                    }
                    else if (!game.knife2.throwing && game.knife2.isInTheAir){
                        game.knife2.throwKnife();
                    }
                }
            }
            );
            playersPanel.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        if (!game.knife1.throwing && !game.knife1.isInTheAir) {
                            game.knife1.jump();
                        } else if (!game.knife1.throwing && game.knife1.isInTheAir) {
                            game.knife1.throwKnife();
                        }
                    }
                }

            });

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
