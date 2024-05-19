package gui;

import config.RessourcesPaths;
import entity.*;
import entity.bosses.*;

import java.awt.*;
import config.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.List;

import java.util.HashMap;
import java.util.Map;



/**
 * La classe KnifeDisplay représente le panneau graphique où le couteau et les cibles sont affichés.
 * Elle étend JPanel pour permettre l'affichage des éléments graphiques du jeu Hitty Knife.
 */
public class EntityDisplay extends JPanel {
    private final Knife knife;
    private final Knife knife2;
    private Image knifeImage;
    private Image knifeImagePowered;
    private Image cibleImage;
    private Image backgroundImage;
    private Image bonusPower;
    private Image bonusGold;
    private Image bonusXP;
    private Image bonusGel;
    private Image ciblesMouventeImage;
    private Image explosionIcon;
    boolean explose = false;

    private Image targetTNT;
    private Image bossT1;
    private Image bossT2;
    private Image bossT3;
    private Image bossT4;
    private Image sign;
    private List<Cible> listeCible;
    private static double bgImgWidth;
    private static double bgImgHeight;
    private final double RATIO_X;
    private final double RATIO_Y;
    private final int RATIO = 18;
    private final int RATIO1v1;

    private int collisionX;
    private int collisionY;
    private double cibleColliX;
    private double cibleColliY;
    private double collisionAngle;
    private boolean animCollision = false;
    private boolean isCollisionMovingTarget= false;
    private Bonus.TypeBonus currentAnimBonusType;
    final float baseOpacity = 1f;
    float opacity;
    Dimension screenSize;
    private final Game game;

    private boolean isSolo;
    private boolean isWin=false;
    private boolean currentKnife = false; //false : gauche , true : droite

    //private ArrayList<Cible> deleteCible;
    


    /**
     * Constructeur de la classe KnifeDisplay.
     * @param knife Le couteau à afficher dans le panneau.
     * @param backgroundPath Le chemin d'accès à l'image de fond du panneau.
     * @param listeCible La liste des cibles à afficher dans le panneau.
     */
    public EntityDisplay(Knife knife, String backgroundPath, ArrayList<Cible> listeCible,boolean isSolo, Game game) {
        //System.out.println("bg x : "+RATIO_X+" bg y : "+RATIO_Y);
        this.listeCible = listeCible;
        this.knife = knife;

        this.knife2 = game.knife3;
        initImage();
        initBg(backgroundPath);
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //RATIO1v1 = (isSolo)?1:2;
        RATIO1v1 = 1;

        RATIO_X = screenSize.width/2;
        //if (isSolo) RATIO_X = screenSize.width/2;
        //else RATIO_X = screenSize.width/4;//getBgImgWidth()/2;

        RATIO_Y = (double) (screenSize.height * 3) /4;//getBgImgHeight()*3/4;

        this.game = game;
        this.isSolo = isSolo;
    }

    /**
     * Renvoie la largeur de l'image de fond du panneau.
     *
     * @return La largeur de l'image de fond.
     */
    public static double getBgImgWidth() {
        return bgImgWidth;
    }

    /**
     * Renvoie la hauteur de l'image de fond du panneau.
     *
     * @return La hauteur de l'image de fond.
     */
    public static double getBgImgHeight() {
        return bgImgHeight;
    }

