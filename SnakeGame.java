import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SnakeGame extends JPanel implements KeyListener, Runnable {

    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int DOT_SIZE = 10;
    private static final int ALL_DOTS = (WIDTH * HEIGHT) / (DOT_SIZE * DOT_SIZE);
    private static final int RAND_POS = 29;
    private static final int DELAY = 100;

    private  List<Point> snake;
    private Point apple;
    private int direction;
    private boolean inGame;
    private Thread animator;
    private int score;

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        initGame();
    }

    private void initGame() {
        snake = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            snake.add(new Point(50 - i * DOT_SIZE, 50));
        }
        locateApple();
        direction = KeyEvent.VK_RIGHT;
        inGame = true;
        animator = new Thread(this);
        animator.start();
        score = 0;
    }

    private void locateApple() {
        int r = new Random().nextInt(RAND_POS);
        apple = new Point((r * DOT_SIZE), (r * DOT_SIZE));
    }

    private void checkApple() {
        if (snake.get(0).equals(apple)) {
            snake.add(new Point(-1, -1));
            locateApple();
            score += 10;
        }
    }

    private void move() {
        for (int i = snake.size() - 1; i > 0; i--) {
            snake.set(i, snake.get(i - 1));
        }
        switch (direction) {
            case KeyEvent.VK_LEFT:
                snake.set(0, new Point(snake.get(0).x - DOT_SIZE, snake.get(0).y));
                break;
            case KeyEvent.VK_RIGHT:
                snake.set(0, new Point(snake.get(0).x + DOT_SIZE, snake.get(0).y));
                break;
            case KeyEvent.VK_UP:
                snake.set(0, new Point(snake.get(0).x, snake.get(0).y - DOT_SIZE));
                break;
            case KeyEvent.VK_DOWN:
                snake.set(0, new Point(snake.get(0).x, snake.get(0).y + DOT_SIZE));
                break;
        }
    }

    private void checkCollision() {
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).equals(snake.get(i))) {
                inGame = false;
            }
        }
        if (snake.get(0).x < 0 || snake.get(0).x >= WIDTH || snake.get(0).y < 0 || snake.get(0).y >= HEIGHT) {
            inGame = false;
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        g.setColor(Color.WHITE);
        g.drawString(msg, (WIDTH - g.getFontMetrics().stringWidth(msg)) / 2, HEIGHT / 2);
        g.drawString("Score: " + score, (WIDTH - g.getFontMetrics().stringWidth("Score: " + score)) / 2, HEIGHT / 2 + 20);
        animator.interrupt();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.setColor(Color.RED);
            g.fillOval(apple.x, apple.y, DOT_SIZE, DOT_SIZE);
            g.setColor(Color.GREEN);
            for (Point p : snake) {
                g.fillOval(p.x, p.y, DOT_SIZE, DOT_SIZE);
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics metrics = g.getFontMetrics();
            g.drawString("Score: " + score, WIDTH - metrics.stringWidth("Score: " + score) - 10, 20);
        } else {
            gameOver(g);
        }
    }

    @Override
    public void run() {
        while (inGame) {
            move();
            checkApple();
            checkCollision();
            repaint();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && direction != KeyEvent.VK_RIGHT) {
            direction = KeyEvent.VK_LEFT;
        } else if (key == KeyEvent.VK_RIGHT && direction != KeyEvent.VK_LEFT) {
            direction = KeyEvent.VK_RIGHT;
        } else if (key == KeyEvent.VK_UP && direction != KeyEvent.VK_DOWN) {
            direction = KeyEvent.VK_UP;
        } else if (key == KeyEvent.VK_DOWN && direction != KeyEvent.VK_UP) {
            direction = KeyEvent.VK_DOWN;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new SnakeGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}