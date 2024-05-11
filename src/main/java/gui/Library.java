package gui;

import config.Game;
import config.RessourcesPaths;
import config.ShopItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static gui.ShopTab.adapteImage;

/**
 * Cette classe représente une bibliothèque dans l'interface graphique du jeu.
 * Elle utilise un panneau avec une image de fond pour personnaliser son apparence.
 */
public class Library extends BackgroundPanel  implements LibraryObserver {
    private List<ShopItem> inventaire;
    private Game game;


    /**
     * Constructeur de la classe Library.
     *
     * @param game Le jeu associé à la bibliothèque.
     */
    public Library(Game game) {
        super(RessourcesPaths.backgroundPath + "bgHome.gif");
        this.game = game;
        this.inventaire = game.getInventaire();
        afficher();
    }


    @Override
    public void updateInventaire() {
        this.inventaire = game.getInventaire();
        afficher();
    }


    /**
     * Détermine le nombre de lignes ou de colonnes en fonction du nombre de contenu dans l'inventaire.
     *
     * @param pos La position spécifiée ("left", "right", ou "bottom").
     * @return Le nombre de lignes ou de colonnes correspondant au nombre de contenu.
     */
    public int getRowOrCol (String pos) {
        int res = 0;
        for (ShopItem item : inventaire) {
            if (pos.equals("left") && item.getArticleImagePath().contains("knife")){
                res++;
            } else if (pos.equals("right") && item.getArticleImagePath().contains("music")){
                res++;
            } else if (pos.equals("bottom") && item.getArticleImagePath().contains("background")){
                res++;
            }
        }
        return res;
    }




    /**
     * Affiche les éléments de la bibliothèque dans des panneaux distincts.
     * Chaque panneau contient des boutons représentant les articles de l'inventaire correspondants.
     * Les boutons sont configurés : quand on clique sur un objet, celui-ci apparait dans le jeu.
     * Un message de bienvenue est également affiché.
     */
    private void afficher() {
        setLayout(new BorderLayout());

        RoundedPanel leftPanel = new RoundedPanel(20, 20, false, true);
        leftPanel.setLayout(new GridLayout(getRowOrCol("left"), 1, 0, 10));
        JPanel middlePanel = new JPanel(new GridBagLayout());
        RoundedPanel rightPanel = new RoundedPanel(20, 20, false, true);
        rightPanel.setLayout(new GridLayout(getRowOrCol("right"), 1, 0, 10));
        RoundedPanel bottomPanel = new RoundedPanel(20, 20, false, true);
        bottomPanel.setLayout(new GridLayout(1, getRowOrCol("bottom"), 10, 0));


        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        middlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));


        for (ShopItem item : inventaire) {

            ImageIcon icon = adapteImage(item.getArticleImagePath(), item.getArticleName());

            JButton itemButton = new JButton(icon);
            itemButton.setOpaque(false); // Rend le bouton transparent
            itemButton.setContentAreaFilled(false); // Supprime le remplissage du bouton
            itemButton.setBorderPainted(false); // Supprime le cadre du bouton

            if (item.getArticleImagePath().contains("knife")) {
                leftPanel.add(itemButton);
            } else if (item.getArticleImagePath().contains("music")) {
                rightPanel.add(itemButton);
            } else if (item.getArticleImagePath().contains("background")) {
                bottomPanel.add(itemButton);
            }


            itemButton.addActionListener(e -> {
                if (item.getArticleImagePath().contains("music")) {
                    // TODO : Changer de musique
                } else {
                    MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
                    if (item.getArticleImagePath().contains("background")){
                        mainFrame.getGameView().updateBackgroundImage(item.getArticleImagePath());
                    } else {
                        mainFrame.getGameView().updateKnifeImage(item.getArticleImagePath());
                    }
                }

            });


            itemButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    itemButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    itemButton.setCursor(Cursor.getDefaultCursor());
                }
            });

        }

        JLabel welcomeLabel = new JLabel(" へようこそ Hitty Knife - Redux");
        JLabel usernameLabel = new JLabel("先生 " + game.getNomUtilisateur());

        welcomeLabel.setFont(new Font("Old English Text MT", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(245,13,13));

        usernameLabel.setFont(new Font("Old English Text MT", Font.BOLD, 24));
        usernameLabel.setForeground(new Color(245,13,13));

        JPanel labelPanel = new JPanel();
        labelPanel.add(welcomeLabel);
        labelPanel.add(usernameLabel);
        labelPanel.setOpaque(false);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        middlePanel.add(welcomeLabel, gbc);

        gbc.gridy = 1;
        middlePanel.add(usernameLabel, gbc);



        leftPanel.setOpaque(false);
        middlePanel.setOpaque(false);
        rightPanel.setOpaque(false);
        bottomPanel.setOpaque(false);


        this.add(leftPanel, BorderLayout.WEST);
        this.add(middlePanel, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(bottomPanel, BorderLayout.SOUTH);


    }

}



