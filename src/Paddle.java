import javax.swing.*;
import java.awt.event.KeyEvent;

public class Paddle extends Sprite
{

    private int dx;

    public Paddle()
    {
        ImageIcon ii = new ImageIcon(getClass().getResource("res/paddle.png"));
        image = ii.getImage();
        i_width = image.getWidth(null);
        i_height = image.getHeight(null);

        resetState();
    }

    public void move()
    {
        x += dx;

        if (x < 0)
            x = 0;

        if (x >= Utils.WIDTH - i_width)
            x = Utils.WIDTH - i_width;

    }

//    public void mouseMoved(MouseEvent e)
//    {
//
//    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
            dx = -1;

        if (key == KeyEvent.VK_RIGHT)
            dx = 1;
    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
            dx = 0;

        if (key == KeyEvent.VK_RIGHT)
            dx = 0;
    }

    public void resetState()
    {
        x = Utils.INIT_PADDLE_X;
        y = Utils.INIT_PADDLE_Y;
    }

}
