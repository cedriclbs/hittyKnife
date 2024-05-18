package gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import config.Game;
import config.GameObserver;

public class BattlePassPanel extends JPanel implements GameObserver {
    private static final int MAX_PALIER = 20;
    private Map<Integer, Boolean> rewardsReached;
    private int currentLevel;
    private Game game;
    private Image backgroundImage;


    /**
     * Constructeur de la classe BattlePassPanel.
     * Initialise le panneau du passe de combat avec les paramètres du jeu et crée la carte des récompenses atteintes.
     *
     * @param game L'instance du jeu associée à ce panneau.
     */
    public BattlePassPanel(Game game) {
        this.game = game;
        this.currentLevel = game.getLevel();
        rewardsReached = new HashMap<>();
        for (int i = 0; i <= MAX_PALIER; i++) {
            rewardsReached.put(i, false);
        }

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // FlowLayout horizontal, centré, avec un espacement de 10 pixels
        updatePaliers();

        try{
            backgroundImage = new ImageIcon("src/main/ressources/background/bgStarWars.gif").getImage(); 
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

     private Image getRewardIcon(int palier) {
        return new ImageIcon("path/to/your/reward/icon.png").getImage();
    }

    /**
     * Met à jour l'affichage des paliers en fonction du niveau du joueur.
     * Elle marque les récompenses atteintes et ajoute des cases pour chaque palier avec des couleurs différentes.
     */
    private void updatePaliers() {
        int currentLevel = game.getLevel();

        // Logique pour mettre à jour les paliers en fonction du niveau du joueur
        // Par exemple, marquer les paliers atteints jusqu'au niveau du joueur
        for (int i = 0; i <= currentLevel; i++) {
            rewardsReached.put(i, true);
        }
        removeAll(); // Supprime tous les composants existants
        // Calcule le nombre de cases à afficher pour être centré
        int totalWidth = (50 + 10) * MAX_PALIER; // Largeur totale des cases et de l'espacement
        int horizontalGap = (getWidth() - totalWidth) / 2; // Espacement horizontal pour centrer les cases
        // Ajoute les nouvelles cases en fonction des paliers atteints
        for (int palier = MAX_PALIER; palier >= 1; palier--) {
            JPanel casePanel = new JPanel();
            casePanel.setPreferredSize(new Dimension(50, 50)); // Taille des cases (50x50 pixels)
            casePanel.setLayout(new BorderLayout());
            JLabel label = new JLabel(String.valueOf(palier));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            casePanel.add(label, BorderLayout.CENTER);
            if (rewardsReached.get(palier)) {
                casePanel.setBackground(Color.WHITE);
            } else {
                casePanel.setBackground(Color.GRAY);
            }
            add(casePanel);
            // Applique l'espacement horizontal pour centrer les cases
            setComponentZOrder(casePanel, 0); // Met à jour l'ordre des composants
            casePanel.setLocation(horizontalGap + (50 + 10) * (MAX_PALIER - palier), (getHeight() - 50) / 2); // Positionne la case
        }
        revalidate(); // Redessine le panneau après la mise à jour des paliers
        repaint();
    }

    @Override
    public void onLevelChange() {
        updatePaliers();
        repaint();
    }

}
