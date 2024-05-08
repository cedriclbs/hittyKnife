package gui;

import config.RessourcesPaths;
import entity.*;
import entity.bosses.*;

import java.awt.*;
import config.Game;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.List;


/**
 * La classe KnifeDisplay représente le panneau graphique où le couteau et les cibles sont affichés.
 * Elle étend JPanel pour permettre l'affichage des éléments graphiques du jeu Hitty Knife.
 */
public class EntityDisplay extends JPanel {
    private final Knife knife;
    private Image knifeImage;
    private Image cibleImage;
    private Image backgroundImage;
    private Image ciblesMouventeImage;
    private Image bossT1;
    private Image bossT2;
    private Image bossT3;
    private Image bossT4;
    private List<Cible> listeCible;
    private static double bgImgWidth;
    private static double bgImgHeight;
    private double RATIO_X;
    private double RATIO_Y;
    private final int RATIO = 18;
    private int RATIO1v1;

    private int collisionX;
    private int collisionY;
    private double cibleColliX;
    private double cibleColliY;
    private double collisionAngle;
    private boolean animCollision = false;
    private boolean isCollisionMovingTarget= false;
    final float baseOpacity = 1f;
    float opacity;
    Dimension screenSize;
    private Game game;



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
        initImage();
        initBg(backgroundPath);
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        RATIO1v1 = (isSolo)?1:2;


        if (isSolo) RATIO_X = screenSize.width/2;
        else RATIO_X = screenSize.width/4;//getBgImgWidth()/2;

        RATIO_Y = screenSize.height*3/4;//getBgImgHeight()*3/4;

