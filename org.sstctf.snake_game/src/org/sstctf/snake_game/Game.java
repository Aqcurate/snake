package org.sstctf.snake_game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.sql.SQLException;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -4394636783234289567L;
    
    public static final int WIDTH = 640, HEIGHT = 640; 
    public static final int SCALE = 16;
    public static final String NAME = "SNAKE";
    
    private Thread thread;
    private volatile boolean running = false;
    private Handler handler;
    private HUD hud;

    private DBConnect db;
    private boolean hasDB;
    private String leaderboard;
    
    public static State gameState = State.	GAME;
    
    public Game() {
        handler = new Handler();
        hud = new HUD(handler);
        db = new DBConnect();
        hasDB = true;
        leaderboard = "";
        setIgnoreRepaint(true);
        setBounds(0, 0, WIDTH, HEIGHT+100);
        
        this.addKeyListener(new KeyInput(handler, this));
        
        new Window(WIDTH, HEIGHT, NAME, this);
        handler.addObject(new Board(0, 0, handler));
        handler.addObject(new Snake(1, 1, handler, this));
        handler.addObject(new Pellet(5, 5, handler));
    }
    
    public void onDeath() {
        gameState = State.DEATH;
        if (hasDB) {
            try {
                db.update(hud.getScore());
                leaderboard = db.getScores(hud);
            } catch (SQLException s) {
                hasDB = false;
                System.out.println(s.getMessage());
            }
        }
    }
    
    // Start the thread
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    // Stop the thread
    public synchronized void stop() {
        running = false;
    }

    // Game Loop
    public void run() {
        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 15.0;
        double ns = 1000000000 / amountOfTicks; // Amount of times our ticks go into 1 second
        double delta = 0.0;

        while (running) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / ns;
            lastTime = currentTime;

            // While one tick of time has passed
            while (delta >= 1) {
                tick();
                delta--;
            }

            if (running) render();
        }
        
        stop();
    }
    
    private void tick() {
        if (gameState == State.GAME) {
            handler.tick();
            hud.tick();
        } 
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3); // Triple buffering
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        // Background
        // g.setColor(Color.WHITE);
        // g.fillRect(0, 0, WIDTH, HEIGHT);

        if (gameState == State.GAME) {
            handler.render(g);
            hud.render(g);
        } else if (gameState == State.DEATH) {
            // TODO: Move menu to another class
            g.setColor(Color.BLACK);
            g.drawString("Press R to Retry", 16, 16);
            
            int leaderboardY = 48;
            g.drawString("Final Score: " + hud.getScore(), 16, 32);
            g.drawString("Leaderboard:", 16, leaderboardY +=16);
            g.drawString("Name - Score", 16, leaderboardY +=16);
            for (String line : leaderboard.split("\n")) {
                g.drawString(line, 16, leaderboardY += 16);
            }
        }
        
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Game();
    }
}
