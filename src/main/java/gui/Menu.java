package gui;

import config.Game;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Classe abstraite {@code Menu} sert de base pour créer les interfaces de menu dans le jeu.
 * Elle initialise et affiche le menu principal avec des options comme jouer, accéder aux paramètres, visiter le magasin, ou quitter le jeu.
 * La classe gère également la musique de fond et le rendu de l'image de fond du menu.
 */
abstract class Menu extends JPanel {

    public static String linkClip = "src/main/ressources/music/";
    Game game;

    /**
     * Clip audio pour la lecture de la musique de fond du menu.
     */
    private static Clip clip;

    /**
     * Construit une instance de {@code Menu} avec les paramètres spécifiés, initialise l'interface utilisateur,
     * et lance la musique de fond.
     *
     * @param backgroundPath Le chemin d'accès à l'image de fond du menu.
     * @param musicPath Le chemin d'accès au fichier audio de la musique de fond.
     */
    public Menu(String backgroundPath, String musicPath) {
        setLayout(new BorderLayout());

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/main/ressources/background/menu.jpg");
        backgroundPanel.setLayout(new BorderLayout());

        JPanel menuPanel = createMenuPanel(backgroundPath);
        JPanel container = new JPanel(new GridBagLayout());
        container.add(menuPanel);
        backgroundPanel.add(container, BorderLayout.CENTER);

        add(backgroundPanel);
        playMusic(musicPath);
        setVisible(true);
    }

    /**
     * Joue la musique de fond en boucle à partir du fichier spécifié par {@code filepath}.
     * Affiche un message d'erreur dans la console si le fichier ne peut pas être trouvé ou si une erreur se produit lors de la lecture.
     *
     * @param filepath Le chemin d'accès au fichier audio de la musique de fond.
     */
    public static void playMusic(String filepath) {
        try {
            File musicPath = new File(filepath);
            if(musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("Can't find audio file");
            }
        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crée et retourne un {@link JPanel} qui sert de panel principal pour le menu.
     * Cette méthode est abstraite et doit être implémentée par les sous-classes pour définir le contenu spécifique du menu.
     *
     * @param background Le chemin d'accès à l'image de fond pour le panel du menu.
     * @return Le {@link JPanel} configuré pour afficher le menu.
     */
    abstract JPanel createMenuPanel(String background);

    /**
     * Sauvegarde l'état actuel du jeu dans un fichier spécifié et ferme l'application.
     */
    void quitterEtSauvegarder() {
        //System.out.print("quitter");
        if (game != null) {
            game.sauvegarderEtat();
        }
        System.exit(0); // Fermer l'application
    }
}
