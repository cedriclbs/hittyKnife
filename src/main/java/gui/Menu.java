package gui;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/**
 * Classe abstraite {@code Menu} sert de base pour créer les interfaces de menu dans le jeu.
 * Elle initialise et affiche le menu principal avec des options comme jouer, accéder aux paramètres, visiter le magasin, ou quitter le jeu.
 * La classe gère également la musique de fond et le rendu de l'image de fond du menu.
 */
abstract class Menu extends JFrame {

    /**
     * Clip audio pour la lecture de la musique de fond du menu.
     */
    private static Clip clip;

    /**
     * Construit une instance de {@code Menu} avec les paramètres spécifiés, initialise l'interface utilisateur,
     * et lance la musique de fond.
     *
     * @param title Le titre de la fenêtre du menu.
     * @param backgroundPath Le chemin d'accès à l'image de fond du menu.
     * @param musicPath Le chemin d'accès au fichier audio de la musique de fond.
     */
    public Menu(String title, String backgroundPath, String musicPath) {
        setTitle(title);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/main/ressources/background/menu.jpg");
        backgroundPanel.setLayout(new BorderLayout());

        JPanel menuPanel = createMenuPanel(backgroundPath);
        JPanel container = new JPanel(new GridBagLayout());
        container.add(menuPanel);
        backgroundPanel.add(container, BorderLayout.CENTER);

        addKeyListenerToEscapeAction();
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
                System.out.println("Can't find file");
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
     * Configure l'action liée à la touche ÉCHAP pour redimensionner la fenêtre du menu hors du mode plein écran.
     * Cette méthode encapsule la logique d'ajout de Key Bindings à la racine du {@code JFrame}.
     */
    private void addKeyListenerToEscapeAction() {
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int height = screenSize.height;
                int width = 500;

                setBounds((screenSize.width - width) / 2, 0, width, height);
                setExtendedState(JFrame.NORMAL);
                getContentPane().revalidate();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
    }
}
