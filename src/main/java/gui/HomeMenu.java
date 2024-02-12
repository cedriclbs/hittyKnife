package gui;


import javax.swing.*;
import java.awt.*;

/**
 * La classe {@code HomeMenu} étend {@code Menu} pour représenter le menu principal du jeu.
 * Elle gère l'affichage des différentes options du menu telles que jouer, accéder aux paramètres, au magasin (shop), ou quitter le jeu.
 * <p>
 * Cette classe configure l'interface utilisateur du menu principal, y compris la musique de fond et le placement des boutons.
 * </p>
 */
public class HomeMenu extends Menu {

    //TODO: quand on appuie sur échap, enlève le plein écran
    /**
     * Crée l'interface du menu principal avec le titre spécifié, le chemin vers l'image de fond et le chemin vers la musique de fond.
     *
     * @param title Le titre de la fenêtre du menu.
     * @param backgroundPath Le chemin d'accès au fichier d'image de fond du menu.
     * @param musicPath Le chemin d'accès au fichier audio de la musique de fond.
     */
    public HomeMenu(String title, String backgroundPath, String musicPath) {
        super(title, backgroundPath, musicPath);
        JPanel menuPanel = createMenuPanel(backgroundPath);
        add(menuPanel);
    }

    /**
     * Crée et retourne un {@link JPanel} configuré avec l'image de fond spécifiée et les boutons du menu.
     * <p>
     * Ce panel inclut les boutons pour les actions "Solo", "1v1", "Shop", et "Quitter", chacun configuré avec ses propres actions.
     * </p>
     *
     * @param backgroundPath Le chemin d'accès au fichier d'image de fond pour le panel.
     * @return Le {@link JPanel} configuré pour le menu principal.
     */
    JPanel createMenuPanel(String backgroundPath) {
        BackgroundPanel panel = new BackgroundPanel(backgroundPath);
        panel.setLayout(new GridLayout(4, 1, 0, 20)); // Espacement vertical entre les boutons
        panel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20)); // Ajoute des marges


        // TODO : Afficher le numéro du niveau actuel centré en haut-> "Niveau X"
        // TODO : Afficher un engrenage pour les paramètres en haut à gauche.

        // Bouton "Solo"
        JButton campaignButton = new JButton(new ImageIcon("src/main/ressources/button/Button.png"));
        campaignButton.setFocusPainted(false);
        campaignButton.setBorderPainted(false);
        campaignButton.setContentAreaFilled(false);
        campaignButton.addActionListener(e -> showGame());
        panel.add(campaignButton);

        // Bouton "1v1"
        JButton versusButton = new JButton(new ImageIcon("src/main/ressources/button/Button.png"));
        versusButton.setFocusPainted(false);
        versusButton.setBorderPainted(false);
        versusButton.setContentAreaFilled(false);
        versusButton.addActionListener(e -> show1v1Menu());
        panel.add(versusButton);

        // Bouton "Shop"
        JButton shopButton = new JButton(new ImageIcon("src/main/ressources/button/Button.png"));
        shopButton.setFocusPainted(false);
        shopButton.setBorderPainted(false);
        shopButton.setContentAreaFilled(false);
        shopButton.addActionListener(e -> showShop());
        panel.add(shopButton);

        // Bouton "Quitter"
        JButton quitterButton = new JButton(new ImageIcon("src/main/ressources/button/Button.png"));
        quitterButton.setFocusPainted(false);
        quitterButton.setBorderPainted(false);
        quitterButton.setContentAreaFilled(false);
        quitterButton.addActionListener(e -> System.exit(0));
        panel.add(quitterButton);

        // Définition de la taille préférée pour centrer dans le GridBagLayout
        panel.setPreferredSize(new Dimension(500, 800)); // Taille du panel de menu

        return panel;

    }

    //TODO : Ajouter la méthode redirigeant au jeu quand la partie graphique du jeu sera implémentée
    private void showGame() {

    }

    //TODO : Ajouter la méthode redirigeant au mode 1v1 quand le mode sera implémenté
    private void show1v1Menu() {
    }

    //TODO : Ajouter la méthode redirigeant au shop quand le shop sera implémenté
    private void showShop() {

    }
}
