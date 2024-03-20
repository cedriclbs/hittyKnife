package gui;

import config.Game;
import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre de menu affichée lorsque la partie est terminée.
 */
public class GameOverMenu extends Menu {

    /**
     * Constructeur de la fenêtre de menu de fin de jeu.
     *
     * @param title Le titre de la fenêtre de menu.
     * @param backgroundPath Le chemin de l'arrière-plan de la fenêtre de menu.
     * @param musicPath Le chemin de la musique de fond de la fenêtre de menu.
     */
    public GameOverMenu(String title, String backgroundPath, String musicPath) {
        super(title, backgroundPath, musicPath);
        JPanel GameOverPanel = createMenuPanel(backgroundPath);
        add(GameOverPanel);
    }

    /**
     * Définit l'instance de jeu associée à ce menu de fin de jeu.
     *
     * @param game L'instance de jeu à associer à ce menu.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Crée le panneau de menu de fin de jeu.
     *
     * @param backgroundPath Le chemin de l'arrière-plan du panneau de menu.
     * @return Le panneau de menu créé.
     */
    @Override
    JPanel createMenuPanel(String backgroundPath) {
        BackgroundPanel panel = new BackgroundPanel(backgroundPath);
        panel.setLayout(new GridLayout(4, 1, 0, 20)); // Espacement vertical entre les boutons
        panel.setBorder(BorderFactory.createEmptyBorder(200, 20, 20, 20)); // Ajoute des marges
    
        // Bouton "Rejouer"
        JPanel restartButtonPanel = new JPanel();
        restartButtonPanel.setOpaque(false); // Rend le panel transparent
        JButton RestartButton = new JButton(new ImageIcon("src/main/ressources/button/Button.png")); //mettre le bon chemin
        RestartButton.setFocusPainted(false);
        RestartButton.setBorderPainted(false);
        RestartButton.setContentAreaFilled(false);
        RestartButton.addActionListener(e -> restartGame());
        restartButtonPanel.add(RestartButton);
        panel.add(restartButtonPanel);
    
        // Bouton "Menu"
        JPanel menuButtonPanel = new JPanel();
        menuButtonPanel.setOpaque(false); // Rend le panel transparent
        JButton MenuButton = new JButton(new ImageIcon("src/main/ressources/button/Button.png"));//mettre le bon chemin
        MenuButton.setFocusPainted(false);
        MenuButton.setBorderPainted(false);
        MenuButton.setContentAreaFilled(false);
        MenuButton.addActionListener(e -> showMenu());
        menuButtonPanel.add(MenuButton);
        panel.add(menuButtonPanel);
    
        // Bouton "Quitter"
        JPanel quitButtonPanel = new JPanel();
        quitButtonPanel.setOpaque(false); // Rend le panel transparent
        JButton quitterButton = new JButton(new ImageIcon("src/main/ressources/button/Button.png"));//mettre le bon chemin
        quitterButton.setFocusPainted(false);
        quitterButton.setBorderPainted(false);
        quitterButton.setContentAreaFilled(false);
        quitterButton.addActionListener(e -> quitterEtSauvegarder());
        quitButtonPanel.add(quitterButton);
        panel.add(quitButtonPanel);
    
        // Définition de la taille préférée pour centrer dans le GridBagLayout
        panel.setPreferredSize(new Dimension(500, 800)); // Taille du panel de menu
    
        return panel;
    }

    //TODO : Ajouter la méthode qui recommence le mode de jeu quand la partie graphique du jeu sera implémentée
    private void restartGame() {

    }

     //TODO : Ajouter la méthode redirigeant au Menu quand la partie graphique du jeu sera implémentée
    private void showMenu() {

    }

}
