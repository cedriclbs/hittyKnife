package gui;

import entity.Cible;
import entity.Knife;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class KnifeDisplay extends JPanel {
    private final Knife knife;
    private Image knifeImage;
    private Image cibleImage;
    private Image backgroundImage;

    private ArrayList<Cible> listeCible;
    private static double bgImgWidth;
    private static double bgImgHeight;
    private double RATIO_X; //1100;
    private double RATIO_Y; //800;
    private final int RATIO = 18;


    public KnifeDisplay(Knife knife, String backgroundPath, ArrayList<Cible> listeCible) {
        //System.out.println("bg x : "+RATIO_X+" bg y : "+RATIO_Y);

        this.listeCible = listeCible;
        this.knife = knife;
        initImage();
        initBg(backgroundPath);

        RATIO_X = getBgImgWidth()/2;
        RATIO_Y = getBgImgHeight()*3/4;
        // Coordonnées du couteau initialisé au milieu de l'écran pour une meilleure visibilité
        //this.knife.getCoordinate().setCoordinate(getBgImgWidth() / 2, getBgImgHeight() / 2);


        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
               /* if (!knife.throwing && !knife.isInTheAir) {
                    knife.jump();
                }
                else if (!knife.throwing && knife.isInTheAir){
                    knife.throwKnife();
                }*/
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
        /*addKeyListener(new KeyListener() {

            public void actionPerformed (ActionEvent e){
                if (countClicked == 1) {
                    // 1 : Le couteau n'est plus figé
                    knife.jump();
                } else if (countClicked == 2) {
                    // 2 : Couteau va attaquer la cible avec sa trajectoire droite
                    knife.throwKnife();
                    return;
                }

                if (countClicked > 2) {
                    countClicked = 0;
                }
                repaint();
            }
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });*/

    }



    private void initImage () {
        this.knifeImage = new ImageIcon("src/main/ressources/knifes/knifeRotate2.png").getImage();
        this.cibleImage = new ImageIcon("src/main/ressources/targets/target#1.png").getImage();
        int w = this.knifeImage.getWidth(null)/3;
        int h = this.knifeImage.getHeight(null)/3;
        this.knifeImage = this.knifeImage.getScaledInstance(w,h,Image.SCALE_SMOOTH);
        this.cibleImage = this.cibleImage.getScaledInstance(this.cibleImage.getWidth(null)/2,this.cibleImage.getHeight(null)/2,Image.SCALE_SMOOTH);
    }

    private void initBg(String backgroundPath) {
        this.backgroundImage = new ImageIcon(backgroundPath).getImage();
        bgImgHeight = this.backgroundImage.getHeight(null);
        bgImgWidth = this.backgroundImage.getWidth(null);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);


        int knifeX = (int) (RATIO_X-(knife.getX()*RATIO));
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
        for (Cible cible : listeCible){
            AffineTransform transformCible = AffineTransform.getTranslateInstance((RATIO_X-cible.getX()*RATIO) - (double) cibleImWidth / 2, (RATIO_Y-cible.getY()*RATIO) - (double) cibleImHeight / 2);
            g2d.drawImage(cibleImage,transformCible,this);
        }



        repaint();
    }

    public static double getBgImgWidth() {
        return bgImgWidth;
    }

    public static double getBgImgHeight() {
        return bgImgHeight;
    }
}
