package gui;

import entity.Cible;
import entity.Knife;
import entity.MovingTarget;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
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
    private double RATIO1v1;
    private final int RATIO = 18;

    /**
     * Constructeur de la classe KnifeDisplay.
     * @param knife Le couteau à afficher dans le panneau.
     * @param backgroundPath Le chemin d'accès à l'image de fond du panneau.
     * @param listeCible La liste des cibles à afficher dans le panneau.
     */
    public EntityDisplay(Knife knife, String backgroundPath, ArrayList<Cible> listeCible,boolean isSolo) {
        //System.out.println("bg x : "+RATIO_X+" bg y : "+RATIO_Y);
        if (isSolo){
            RATIO1v1 = 1;
        }
        else{
            RATIO1v1 = 2;
        }
        this.listeCible = listeCible;
        this.knife = knife;
        initImage();
        initBg(backgroundPath);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


        RATIO_X = screenSize.width/2;//getBgImgWidth()/2;
        RATIO_Y = screenSize.height*3/4;//getBgImgHeight()*3/4;
        // Coordonnées du couteau initialisé au milieu de l'écran pour une meilleure visibilité
        //this.knife.getCoordinate().setCoordinate(getBgImgWidth() / 2, getBgImgHeight() / 2);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!knife.throwing && !knife.isInTheAir) {
                    knife.jump();
                }
                else if (!knife.throwing && knife.isInTheAir){
                    knife.throwKnife();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

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
        this.knifeImage = new ImageIcon("src/main/ressources/knifes/knifeRotate2.png").getImage();
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
    private void initBg(String backgroundPath) {
        this.backgroundImage = new ImageIcon(backgroundPath).getImage();
        bgImgHeight = this.backgroundImage.getHeight(null);
        bgImgWidth = this.backgroundImage.getWidth(null);
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

        int knifeX = (int) ((RATIO_X-(knife.getX()*RATIO))/RATIO1v1);
        int knifeY = (int) (RATIO_Y-(knife.getY()*RATIO));

        if (knifeX>getBgImgWidth() || knifeX<0 || knifeY > getBgImgHeight() || knifeY<0){
            knife.resetKnife();
        }

        int knifeImgWidth = knifeImage.getWidth(this);
        int knifeImgHeight = knifeImage.getHeight(this);
        int cibleImWidth = cibleImage.getWidth(this);
        int cibleImHeight = cibleImage.getHeight(this);

        AffineTransform transform = AffineTransform.getTranslateInstance(knifeX - (double) knifeImgWidth / 2, knifeY - (double) knifeImgHeight / 2);
        transform.rotate(Math.toRadians(knife.getAngle()), (double) knifeImgWidth / 2, (double) knifeImgHeight / 2);
        g2d.drawImage(knifeImage, transform, this);

        ArrayList<Cible> deleteCible= new ArrayList<>();
        for (Cible cible : listeCible){
            double cibleX = (RATIO_X-cible.getX()*RATIO)/RATIO1v1;
            double cibleY = (RATIO_Y-cible.getY()*RATIO);
            AffineTransform transformCible = AffineTransform.getTranslateInstance(cibleX - (double) cibleImWidth / 2, cibleY - (double) cibleImHeight / 2);
            if (cible instanceof MovingTarget){
                g2d.drawImage(ciblesMouventeImage,transformCible,this);

            }
            else {
                g2d.drawImage(cibleImage, transformCible, this);
            }

            int cw=50;int ch=50;
            if (knifeX > cibleX-cw && knifeX<cibleX+cw && knifeY > cibleY-ch && knifeY<cibleY+ch){
                deleteCible.add(cible);
                knife.resetKnife();
            }
        }
        for (Cible c : deleteCible){
            listeCible.remove(c);
        }
        repaint();
    }

}
