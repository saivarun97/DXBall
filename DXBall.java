import javax.swing.*;
/*import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;*/
import java.awt.event.*;
import java.awt.*;

public class DXBall extends JFrame implements ActionListener {
    private JButton b1;
    private JButton b2;

    public DXBall(){
        setTitle("DXBall");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Dimensions.WIDTH, Dimensions.HEIGTH);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel p = new JPanel();

        b1 = new JButton(new ImageIcon("image/play.png"));
        b1.addActionListener(this);
        p.setLayout(null);
        p.setBackground(Color.BLACK);
        b1.setBounds(123,550,298,100);
        p.add(b1);

        b2 = new JButton(new ImageIcon("image/bg.png"));
        b2.setBounds(0,0,541,257);
        p.add(b2);

        getContentPane().add(p);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
        {
        if(e.getSource() == b1)
            {
            setVisible(false);
            Start game = new Start();
            game.setVisible(true);   
            }   
        }

    public static void main(String[] args) {

        DXBall d = new DXBall();

    }

}

class Start extends JFrame {

    public Start() {
        
        initUI();
    }
    
    private void initUI() {
        add(new Board());
        setTitle("DXBall");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Dimensions.WIDTH, Dimensions.HEIGTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
}