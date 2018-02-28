import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Board extends JPanel
{

    private Timer timer;
    private String message = "Game over";
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private boolean ingame = true;

    public Board()
    {
        initBoard();
    }

    private void initBoard()
    {
        addKeyListener(new TAdapter());
        setFocusable(true);

        bricks = new Brick[Utils.N_OF_BRICKS];
        setDoubleBuffered(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), Utils.DELAY, Utils.PERIOD);
    }

    public void addNotify()
    {
        super.addNotify();
        gameInit();
    }

    public void gameInit()
    {
        ball = new Ball();
        paddle = new Paddle();

        int k = 0;

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 6; j++)
            {
                bricks[k] = new Brick(j * 40 + 30, i * 10 + 50);
                k++;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        if (ingame)
            drawObjects(g2d);
        else
            gameFinished(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics2D g2d)
    {
        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getI_width(), ball.getI_height(), this);
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), paddle.getI_width(), paddle.getI_height(), this);

        for (int i = 0; i < Utils.N_OF_BRICKS; i++)
        {
            if (!bricks[i].isDestroyed())
                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(), bricks[i].getY(), bricks[i].getI_width(), bricks[i].getI_height(), this);
        }
    }


    private void gameFinished(Graphics2D g2d)
    {
        Font font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics metr = getFontMetrics(font);
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(message, (Utils.WIDTH - metr.stringWidth(message)) / 2, Utils.WIDTH / 2);
    }

    private class TAdapter extends KeyAdapter
    {
        @Override
        public void keyReleased(KeyEvent e)
        {
            paddle.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            paddle.keyPressed(e);
        }
    }

    private class ScheduleTask extends TimerTask
    {

        @Override
        public void run()
        {
            ball.move();
            paddle.move();
            checkCollision();
            repaint();
        }
    }

    private void stopGame()
    {
        ingame = false;
        timer.cancel();
    }

    private void checkCollision()
    {
        if (ball.getRect().getMaxY() > Utils.BOTTOM_EDGE)
            stopGame();

        for (int i = 0, j = 0; i < Utils.N_OF_BRICKS; i++)
        {
            if (bricks[i].isDestroyed())
                j++;

            if (j == Utils.N_OF_BRICKS)
            {
                message = "Victory";
                stopGame();
            }
        }

        if (ball.getRect().intersects(paddle.getRect()))
        {
            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballPos < first)
            {
                ball.setXdir(-1);
                ball.setYdir(-1);
            }

            if (ballPos >= first && ballPos < second)
            {
                ball.setXdir(-1);
                ball.setYdir(-1 * ball.getYdir());
            }

            if (ballPos >= second && ballPos < third)
            {
                ball.setXdir(0);
                ball.setYdir(-1);
            }

            if (ballPos >= third && ballPos < fourth)
            {
                ball.setXdir(0);
                ball.setYdir(-1 * ball.getYdir());
            }

            if (ballPos > fourth)
            {
                ball.setXdir(1);
                ball.setYdir(-1);
            }

        }

        for (int i = 0; i < Utils.N_OF_BRICKS; i++)
        {
            if (ball.getRect().intersects(bricks[i].getRect()))
            {
                int ballLeft = (int) ball.getRect().getMinX();
                int ballHeight = (int) ball.getRect().getHeight();
                int ballWidth = (int) ball.getRect().getWidth();
                int ballTop = (int) ball.getRect().getMinY();

                Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                Point pointLeft = new Point(ballLeft - 1, ballTop);
                Point pointTop = new Point(ballLeft, ballTop - 1);
                Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                if (!bricks[i].isDestroyed())
                {
                    if (bricks[i].getRect().contains(pointRight))
                        ball.setXdir(-1);
                    else if (bricks[i].getRect().contains(pointLeft))
                        ball.setXdir(1);

                    if (bricks[i].getRect().contains(pointTop))
                        ball.setYdir(1);
                    else if (bricks[i].getRect().contains(pointBottom))
                        ball.setYdir(-1);

                    bricks[i].setDestroyed(true);
                }

            }
        }
    }

}