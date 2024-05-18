package gui;

import config.Game;
import config.RessourcesPaths;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    private Game game;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JButton homeButton;
    private JButton soloButton;
    private JButton shopButton;
    private JButton versusButton;
    private JButton battlePassButton;

    public static String knifePathClicked;


    public MainFrame(Game game) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Hitty Knife - Redux");
        setIconImage(new ImageIcon(RessourcesPaths.iconPath + "icon.png").getImage());

        this.game = game;
        this.knifePathClicked = RessourcesPaths.knifePath + "knife#3.png";

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (game != null) {
                    game.sauvegarderEtat(); // Appel de la méthode de sauvegarde
                }
            }
        });

        // Création des boutons de navigation
        homeButton = new JButton("Home");
        soloButton = new JButton("Solo");
        shopButton = new JButton("Shop");
        versusButton = new JButton("Versus");
        battlePassButton = new JButton("BattlePass");


        // Supprimer le rectangle de sélection lorsqu'un bouton est enfoncé
        homeButton.setFocusPainted(false);
        soloButton.setFocusPainted(false);
        shopButton.setFocusPainted(false);
        versusButton.setFocusPainted(false);
        battlePassButton.setFocusPainted(false);

        // Désactive la possibilité pour les boutons de prendre le focus
        homeButton.setFocusable(false);
        soloButton.setFocusable(false);
        shopButton.setFocusable(false);
        versusButton.setFocusable(false);
        battlePassButton.setFocusable(false);

        // Ajout des actions aux boutons de navigation
        homeButton.addActionListener(e -> switchToPanel("Home"));
        soloButton.addActionListener(e -> switchToPanel("Solo"));
        shopButton.addActionListener(e -> switchToPanel("Shop"));
        versusButton.addActionListener(e -> switchToPanel("Versus"));
        battlePassButton.addActionListener(e -> switchToPanel("BattlePass"));


        // Création du panneau de menu principal
        JPanel homePanel = createHomeMenuPanel();
        JPanel soloPanel = createSoloPanel();
        JPanel shopPanel = createShopPanel();
        JPanel versusPanel = createVersusPanel();
        JPanel battlepassPanel = createBattlepassPanel();


        // Création du conteneur de panneaux avec CardLayout
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.add(homePanel, "Home");
        cardPanel.add(soloPanel, "Solo");
        cardPanel.add(shopPanel, "Shop");
        cardPanel.add(versusPanel, "Versus");
        cardPanel.add(battlepassPanel, "BattlePass");


        // Ajout des boutons de navigation en haut de la fenêtre
        JPanel navPanel = new JPanel(new FlowLayout());
        navPanel.add(homeButton);
        navPanel.add(soloButton);
        navPanel.add(shopButton);
        navPanel.add(versusButton);
        navPanel.add(battlePassButton);


        // Utilisation d'un BorderLayout pour la JFrame
        setLayout(new BorderLayout());

        // Ajout du navPanel en haut et du cardPanel au centre
        add(navPanel, BorderLayout.SOUTH);
        add(cardPanel, BorderLayout.CENTER);

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Récupère la vue de jeu actuellement affichée dans le conteneur de panneaux.
     *
     * @return La vue de jeu actuellement affichée, ou null s'il n'y en a pas.
     */
    public GameView getGameView() {
        Component[] components = cardPanel.getComponents();
        for (Component component : components) {
            if (component instanceof GameView) {
                return (GameView) component;
            }
        }
        return null;
    }



    private void switchToPanel(String name) {
        cardLayout.show(cardPanel, name);
        SwingUtilities.invokeLater(() -> {
            cardPanel.revalidate();
            cardPanel.repaint();
        });
        Component visibleComp = null;
        for (Component comp : cardPanel.getComponents()) {
            if (comp.isVisible()) {
                visibleComp = comp;
                if (comp instanceof GameView) {
                    game.setGameView((GameView) comp);  
                }
            }
        }
        if (visibleComp != null) {
            visibleComp.requestFocusInWindow();
        }
        if(name == "Solo"){
            game.setIsSOlo(true);
        }
        else {
            game.setIsSOlo(false);
        }


    }


    private JPanel createShopPanel() {
        return new ShopMenu(game);
    }

    private JPanel createSoloPanel() {
        return new GameView(true, game);
    }

    private JPanel createVersusPanel() {
        return new GameView(false, game);
    }

    private JPanel createHomeMenuPanel(){
        HomeMenu homeMenu = new HomeMenu(game);
        game.addLibraryObserver(homeMenu);
        return homeMenu;
    }

    private JPanel createBattlepassPanel() {

        BattlePassPanel battlePassPanel = new BattlePassPanel(game);
        JPanel mainPanel = new JPanel(new GridBagLayout()){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(battlePassPanel.getBackgroundImage(), 0, 0, getWidth(),getHeight(),this);
            }   
        };
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;

        mainPanel.add(battlePassPanel, gbc);

        game.addObserver(battlePassPanel); // Enregistrement de BattlePassPanel en tant qu'observateur

        return mainPanel;
    }
}
