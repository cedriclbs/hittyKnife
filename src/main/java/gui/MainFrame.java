package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JButton homeButton;
    private JButton soloButton;
    private JButton shopButton;
    private JButton versusButton;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Hitty Knife");

        // Création des boutons de navigation
        homeButton = new JButton("Home");
        soloButton = new JButton("Solo");
        shopButton = new JButton("Shop");
        versusButton = new JButton("Versus");

        // Ajout des actions aux boutons de navigation
        homeButton.addActionListener(e -> cardLayout.show(cardPanel, "Home"));
        soloButton.addActionListener(e -> cardLayout.show(cardPanel, "Solo"));
        shopButton.addActionListener(e -> cardLayout.show(cardPanel, "Shop"));
        versusButton.addActionListener(e -> cardLayout.show(cardPanel, "Versus"));

        // Création du panneau de menu principal
        JPanel homePanel = createHomePanel();
        JPanel soloPanel = createSoloPanel();
        JPanel shopPanel = createShopPanel();
        JPanel versusPanel = createVersusPanel();

        // Création du conteneur de panneaux avec CardLayout
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.add(homePanel, "Home");
        cardPanel.add(soloPanel, "Solo");
        cardPanel.add(shopPanel, "Shop");
        cardPanel.add(versusPanel, "Versus");

        // Ajout des boutons de navigation en haut de la fenêtre
        JPanel navPanel = new JPanel(new FlowLayout());
        navPanel.add(homeButton);
        navPanel.add(soloButton);
        navPanel.add(shopButton);
        navPanel.add(versusButton);

        // Ajout des composants à la fenêtre principale
        getContentPane().add(navPanel, BorderLayout.NORTH);
        getContentPane().add(cardPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private JPanel createHomePanel() {
        return new HomeMenu("src/main/ressources/background/Background_MainMenu.png", "");
    }


    private JPanel createShopPanel() {
        return new ShopMenu("src/main/ressources/background/bgShopMenu.png");
    }

    private JPanel createSoloPanel() {
        return new GameView(true);
    }


    private JPanel createVersusPanel() {
        return new GameView(false);
    }

}
