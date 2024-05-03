package gui;

import config.RessourcesPaths;
import entity.Cible;
import entity.Knife;
import entity.MovingTarget;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

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

    private ArrayList<Cible> listeCible;
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

    /**
     * Constructeur de la classe KnifeDisplay.
     * @param knife Le couteau à afficher dans le panneau.
     * @param backgroundPath Le chemin d'accès à l'image de fond du panneau.
     * @param listeCible La liste des cibles à afficher dans le panneau.
     */
    public EntityDisplay(Knife knife, String backgroundPath, ArrayList<Cible> listeCible,boolean isSolo) {
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
        this.knifeImage = new ImageIcon(RessourcesPaths.knifePath + "knifeRotate1.png").getImage();
        this.cibleImage = new ImageIcon("src/main/ressources/targets/target#1.png").getImage();
        this.ciblesMouventeImage =  new ImageIcon("src/main/ressources/targets/target#2.png").getImage();
        int w = this.knifeImage.getWidth(null)/3;
        int h = this.knifeImage.getHeight(null)/3;
        this.knifeImage = this.knifeImage.getScaledInstance(w,h,Image.SCALE_SMOOTH);
        this.cibleImage = this.cibleImage.getScaledInstance(this.cibleImage.getWidth(null)/2,this.cibleImage.getHeight(null)/2,Image.SCALE_SMOOTH);
        this.ciblesMouventeImage = this.ciblesMouventeImage.getScaledInstance(this.ciblesMouventeImage.getWidth(null)/2,this.ciblesMouventeImage.getHeight(null)/2,Image.SCALE_SMOOTH);
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
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return new Rectangle(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
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

        int knifeX = (int) (RATIO_X-(knife.getX()*RATIO));
        int knifeY = (int) (RATIO_Y-(knife.getY()*RATIO));

        if (knifeX>screenSize.width/RATIO1v1 || knifeX<0 || knifeY > screenSize.height || knifeY<0){
            knife.resetKnife();
        }

        int knifeImgWidth = knifeImage.getWidth(this);
        int knifeImgHeight = knifeImage.getHeight(this);
        int cibleImWidth = cibleImage.getWidth(this);
        int cibleImHeight = cibleImage.getHeight(this);

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
            if (opacity>0.01f)opacity-=0.001f;
            else animCollision=false;
        }

        //-------------------------------AFFICHAGE NORMAL DES CIBLES -------------------------------

        ArrayList<Cible> deleteCible= new ArrayList<>();
        for (Cible cible : listeCible){
            double cibleX = (RATIO_X-cible.getX()*RATIO);
            double cibleY = (RATIO_Y-cible.getY()*RATIO);
            AffineTransform transformCible = AffineTransform.getTranslateInstance(cibleX - (double) cibleImWidth / 2, cibleY - (double) cibleImHeight / 2);
            if (cible instanceof MovingTarget){
                g2d.drawImage(ciblesMouventeImage,transformCible,this);

            }
            else {
                g2d.drawImage(cibleImage, transformCible, this);
            }

            //--------------------COLLISIONS------------------------

            Shape knifeMask = createCollisionMask(knifeImage);
            Shape cibleMask = createCollisionMask(cibleImage);
            Shape transformedCibleMask = transformCible.createTransformedShape(cibleMask);
            Shape transformedKnifeMask = transform.createTransformedShape(knifeMask);

            //AFFICHAGE COLLISIONS

            g2d.setColor(Color.RED);
            g2d.draw(transformedCibleMask);
            g2d.setColor(Color.BLUE);
            g2d.draw(transformedKnifeMask);

            //int cw=55;int ch=55;
            //if (knifeX > cibleX-cw && knifeX<cibleX+cw && knifeY > cibleY-ch && knifeY<cibleY+ch){
            if (transformedCibleMask.intersects(transformedKnifeMask.getBounds2D())) {
                collisionX = knifeX;
                collisionY = knifeY;
                cibleColliX = cibleX;
                cibleColliY = cibleY;
                collisionAngle = knife.getAngle();
                animCollision = true;
                deleteCible.add(cible);
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