        this.game = game;
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
        this.knifeImage = new ImageIcon(RessourcesPaths.knifePath + "knifeRotate2.png").getImage();
        this.cibleImage = new ImageIcon("src/main/ressources/targets/target#1.png").getImage();
        this.ciblesMouventeImage =  new ImageIcon("src/main/ressources/targets/target#2.png").getImage();
        String filepath = "src/main/ressources/targets/";
        this.bossT1 = new ImageIcon(filepath + "Boss2 (1).png").getImage();
        this.bossT2 = new ImageIcon(filepath + "Boss2 (1).png").getImage();
        this.bossT3 = new ImageIcon(filepath + "Boss2 (1).png").getImage();
        this.bossT4 = new ImageIcon(filepath + "Boss2 (1).png").getImage();
        int w = this.knifeImage.getWidth(null)/3;
        int h = this.knifeImage.getHeight(null)/3;
        this.knifeImage = this.knifeImage.getScaledInstance(w,h,Image.SCALE_SMOOTH);
        this.cibleImage = this.cibleImage.getScaledInstance(this.cibleImage.getWidth(null)/2,this.cibleImage.getHeight(null)/2,Image.SCALE_SMOOTH);
        this.ciblesMouventeImage = this.ciblesMouventeImage.getScaledInstance(this.ciblesMouventeImage.getWidth(null)/2,this.ciblesMouventeImage.getHeight(null)/2,Image.SCALE_SMOOTH);
        this.bossT1 = this.bossT1.getScaledInstance(this.bossT1.getWidth(null)/2, this.bossT1.getHeight(null)/2, Image.SCALE_SMOOTH);
        this.bossT2 = this.bossT2.getScaledInstance(this.bossT2.getWidth(null)/2, this.bossT2.getHeight(null)/2, Image.SCALE_SMOOTH);
        this.bossT3 = this.bossT3.getScaledInstance(this.bossT3.getWidth(null)/2, this.bossT3.getHeight(null)/2, Image.SCALE_SMOOTH);
        this.bossT4 = this.bossT4.getScaledInstance(this.bossT4.getWidth(null)/2, this.bossT4.getHeight(null)/2, Image.SCALE_SMOOTH);
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
        String res = knifePathClicked;
        String abr = RessourcesPaths.knifePath;
        if (knifePathClicked.equals(abr +"knife.png")){
            res = abr + "knifeRotate1.png";
        } else if (knifePathClicked.equals(abr +"knife#2.png")){
            res = abr + "knifeRotate2.png";
        } else if (knifePathClicked.equals(abr +"knife#3.png")){
            res = abr+ "knifeRotate3.png";
        }
        return res;
    }


    /**
     * Crée et retourne un masque de collision à partir de l'image spécifiée.
     * @param image L'image à partir de laquelle créer le masque de collision.
     * @return Un objet de type Shape représentant le masque de collision créé.
     */
    public Shape createCollisionMask(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
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

        if(game.getIsSolo()){
            // Affichage des niveaux et des rounds avec effet d'ombre sur le texte
            g2d.setFont(new Font("SansSerif", Font.BOLD, 24));

            // Texte principal pour le niveau
            String niveauTexte = "LEVEL : " + game.getCurrentLevel();
            int niveauTexteWidth = g2d.getFontMetrics().stringWidth(niveauTexte);

            int xPosition = (getWidth() - niveauTexteWidth) / 2;
            int yPosi = 30;

            // Dessine l'ombre
            g2d.setColor(new Color(0, 0, 0, 64));
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
            g2d.setColor(new Color(0, 0, 0, 64));
            g2d.fillRoundRect(startX - 10, yPosition - (barHeight / 2) + (circleDiameter / 2), (totalRounds * (circleDiameter + spacing)) - spacing + 20, barHeight, arcWidth, arcHeight);


            for (int i = 0; i < totalRounds; i++) {
                if (i == totalRounds - 1 && currentRoundIndex == totalRounds - 1) {
                    g2d.setColor(Color.RED);
                } else if (i <= currentRoundIndex) {
                    g2d.setColor(Color.WHITE);
                } else {
                    g2d.setColor(Color.BLACK);
                }
                // Dessine le cercle
                g2d.fillOval(startX + i * (circleDiameter + spacing), yPosition, circleDiameter, circleDiameter);
            }



            // Si c'est le dernier round, affiche "Boss Fight!"
            if (currentRoundIndex == totalRounds - 1) {
                String bossFightText = "Boss Fight!";
                int textWidth = g2d.getFontMetrics().stringWidth(bossFightText);
                int textYPosition = yPosition + circleDiameter + 30;

                // Dessine l'ombre pour "Boss Fight!" en noir
                g2d.setColor(Color.BLACK);
                g2d.drawString(bossFightText, (getWidth() - textWidth) / 2 + shadowOffset, textYPosition + shadowOffset);

                // Dessine le texte principal pour "Boss Fight!" en rouge
                g2d.setColor(Color.RED);
                g2d.drawString(bossFightText, (getWidth() - textWidth) / 2, textYPosition);
            }
        }


        int knifeX = (int) (RATIO_X-(knife.getX()*RATIO));
        int knifeY = (int) (RATIO_Y-(knife.getY()*RATIO));

        if (knifeX>screenSize.width/RATIO1v1 || knifeX<0 || knifeY > screenSize.height || knifeY<0){
            knife.resetKnife();
        }

        int knifeImgWidth = knifeImage.getWidth(this);
        int knifeImgHeight = knifeImage.getHeight(this);
        int cibleImWidth = cibleImage.getWidth(this);
        int cibleImHeight = cibleImage.getHeight(this);
        int bossImgWidth = bossT1.getWidth(this);
        int bossImgHeight = bossT1.getHeight(this);


        AffineTransform transform = AffineTransform.getTranslateInstance(knifeX - (double) knifeImgWidth / 2, knifeY - (double) knifeImgHeight / 2);
        transform.rotate(Math.toRadians(knife.getAngle()), (double) knifeImgWidth / 2, (double) knifeImgHeight / 2);
        g2d.drawImage(knifeImage, transform, this);

        //--------------------------AFFICHAGE ANIMATION COLLISION -------------------------------
        if (animCollision){
            Composite oldComposite = g2d.getComposite();
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);

            g2d.setComposite(alphaComposite);
            AffineTransform transformColli = AffineTransform.getTranslateInstance(collisionX - (double) knifeImgWidth / 2, collisionY - (double) knifeImgHeight / 2);
            transformColli.rotate(Math.toRadians(collisionAngle), (double) knifeImgWidth / 2, (double) knifeImgHeight / 2);
            g2d.drawImage(knifeImage, transformColli, this);

            AffineTransform transformCibleColli = AffineTransform.getTranslateInstance(cibleColliX - (double) cibleImWidth / 2, cibleColliY - (double) cibleImHeight / 2);
            if (isCollisionMovingTarget) g2d.drawImage(ciblesMouventeImage, transformCibleColli, this);
            else g2d.drawImage(cibleImage, transformCibleColli, this);

            g2d.setComposite(oldComposite);
            if (opacity>0.01f)opacity-=0.007f;
            else animCollision=false;
        }

        //-------------------------------AFFICHAGE NORMAL DES CIBLES -------------------------------

        ArrayList<Cible> deleteCible= new ArrayList<>();
        Shape knifeMask = createCollisionMask(knifeImage);
        Shape transformedKnifeMask = transform.createTransformedShape(knifeMask);
        for (Cible cible : listeCible){
            boolean collision = false;
            double cibleX = (RATIO_X-cible.getX()*RATIO);
            double cibleY = (RATIO_Y-cible.getY()*RATIO);
            AffineTransform transformCible;
            AffineTransform transformBoss;

            if (cible instanceof Boss){
                transformBoss = AffineTransform.getTranslateInstance(cibleX - (double) bossImgWidth / 2, cibleY - (double) bossImgHeight / 2);
                if (cible instanceof BossType1) {
                    g2d.drawImage(bossT1, transformBoss , this);
                }
                else if (cible instanceof BossType2) {
                    g2d.drawImage(bossT2, transformBoss , this);
                }
                else if (cible instanceof BossType3) {
                    g2d.drawImage(bossT3, transformBoss, this);
                }
                else if (cible instanceof BossType4) {
                    g2d.drawImage(bossT4, transformBoss, this);
                }
                Shape bossMask = createCollisionMask(bossT1);
                Shape transformedBossMask = transformBoss.createTransformedShape(bossMask);
                g2d.setColor(Color.RED);
                g2d.draw(transformedBossMask);
                if (transformedBossMask.intersects(transformedKnifeMask.getBounds2D())) {
                    collision = true;
                }

            }
            else {
                transformCible = AffineTransform.getTranslateInstance(cibleX - (double) cibleImWidth / 2, cibleY - (double) cibleImHeight / 2);
                if (cible instanceof MovingTarget){
                    g2d.drawImage(ciblesMouventeImage,transformCible,this);

                }
                else{
                    g2d.drawImage(cibleImage, transformCible, this);
                }
                Shape cibleMask = createCollisionMask(cibleImage);
                Shape transformedCibleMask = transformCible.createTransformedShape(cibleMask);
                g2d.setColor(Color.RED);
                g2d.draw(transformedCibleMask);
                if (transformedCibleMask.intersects(transformedKnifeMask.getBounds2D())) {
                    collision = true;
                }
            }



            //--------------------COLLISIONS------------------------




            //AFFICHAGE COLLISIONS


            g2d.setColor(Color.BLUE);
            g2d.draw(transformedKnifeMask);

            //int cw=55;int ch=55;
            //if (knifeX > cibleX-cw && knifeX<cibleX+cw && knifeY > cibleY-ch && knifeY<cibleY+ch){
            if (collision) {
                collisionX = knifeX;
                collisionY = knifeY;
                cibleColliX = cibleX;
                cibleColliY = cibleY;
                collisionAngle = knife.getAngle();
                animCollision = true;
                game.addXP(10);
                game.addArgent(10);
                System.out.println("XP+10 ");
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
                knife.resetKnife();
                opacity = baseOpacity;
                isCollisionMovingTarget=cible instanceof MovingTarget;
            }
        }
        for (Cible c : deleteCible){
            listeCible.remove(c);
        }
        repaint();
    }

}