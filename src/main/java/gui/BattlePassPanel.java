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

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

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
            backgroundImage = new ImageIcon("src/main/ressources/background/bgBattlePass.gif").getImage(); 
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    // Méthode pour mettre à jour les paliers en fonction du niveau/XP du jeu
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

    /*
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50; // Position horizontale de départ pour les cases
        int y = getHeight() / 2; // Position verticale au centre du panneau
        int width = 50; // Largeur des cases
        int height = 50; // Hauteur des cases

        // Dessine chaque palier du battlepass
        for (int palier = 1; palier <= MAX_PALIER; palier++) {
            // Dessine la case
            g.setColor(rewardsReached.get(palier) ? Color.WHITE : Color.GRAY);
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            // Dessine le numéro du palier au centre de la case
            g.drawString(String.valueOf(palier), x + width / 2 - 5, y + height / 2 + 5);
            // Dessine l'icône de récompense si le palier est atteint
            if (rewardsReached.get(palier)) {
                // Dessine une icône factice, vous pouvez remplacer par vos propres images
                //g.drawImage(getRewardIcon(palier), x + 5, y + 5, width - 10, height - 10, null);
                g.setColor(Color.red);
            }
            x += width + 10; // Décale la position horizontale pour la prochaine case
        }

        // Dessine le niveau de l'utilisateur
        g.drawString("Niveau : " + game.getLevel(), getWidth() / 2 - 30, getHeight() - 20);
    }
    */

    // Méthode factice pour obtenir l'icône de récompense en fonction du palier
    private Image getRewardIcon(int palier) {
        // Ici vous devriez charger vos propres images d'icônes de récompense
        // Cette méthode retourne une image factice à des fins de démonstration
        // Remplacez cette méthode par une qui charge réellement vos images d'icônes
        return new ImageIcon("path/to/your/reward/icon.png").getImage();
    }
    @Override
    public void onLevelChange() {
        updatePaliers();
        repaint();
    }

}
