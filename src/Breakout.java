import javax.swing.*;
import java.awt.*;

public class Breakout extends JFrame
{
    public Breakout()
    {
        initUI();
    }

    private void initUI()
    {
        add(new Board());
        setTitle("Novikov Breakout");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Utils.WIDTH,Utils.HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(() ->
        {
            Breakout game = new Breakout();
            game.setVisible(true);
        });
    }
}
