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
    
    //-------------------------------Set up Main Frame Start--------------------------------------------------
    public GFrameProJ() {
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        Panel4Paint panel4Paint = new Panel4Paint();
        add(panel4Paint);
        setVisible(true);
    }
    //------------------------------Set up Main Frame End-------------------------------------------------


    //--------------------------------Main method 4 Run Start-------------------------------------------------------- 
    public static void main(String[] args) {
        GFrameProJ gf = new GFrameProJ();
    }
}
    //--------------------------------Main method 4 Run End-------------------------------------------------------- 







    //----------------------------------Panel 4 Pain :D Start-----------------------------------------------------
class Panel4Paint extends JPanel {
    //////////////Attribute/////////////////////
    private ArrayList<Meteor> meteorsArL;
    Random ran = new Random();
    int numOfImg = 0;

    
    Image BGI = Toolkit.getDefaultToolkit().createImage(
        System.getProperty("user.dir") + File.separator + "imgProject" + File.separator + "world.jpg");

    //////////////Attribute/////////////////////



    public Panel4Paint() {

        setSize(1200, 700);//set up Panel
        setLayout(null);

        meteorsArL = new ArrayList<>();//Local Variable
        Scanner input = new Scanner(System.in);



        try {
            System.out.print("Enter number of Meteors: ");
            numOfImg = input.nextInt();

            for (int i = 0; i < numOfImg; i++) {
                meteorsArL.add(new Meteor(ran.nextInt(1000), ran.nextInt(500), //create Meteor
                    ran.nextInt(5) - 2, ran.nextInt(5) - 2, ran.nextInt(10) + 1));
            }


            //Timer 4 check movement Meteor
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
        
        g.drawImage(BGI, 0, 0, getWidth(), getHeight(), this);

        
        for (Meteor MTO : meteorsArL) {
            if(MTO.getisDestroy()){
                g.drawImage(MTO.getDestroyImg(), MTO.getXMeto(), MTO.getYMeto(), 100, 100, this);//Change meteor to bomb
            } else {
                g.drawImage(MTO.getMTOImage(), MTO.getXMeto(), MTO.getYMeto(), 100, 100, this);//drawn Meteor
            }
        }

        //Check mouse clicked 2 time
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    for (Meteor MTO : meteorsArL) {
                        if (MTO.isCollidingWithMouse(e.getX(), e.getY())) {
                            MTO.setisDestroy(); 
                        
                            repaint();
                                
                
                            //set alarm bomb 2 second after double clicked Meteor
                            Timer alarm = new Timer(1000, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    meteorsArL.remove(MTO);//delete Meteor from ArrayList

                                    repaint();
                                        
                                }
                            });
                            alarm.setRepeats(false);//set Timer work 1 time **repeat = ทำซ้ำ**
                            alarm.start();
                            break;
                        }
                    }
                }
            }
        });
    }
}
    //----------------------------------Panel 4 Pain :D End-----------------------------------------------------
