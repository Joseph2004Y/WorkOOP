import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

//-----------------------------Class of Attribute Meteor------------------------
// 1 class can make many Meteor
public class Meteor {
    ////////////Attribute///////////
    public int xStart, yStart;
    public int MTOspeedX, MTOspeedY;
    public int numOfImgMTO;
    private boolean isDestroy = false;
    private int sizeOfMeteor = 100;
    Random random = new Random();
    
    Image imgMTO;
    Image DestroyMeteor;
    ////////////Attribute///////////




    ///////////////             Constructor             ////////////////////////////////////////////
    public Meteor(int x, int y, int spdX, int spdY, int numImgMTO) {
        this.xStart = x;
        this.yStart = y;
        this.MTOspeedX = spdX;
        this.MTOspeedY = spdY;
        this.numOfImgMTO = numImgMTO;
        
        imgMTO = new ImageIcon(System.getProperty("user.dir") + File.separator + 
            "imgProject" + File.separator + numOfImgMTO + ".png").getImage();

        DestroyMeteor = new ImageIcon(System.getProperty("user.dir") + File.separator + 
            "imgProject" + File.separator + "bomb.gif").getImage();

    }
    ///////////////             Constructor             ////////////////////////////////////////////


    //////////////              Method          -///////////////////////////////////////
    public void meteorMove(int width, int height, ArrayList<Meteor> meteors) {
        
        //bounce check in X
        if (xStart <= 0 || xStart >= width - getSizeOfMeteor()) {
            MTOspeedX = -MTOspeedX;
            adjustSpeed();
            //set meteor in frame
            xStart = Math.max(0, Math.min(xStart, width - getSizeOfMeteor()));
        }
        //bounce check in Y
        if (yStart <= 0 || yStart >= height - getSizeOfMeteor()) {
            MTOspeedY = -MTOspeedY;
            adjustSpeed();
            //set meteor in frame
            yStart = Math.max(0, Math.min(yStart, height - getSizeOfMeteor()));
        }

        // bounce between Meteor
        for (Meteor MTO : meteors) {
            if (MTO != this && isColliding(MTO)) {
                resolveCollision(MTO);
            }
        }

        // update position Meteor
        xStart += MTOspeedX;
        yStart += MTOspeedY;

        // check Meteor isInframe again
        xStart = Math.max(0, Math.min(xStart, width - getSizeOfMeteor()));
        yStart = Math.max(0, Math.min(yStart, height - getSizeOfMeteor()));
    }

    // check bounce by size of meteor
    public boolean isColliding(Meteor MTO) {
        int distance = (int) Math.sqrt(Math.pow(xStart - MTO.xStart, 2) + Math.pow(yStart - MTO.yStart, 2));
        return distance < getSizeOfMeteor(); // distance = ระยะชน
    }

    // check overlap Meteor --ซ้อนกัน--
    public void resolveCollision(Meteor MTO) {
        int overlap = getSizeOfMeteor() - (int) Math.sqrt(Math.pow(xStart - MTO.xStart, 2) + Math.pow(yStart - MTO.yStart, 2));

        
        //change direction after bounce
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

        // after bounce change direction
        MTOspeedX = -MTOspeedX;
        MTO.MTOspeedX = -MTO.MTOspeedX;
        MTOspeedY = -MTOspeedY;
        MTO.MTOspeedY = -MTO.MTOspeedY;
    }

    // adjust Speed After bounce between Meteor
    public void adjustSpeed() {
        // speed
        do {
            MTOspeedX = random.nextInt(30) - 5;
        } while (MTOspeedX == 0);

        do {
            MTOspeedY = random.nextInt(30) - 5;
        } while (MTOspeedY == 0);
    }


    // check point clicked in area meteor
    public boolean isCollidingWithMouse(int mouseX, int mouseY) {
        return (mouseX >= xStart && mouseX <= xStart + 100 && 
                mouseY >= yStart && mouseY <= yStart + 100);
    }

    
    /////////////////     Geter Seter Medthod     /////////////////////
    public int getXMeto() {
        return xStart;
    }

    public int getYMeto() {
        return yStart;
    }

    public Image getMTOImage() {
        return imgMTO;
    }

    public Image getDestroyImg(){
        return DestroyMeteor;
    }

    public boolean getisDestroy(){
        return isDestroy;
    }

    public int getSizeOfMeteor(){
        return sizeOfMeteor;
    }

    public void setisDestroy(){
        isDestroy = true;
    }

    public void resetisDestroy(){
        isDestroy = false;
    }

    /////////////////     Geter Seter Medthod     /////////////////////

    //////////////              Method          -///////////////////////////////////////

}
