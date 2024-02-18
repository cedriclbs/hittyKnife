package gui;


import javax.swing.*;


public class SoloGameFrame extends JFrame implements GameFrame {
    


    public SoloGameFrame (String title, String backgroundPath){
        super(title);
        initialize(backgroundPath);
    }


    @Override
    public void initialize (String backgroundPath) {
        BackgroundPanel soloGame = new BackgroundPanel(backgroundPath);
        setContentPane(soloGame);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);

        JButton pauseButton = new JButton("Pause");
        (getContentPane()).add(pauseButton);
    }
    



}
