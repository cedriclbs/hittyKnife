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

        // Supprimer le rectangle de sélection lorsqu'un bouton est enfoncé
        homeButton.setFocusPainted(false);
        soloButton.setFocusPainted(false);
        shopButton.setFocusPainted(false);
        versusButton.setFocusPainted(false);

        // Désactive la possibilité pour les boutons de prendre le focus
        homeButton.setFocusable(false);
        soloButton.setFocusable(false);
        shopButton.setFocusable(false);
        versusButton.setFocusable(false);

        // Ajout des actions aux boutons de navigation
        homeButton.addActionListener(e -> switchToPanel("Home"));
        soloButton.addActionListener(e -> switchToPanel("Solo"));
        shopButton.addActionListener(e -> switchToPanel("Shop"));
        versusButton.addActionListener(e -> switchToPanel("Versus"));

        // Création du panneau de menu principal
        JPanel homePanel = createTitleScreen();
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

        // Utilisation d'un BorderLayout pour la JFrame
        setLayout(new BorderLayout());

        // Ajout du navPanel en haut et du cardPanel au centre
        add(navPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Basculer vers le panneau spécifié par son nom et lui attribuer le focus.
     * Cette méthode change la vue visible dans le conteneur géré par le {@link CardLayout},
     * en affichant le panneau correspondant au nom spécifié. Après avoir affiché le panneau,
     * cette méthode parcourt également tous les composants du conteneur de panneaux pour trouver le panneau nouvellement visible
     * et lui attribue le focus pour permettre la gestion correcte des entrées clavier.
     *
     * @param name le nom du panneau à afficher, tel que spécifié lors de l'ajout du panneau au {@link CardLayout}.
     */
    private void switchToPanel(String name) {
        cardLayout.show(cardPanel, name);
        for (Component comp : cardPanel.getComponents()) {
            if (comp.isVisible() && comp instanceof JPanel) {
                comp.requestFocusInWindow();
                break;
            }
        }
    }


    private JPanel createTitleScreen() {
        return new HomeMenu("src/main/ressources/background/Background_MainMenu.png", Menu.linkClip+"Main_theme.wav");
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
