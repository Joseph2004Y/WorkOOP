import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class GFrameProJ extends JFrame {

    //Set up Main Frame Start
    public GFrameProJ() {
        setSize(1200, 700);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null); 
        setLayout(null);  

        Panel4Paint panel4Paint = new Panel4Paint(); 
        add(panel4Paint);  
        setVisible(true);  
    }
    public static void main(String[] args) {
        GFrameProJ display = new GFrameProJ();  
    }
}
//===========================================================================================================================================

class Panel4Paint extends JPanel {

        private ArrayList<Meteor> meteorsArL;  
        private Random ran = new Random();  
        private int numOfImg = 0;  
        private Image BGI;  
    
        
        public Panel4Paint() {

        setSize(1200, 700);  
        setLayout(null);
    
            BGI = Toolkit.getDefaultToolkit().createImage(
                System.getProperty("user.dir") + File.separator + "imgProject" + File.separator + "world.jpg");
    
            meteorsArL = new ArrayList<>();  
            Scanner input = new Scanner(System.in);  
    
            try {

                System.out.print("Enter number of Meteors: ");
                numOfImg = input.nextInt();
    
                for (int i = 0; i < numOfImg; i++) {
        
                    Meteor meteor = new Meteor(ran.nextInt(1000), ran.nextInt(500),
                        ran.nextInt(30) - 5, ran.nextInt(30) - 5, ran.nextInt(10) + 1, this);
                    meteorsArL.add(meteor);  
    
                    
                    Thread meteorThread = new Thread(meteor);
                    meteorThread.start(); 
                }
    
            } catch (InputMismatchException ex) {
                System.out.println("Error: " + ex.getMessage()); 
            }
        }
    

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(BGI, 0, 0, getWidth(), getHeight(), this); 
    
            // วาดอุกกาบาตทั้งหมดใน
            for (Meteor MTO : meteorsArL){
                if (MTO.checkMeteorBoom()){
                    g.drawImage(MTO.getBombImage(), MTO.getXMeteor(), MTO.getYMeteor(), 100, 100, this);
                } 
                else {
                    g.drawImage(MTO.getMeteorImage(), MTO.getXMeteor(), MTO.getYMeteor(), 100, 100, this);

                }
            }
    

           
            addMouseListener(new MouseAdapter() { 
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {  
                        for (Meteor MTO : meteorsArL) {
                            if (MTO.checkMouseDoubleClickInAreaMTO(e.getX(), e.getY())) {  
                                MTO.setBombBoom();  
                                repaint();  
    
                                // ลบอุกกาบาตออกจากหน้าจอ
                                Timer alarm = new Timer(500, new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        MTO.stopRunning(); 
                                        meteorsArL.remove(MTO); 
                                        repaint(); 
                                    }

                                });
                                alarm.setRepeats(false); 
                                alarm.start(); 
                                
                            }
                        }
                    }
                }
            });
        }
    
        // คืนค่า ArrayList ของอุกกาบาต
        public ArrayList<Meteor> getMeteors() {
            return meteorsArL;
        }
    }

