package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public interface GameFrame {


    //Les frames (solo, 1v1) devront toutes implémenter ces fonctions (et les fonctions à venir!)

    void initialize(String backgroundPath);

    default void buttonPause (BackgroundPanel gamePanel) {
        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code pour mettre en pause le jeu. A implémenter plus tard.
            }
        });
        gamePanel.add(pauseButton);
    }

}