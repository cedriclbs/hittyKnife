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

    private ShopMenu shopMenu;


    //TODO: quand on appuie sur échap, enlève le plein écran
    /**
     * Crée l'interface du menu principal avec le titre spécifié, le chemin vers l'image de fond et le chemin vers la musique de fond.
     *
     * @param backgroundPath Le chemin d'accès au fichier d'image de fond du menu.
     * @param musicPath Le chemin d'accès au fichier audio de la musique de fond.
     */
    public HomeMenu(String backgroundPath, String musicPath) {
        super(backgroundPath, musicPath);
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
    @Override
    JPanel createMenuPanel(String backgroundPath) {
        String ButtonAd = "src/main/ressources/button";

        BackgroundPanel panel = new BackgroundPanel(backgroundPath);
        panel.setLayout(new GridLayout(4, 1, 0, 20)); //Espacement vertical entre les boutons
        panel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20)); // Ajoute des marges

        // Bouton "Solo"
        JPanel campaignButtonPanel = new JPanel();
        campaignButtonPanel.setOpaque(false); // Rend le panel transparent
        JButton campaignButton = new JButton(new ImageIcon(ButtonAd + "/PlayButton.png"));
        campaignButton.setFocusPainted(false);
        campaignButton.setBorderPainted(false);
        campaignButton.setContentAreaFilled(false);
        //campaignButton.addActionListener(e -> showGame());
        campaignButtonPanel.add(campaignButton);
        panel.add(campaignButtonPanel);

        // Bouton "1v1"
        JPanel versusButtonPanel = new JPanel();
        versusButtonPanel.setOpaque(false); // Rend le panel transparent
        JButton versusButton = new JButton(new ImageIcon(ButtonAd + "/VersusButton.png"));
        versusButton.setFocusPainted(false);
        versusButton.setBorderPainted(false);
        versusButton.setContentAreaFilled(false);
        //versusButton.addActionListener(e -> show1v1Menu());
        versusButtonPanel.add(versusButton);
        panel.add(versusButtonPanel);

        // Bouton "Shop"
        JPanel shopButtonPanel = new JPanel();
        shopButtonPanel.setOpaque(false); // Rend le panel transparent
        JButton shopButton = new JButton(new ImageIcon(ButtonAd + "/ShopButton.png"));
        shopButton.setFocusPainted(false);
        shopButton.setBorderPainted(false);
        shopButton.setContentAreaFilled(false);
        shopButton.addActionListener(e -> showShop());
        shopButtonPanel.add(shopButton);
        panel.add(shopButtonPanel);

        // Bouton "Quitter"
        JPanel quitterButtonPanel = new JPanel();
        quitterButtonPanel.setOpaque(false); // Rend le panel transparent
        JButton quitterButton = new JButton(new ImageIcon(ButtonAd + "/QuitButton.png"));
        quitterButton.setFocusPainted(false);
        quitterButton.setBorderPainted(false);
        quitterButton.setContentAreaFilled(false);
        quitterButton.addActionListener(e -> quitterEtSauvegarder());
        quitterButtonPanel.add(quitterButton);
        panel.add(quitterButtonPanel);

        // Définition de la taille préférée pour centrer dans le GridBagLayout
        panel.setPreferredSize(new Dimension(500, 800)); // Taille du panel de menu

        return panel;
    }

    /**
     * Affiche le jeu en mode solo lorsque le bouton "Solo" est cliqué.
     */
//    private void showGame() {
//        GameView gameView = new GameView(true);
//        gameView.startSoloGame();
//    }
//
//    private void show1v1Menu() {
//        GameView gameView = new GameView(false);
//        gameView.startSoloGame();
//    }

    private void showShop() {
        if (shopMenu == null) {
            shopMenu = new ShopMenu(null);
        }
        shopMenu.startShopMenu();
    }
}