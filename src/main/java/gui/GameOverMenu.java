package gui;

import config.Game;
import javax.swing.*;
import java.awt.*;
import static config.States.*;

public class GameOverMenu extends Menu {
   

    public GameOverMenu(String title, String backgroundPath, String musicPath) {
        super(title, backgroundPath, musicPath);
        JPanel GameOverPanel = createMenuPanel(backgroundPath);
        add(GameOverPanel);
    }


    @Override
    JPanel createMenuPanel(String backgroundPath) {
        BackgroundPanel panel = new BackgroundPanel(backgroundPath);
        panel.setLayout(new GridLayout(4, 1, 0, 20)); // Espacement vertical entre les boutons
        panel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20)); // Ajoute des marges

         // Bouton "Rejouer"
        JButton RestartButton = new JButton(new ImageIcon("src/main/ressources/button/Button.png")); //mettre le bon chemin
        RestartButton.setFocusPainted(false);
        RestartButton.setBorderPainted(false);
        RestartButton.setContentAreaFilled(false);
        RestartButton.addActionListener(e -> restartGame());
        panel.add(RestartButton);

        // Bouton "Menu"
        JButton MenuButton = new JButton(new ImageIcon("src/main/ressources/button/Button.png"));//mettre le bon chemin
        MenuButton.setFocusPainted(false);
        MenuButton.setBorderPainted(false);
        MenuButton.setContentAreaFilled(false);
        MenuButton.addActionListener(e -> showMenu());
        panel.add(MenuButton);

        // Bouton "Quitter"
        JButton quitterButton = new JButton(new ImageIcon("src/main/ressources/button/Button.png"));//mettre le bon chemin
        quitterButton.setFocusPainted(false);
        quitterButton.setBorderPainted(false);
        quitterButton.setContentAreaFilled(false);
        quitterButton.addActionListener(e -> System.exit(0));
        panel.add(quitterButton);
        
         // Définition de la taille préférée pour centrer dans le GridBagLayout
         panel.setPreferredSize(new Dimension(500, 800)); // Taille du panel de menu

         return panel;
    }

    // Setter pour l'instance de Game
    public void setGame(Game game) {
        this.game = game;
    }

    //TODO : Ajouter la méthode qui recommence le mode de jeu quand la partie graphique du jeu sera implémentée
    private void restartGame() {

    }

     //TODO : Ajouter la méthode redirigeant au Menu quand la partie graphique du jeu sera implémentée
    private void showMenu() {

    }

}
