package gui;

import entity.Cible;
import entity.Knife;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class KnifeDisplay extends JPanel {
    private final Knife knife;
    private Image knifeImage;
    private Image backgroundImage;

    private static int countClicked = 0;

    private static double bgImgWidth;
    private static double bgImgHeight;


    public KnifeDisplay(Knife knife, String backgroundPath) {


        this.knife = knife;
        initImage(); //Img du knife
        initBg(backgroundPath); //Img du background


        // Coordonnées du couteau initialisé au milieu de l'écran pour une meilleure visibilité
        this.knife.getCoordinate().setCoordinate(getBgImgWidth() / 2, getBgImgHeight() / 2);



        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!knife.throwing){
                    countClicked++;

                    if (countClicked == 1) {
                        knife.isInTheAir = true;
                    } else if (countClicked == 2) {
                        // 2 : Couteau va attaquer la cible avec sa trajectoire droite
                        knife.throwKnife();
                        countClicked = 0;
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

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

        Timer timer = new Timer(1000/60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (countClicked == 1) {
                    // 1 : Le couteau n'est plus figé
                    knife.jump();
                    knife.addAngle(30);
                } else if (countClicked == 2) {
                    // 2 : Couteau va attaquer la cible avec sa trajectoire droite
                    knife.throwKnife();
                    return;
                }

                if (countClicked > 2){
                    countClicked = 0;
                    knife.resetKnife();
                }

                knife.updateMovement();
                repaint();
            }
        });
        timer.start();
    }



    private void initImage () {
        this.knifeImage = new ImageIcon("src/main/ressources/knifes/knife.png").getImage();
        int w = this.knifeImage.getWidth(null)/3;
        int h = this.knifeImage.getHeight(null)/3;
        this.knifeImage = this.knifeImage.getScaledInstance(w,h,Image.SCALE_SMOOTH);
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

        int knifeX = (int) knife.getX();
        int knifeY = (int) knife.getY();

        int knifeImgWidth = knifeImage.getWidth(this);
        int knifeImgHeight = knifeImage.getHeight(this);

        AffineTransform transform = AffineTransform.getTranslateInstance(knifeX - (double) knifeImgWidth / 2, knifeY - (double) knifeImgHeight / 2);
        transform.rotate(Math.toRadians(knife.getAngle()), (double) knifeImgWidth / 2, (double) knifeImgHeight / 2);
        g2d.drawImage(knifeImage, transform, this);

        repaint();
    }

    public static double getBgImgWidth() {
        return bgImgWidth;
    }

    public static double getBgImgHeight() {
        return bgImgHeight;
    }
}
