package gui;

import config.Game;
import config.RessourcesPaths;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private Game game;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JButton homeButton;
    private JButton soloButton;
    private JButton shopButton;
    private JButton versusButton;
    private JButton libraryButton;

    public static String knifePathClicked;


    public MainFrame(Game game) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Hitty Knife");

        this.game = game;
        this.knifePathClicked = RessourcesPaths.knifePath + "knife#3.png";


        // Création des boutons de navigation
        homeButton = new JButton("Home");
        soloButton = new JButton("Solo");
        shopButton = new JButton("Shop");
        versusButton = new JButton("Versus");
        libraryButton = new JButton("Inventaire");


        // Ajout des actions aux boutons de navigation
        homeButton.addActionListener(e -> cardLayout.show(cardPanel, "Home"));
        soloButton.addActionListener(e -> cardLayout.show(cardPanel, "Solo"));
        shopButton.addActionListener(e -> cardLayout.show(cardPanel, "Shop"));
        versusButton.addActionListener(e -> cardLayout.show(cardPanel, "Versus"));
        libraryButton.addActionListener(e -> cardLayout.show(cardPanel, "Inventaire"));


        // Création du panneau de menu principal
        JPanel homePanel = createHomePanel();
        JPanel soloPanel = createSoloPanel();
        JPanel shopPanel = createShopPanel();
        JPanel versusPanel = createVersusPanel();
        JPanel libraryPanel = createLibraryPanel();


        // Création du conteneur de panneaux avec CardLayout
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.add(homePanel, "Home");
        cardPanel.add(soloPanel, "Solo");
        cardPanel.add(shopPanel, "Shop");
        cardPanel.add(versusPanel, "Versus");
        cardPanel.add(libraryPanel, "Inventaire");


        // Ajout des boutons de navigation en haut de la fenêtre
        JPanel navPanel = new JPanel(new FlowLayout());
        navPanel.add(homeButton);
        navPanel.add(soloButton);
        navPanel.add(shopButton);
        navPanel.add(versusButton);
        navPanel.add(libraryButton);


        // Ajout des composants à la fenêtre principale
        getContentPane().add(navPanel, BorderLayout.NORTH);
        getContentPane().add(cardPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public GameView getGameView() {
        Component[] components = cardPanel.getComponents();
        for (Component component : components) {
            if (component instanceof GameView) {
                return (GameView) component;
            }
        }
        return null;
    }


    private JPanel createHomePanel() {
        return new HomeMenu("src/main/ressources/background/Background_MainMenu.png", "");
    }


    private JPanel createShopPanel() {
        return new ShopMenu(game);
    }

    private JPanel createSoloPanel() {
        return new GameView(true);
    }


    private JPanel createVersusPanel() {
        return new GameView(false);
    }

    private JPanel createLibraryPanel(){
        return new Library(game.getNomUtilisateur());
    }




}
