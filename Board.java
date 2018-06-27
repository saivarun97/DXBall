import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;


public class Board extends JPanel implements Dimensions {

    private Timer timer;
    private String message = "Game Over";
    private Ball ball;
    private Paddle paddle;
    private Brick bricks[];
    private boolean ingame = true;



    public Board() {
        Sound.sound.loop();
        setBackground(Color.BLACK);
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);

        bricks = new Brick[N_OF_BRICKS];
        setDoubleBuffered(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), DELAY, PERIOD);
    }

    @Override
    public void addNotify() {

        super.addNotify();
        gameInit();
    }

    private void gameInit() {

        ball = new Ball();
        paddle = new Paddle();

        int k = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                bricks[k] = new Brick(j * 72 + (j+1)*15, i * 44 + 60);
                if(i==5){
                    bricks[k].setDes(2);
                    bricks[k].setImg("image/brick1.png");
                }
                k++;
            }
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        if (ingame) {
            
            drawObjects(g2d);
        } else {

            gameFinished(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }
    
    private void drawObjects(Graphics2D g2d) {
        
        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),
                ball.getWidth(), ball.getHeight(), this);
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
                paddle.getWidth(), paddle.getHeight(), this);

        for (int i = 0; i < N_OF_BRICKS; i++) {
            if (!bricks[i].isDestroyed()) {
                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(),
                        bricks[i].getY(), bricks[i].getWidth(),
                        bricks[i].getHeight(), this);
            }
        }
    }
    
    private void gameFinished(Graphics2D g2d) {

        Font font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics metr = this.getFontMetrics(font);

        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString(message,
                (Dimensions.WIDTH - metr.stringWidth(message)) / 2,
                Dimensions.WIDTH / 2);
        Sound.sound.stop();
        Sound.sound1.play();

    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            paddle.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            paddle.keyPressed(e);
        }
    }

    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {

            ball.move();
            paddle.move();
            checkCollision();
            repaint();
        }
    }

    private void stopGame() {

        ingame = false;
        timer.cancel();
    }

    private void checkCollision() {

        if (ball.getRect().getMaxY() > Dimensions.BOTTOM_EDGE) {
            stopGame();
        }

        for (int i = 0, j = 0; i < N_OF_BRICKS; i++) {
            
            if (bricks[i].isDestroyed()) {
                j++;
            }
            
            if (j == N_OF_BRICKS) {
                message = "Victory";
                stopGame();
                Sound.sound.stop();
                Sound.sound2.play();
            }
        }

        if ((ball.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 24;
            int second = paddleLPos + 48;
            int third = paddleLPos + 72;
            int fourth = paddleLPos + 96;

            if (ballLPos < first) {
                ball.setXDir(-1);
                ball.setYDir(-1);
            }

            if (ballLPos >= first && ballLPos < second) {
                ball.setXDir(-1);
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos >= second && ballLPos < third) {
                ball.setXDir(0);
                ball.setYDir(-1);
            }

            if (ballLPos >= third && ballLPos < fourth) {
                ball.setXDir(1);
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos > fourth) {
                ball.setXDir(1);
                ball.setYDir(-1);
            }
        }

        for (int i = 0; i < N_OF_BRICKS; i++) {
            
            if ((ball.getRect()).intersects(bricks[i].getRect())) {

                int ballLeft = (int) ball.getRect().getMinX();
                int ballHeight = (int) ball.getRect().getHeight();
                int ballWidth = (int) ball.getRect().getWidth();
                int ballTop = (int) ball.getRect().getMinY();

                Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                Point pointLeft = new Point(ballLeft - 1, ballTop);
                Point pointTop = new Point(ballLeft, ballTop - 1);
                Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                if (!bricks[i].isDestroyed()) {
                    if (bricks[i].getRect().contains(pointRight)) {
                        ball.setXDir(-1);
                    } else if (bricks[i].getRect().contains(pointLeft)) {
                        ball.setXDir(1);
                    }

                    if (bricks[i].getRect().contains(pointTop)) {
                        ball.setYDir(1);
                    } else if (bricks[i].getRect().contains(pointBottom)) {
                        ball.setYDir(-1);
                    }
                    int y = bricks[i].getDes()-1;
                    bricks[i].setDes(y);
                    if(bricks[i].getDes()==1)
                        bricks[i].setImg("image/brick.png");

                    if(bricks[i].getDes()==0)
                        bricks[i].setDestroyed(true);
                }
            }
        }
    }
}