package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Fenêtre du magasin où les joueurs peuvent acheter des objets ou des améliorations.
 */
public class ShopMenu extends Menu {

    /**
     * Constructeur de la fenêtre du magasin.
     *
     * @param title Le titre de la fenêtre du magasin.
     * @param backgroundPath Le chemin de l'image de fond du magasin.
     * @param musicPath Le chemin de la musique de fond du magasin.
     */
    public ShopMenu(String title, String backgroundPath, String musicPath) {
        super(title, backgroundPath, musicPath);
        JPanel menuPanel = createMenuPanel(backgroundPath);
        add(menuPanel);
    }

    /**
     * Crée et configure le panneau du menu du magasin.
     *
     * @param backgroundPath Le chemin de l'image de fond du panneau du menu.
     * @return Le panneau du menu du magasin configuré.
     */
    @Override
    JPanel createMenuPanel(String backgroundPath) {
        BackgroundPanel panel = new BackgroundPanel(backgroundPath);
        panel.setLayout(new BorderLayout()); // Utilisation d'un BorderLayout pour placer le bouton "Menu" dans le coin supérieur gauche

        // Création d'un JPanel pour les boutons de menu principal
        JPanel mainMenuPanel = new JPanel(new GridLayout(3, 3, 150, 20));
        mainMenuPanel.setBorder(BorderFactory.createEmptyBorder(200,200,200,200)); // Ajoute des marges

        // Ajout des boutons de menu principal
        JButton sword1 = new JButton("Sword 1");
        configureButton(sword1, e -> {
            // TODO : action lorsque le bouton "Sword 1" est cliqué
        });
        mainMenuPanel.add(sword1);

        JButton sword2 = new JButton("Sword 2");
        configureButton(sword2, e -> {
            // TODO : action lorsque le bouton "Sword 2" est cliqué
        });
        mainMenuPanel.add(sword2);

        JButton sword3 = new JButton("Sword 3");
        configureButton(sword3, e -> {
            // TODO : action lorsque le bouton "Sword 3" est cliqué
        });
        mainMenuPanel.add(sword3);

        JButton sword4 = new JButton("Sword 4");
        configureButton(sword4, e -> {
            // TODO : action lorsque le bouton "Sword 4" est cliqué
        });
        mainMenuPanel.add(sword4);

        JButton background1 = new JButton("Background 1");
        configureButton(background1, e -> {
            // TODO : action lorsque le bouton "Background 1" est cliqué
        });
        mainMenuPanel.add(background1);

        JButton background2 = new JButton("Background 2");
        configureButton(background2, e -> {
            // TODO : action lorsque le bouton "Background 2" est cliqué
        });
        mainMenuPanel.add(background2);

        JButton background3 = new JButton("Background 3");
        configureButton(background3, e -> {
            // TODO : action lorsque le bouton "Background 3" est cliqué
        });
        mainMenuPanel.add(background3);

        JButton music1 = new JButton("Music 1");
        configureButton(music1, e -> {
            // TODO : action lorsque le bouton "Music 1" est cliqué
        });
        mainMenuPanel.add(music1);

        JButton music2 = new JButton("Music 2");
        configureButton(music2, e -> {
            // TODO : action lorsque le bouton "Music 2" est cliqué
        });
        mainMenuPanel.add(music2);

        // Ajout du JPanel des boutons de menu principal au centre du panel global
        panel.add(mainMenuPanel, BorderLayout.CENTER);

        // Création et configuration du bouton "Menu"
        JButton menuButton = new JButton("Menu");
        configureButton(menuButton, e -> {
            // TODO : action lorsque le bouton "Menu" est cliqué
            System.exit(0);
            //showMenu();
        });

        // Ajout du bouton "Menu" dans le coin supérieur gauche
        panel.add(menuButton, BorderLayout.NORTH);

        panel.setPreferredSize(new Dimension(100, 100)); // Taille du panel de menu

        return panel;
    }

    /**
     * Configure les propriétés visuelles et comportementales d'un bouton.
     *
     * @param button Le bouton à configurer.
     * @param actionListener L'action à exécuter lorsque le bouton est cliqué.
     */
    private void configureButton(JButton button, ActionListener actionListener) {
        button.setPreferredSize(new Dimension(50,50));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.addActionListener(actionListener);
    }

    /**
     * Redirige vers le menu principal.
     * Cette méthode est appelée lorsque le bouton "Menu" est cliqué.
     */
    private void showMenu() {
        // TODO : Implémenter la redirection vers le menu principal
    }

}
