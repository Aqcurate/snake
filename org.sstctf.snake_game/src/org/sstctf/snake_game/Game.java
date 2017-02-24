package org.sstctf.snake_game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * The main class of the snake_game package.
 * It is a canvas that can be added to a frame.
 * It is creates a thread on start that executes the
 * game loop.
 * The class controls the rendering and updating of all
 * game objects.
 * 
 * @author Andrew Quach
 * @author Stanislav Lyakhov
 *
 * @version 1.0.0
 */
public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -4394636783234289567L;

    public static final int WIDTH = 640, HEIGHT = 640; 
    public static final int SCALE = 16;
    public static final String NAME = "SNAKE";

    private Thread thread;
    private volatile boolean running = false;
    private Handler handler;
    private HUD hud;
    private DeathScreen deathScreen;

    public static State gameState = State.GAME;

    /**
     * Creates new handler, hud, and deathScreen objects.
     * Sets boundaries and repaint options for canvas.
     * Adds the key listener from KeyInput class.
     * Creates the main window.
     * Adds the main game objects.
     */
    public Game() {
        // New handler, hud, and death screen objects
        handler = new Handler();
        hud = new HUD(handler);
        deathScreen = new DeathScreen(hud);
        // Set canvas options
        setIgnoreRepaint(true);
        setBounds(0, 0, WIDTH, HEIGHT+100);
        // Add key listener
        this.addKeyListener(new KeyInput(handler));
        // Create new window
        new Window(WIDTH, HEIGHT, NAME, this);
        // Add main game objects
        handler.addObject(new Board(0, 0));
        handler.addObject(new Snake(1, 1, handler, this));
        handler.addObject(new Pellet(5, 5, handler));
    }

    /**
     * Delegates onDeath actions to deathScreen object.
     */
    public void onDeath() {
        deathScreen.onDeath();
    }

    /**
     * Create a new thread, start it, and set
     * the running state to true.
     */
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    /**
     * Set the running state to false to
     * stop the thread.
     */
    public synchronized void stop() {
        running = false;
    }

    /**
     * Override of run() in runnable that executes on thread start.
     * Requests the window focus on start.
     * Main game loop that executes tick() every 15 seconds.
     * Continuously renders by calling render() while game is running. 
     */
    @Override
    public void run() {
        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 15.0;
        // Amount of times our ticks go into 1 second
        double ns = 1000000000 / amountOfTicks;
        double delta = 0.0;

        while (running) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / ns;
            lastTime = currentTime;

            // While one tick of time has passed
            while (delta >= 1) {
                // Run tick() every tick
                tick();
                delta--;
            }
            // Render everything if game is still running
            if (running) render();
        }

        stop();
    }

    /**
     * Call the tick methods of handler and hud
     * while the game state is GAME.
     */
    private void tick() {
        if (gameState == State.GAME) {
            handler.tick();
            hud.tick();
        } 
    }

    /**
     * Render method that controls all rendering in the game.
     * Uses a bufferstrategy to prevent unnecessary flickering.
     * Controls which classes gets to render depending on game state.
     */
    private void render() {
        // Get a buffer strategy from canvas if it exists
        BufferStrategy bs = this.getBufferStrategy();
        // Else create a buffer strategy
        if (bs == null) {
            // Triple buffering
            this.createBufferStrategy(3);
            return;
        }
        // Create graphics object from buffer strategy
        Graphics g = bs.getDrawGraphics();

        // Render handler and hud if the state is GAME
        if (gameState == State.GAME) {
            handler.render(g);
            hud.render(g);
        // Render deathScreen is the state is DEATH
        } else if (gameState == State.DEATH) {
            deathScreen.render(g);
        }

        g.dispose();
        // Display the buffer
        bs.show();
    }

    /**
     * Main method responsible of creating the game object.
     */
    public static void main(String[] args) {
        new Game();
    }
}
