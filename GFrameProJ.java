import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
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

                xObject[i] = (int)(Math.random()*900);//สุ่มตำแหน่ง x
                yObject[i] = (int)(Math.random()*500);//สุ่มตำแหน่ง y
                ShowObject[i] = true;

                int randInt =  (int)(Math.random()*10)+1;
                String randOf = String.valueOf(randInt); 
                Img[i] =   Toolkit.getDefaultToolkit().createImage(
                    System.getProperty("user.dir") + File.separator + "imgProject\\" + randOf +".png"
                );

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

            g.drawImage(Img[i], xObject[i], yObject[i], 50,50,this);

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

// คลาสสำหรับรันเธรด (ยังไม่ได้ใช้งาน)
class TheThreadRun extends Thread{


    TheThreadRun(){




    }



}


