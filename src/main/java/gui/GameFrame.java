package gui;

import javax.swing.JButton;

/**
 * Interface représentant un cadre de jeu.
 */
public interface GameFrame {

    /**
     * Initialise le cadre de jeu avec un chemin d'arrière-plan donné.
     *
     * @param backgroundPath Le chemin de l'arrière-plan du cadre de jeu.
     */
    void initialize(String backgroundPath);

    /**
     * Ajoute un bouton de pause au panneau de jeu.
     *
     * @param gamePanel Le panneau de jeu auquel ajouter le bouton de pause.
     */
    default void buttonPause(BackgroundPanel gamePanel) {
        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> {
            // Code pour mettre en pause le jeu. A implémenter plus tard.
        });
        gamePanel.add(pauseButton);
    }

}
