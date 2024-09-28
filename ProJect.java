import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class ProJect extends JFrame {
    
    public ProJect() {
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        Panel4Paint panel4Paint = new Panel4Paint();
        add(panel4Paint);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ProJect();
    }
}

class Panel4Paint extends JPanel {
    private ArrayList<Meteor> meteorsArL;
    Random ran = new Random();
    int numOfImg = 0;

    
    Image BGI = Toolkit.getDefaultToolkit().createImage(
        System.getProperty("user.dir") + File.separator + "imgProject" + File.separator + "world.jpg");

    public Panel4Paint() {
        setSize(1200, 700);
        setLayout(null);

        meteorsArL = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        try {
            // รับจำนวนอุกกาบาตจากผู้ใช้
            System.out.print("Enter number of Meteors: ");
            numOfImg = input.nextInt();

            for (int i = 0; i < numOfImg; i++) {
                meteorsArL.add(new Meteor(ran.nextInt(1000), ran.nextInt(500), 
                    ran.nextInt(5) - 2, ran.nextInt(5) - 2, ran.nextInt(10) + 1));
            }


            Timer timer = new Timer(100, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (Meteor MTO : meteorsArL) {
                        MTO.meteorMove(getWidth(), getHeight(), meteorsArL);
                    }
                    repaint();
                }
            });
            timer.start();

        } catch (InputMismatchException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // วาดภาพพื้นหลัง
        g.drawImage(BGI, 0, 0, getWidth(), getHeight(), this);

        // วาดอุกกาบาตทั้งหมด
        for (Meteor MTO : meteorsArL) {
            if(MTO.getisDestroy()){
                g.drawImage(MTO.getDestroyImg(), MTO.getXMeto(), MTO.getYMeto(), 100, 100, this);
            } else {
                g.drawImage(MTO.getMTOImage(), MTO.getXMeto(), MTO.getYMeto(), 100, 100, this);
            }
        }


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    for (Meteor MTO : meteorsArL) {
                        if (MTO.isCollidingWithMouse(e.getX(), e.getY())) {
                            MTO.setisDestroy(); 
                            
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    repaint();
                                }
                            });
                            

                            Timer alarm = new Timer(1000, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    meteorsArL.remove(MTO);

                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            repaint();
                                        }
                                    });
                                    
                                }
                                
                            });
                            alarm.setRepeats(false);
                            alarm.start();
                            break;
                        }
                    }
                }
            }
        });
    }



}