    /**
     * Initialise les images des couteaux et des cibles en chargeant les images correspondantes depuis les chemins spécifiés.
     * Redimensionne également les images pour les adapter à la taille souhaitée.
     */
    private void initImage () {
        //this.explosionImage = new ImageIcon("src/main/ressources/effets/explosion.gif").getImage();
        //this.explosionIcon = new ImageIcon("src/main/ressources/effets/explosion.gif");
        this.explosionIcon = new ImageIcon("src/main/ressources/effets/explosion4.png").getImage();

        this.knifeImage = new ImageIcon(RessourcesPaths.knifePath + "knifeRotate1.png").getImage();
        this.knifeImagePowered = new ImageIcon(RessourcesPaths.knifePath + "knifePowered.png").getImage();
        this.cibleImage = new ImageIcon(RessourcesPaths.targetPath + "target#1.png").getImage();
        this.ciblesMouventeImage =  new ImageIcon(RessourcesPaths.targetPath + "target#2.png").getImage();
        this.bonusPower = new ImageIcon(RessourcesPaths.targetPath + "target#1Power.png").getImage();
        this.bonusGold = new ImageIcon(RessourcesPaths.targetPath + "targetCoin.png").getImage();
        this.bonusXP = new ImageIcon(RessourcesPaths.targetPath + "targetXP.png").getImage();
        this.bonusGel = new ImageIcon(RessourcesPaths.targetPath + "targetGel.png").getImage();
        this.targetTNT = new ImageIcon(RessourcesPaths.targetPath + "targetTNT.png").getImage();
        this.bossT1 = new ImageIcon(RessourcesPaths.targetPath + "BossType1.png").getImage();
        this.bossT2 = new ImageIcon(RessourcesPaths.targetPath + "BossType2.png").getImage();
        this.bossT3 = new ImageIcon(RessourcesPaths.targetPath + "BossType3.png").getImage();
        this.bossT4 = new ImageIcon(RessourcesPaths.targetPath + "BossType4.png").getImage();
        this.sign = new ImageIcon("src/main/ressources/button/sign.png").getImage();
        int w = this.knifeImage.getWidth(null)/3;
        int h = this.knifeImage.getHeight(null)/3;
        this.knifeImage = this.knifeImage.getScaledInstance(w,h,Image.SCALE_SMOOTH);
        this.knifeImagePowered = this.knifeImagePowered.getScaledInstance(w,h,Image.SCALE_SMOOTH);
        this.cibleImage = this.cibleImage.getScaledInstance(this.cibleImage.getWidth(null)/2,this.cibleImage.getHeight(null)/2,Image.SCALE_SMOOTH);
        this.bonusPower = this.bonusPower.getScaledInstance(this.bonusPower.getWidth(null)/2,this.bonusPower.getHeight(null)/2,Image.SCALE_SMOOTH);
        this.bonusGold = this.bonusGold.getScaledInstance(this.bonusGold.getWidth(null)/2,this.bonusGold.getHeight(null)/2,Image.SCALE_SMOOTH);
        this.bonusXP = this.bonusXP.getScaledInstance(this.bonusXP.getWidth(null)/2,this.bonusXP.getHeight(null)/2,Image.SCALE_SMOOTH);
        this.bonusGel = this.bonusGel.getScaledInstance(this.bonusGel.getWidth(null)/2,this.bonusGel.getHeight(null)/2,Image.SCALE_SMOOTH);
        this.targetTNT = this.targetTNT.getScaledInstance(this.targetTNT.getWidth(null)/2,this.targetTNT.getHeight(null)/2,Image.SCALE_SMOOTH);
        this.ciblesMouventeImage = this.ciblesMouventeImage.getScaledInstance(this.ciblesMouventeImage.getWidth(null)/2,this.ciblesMouventeImage.getHeight(null)/2,Image.SCALE_SMOOTH);
        this.explosionIcon = this.explosionIcon.getScaledInstance(this.explosionIcon.getWidth(null)/2,this.explosionIcon.getHeight(null)/2,Image.SCALE_SMOOTH);
        this.bossT1 = this.bossT1.getScaledInstance(this.bossT1.getWidth(null)/2, this.bossT1.getHeight(null)/2, Image.SCALE_SMOOTH);
        this.bossT2 = this.bossT2.getScaledInstance(this.bossT2.getWidth(null)/2, this.bossT2.getHeight(null)/2, Image.SCALE_SMOOTH);
        this.bossT3 = this.bossT3.getScaledInstance(this.bossT3.getWidth(null)/2, this.bossT3.getHeight(null)/2, Image.SCALE_SMOOTH);
        this.bossT4 = this.bossT4.getScaledInstance(this.bossT4.getWidth(null)/2, this.bossT4.getHeight(null)/2, Image.SCALE_SMOOTH);
        this.sign = this.sign.getScaledInstance(this.sign.getWidth(null)/2, this.sign.getHeight(null)/2, Image.SCALE_SMOOTH);
    }

    /**
     * Initialise l'image de fond en chargeant l'image à partir du chemin spécifié.
     *
     * @param backgroundPath Le chemin de l'image de fond à charger.
     */
    void initBg(String backgroundPath) {
        this.backgroundImage = new ImageIcon(backgroundPath).getImage();
        bgImgHeight = this.backgroundImage.getHeight(null);
        bgImgWidth = this.backgroundImage.getWidth(null);
    }
    /**
     * Cette méthode déclenche une explosion à une position donnée (x, y), supprimant les cibles
     * se trouvant dans la zone d'explosion et ajoutant des points d'expérience et d'argent au jeu.
     *
     * @param x La coordonnée x de la position de l'explosion.
     * @param y La coordonnée y de la position de l'explosion.
     * @param deleteCible La liste des cibles à supprimer suite à l'explosion.
     */
    private void explosion(double x, double y,ArrayList<Cible> deleteCible, Cible self) {
        for (Cible cible : listeCible) {
            if (cible != self) {
                double cibleX = (RATIO_X - cible.getX() * RATIO);
                double cibleY = (RATIO_Y - cible.getY() * RATIO);
                int cw = 250;
                int ch = 250;
                if (x > cibleX - cw && x < cibleX + cw && y > cibleY - ch && y < cibleY + ch) {
                    deleteCible.add(cible);
                    if (isSolo) {
                        game.addXP(10);
                        game.addArgent(10);
                        game.sauvegarderEtat();
                    } else {
                        if (currentKnife) {
                            game.scoreJoueur2++;
                        } else game.scoreJoueur1++;
                    }
                }
            }
        }
    }



