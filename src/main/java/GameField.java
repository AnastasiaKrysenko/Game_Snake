import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;

    private Image dot;
    private Image apple;

    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int [] appleX = new int[3];
    private int [] appleY = new int[3];
    private int dots;
    public Timer timer;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private boolean inGame = true;

    public void loadImage(){
        ImageIcon iia = new ImageIcon("src/main/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iid = new ImageIcon("src/main/resources/dot.png");
        dot = iid.getImage();
    }
    public void createApple(){
        Random random = new Random();
       /* appleX = random.nextInt(20) * DOT_SIZE;
        appleY = random.nextInt(20) * DOT_SIZE;*/
        /*int [] appleX = new int[3];
        int [] appleY = new int[3];*/
        for (int i = 0; i < 3; i++) {
           appleX[i] = random.nextInt(20) * DOT_SIZE;
           appleY[i] = random.nextInt(20) * DOT_SIZE;}
    }
    public void initGame(){
        dots = 3;
        for (int i = 0; i <dots ; i++) {
            y[i] = 48;
            x[i] = 48 - i*DOT_SIZE;
        }
        timer = new Timer(170,this);
        timer.start();
        createApple();
    }
    public void checkApple(){
        for (int i = 0; i < 3; i++) {
            if (x[0] == appleX[i] && y[0] == appleY[i]){
                dots++;
                createApple();
            }
        }

    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(inGame){
            for (int i = 0; i < 3; i++) {
                g.drawImage(apple,appleX[i],appleY[i],this);
            }
            for (int j = 0; j < dots; j++) {
                g.drawImage(dot, x[j], y[j],this);
            }
        }else {
            String str = "Game Over";
            g.setColor(Color.cyan);
            g.drawString(str,SIZE/6,SIZE/2);
        }
    }
    public void checkCollision(){
        for (int i = dots; i > 0 ; i--) {
            if (x[0]==x[i]&&y[0]==y[i]){
                inGame = false;
            }
        }
        if(x[0]>SIZE)
            x[0] = 0;
        if(x[0]<0)
            x[0] = SIZE;
        if (y[0]>SIZE)
            inGame = false;
        if(y[0]<0)
            inGame = false;

        /*if (y[0]>SIZE)
             inGame = false;
        if(y[0]<0)
            inGame = false;*/
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if (inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    public GameField(){
        setBackground(Color.black);
        loadImage();
        initGame();
        addKeyListener(new FiledKeyListener());
        setFocusable(true);
    }
    public void move(){
        for (int i = dots; i > 0 ; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left)
            x[0] -= DOT_SIZE;
        if (right)
            x[0] += DOT_SIZE;
        if (up)
            y[0] -= DOT_SIZE;
        if (down)
            y[0] += DOT_SIZE;
    }
    class FiledKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent k){
            super.keyPressed(k);
            int key = k.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                right = false;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                right = false;
                left = false;
            }

        }

    }

}

