import javax.swing.ImageIcon;


public class Brick extends Objects {

    private int des;
    private boolean destroyed;

    public Brick(int x, int y) {
        des = 1;
        this.x = x;
        this.y = y;

        ImageIcon ii = new ImageIcon("image/brick.png");
        image = ii.getImage();

        i_width = image.getWidth(null);
        i_heigth = image.getHeight(null);

        destroyed = false;
    }

    public void setImg(String s){
        ImageIcon i = new ImageIcon(s);
        image = i.getImage();
    }

    public int getDes(){
        return des;
    }

    public void setDes(int x){
        des = x;
    }

    public boolean isDestroyed() {
        
        return destroyed;
    }

    public void setDestroyed(boolean val) {
        
        destroyed = val;
    }
}