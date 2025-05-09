package gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import config.Game;
import config.GameObserver;

public class BattlePassPanel extends JPanel implements GameObserver {
    private static final int MAX_PALIER = 15;
    private Map<Integer, Boolean> rewardsReached;
    private int currentLevel;
    private Game game;
    private Image backgroundImage;
    private Map<Integer, Image> rewardIcons;


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
        rewardIcons = new HashMap<>();

        for (int i = 0; i <= MAX_PALIER; i++) {
            rewardsReached.put(i, false);
        }

        // Charger les images des récompenses
        loadRewardIcons();

        try {
            backgroundImage = new ImageIcon("src/main/ressources/background/bgStarWars.gif").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // FlowLayout horizontal, centré, avec un espacement de 10 pixels
        updatePaliers();
    }

    // Méthode pour charger les images des récompenses
    private void loadRewardIcons() {
        Image moneyIcon = new ImageIcon("src/main/ressources/icon/MoneyIcon.png").getImage();
        Image knife4Icon = new ImageIcon("src/main/ressources/knifes/knife#4.png").getImage();
        Image musicIcon = new ImageIcon("src/main/ressources/button/music.png").getImage();
        Image knife5Icon = new ImageIcon("src/main/ressources/knifes/knife#5.png").getImage();

        // Ajout des icônes aux paliers correspondants
        addRewardIcons(new int[]{1, 2, 4, 5, 7, 8, 10, 11, 13, 14}, moneyIcon);
        rewardIcons.put(3, knife4Icon);
        rewardIcons.put(6, musicIcon);
        rewardIcons.put(9, knife5Icon);
        rewardIcons.put(12, musicIcon);
        rewardIcons.put(15, musicIcon);
    }

    // Méthode pour ajouter la même icône à plusieurs paliers
    private void addRewardIcons(int[] paliers, Image icon) {
        for (int palier : paliers) {
            rewardIcons.put(palier, icon);
        }
    }

    // Méthode pour obtenir l'icône de récompense en fonction du palier
    private Image getRewardIcon(int palier) {
        return rewardIcons.get(palier);
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    /**
     * Met à jour l'affichage des paliers en fonction du niveau du joueur.
     * Elle marque les récompenses atteintes et ajoute des cases pour chaque palier avec des couleurs différentes.
     */
    private void updatePaliers() {
        int currentLevel = game.getLevel();

        // Logique pour mettre à jour les paliers en fonction du niveau du joueur
        for (int i = 0; i <= currentLevel; i++) {
            rewardsReached.put(i, true);
        }
        removeAll(); // Supprime tous les composants existants

        // Ajoute les nouvelles cases en fonction des paliers atteints
        for (int palier = 1; palier <= MAX_PALIER; palier++) { // Boucle modifiée pour aller de 1 à 20
            JPanel casePanel = new JPanel();
            casePanel.setPreferredSize(new Dimension(75, 75)); // Taille des cases (75x75 pixels)
            casePanel.setLayout(new BorderLayout());

            JLabel label = new JLabel(String.valueOf(palier));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            casePanel.add(label, BorderLayout.NORTH);

            if (rewardsReached.get(palier)) {
                casePanel.setBackground(Color.WHITE);
            } else {
                casePanel.setBackground(Color.GRAY);
            }

            // Ajouter l'icône de récompense
            Image rewardIcon = getRewardIcon(palier);
            if (rewardIcon != null) {
                JLabel iconLabel = new JLabel(new ImageIcon(rewardIcon.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
                casePanel.add(iconLabel, BorderLayout.CENTER);
            }

            add(casePanel);
        }
        revalidate(); // Redessine le panneau après la mise à jour des paliers
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void onLevelChange() {
        updatePaliers();
        repaint();
    }
}
