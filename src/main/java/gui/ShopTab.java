package gui;

import config.RessourcesPaths;
import config.ShopItem;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Cette classe représente un menu du magasin dans l'interface utilisateur graphique.
 * Chaque onglet affiche des articles de différentes catégories tels que des couteaux, des fonds d'écran, de la musique, etc.
 * Les utilisateurs peuvent ajouter des articles à leur panier à partir de cet onglet.
 */
public class ShopTab {

    private ShopMenu shopMenu;
    private JLabel argentLabel;



    /**
     * Constructeur de la classe ShopTab.
     *
     * @param tabbedPane Le JTabbedPane où l'onglet sera ajouté.
     * @param title Le titre de l'onglet.
     * @param imagePath Le chemin de l'image associée à l'onglet.
     * @param iconPath Le chemin de l'icône associée à l'onglet.
     * @param category La catégorie de l'onglet.
     * @param shopMenu L'instance de ShopMenu associée à cet onglet.
     */
    public ShopTab(JTabbedPane tabbedPane, String title, String imagePath, String iconPath, String category, ShopMenu shopMenu) {
        this.shopMenu = shopMenu;
        JPanel panel = createPanel(imagePath, category);
        tabbedPane.addTab(title, panel);
        if (iconPath != null) {
            ImageIcon icon = new ImageIcon(RessourcesPaths.tabPath + iconPath);
            tabbedPane.setIconAt(tabbedPane.indexOfComponent(panel), icon);
        }
        shopMenu.getGame().addMoneyListener(this::updateMoneyLabel);
    }

    /**
     * Crée le contenu du panneau principal de l'onglet en fonction de la catégorie spécifiée.
     *
     * @param path Le chemin de l'image ou du répertoire associé à la catégorie.
     * @param category La catégorie de l'onglet.
     * @return Le JPanel contenant le contenu principal de l'onglet.
     */
    JPanel createPanel(String path, String category) {
        BackgroundPanel tabPanel = new BackgroundPanel(RessourcesPaths.backgroundPath + "bgShop.gif");
        tabPanel.setLayout(new BorderLayout());

        addMoneyLabel(tabPanel);

        JPanel mainMenuPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        if (category.equals("background")){
            mainMenuPanel.setBorder(new EmptyBorder(300, 75, 300, 75));
        } else if (category.equals("cart")) {
            mainMenuPanel.setBorder(new EmptyBorder(100, 100, 100, 100));
        } else {
            mainMenuPanel.setBorder(new EmptyBorder(200, 200, 200, 200));
        }
        mainMenuPanel.setOpaque(false);

        switch (category) {
            case "couteaux" -> {
                addItemToPanel(mainMenuPanel, path + "knife#1.png", 0, "Sword 1");
                addItemToPanel(mainMenuPanel, path + "knife#2.png", 100, "Sword 2");
                addItemToPanel(mainMenuPanel, path + "knife#3.png", 200, "Sword 3");
            }
            case "background" -> {
                addItemToPanel(mainMenuPanel, path + "bgClassiqueCave.png", 100, "Background 1");
                addItemToPanel(mainMenuPanel, path + "bgClassiqueLake.png", 200, "Background 2");
                addItemToPanel(mainMenuPanel, path + "bgClassiqueForet.png", 300, "Background 3");
            }
            case "cart" -> {
                JPanel cartPanel = shopMenu.cart.displayCart(this.shopMenu, shopMenu.cart.getCart(), mainMenuPanel, "Panier");
            }
        }
        tabPanel.add(mainMenuPanel, BorderLayout.CENTER);
        return tabPanel;
    }


