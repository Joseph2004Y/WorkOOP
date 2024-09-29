import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

public class Meteor extends Thread {

    public int xStart, yStart;
    public int MTOspeedX, MTOspeedY;
    public int numOfImgMTO;
    private boolean isBombBoom = false;
    private int sizeOfMeteor = 100;
    private boolean running = true; 
    private Panel4Paint panel; 

    Random random = new Random();
    Image imgMTO;
    Image boomMeteor;
    

    //Constructor 
    public Meteor(int x, int y, int spdX, int spdY, int numImgMTO, Panel4Paint panel) {
        this.xStart = x;
        this.yStart = y;
        this.MTOspeedX = spdX;
        this.MTOspeedY = spdY;
        this.numOfImgMTO = numImgMTO;
        this.panel = panel;


        imgMTO = new ImageIcon(System.getProperty("user.dir") + File.separator + 
            "imgProject" + File.separator + numOfImgMTO + ".png").getImage();

        boomMeteor = new ImageIcon(System.getProperty("user.dir") + File.separator + 
            "imgProject" + File.separator + "bomb.gif").getImage();
    }

    // Thread run 
    @Override
    public void run() {

        while (running) {

            meteorMove(panel.getWidth(), panel.getHeight(), panel.getMeteors());
            panel.repaint();

            try {
                Thread.sleep(144); 

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopRunning() {
        running = false;
    }

    // Meteor Movement Methods
    public void meteorMove(int width, int height, ArrayList<Meteor> meteors) {
        if (xStart <= 0 || xStart >= width - getSizeOfMeteor()) {
            MTOspeedX = -MTOspeedX;
            changeSpeedMeteor();
            xStart = Math.max(0, Math.min(xStart, width - getSizeOfMeteor()));
        }

        if (yStart <= 0 || yStart >= height - getSizeOfMeteor()) {
            MTOspeedY = -MTOspeedY;
            changeSpeedMeteor();
            yStart = Math.max(0, Math.min(yStart, height - getSizeOfMeteor()));
        }

        for (Meteor MTO : meteors) {
            if (MTO != this && checkCollideMeteor(MTO)) {
                changePositionAfterCollide(MTO);;
            }
        }

        xStart += MTOspeedX;
        yStart += MTOspeedY;

        xStart = Math.max(0, Math.min(xStart, width - getSizeOfMeteor()));
        yStart = Math.max(0, Math.min(yStart, height - getSizeOfMeteor()));
    }

    public boolean checkCollideMeteor(Meteor MTO) {
        int distance = (int) Math.sqrt(Math.pow(xStart - MTO.xStart, 2) + Math.pow(yStart - MTO.yStart, 2));
        return distance < getSizeOfMeteor();
    }

    public void changePositionAfterCollide(Meteor MTO) {
        int overlap = getSizeOfMeteor() - (int) Math.sqrt(Math.pow(xStart - MTO.xStart, 2) + Math.pow(yStart - MTO.yStart, 2));

        int dx = xStart - MTO.xStart;
        int dy = yStart - MTO.yStart;

        if (dx != 0) {
            xStart += (dx / Math.abs(dx)) * overlap / 2;
            MTO.xStart -= (dx / Math.abs(dx)) * overlap / 2;
        }

        if (dy != 0) {
            yStart += (dy / Math.abs(dy)) * overlap / 2;
            MTO.yStart -= (dy / Math.abs(dy)) * overlap / 2;
        }

        MTOspeedX = -MTOspeedX;
        MTO.MTOspeedX = -MTO.MTOspeedX;
        MTOspeedY = -MTOspeedY;
        MTO.MTOspeedY = -MTO.MTOspeedY;
    }

    public void changeSpeedMeteor(){
        do {
            MTOspeedX = random.nextInt(30) - 5;
        } while (MTOspeedX == 0);

        do {
            MTOspeedY = random.nextInt(30) - 5;
        } while (MTOspeedY == 0);
    }

    public boolean checkMouseDoubleClickInAreaMTO(int mouseX, int mouseY) {
        return (mouseX >= xStart && mouseX <= xStart + 100 && 
                mouseY >= yStart && mouseY <= yStart + 100);
    }

    public int getXMeteor() {
        return xStart;
    }

    public int getYMeteor() {
        return yStart;
    }



    public Image getMeteorImage() {
        return imgMTO;
    }

    public Image getBombImage() {
        return boomMeteor;
    }

    public boolean checkMeteorBoom() {
        return isBombBoom;
    }

    public int getSizeOfMeteor() {
        return sizeOfMeteor;
    }

    public void setBombBoom() {
        isBombBoom = true;
    }
}
