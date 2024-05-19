package gui;

import config.Game;
import config.RessourcesPaths;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class MainFrame extends JFrame {

    private Game game;
    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    public static String knifePathClicked;

    /**
     * Constructeur de la classe MainFrame.
     *
     * @param game Le jeu associé à la fenêtre principale.
     */
    public MainFrame(Game game) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Hitty Knife - Redux");
        setIconImage(new ImageIcon(RessourcesPaths.iconPath + "icon.png").getImage());

        this.game = game;
        knifePathClicked = RessourcesPaths.knifePath + "knife#3.png";

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (game != null) {
                    game.sauvegarderEtat(); // Appel de la méthode de sauvegarde
                }
            }
        });

        JButton homeButton = new JButton("Home");
        JButton soloButton = new JButton("Solo");
        JButton shopButton = new JButton("Shop");
        JButton versusButton = new JButton("Versus");
        JButton battlePassButton = new JButton("BattlePass");

        homeButton.setBackground(new Color(124, 143, 184));
        soloButton.setBackground(new Color(124, 143, 184));
        shopButton.setBackground(new Color(124, 143, 184));
        versusButton.setBackground(new Color(124, 143, 184));
        battlePassButton.setBackground(new Color(124, 143, 184));

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


        JPanel navPanel = new JPanel(new FlowLayout());
        navPanel.add(homeButton);
        navPanel.add(soloButton);
        navPanel.add(shopButton);
        navPanel.add(versusButton);
        navPanel.add(battlePassButton);

        navPanel.setBackground(Color.DARK_GRAY);

        // Utilisation d'un BorderLayout pour la JFrame
        setLayout(new BorderLayout());

        // Ajout du navPanel en bas et du cardPanel au centre
        add(navPanel, BorderLayout.SOUTH);
        add(cardPanel, BorderLayout.CENTER);

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Récupère la vue de jeu solo actuellement affichée dans le conteneur de panneaux.
     *
     * @return La vue de jeu actuellement affichée, ou null s'il n'y en a pas.
     */
    public GameView getGameViewSolo() {
        Component[] components = cardPanel.getComponents();
        for (Component component : components) {
            if (component instanceof GameView) {
                GameView view = (GameView) component;
                if(view.isSolo){
                    return (GameView) component;
                }
            }
        }
        return null;
    }

    /**
     * Récupère la vue de jeu versus actuellement affichée dans le conteneur de panneaux.
     *
     * @return La vue de jeu actuellement affichée, ou null s'il n'y en a pas.
     */
    public GameView getGameViewVersus() {
        Component[] components = cardPanel.getComponents();
        for (Component component : components) {
            if (component instanceof GameView) {
                GameView view = (GameView) component;
                if(!view.isSolo){
                    return (GameView) component;
                }
            }
        }
        return null;
    }

    /**
     * Change le panneau affiché dans la fenêtre principale.
     *
     * @param name Le nom du panneau à afficher.
     */
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
                    GameView view = (GameView) comp;
                    if(view.isSolo){  
                        game.setGameView((GameView) comp);  
                    }
                }
            }
        }
        if (visibleComp != null) {
            visibleComp.requestFocusInWindow();
        }
        game.setIsSOlo(Objects.equals(name, "Solo"));
    }

    /**
     * Crée et retourne un panneau de la boutique.
     *
     * @return un {@link JPanel} représentant le menu de la boutique.
     */
    private JPanel createShopPanel() {
        return new ShopMenu(game);
    }

    /**
     * Crée et retourne un panneau pour le mode solo du jeu.
     *
     * @return un {@link JPanel} représentant la vue de jeu en mode solo.
     */
    private JPanel createSoloPanel() {
        return new GameView(true, game);
    }

    /**
     * Crée et retourne un panneau pour le mode versus du jeu.
     *
     * @return un {@link JPanel} représentant la vue de jeu en mode versus.
     */
    private JPanel createVersusPanel() {
        return new GameView(false, game);
    }

    private JPanel createHomeMenuPanel(){
        HomeMenu homeMenu = new HomeMenu(game);
        game.addLibraryObserver(homeMenu);
        return homeMenu;
    }

    /**
     * Crée et retourne un panneau pour le menu principal.
     * La méthode initialise également le menu principal en tant qu'observateur de l'objet jeu.
     *
     * @return un {@link JPanel} représentant le menu principal.
     */
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
