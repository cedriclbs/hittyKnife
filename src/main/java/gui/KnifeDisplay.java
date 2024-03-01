package gui;

import entity.Knife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import static java.lang.Math.round;


/**
 * La classe KnifeDisplay représente un panneau graphique affichant un couteau.
 * Le couteau est représenté par une image qui peut être tournée autour de son centre.
 * Un fond d'écran peut également être affiché en arrière-plan.
 */
public class KnifeDisplay extends JPanel {
    private Knife knife;
    private Image knifeImage;
    private Image backgroundImage;
    private double rotationAngle;


/**
 * Constructeur de KnifeDisplay
 * @param knife Le couteau à afficher.
 * @param backgroundPath Le chemin de l'image d'arrière-plan.
*/
    public KnifeDisplay(Knife knife, String backgroundPath) {
        this.knife = knife;
        this.knifeImage = new ImageIcon("src/main/ressources/knifes/knife.png").getImage();
        this.backgroundImage = new ImageIcon(backgroundPath).getImage();
        this.rotationAngle = knife.getAngle();

        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotationAngle += Math.toRadians(5);
                repaint();
            }
        });
        timer.start();
    }




    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g2d.rotate(rotationAngle, (double) getWidth() / 2, (double) getHeight() / 2);

        AffineTransform transform = AffineTransform.getTranslateInstance(
                (double) (getWidth() - knifeImage.getWidth(this)) / 2,
                (double) (getHeight() - knifeImage.getHeight(this)) / 2
        );

        transform.rotate(Math.toRadians(
                        knife.getAngle()),
                (double) knifeImage.getWidth(this) / 2,
                (double) knifeImage.getHeight(this) / 2
        );

        g2d.drawImage(knifeImage, transform, this);

        //g2d.rotate(-rotationAngle, (double) getWidth() / 2, (double) getHeight() / 2);
    }






//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//
//        int LONGUEUR_VECT = 5;
//
//        for (int i = 0; i < 40; i++) {
//            for (int j = 0; j < 20; j++) {
//                if (geometry.Geometry.estDansVecteur(LONGUEUR_VECT, knife.getAngle(), knife.getX(), knife.getY(), j, 40 - i)) {
//
//                    AffineTransform transform = AffineTransform.getTranslateInstance(
//                            (double) (getWidth() - knifeImage.getWidth(this)) / 2,
//                            (double) (getHeight() - knifeImage.getHeight(this)) / 2
//                    );
//
//                    transform.rotate(Math.toRadians(
//                                    knife.getAngle()),
//                            (double) knifeImage.getWidth(this) / 2,
//                            (double) knifeImage.getHeight(this) / 2
//                    );
//
//                    g2d.drawImage(knifeImage, transform, this);
//                }
//            }
//        }
//    }





}
