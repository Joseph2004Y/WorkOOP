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
        GFrameProJ gf = new GFrameProJ();  
    }
}
//===========================================================================================================================================

class Panel4Paint extends JPanel {

        private ArrayList<Meteor> meteorsArL;  // เก็บข้อมูลอุกกาบาตทั้งหมดในรูปแบบ ArrayList
        private Random ran = new Random();  // สร้างออบเจ็กต์สุ่มค่าต่างๆ
        private int numOfImg = 0;  // จำนวนอุกกาบาต
        private Image BGI;  // รูปภาพพื้นหลัง
    
        // คอนสตรัคเตอร์สำหรับแผงวาดภาพ
        public Panel4Paint() {

        setSize(1200, 700);  // กำหนดขนาดแผง
        setLayout(null);
    
            BGI = Toolkit.getDefaultToolkit().createImage(
                System.getProperty("user.dir") + File.separator + "imgProject" + File.separator + "world.jpg");
    
            meteorsArL = new ArrayList<>();  // สร้าง ArrayList สำหรับเก็บอุกกาบาต
            Scanner input = new Scanner(System.in);  // รับข้อมูลจากผู้ใช้ผ่านคอนโซล
    
            try {

                System.out.print("Enter number of Meteors: ");
                numOfImg = input.nextInt();
    
                for (int i = 0; i < numOfImg; i++) {
        
                    Meteor meteor = new Meteor(ran.nextInt(1000), ran.nextInt(500),
                        ran.nextInt(5) - 2, ran.nextInt(5) - 2, ran.nextInt(10) + 1, this);
                    meteorsArL.add(meteor);  // เพิ่มอุกกาบาตเข้าใน ArrayList
    
                    // สร้าง thread สำหรับอุกกาบาตแต่ละตัว
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
            for (Meteor MTO : meteorsArL) {
                if (MTO.getisDestroy()) {

                    // ให้BOOM
                    g.drawImage(MTO.getDestroyImg(), MTO.getXMeto(), MTO.getYMeto(), 100, 100, this);

                } 
                else {

                    // หากอุกกาบาตยังไม่ถูกทำลาย ให้แสดงภาพอุกกาบาต
                    g.drawImage(MTO.getMTOImage(), MTO.getXMeto(), MTO.getYMeto(), 100, 100, this);

                }
            }
    

           
            addMouseListener(new MouseAdapter() { // ตรวจจับการคลิกเมาส์
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {  // ตรวจสอบว่าคลิกสองครั้ง
                        for (Meteor MTO : meteorsArL) {
                            if (MTO.isCollidingWithMouse(e.getX(), e.getY())) {  // ตรวจสอบว่าเมาส์ชนกับอุกกาบาตหรือไม่
                                MTO.setisDestroy();  
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
                                break;
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

