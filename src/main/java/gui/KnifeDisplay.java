package gui;

import entity.Knife;
import geometry.Coordinate;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;


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
    public static boolean mouseClicked = false;

    private static int countClicked = 0;


/**
 * Constructeur de KnifeDisplay
 * @param knife Le couteau à afficher.
 * @param backgroundPath Le chemin de l'image d'arrière-plan.
*/
    public KnifeDisplay(Knife knife, String backgroundPath) {
        this.knife = knife;
        initImage();
        this.backgroundImage = new ImageIcon(backgroundPath).getImage();
        this.rotationAngle = knife.getAngle();

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseClicked = true;
                countClicked++;
                if (countClicked > 2){
                    countClicked=0;
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

        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mouseClicked){
                    if (countClicked == 1){
                        //1 : le couteau n'est plus figé
                        knife.jump();
                        knife.addAngle(10);
                    } else if (countClicked == 2){
                        //2 : le couteau doit aller dans une trajectoire droite pour attaquer l'ennemi. les modifications pour image
                        knife.throwKnife();
                    }
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


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(backgroundImage, 0,0, getWidth(), getHeight(), this);
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

    public void handleMouseClick() {
        mouseClicked = true;
        knife.startThrowing();
    }





}
