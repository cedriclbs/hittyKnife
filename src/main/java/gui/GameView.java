package gui;

import App.Loop;
import App.Main;
import config.Game;
import entity.Cible;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * La classe SoloMode représente le mode de jeu solo du jeu et gère l'initialisation et le démarrage d'une partie solo du jeu.
 */
public class GameView extends JPanel{

    private Game game;
    private EntityDisplay entityDisplay;
    private EntityDisplay entityDisplay2;
    private boolean isSolo;

    private Image backgroundImage;
    private static Clip musicClip;

    /**
     * Constructeur de la classe SoloMode.
     *
     * @param isSolo Indique si le mode de jeu est solo ou non.
     * @param game   Le jeu associé à cette vue.
     */
    public GameView(boolean isSolo, Game game)  {
        this.isSolo = isSolo;
        initBg("src/main/ressources/background/bgForet.png");
        this.game = game;
        Main.loop = new Loop(game);
        Main.loop.startTickFunction();
        this.entityDisplay = new EntityDisplay(game.knife1, "src/main/ressources/background/bgJap10.gif", (ArrayList<Cible>)game.getListeCible(),isSolo, game);
        this.entityDisplay2 = new EntityDisplay(game.knife2, "src/main/ressources/background/fond1v1.jpg",  (ArrayList<Cible>)game.getListeCible2(),isSolo, game);
        initialize();
    }


    /**
     * Méthode générique pour jouer de la musique
     *
     * @param filePath: lien de la musique à jouer
     */
    private static void playMusic(String filePath) {
        try {
            if (musicClip != null && musicClip.isRunning()) {
                musicClip.stop();
                musicClip.close();
            }
            File musicFile = new File(filePath);
            if (musicFile.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
                musicClip = AudioSystem.getClip();
                musicClip.open(audioInput);
                musicClip.start();
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("Audio file not found: " + filePath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change la musique de fond du jeu.
     *
     * @param newMusicPath Le chemin vers le nouveau fichier audio à jouer.
     */
    public static void changeMusic(String newMusicPath) {
        playMusic(newMusicPath);
    }

    /**
     * Initialise l'interface utilisateur et démarre le jeu.
     */
    public void initialize() {
        setLayout(new BorderLayout());
        if (isSolo) {
            game.setIsSOlo(true);
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
            game.setIsSOlo(false);
            JPanel playersPanel = new JPanel(new GridLayout(1, 1));
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

            // Key Bindings pour la touche espace
            playersPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "jumpOrThrow");
            playersPanel.getActionMap().put("jumpOrThrow", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!game.knife3.throwing && !game.knife3.isInTheAir) {
                        game.knife3.jump();
                    } else if (!game.knife3.throwing && game.knife3.isInTheAir) {
                        game.knife3.throwKnife();
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
    }

    /**
     * Met à jour l'image du couteau suite au choix du joueur dans l'inventaire.
     *
     * @param knifePathClicked Le chemin d'accès vers l'image du couteau.
     */
    public void updateKnifeImage(String knifePathClicked) {
        this.entityDisplay.updateKnifeImage(knifePathClicked);
        if (!isSolo) {
            this.entityDisplay2.updateKnifeImage(knifePathClicked);
        }
    }

    /**
     * Met à jour l'image de fond du jeu suite au choix du joueur dans l'inventaire.
     *
     * @param backgroundPath Le chemin d'accès à la nouvelle image de fond.
     */
    public void updateBackgroundImage(String backgroundPath) {
        this.entityDisplay.initBg(backgroundPath);
        revalidate();
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.dispose();
    }
}