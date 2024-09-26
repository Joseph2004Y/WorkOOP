import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
//test git hub
public class GFrameProJ extends JFrame {

public GFrameProJ(){

    setSize(950, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

}
public static void main(String[] args) {
    
    GFrameProJ frame = new GFrameProJ();
    PanelProJ Mypanel = new PanelProJ();
    
    Mypanel.addMouseMotionListener(Mypanel);
    frame.add(Mypanel);
    frame.setVisible(true);

}

}

//================================================================================


class PanelProJ extends JPanel implements MouseMotionListener{

    //ข้อมูลตำแหน่งของอุกาบาต
    int []xObject ;
    int []yObject ;
    //แสดงอุกาบาตนั้นๆ หากเป็น true
    boolean []ShowObject ; 
    //เก็บอุกาบาตที่สสุ่มได้
    Image []Img;
    TheThreadRun []Run;

    int xFrame=0;
    int yFrame=0;


    Image backgrou =  Toolkit.getDefaultToolkit().createImage(
        System.getProperty("user.dir") + File.separator + "imgProject\\world.jpg"
    );


    public PanelProJ(){

        setSize(950, 600);

        //กำหนดจำนวนอุกาบาต
        try (Scanner input = new Scanner(System.in)) {
            System.out.print("Enter :");int Object = input.nextInt();

            xObject = new int[Object];
            yObject = new int[Object];
            ShowObject = new boolean[Object];
            Img = new Image[Object];
            Run = new TheThreadRun[Object];

            //จัดการข้อมูล และสุ่มตำแหน่งอุกาบาต
            for(int i=0;i<Object;i++){

                int randInt =  (int)(Math.random()*10)+1;
                String randOf = String.valueOf(randInt); 
                Img[i] =   Toolkit.getDefaultToolkit().createImage(
                    System.getProperty("user.dir") + File.separator + "imgProject" + File.separator + randOf +".png"
                );


                xObject[i] = (int)(Math.random()*900);//สุ่มตำแหน่ง x
                yObject[i] = (int)(Math.random()*500);//สุ่มตำแหน่ง y
                ShowObject[i] = true;
                Run[i] = new TheThreadRun(xObject, yObject, i);
                Run[i].start();

            }
        }
        
    }

    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);

        //ภาพพื้นหลัง
        g.drawImage(backgrou, 0, 0, getWidth(), getHeight(),this);
        
        //แสดงอุกาบาต 
        for(int i=0;i<ShowObject.length;i++){

            if(ShowObject[i])
            {

            g.drawImage(Img[i], xObject[i], yObject[i], 100,100,this);

            }
            

        }
        repaint();

    }
//======================================================================================

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        xFrame = e.getX();
        yFrame = e.getY();

        System.out.println("X :" + xFrame + "\tY :" + yFrame);//แสดงตำแหน่ง x y ทุกคร้งที่ขยับเม้า
        repaint();
    }

}

class TheThreadRun extends Thread{

    int[] xObject;
    int[] yObject;
    int i;
    int xMove = 5;
    int yMove = 5;  

    TheThreadRun(int[] xObject, int[] yObject, int i){

        this.xObject = xObject;
        this.yObject = yObject;
        this.i = i;


    }

    @Override
    public void run() {

        Random random = new Random();
        int sleep = random.nextInt(500);//สุ่ม เวลาการขยับช้าหรือเร็ว

        while (true) {
            
            try {
            
                Thread.sleep(sleep);
                
                

                if (xObject[i] >= 900-50||yObject[i] >= 500-50) {
                    
                    sleep = random.nextInt(500);
                    xMove = random.nextInt(10);
                    yMove = random.nextInt(10);
                    xMove *= -1;
                    yMove *= -1;
                
                }

                if (xObject[i] <= 0||yObject[i] <= 0) {
                    
                    sleep = random.nextInt(500);
                    xMove = random.nextInt(10);
                    yMove = random.nextInt(10);
                    xMove *= 1;
                    yMove *= 1;
                }
                xObject[i] += xMove;
                yObject[i] += yMove;

            } catch (Exception e) {
                // TODO: handle exception
            }
            


        }
    }

}


