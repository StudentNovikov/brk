import javax.swing.*;

public class Ball extends Sprite
{

    private int xdir, ydir;

    public Ball()
    {
        xdir = 1;
        ydir = -1;
        ImageIcon ii = new ImageIcon(getClass().getResource("res/ball.png"));
        image = ii.getImage();
        i_width = image.getWidth(null);
        i_height = image.getHeight(null);

        resetState();
    }

    public void move()
    {
        x += xdir;
        y += ydir;

        if (x == 0)
            setXdir(1);

        if (x == Utils.WIDTH - i_width)
            setXdir(-1);

        if (y == 0)
            setYdir(1);
    }

    public int getXdir()
    {
        return xdir;
    }

    public void setXdir(int xdir)
    {
        this.xdir = xdir;
    }

    public int getYdir()
    {
        return ydir;
    }

    public void setYdir(int ydir)
    {
        this.ydir = ydir;
    }

    private void resetState()

    {
        x = Utils.INIT_BALL_X;
        y = Utils.INIT_BALL_Y;
    }

}