    /**
     * Ajoute un composant pour afficher le montant d'argent disponible dans le panneau de l'onglet ainsi que l'icône.
     *
     * @param tabPanel Le panneau d'arrière-plan de l'onglet.
     */
    private void addMoneyLabel(BackgroundPanel tabPanel) {
        JPanel argentPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 0, 0));
        argentPanel.setOpaque(false);

        argentLabel = new JLabel(String.valueOf(shopMenu.getGame().getArgent()));
        argentLabel.setHorizontalAlignment(SwingConstants.LEFT);
        argentLabel.setBorder(new EmptyBorder(10, 10, 10, 5));
        argentLabel.setForeground(Color.WHITE);
        argentLabel.setFont(new Font("Arial", Font.BOLD, 16));

        ImageIcon coinIcon = adapteImage(RessourcesPaths.iconPath + "coin.png", "Coin");

        JLabel coinLabel = new JLabel(coinIcon);

        argentPanel.add(argentLabel);
        argentPanel.add(coinLabel);
        tabPanel.add(argentPanel, BorderLayout.NORTH);
    }



    /**
     * Ajoute un article à un JPanel en tant qu'élément de liste avec un bouton associé pour l'ajouter au panier.
     *
     * @param panel Le JPanel où l'article sera ajouté.
     * @param imagePath Le chemin de l'image de l'article.
     * @param itemName Le nom de l'article.
     */
    private void addItemToPanel(JPanel panel, String imagePath, int articlePrice, String itemName) {
        // Création d'un panel arrondi pour l'article
        RoundedPanel itemRoundedPanel = new RoundedPanel(20, 20, true, false);
        itemRoundedPanel.setLayout(new BorderLayout());
        itemRoundedPanel.setOpaque(false);
        itemRoundedPanel.setBorder(null);
        itemRoundedPanel.setFocusable(false);

        ImageIcon icon = adapteImage(imagePath, itemName);

        RoundedButton itemButton = new RoundedButton("");
        itemButton.setIcon(icon);
        itemButton.setBorderPainted(false);
        itemButton.setContentAreaFilled(false);
        itemButton.setFocusPainted(false);
        itemButton.setBackground(new Color(255, 255, 255, 200));

        RoundedPanel itemCaption = makeItemCaption(articlePrice, itemName);

        itemRoundedPanel.add(itemButton);
        itemRoundedPanel.add(itemCaption, BorderLayout.SOUTH);

        panel.add(itemRoundedPanel);

        // MouseListener ajouté pour changer le curseur en main quand la souris survole
        itemButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                itemButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {itemButton.setCursor(Cursor.getDefaultCursor());
            }
        });

        // Configure le bouton pour ajouter l'article au panier lorsqu'il est cliqué
        shopMenu.configureButton(itemButton, e -> {
            shopMenu.cart.addArticle(new ShopItem(itemName, articlePrice, imagePath));
            refreshCartTab();
        });

    }

    /**
     * Adapte la taille de l'image en fonction de la catégorie de l'article pour l'affichage dans l'inventaire et dans la boutique du jeu.
     *
     * @param imagePath Le chemin de l'image de l'article.
     * @param itemName Le nom de l'article.
     * @return L'ImageIcon adapté à la taille spécifique de la catégorie de l'article.
     */
    static ImageIcon adapteImage(String imagePath, String itemName) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        ImageIcon icon = new ImageIcon(imagePath);
        if (itemName.contains("Background")){
            Image resizedImage = icon.getImage().getScaledInstance(screenWidth/5, screenHeight /5, Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImage);
        } else if (itemName.contains("Coin")){
            Image resizedImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImage);
        }
        else if (itemName.contains("Caption")){
            Image resizedImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImage);
        }
        else{
            Image resizedImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImage);
        }
        return icon;
    }




    /**
     * Crée le composant de légende pour un article avec son nom, son prix, et l'image de la pièce.
     *
     * @param articlePrice Le prix de l'article.
     * @param itemName Le nom de l'article.
     * @return Le JPanel contenant le composant de légende.
     */
    private static RoundedPanel makeItemCaption(int articlePrice, String itemName) {
        RoundedPanel itemCaption = new RoundedPanel(125,125, false, false);
        itemCaption.setLayout(new BorderLayout());

        JLabel itemNameLabel = new JLabel("           " + itemName);
        itemNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel itemPriceLabel = new JLabel(String.valueOf(articlePrice));
        itemPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon coinIcon = adapteImage(RessourcesPaths.iconPath + "coin.png", "Caption");
        JLabel coinLabel = new JLabel(coinIcon);
        coinLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        coinLabel.setOpaque(false);

        //JPanel englobant pour priceLabel et coinLabel
        JPanel pricePanel = new JPanel(new BorderLayout());
        pricePanel.add(itemPriceLabel, BorderLayout.CENTER);
        pricePanel.add(coinLabel, BorderLayout.EAST);
        pricePanel.setOpaque(false);

        itemCaption.setFont(itemCaption.getFont().deriveFont(Font.BOLD | Font.ITALIC, 14f));
        itemCaption.setBackground(new Color(255, 255, 255, 255));
        itemCaption.add(itemNameLabel, BorderLayout.CENTER);
        itemCaption.add(pricePanel, BorderLayout.EAST);

        return itemCaption;
    }

    /**
     * Actualise l'onglet du panier du magasin.
     */
    void refreshCartTab() {
        int cartTabIndex = shopMenu.tabbedPane.indexOfTab("Panier");
        if (cartTabIndex != -1) {
            JPanel cartPanel = createPanel(null, "cart");
            shopMenu.tabbedPane.setComponentAt(cartTabIndex, cartPanel);
        }
    }

    void updateMoneyLabel(){
        this.argentLabel.setText("Money: " + shopMenu.getGame().getArgent());
        this.argentLabel.repaint();
    }


}