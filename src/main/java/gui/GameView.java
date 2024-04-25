package gui;

import App.Loop;
import App.Main;
import config.Game;
import entity.Cible;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;



/**
 * La classe SoloMode représente le mode de jeu solo du jeu et gère l'initialisation et le démarrage d'une partie solo du jeu.
 */
public class GameView extends JPanel{

    private Game game;
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
     */
    public GameView(boolean isSolo)  {
        this.isSolo = isSolo;
        initBg("src/main/ressources/background/bgForet.png");
        this.game = new Game(isSolo);
        Main.loop = new Loop(game);
        Main.loop.startTickFunction();
        //this.knife = game.knife1;
        //this.cible = new Cible("Cible", 100, KnifeDisplay.getBgImgWidth() / 2, KnifeDisplay.getBgImgHeight() / 2, 0);
        if (isSolo) {
            this.entityDisplay = new EntityDisplay(game.knife1, "src/main/ressources/background/bgForet.png", (ArrayList<Cible>) game.getListeCible(),isSolo);
        }
        else{
            this.entityDisplay = new EntityDisplay(game.knife1, "src/main/ressources/background/fond1v1.jpg", (ArrayList<Cible>) game.getListeCible(),isSolo);
            this.entityDisplay2 = new EntityDisplay(game.knife2, "src/main/ressources/background/fond1v1.jpg", (ArrayList<Cible>) game.getListeCible2(),isSolo);
        }
        initialize();
    }

    /**
     * @return Le couteau utilisé dans la partie solo.
     */
    /*public Knife getKnife() {
        return knife;
    }*/

    /**
     * Initialise l'interface utilisateur et démarre le jeu.
     */
    public void initialize() {
        setLayout(new BorderLayout());
        if (isSolo) {
            add(entityDisplay);
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (!game.knife1.throwing && !game.knife1.isInTheAir) {
                        game.knife1.jump();
                    } else if (!game.knife1.throwing && game.knife1.isInTheAir) {
                        game.knife1.throwKnife();
                    }
                }
            });
            requestFocusInWindow();
        } else {
            JPanel playersPanel = new JPanel(new GridLayout(1, 2));
            playersPanel.add(entityDisplay);
            playersPanel.add(entityDisplay2);
            playersPanel.setFocusable(true);
            playersPanel.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (!game.knife2.throwing && !game.knife2.isInTheAir) {
                        game.knife2.jump();
                    } else if (!game.knife2.throwing && game.knife2.isInTheAir) {
                        game.knife2.throwKnife();
                    }
                }
            });
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
            playersPanel.requestFocusInWindow();
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g2d.dispose();
    }


    /**
     * Démarre la partie solo.
     */
    public void startSoloGame() {
        initialize();
    }
    
}