    /**
     * Met à jour l'image du couteau suite au choix du joueur dans l'inventaire.
     *
     * @param knifePathClicked Le chemin d'accès vers l'image du couteau.
     */
    public void updateKnifeImage(String knifePathClicked) {
        knifePathClicked = verifImage(knifePathClicked);
        this.knifeImage = new ImageIcon(knifePathClicked).getImage();
        int w = this.knifeImage.getWidth(null)/3;
        int h = this.knifeImage.getHeight(null)/3;
        this.knifeImage = this.knifeImage.getScaledInstance(w,h,Image.SCALE_SMOOTH);
        repaint();
    }


    /**
     * Vérifie et met à jour le chemin d'accès à l'image du couteau.
     *
     * @param knifePathClicked Le chemin d'accès à l'image du couteau.
     * @return Le chemin d'accès mis à jour à l'image du couteau.
     */
    private String verifImage(String knifePathClicked) {
        String abr = RessourcesPaths.knifePath;

        // Create the map with the possible transformations
        Map<String, String> pathMap = new HashMap<>();
        pathMap.put(abr + "knife#1.png", abr + "knifeRotate1.png");
        pathMap.put(abr + "knife#2.png", abr + "knifeRotate2.png");
        pathMap.put(abr + "knife#3.png", abr + "knifeRotate3.png");
        pathMap.put(abr + "knife#4.png", abr + "knifeRotate4.png");
        pathMap.put(abr + "knife#5.png", abr + "knifeRotate5.png");

        // Return the transformed path if it exists, otherwise return the original path
        return pathMap.getOrDefault(knifePathClicked, knifePathClicked);
    }



    /**
     * Crée et retourne un masque de collision à partir de l'image spécifiée.
     * @param image L'image à partir de laquelle créer le masque de collision.
     * @return Un objet de type Shape représentant le masque de collision créé.
     */
    public Shape createCollisionMask(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        if (width <= 0 || height <= 0) {
            return null;
        }
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
    
        Area mask = new Area();
        int lastY = -1; // Dernière ligne où un rectangle a été démarré
    
        // Utilise un tableau pour mémoriser le début des rectangles pour chaque colonne
        int[] startX = new int[width];
        boolean[] active = new boolean[width]; // Si un rectangle est actif dans la colonne
    
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean isPixelVisible = (bufferedImage.getRGB(x, y) & 0xFF000000) != 0x00000000;
    
                if (isPixelVisible) {
                    if (!active[x]) {
                        startX[x] = y; // Démarre un nouveau rectangle
                        active[x] = true;
                    }
                } else if (active[x]) {
                    // Fin d'un rectangle, l'ajouter au masque
                    mask.add(new Area(new Rectangle2D.Float(x, startX[x], 1, y - startX[x])));
                    active[x] = false;
                }
            }
        }
    
        // Ferme les rectangles ouverts à la fin de l'image
        for (int x = 0; x < width; x++) {
            if (active[x]) {
                mask.add(new Area(new Rectangle2D.Float(x, startX[x], 1, height - startX[x])));
            }
        }
    
        return mask;
    }

    /**
     * Cette méthode anime une collision entre un couteau et une cible.
     * Si une animation de collision est en cours, elle dessine le couteau et la cible
     * en fonction des paramètres fournis, avec une opacité réglée selon l'état actuel
     * de l'animation.
     *
     * @param g2d Le contexte graphique dans lequel dessiner l'animation de collision.
     * @param knifeImgWidth La largeur de l'image du couteau.
     * @param knifeImgHeight La hauteur de l'image du couteau.
     * @param cibleImWidth La largeur de l'image de la cible.
     * @param cibleImHeight La hauteur de l'image de la cible.
     */
    public void animationCollision(Graphics2D g2d, double knifeImgWidth, double knifeImgHeight, double cibleImWidth, double cibleImHeight){
        if (animCollision){
            Composite oldComposite = g2d.getComposite();
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);

            g2d.setComposite(alphaComposite);
            if (isSolo){
                if (!game.powered[0]) {
                    AffineTransform transformColli = AffineTransform.getTranslateInstance(collisionX - (double) knifeImgWidth / 2, collisionY - (double) knifeImgHeight / 2);
                    transformColli.rotate(Math.toRadians(collisionAngle), (double) knifeImgWidth / 2, (double) knifeImgHeight / 2);
                    g2d.drawImage(knifeImage, transformColli, this);
                }
            }
            else {
                if (!currentKnife) {
                    if (!game.powered[1]) {
                        AffineTransform transformColli = AffineTransform.getTranslateInstance(collisionX - (double) knifeImgWidth / 2, collisionY - (double) knifeImgHeight / 2);
                        transformColli.rotate(Math.toRadians(collisionAngle), (double) knifeImgWidth / 2, (double) knifeImgHeight / 2);
                        g2d.drawImage(knifeImage, transformColli, this);
                    }
                }
                else{
                    if (!game.powered[2]) {
                        AffineTransform transformColli = AffineTransform.getTranslateInstance(collisionX - (double) knifeImgWidth / 2, collisionY - (double) knifeImgHeight / 2);
                        transformColli.rotate(Math.toRadians(collisionAngle), (double) knifeImgWidth / 2, (double) knifeImgHeight / 2);
                        g2d.drawImage(knifeImage, transformColli, this);
                    }
                }
            }
            AffineTransform transformCibleColli;
            //AffineTransform transformCibleColli = AffineTransform.getTranslateInstance(cibleColliX - (double) cibleImWidth / 2, cibleColliY - (double) cibleImHeight / 2);
            if (explose){
                double exploseWidth = explosionIcon.getWidth(this);
                double exploseHeight = explosionIcon.getHeight(this);
                transformCibleColli = AffineTransform.getTranslateInstance(cibleColliX - exploseWidth / 2, cibleColliY - exploseHeight / 2);
                g2d.drawImage(explosionIcon, transformCibleColli, this);}
            else {
                transformCibleColli = AffineTransform.getTranslateInstance(cibleColliX - cibleImWidth / 2, cibleColliY - cibleImHeight / 2);
                if (isCollisionMovingTarget) g2d.drawImage(ciblesMouventeImage, transformCibleColli, this);
                else {
                    if (currentAnimBonusType!=null){
                        switch (currentAnimBonusType){
                            case BONUS_POWER : g2d.drawImage(bonusPower, transformCibleColli, this);break;
                            case BONUS_TNT : g2d.drawImage(targetTNT, transformCibleColli, this);break;
                            case BONUS_GOLD : g2d.drawImage(bonusGold, transformCibleColli, this);break;
                            case BONUS_XP : g2d.drawImage(bonusXP, transformCibleColli, this);break;
                            case BONUS_GEL: g2d.drawImage(bonusGel, transformCibleColli, this);break;
                        }
                    }
                    else g2d.drawImage(cibleImage, transformCibleColli, this);
                }}

            g2d.setComposite(oldComposite);
            if (opacity>0.01f){
                if (explose)opacity-=0.002f;
                else opacity-=0.007f;
            }
            else {animCollision=false;explose = false;}
        }
    }

    public void collision(ArrayList<Cible> deleteCible,Cible cible,double cibleX,double cibleY,int player){
        if (!explose) {
            cibleColliX = cibleX;
            cibleColliY = cibleY;
            opacity = baseOpacity;
        }
        if (!(cible instanceof Boss)){animCollision = true;currentAnimBonusType=null;}
        if (isSolo) {
            game.addXP(10);
            game.addArgent(10);
            game.sauvegarderEtat();
        }
        if (cible instanceof BossType1) {
            ((BossType1) cible).attacked();
            if (((BossType1) cible).isDead()) {
                deleteCible.add(cible);
            }
        }
        else if (cible instanceof BossType2) {
            ((BossType2) cible).attacked();
            if (((BossType2) cible).isDead()) {
                deleteCible.add(cible);
            }
        }
        else if (cible instanceof BossType3) {
            ((BossType3) cible).attacked();
            if (((BossType3) cible).isDead()) {
                deleteCible.add(cible);
            }
        }
        else if (cible instanceof BossType4) {
            ((BossType4) cible).attacked();
            if (((BossType4) cible).isDead()) {
                deleteCible.add(cible);
            }
        }
        else {
            deleteCible.add(cible);
        }
        isCollisionMovingTarget=cible instanceof MovingTarget;
        if (cible instanceof Bonus){
            currentAnimBonusType = ((Bonus) cible).getTypeBonus();
            game.bonusManager.appliquerBonus(((Bonus) cible).getTypeBonus(),player);
            if (((Bonus) cible).getTypeBonus()== Bonus.TypeBonus.BONUS_TNT){
                explose = true;
                explosion(cibleX,cibleY,deleteCible,cible);
            }
        }

    }


    /**
     * Redessine le composant en dessinant l'image de fond, les couteaux et les cibles.
     * Effectue également la gestion des collisions entre les couteaux et les cibles.
     *
     * @param g L'objet Graphics utilisé pour dessiner les composants.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        //System.out.println(game.getIsSolo());

        //-------------------------- DESSINE LES NIVEAUX ET LES ROUNDS ----------------------------------------

        if(game.getIsSolo()){
            // Affichage des niveaux et des rounds avec effet d'ombre sur le texte
            g2d.setFont(new Font("SansSerif", Font.BOLD, 24)); 

            // Texte principal pour le niveau
            String niveauTexte = "LEVEL : " + game.getCurrentLevel();
            int niveauTexteWidth = g2d.getFontMetrics().stringWidth(niveauTexte);

            int xPosition = (getWidth() - niveauTexteWidth) / 2;
            int yPosi = 30;

            // Dessine l'ombre
            g2d.setColor(Color.darkGray);
            int shadowOffset = 2; 
            g2d.drawString(niveauTexte, xPosition + shadowOffset, yPosi + shadowOffset);

            // Dessine le texte principal
            g2d.setColor(Color.WHITE); // Couleur du texte
            g2d.drawString(niveauTexte, xPosition, yPosi);

            // Affiche les cercles pour les rounds
            int totalRounds = game.getRoundManagement().getListeRounds().size();
            int currentRoundIndex = game.getRoundManagement().getCurrentRoundIndex(); 

            int circleDiameter = 20; // Diamètre de chaque cercle
            int spacing = 28; // Espacement entre les cercles
            int startX = (getWidth() - (totalRounds * spacing + (totalRounds - 1) * circleDiameter)) / 2; // Position de départ X pour centrer les cercles
            int yPosition = 50; // Position Y des cercles 

            // Dessine une barre semi-transparente avec des bords arrondis en arrière-plan des cercles
            int barHeight = 30; // Hauteur de la barre de fond
            int arcWidth = 25; // Largeur de l'arc pour les coins arrondis
            int arcHeight = 25; // Hauteur de l'arc pour les coins arrondis
            g2d.setColor(new Color(192, 192, 192, 80));
            g2d.fillRoundRect(startX - 10, yPosition - (barHeight / 2) + (circleDiameter / 2), (totalRounds * (circleDiameter + spacing)) - spacing + 20, barHeight, arcWidth, arcHeight);


            for (int i = 0; i < totalRounds; i++) {
                if (i == totalRounds - 1 && currentRoundIndex == totalRounds - 1) {
                    g2d.setColor(Color.RED); 
                } else if (i <= currentRoundIndex) {
                    g2d.setColor(Color.WHITE); 
                } else {
                    g2d.setColor(Color.black); 
                }
                // Dessine le cercle
                g2d.fillOval(startX + i * (circleDiameter + spacing), yPosition, circleDiameter, circleDiameter);
            }

            // Configuration et dessin des croix des vies
            int lineThickness = 5;
            int totalLives = 4;
            int livesSpacing = 40;
            int livesXPosition = startX + ((totalRounds * (circleDiameter + spacing) - (totalLives * livesSpacing + (totalLives - 1) * 10)) / 2) + 42;
            int livesYPosition = yPosition + circleDiameter + 30;

            for (int i = 0; i < totalLives - 1; i++) {
                g2d.setColor(i < totalLives - game.getVies() ? Color.RED : Color.darkGray);
                g2d.setStroke(new BasicStroke(lineThickness));
                g2d.drawLine(livesXPosition + i * livesSpacing - 10, livesYPosition - 10, livesXPosition + i * livesSpacing + 10, livesYPosition + 10);
                g2d.drawLine(livesXPosition + i * livesSpacing + 10, livesYPosition - 10, livesXPosition + i * livesSpacing - 10, livesYPosition + 10);
            }


            // Si c'est le dernier round, affiche "Boss Fight!"
            if (currentRoundIndex == totalRounds - 1) {
                String bossFightText = "Boss Fight!";
                int textWidth = g2d.getFontMetrics().stringWidth(bossFightText);
                int textYPosition = livesYPosition + 40;

                // Dessine l'ombre pour "Boss Fight!" en noir
                g2d.setColor(Color.BLACK);
                g2d.drawString(bossFightText, (getWidth() - textWidth) / 2 + shadowOffset, textYPosition + shadowOffset);

                // Dessine le texte principal pour "Boss Fight!" en rouge
                g2d.setColor(Color.RED);
                g2d.drawString(bossFightText, (getWidth() - textWidth) / 2, textYPosition);
            }
        }

        //-------------------------- DESSINE LE SCORE EN VERSUS ----------------------------------------

        else{
            g2d.setFont(new Font("SansSerif", Font.BOLD, 50));



            // Texte principal pour le niveau
            String scoreTexte1 = "SCORE  "+game.scoreJoueur1+"/"+game.MAX_SCORE;
            String scoreTexte2 = "SCORE  "+game.scoreJoueur2+"/"+game.MAX_SCORE;

            int niveauTexteWidth1 = g2d.getFontMetrics().stringWidth(scoreTexte1);
            int niveauTexteWidth2 = g2d.getFontMetrics().stringWidth(scoreTexte1);

            int xPosition1 = (getWidth() - niveauTexteWidth1) / 5;
            int xPosition2 = (4*(getWidth() - niveauTexteWidth2)) / 5;
            int yPosi = 100;
            //AffineTransform transformSign = AffineTransform.getTranslateInstance(xPosition1 - (double) sign.getWidth(this) / 2, yPosi - (double) sign.getHeight(this) / 2);
            AffineTransform transformSign = AffineTransform.getTranslateInstance(xPosition1 -55 , yPosi - (double) sign.getHeight(this) /2-12);
            g2d.drawImage(sign,transformSign,this);

            AffineTransform transformSign2 = AffineTransform.getTranslateInstance(xPosition2 -55 , yPosi - (double) sign.getHeight(this) /2-12);
            g2d.drawImage(sign,transformSign2,this);
            // Dessine l'ombre
            g2d.setColor(new Color(0, 0, 0, 64));
            int shadowOffset = 2;
            g2d.drawString(scoreTexte1, xPosition1 + shadowOffset, yPosi + shadowOffset);
            g2d.drawString(scoreTexte2, xPosition2 + shadowOffset, yPosi + shadowOffset);

            // Dessine le texte principal
            g2d.setColor(Color.BLACK); // Couleur du texte
            g2d.drawString(scoreTexte1, xPosition1, yPosi);
            g2d.drawString(scoreTexte2, xPosition2, yPosi);

            if ((game.scoreJoueur1>=game.MAX_SCORE || game.scoreJoueur2>=game.MAX_SCORE ) && !this.isWin){
                this.isWin = true;

                String backgroundImagePath = RessourcesPaths.buttonPath + "signB.png";

                // Création d'un JDialog indépendant
                JDialog dialog = new JDialog((Frame) null, "VICTORY", true);
                dialog.setSize(300, 150);
                dialog.setLayout(new BorderLayout());

                // Création du BackgroundPanel avec l'image de fond
                BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImagePath);
                backgroundPanel.setLayout(new BorderLayout());
                dialog.add(backgroundPanel);

                // Création d'un JPanel pour le centre avec un message
                JPanel centerPanel = new JPanel();
                centerPanel.setOpaque(false);  // Rendre transparent pour voir l'image de fond
                centerPanel.setLayout(new GridBagLayout());  // Utilisation de GridBagLayout pour centrer le label

                String joueur = (game.scoreJoueur1>=game.MAX_SCORE)?"player 1":"player 2";
                JLabel messageLabel = new JLabel(joueur+" have win");
                messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
                centerPanel.add(messageLabel, new GridBagConstraints());
                backgroundPanel.add(centerPanel, BorderLayout.CENTER);

                // Création d'un JPanel pour le bas avec un bouton
                JPanel bottomPanel = new JPanel();
                bottomPanel.setOpaque(false);  // Rendre transparent pour voir l'image de fond
                JButton closeButton = new JButton("Restart");
                closeButton.setFont(new Font("Arial", Font.BOLD, 20));
                bottomPanel.add(closeButton);
                backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

                // Ajout d'un ActionListener au bouton de fermeture
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        game.resetScore();  // Remplacez `game.resetScore()` par l'appel de méthode approprié
                        isWin = false;
                        dialog.dispose();
                    }
                });

                // Centrage du JDialog à l'écran
                dialog.setLocationRelativeTo(null);

                // Rendre le JDialog visible
                dialog.setVisible(true);

            }


        }
         
        //--------------------------------------DESSINE YOU LOSE QUAND ON PERD------------------------------------------------------

        if (game.gameOver) {
            g2d.setFont(new Font("Arial", Font.BOLD, 100));
            g2d.setColor(new Color(255, 0, 0, (int) (255 * game.gameOverOpacity)));  
            String gameOverText = "YOU LOSE!";
            int textWidth = g2d.getFontMetrics().stringWidth(gameOverText);
            int x = (getWidth() - textWidth) / 2;
            int y = getHeight() / 2;
            g2d.drawString(gameOverText, x, y);
        
            g2d.setFont(new Font("Arial", Font.BOLD, 50)); 
            String japaneseText = "ヨウ lオセ";
            textWidth = g2d.getFontMetrics().stringWidth(japaneseText);
            x = (getWidth() - textWidth) / 2;
            y += g2d.getFontMetrics().getHeight();
            g2d.drawString(japaneseText, x, y);
        }
        
        //--------------------------------------------------------------------------------------------------------------------------

        int knifeX = (int) (RATIO_X-(knife.getX()*RATIO));
        int knifeY = (int) (RATIO_Y-(knife.getY()*RATIO));

        if (knifeX>screenSize.width/RATIO1v1 || knifeX<0 || knifeY > screenSize.height || knifeY<0){
            knife.resetKnife();
            if(isSolo){
                if(!game.powered[0]){
                    game.perdreVie();
                }
            }
        }

        int knife2X = (int) (RATIO_X-(knife2.getX()*RATIO));
        int knife2Y = (int) (RATIO_Y-(knife2.getY()*RATIO));

        if (!isSolo) {
            if (knife2X > screenSize.width || knife2X < 0 || knife2Y > screenSize.height || knife2Y < 0) {
                knife2.resetKnife();
            }
        }


        int knifeImgWidth = knifeImage.getWidth(this);
        int knifeImgHeight = knifeImage.getHeight(this);
        int cibleImWidth = cibleImage.getWidth(this);
        int cibleImHeight = cibleImage.getHeight(this);
        int bossImgWidth = bossT1.getWidth(this);
        int bossImgHeight = bossT1.getHeight(this);


        AffineTransform transform = AffineTransform.getTranslateInstance(knifeX - (double) knifeImgWidth / 2, knifeY - (double) knifeImgHeight / 2);
        transform.rotate(Math.toRadians(knife.getAngle()), (double) knifeImgWidth / 2, (double) knifeImgHeight / 2);

        AffineTransform transform2 = AffineTransform.getTranslateInstance(knife2X - (double) knifeImgWidth / 2, knife2Y - (double) knifeImgHeight / 2);
        transform2.rotate(Math.toRadians(knife2.getAngle()), (double) knifeImgWidth / 2, (double) knifeImgHeight / 2);
        if (isSolo){
            if (!game.powered[0]) {
                g2d.drawImage(knifeImage, transform, this);
            }
            else{
                g2d.drawImage(knifeImagePowered, transform, this);
            }
        }
        else {
            if (!game.powered[1]) {
                g2d.drawImage(knifeImage, transform, this);
            } else {
                g2d.drawImage(knifeImagePowered, transform, this);
            }
            if (!game.powered[2]) {
                g2d.drawImage(knifeImage, transform2, this);
            }
            else{
                g2d.drawImage(knifeImagePowered, transform2, this);
            }
        }

        //--------------------------TRAJECTOIRE SOUS FORME DE ROND DU COUTEAU ----------------------------------------
       
        //Vérife si le couteau est en l'air
        if(knife.isInTheAir){
            // Calcul de la pointe du couteau
            double angleInRadians = Math.toRadians(knife.getAngle() + 180);
            int tipX = knifeX + (int) (knifeImgHeight / 2 * Math.cos(angleInRadians));
            int tipY = knifeY + (int) (knifeImgHeight / 2 * Math.sin(angleInRadians));

            // Longueur de la ligne de trajectoire
            double lineLength = 280; // 2,8 cm en pixels 

            // Nombre de cercles
            int numCircles = 11;
            double stepSize = (lineLength / numCircles) * 0.7; //taille des espacements
            double radius = 4.5; // Rayon initial des cercles

            for (int i = 0; i < numCircles; i++) {
                int circleX = tipX + (int) (i * stepSize * Math.cos(angleInRadians));
                int circleY = tipY + (int) (i * stepSize * Math.sin(angleInRadians));
                double currentRadius = radius * Math.pow(0.90, i); // Formule exponentielle pour diminuer le rayon
                int opacity = (int) (255 * (1 - (double) i / numCircles)); // Opacité qui diminue
                g2d.setColor(new Color(255, 255, 255, opacity)); 
                g2d.fillOval(circleX - (int) currentRadius, circleY - (int) currentRadius, (int) (2 * currentRadius), (int) (2 * currentRadius));
            }
        }

        //--------------------------AFFICHAGE ANIMATION COLLISION -------------------------------

        animationCollision(g2d,knifeImgWidth,knifeImgHeight,cibleImWidth,cibleImHeight);
        
        //-------------------------------AFFICHAGE NORMAL DES CIBLES + BARRE DE VIE DES BOSS -------------------------------

        ArrayList<Cible> deleteCible= new ArrayList<>();
        Shape knifeMask = createCollisionMask(knifeImage);
        Shape transformedKnifeMask = transform.createTransformedShape(knifeMask);
        Shape knifeMask2 = createCollisionMask(knifeImage);
        Shape transformedKnifeMask2 = transform2.createTransformedShape(knifeMask2);
        for (Cible cible : listeCible){
            boolean collision = false;
            double cibleX = (RATIO_X-cible.getX()*RATIO);
            double cibleY = (RATIO_Y-cible.getY()*RATIO);
            AffineTransform transformCible;
            AffineTransform transformBoss;

            if (cible instanceof Boss boss) {
                int health = 0;
                transformBoss = AffineTransform.getTranslateInstance(cibleX - (double) bossImgWidth / 2, cibleY - (double) bossImgHeight / 2);
                if(cible instanceof BossType1){
                    g2d.drawImage(bossT1, transformBoss, this);
                    health = ((BossType1) boss).getHitCount();
                }
                else if (cible instanceof BossType2) {
                    g2d.drawImage(bossT2, transformBoss, this);
                    health = ((BossType2) boss).getHitCount();
                }
                else if (cible instanceof BossType3) {
                    g2d.drawImage(bossT3, transformBoss, this);
                    health = ((BossType3) boss).getHitCount();
                }
                else if (cible instanceof BossType4) {
                    g2d.drawImage(bossT4, transformBoss, this);
                    health = ((BossType4) boss).getHitCount();
                }
            
            
                // Configuration de la barre de vie divisée en trois compartiments
                int totalHealthSections = 3; // Nombre total de sections 
                int hitsReceived = health; // Le nombre de coups reçus
                int healthBarWidth = bossT1.getWidth(this);
                int healthBarHeight = 10; // Hauteur de la barre de santé
                int sectionWidth = healthBarWidth / totalHealthSections; // Largeur de chaque section de la barre de santé

                // Positionnement de la barre de santé au-dessus de l'image du boss
                int healthBarX = (int) (cibleX - (double) healthBarWidth / 2);
                int healthBarY = (int) (cibleY - bossT1.getHeight(this) / 2 - healthBarHeight - 5); // 5 pixels au-dessus de l'image du boss

                // Dessine chaque section de la barre de santé
                for (int i = 0; i < totalHealthSections; i++) {
                    int indexFromRight = totalHealthSections - 1 - i;
                    if (indexFromRight < hitsReceived) { // Colorie en rouge les sections en commençant par la droite
                        g2d.setColor(Color.RED);
                    } else {
                        g2d.setColor(Color.GREEN);
                    }
                    g2d.fillRect(healthBarX + i * sectionWidth, healthBarY, sectionWidth, healthBarHeight);
                }
            
                Shape bossMask = createCollisionMask(bossT1);
                Shape transformedBossMask = transformBoss.createTransformedShape(bossMask);
                //g2d.setColor(Color.RED);
                //g2d.draw(transformedBossMask);
                if (isSolo){
                    if (transformedBossMask.intersects(transformedKnifeMask.getBounds2D())) {
                        if (!game.powered[0]) {
                            collisionAngle = knife.getAngle();
                            collisionX = knifeX;
                            collisionY = knifeY;
                            collision(deleteCible, cible, cibleX, cibleY, 0);
                            knife.resetKnife();
                        }
                        else collision(deleteCible, cible, cibleX, cibleY, 0);
                    }
                }
                else {
                    if (transformedBossMask.intersects(transformedKnifeMask.getBounds2D())) {
                        currentKnife = false;
                        if (!game.powered[1]) {
                            collisionAngle = knife.getAngle();
                            collisionX = knifeX;
                            collisionY = knifeY;
                            collision(deleteCible, cible, cibleX, cibleY, 1);
                            knife.resetKnife();
                        }
                        else collision(deleteCible, cible, cibleX, cibleY, 1);
                        if (((Boss) cible).isDead()) game.scoreJoueur1+=2;
                    }
                    if (transformedBossMask.intersects(transformedKnifeMask2.getBounds2D())) {
                        currentKnife = true;
                        if (!game.powered[2]) {
                            collisionAngle = knife2.getAngle();
                            collisionX = knife2X;
                            collisionY = knife2Y;
                            collision(deleteCible, cible, cibleX, cibleY, 2);
                            knife2.resetKnife();
                        }
                        else collision(deleteCible, cible, cibleX, cibleY, 2);
                        if (((Boss) cible).isDead()) game.scoreJoueur2+=2;
                    }
                }


            }
            else {
                transformCible = AffineTransform.getTranslateInstance(cibleX - (double) cibleImWidth / 2, cibleY - (double) cibleImHeight / 2);
                if (cible instanceof MovingTarget){
                    g2d.drawImage(ciblesMouventeImage,transformCible,this);

                }
                else if (cible instanceof Bonus){
                    switch (((Bonus) cible).getTypeBonus()){
                        case BONUS_POWER : g2d.drawImage(bonusPower, transformCible, this);break;
                        case BONUS_TNT : g2d.drawImage(targetTNT, transformCible, this);break;
                        case BONUS_GOLD : g2d.drawImage(bonusGold, transformCible, this);break;
                        case BONUS_XP : g2d.drawImage(bonusXP, transformCible, this);break;
                        case BONUS_GEL: g2d.drawImage(bonusGel, transformCible, this);break;
                    }
                }
                else {
                    g2d.drawImage(cibleImage, transformCible, this);
                }
                Shape cibleMask = createCollisionMask(cibleImage);
                Shape transformedCibleMask = transformCible.createTransformedShape(cibleMask);
                if (transformedCibleMask == null) {
                    return;
                }
                //g2d.setColor(Color.RED);
                //g2d.draw(transformedCibleMask);
                if (isSolo){
                    if (transformedCibleMask.intersects(transformedKnifeMask.getBounds2D())) {
                        if (!game.powered[0]) {
                            collisionAngle = knife.getAngle();
                            collisionX = knifeX;
                            collisionY = knifeY;
                            collision(deleteCible, cible, cibleX, cibleY, 0);
                            knife.resetKnife();
                        }
                        else{
                            collision(deleteCible, cible, cibleX, cibleY, 0);
                        }
                    }
                }

                else {
                    if (transformedCibleMask.intersects(transformedKnifeMask.getBounds2D())) {
                        currentKnife = false;
                        game.scoreJoueur1++;
                        if (!game.powered[1]) {
                            collisionAngle = knife.getAngle();
                            collisionX = knifeX;
                            collisionY = knifeY;
                            collision(deleteCible, cible, cibleX, cibleY, 1);
                            knife.resetKnife();
                        }
                        else collision(deleteCible, cible, cibleX, cibleY, 1);
                    }
                    if (transformedCibleMask.intersects(transformedKnifeMask2.getBounds2D())) {
                        currentKnife = true;
                        game.scoreJoueur2++;
                        if (!game.powered[2]) {
                            collisionAngle = knife2.getAngle();
                            collisionX = knife2X;
                            collisionY = knife2Y;
                            collision(deleteCible, cible, cibleX, cibleY, 2);
                            knife2.resetKnife();
                        }
                        else collision(deleteCible, cible, cibleX, cibleY, 2);
                    }
                }
            }

        }
        for (Cible c : deleteCible){
            listeCible.remove(c);
        }
        repaint();
    }

}