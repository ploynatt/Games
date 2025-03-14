package uk.ac.nulondon;

import doodlepad.Oval;
import doodlepad.Pad;
import doodlepad.Shape;
import doodlepad.Text;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SnakeE extends Pad {
    private ArrayList<Oval> body;
    private Oval head;
    private Oval orange;
    private int orangesEaten;
    private static final int WIDTH = 30;
    private boolean headRight;
    private boolean headLeft;
    private boolean headUp;
    private boolean headDown;

    public SnakeE(Color c) {
        super(500, 500);
        this.setBackground((double)10.0F, (double)247.0F, (double)101.0F);
        this.makeSnake(c);
        this.makeOrange();
    }

    public void makeSnake(Color c) {
        int x = 250;
        int y = 250;
        this.head = new Oval((double)x, (double)y, (double)30.0F, (double)30.0F);
        this.head.setFillColor(Color.RED);
        this.headRight = true;
        this.body = new ArrayList();

        for(int i = 0; i < 3; ++i) {
            x -= 30;
            Oval o = new Oval((double)x, (double)y, (double)30.0F, (double)30.0F);
            o.setFillColor(c);
            this.body.add(o);
        }

    }

    public boolean touchOrange() {
        if (this.head.intersects(this.orange)) {
            return true;
        } else {
            for(int i = 0; i < this.body.size(); ++i) {
                if (((Oval)this.body.get(i)).intersects(this.orange)) {
                    return true;
                }
            }

            return false;
        }
    }

    public void makeOrange() {
        int randX = (int)(Math.random() * (double)470.0F);
        int ranY = (int)(Math.random() * (double)470.0F);
        this.orange = new Oval((double)randX, (double)ranY, (double)30.0F, (double)30.0F);
        this.orange.setFillColor(Color.orange);

        while(this.touchOrange()) {
            this.orange.setX((double)((int)(Math.random() * (double)470.0F)));
            this.orange.setY((double)((int)(Math.random() * (double)470.0F)));
        }

    }

    public boolean ateOrange() {
        if (this.head.intersects(this.orange)) {
            ++this.orangesEaten;
            this.removeShape(this.orange);
            this.makeOrange();
            return true;
        } else {
            return false;
        }
    }

    public void moveSnake(int changeX, int changeY) {
        double headX = this.head.getX();
        double headY = this.head.getY();
        this.head.setX(headX + (double)changeX);
        this.head.setY(headY + (double)changeY);
        if (this.ateOrange()) {
            Oval added = new Oval(headX, headY, (double)30.0F, (double)30.0F);
            added.setFillColor(((Oval)this.body.get(0)).getFillColor());
            this.body.add(0, added);
        } else {
            this.removeShape((Shape)this.body.remove(this.body.size() - 1));
            Oval newCircle = new Oval(headX, headY, (double)30.0F, (double)30.0F);
            newCircle.setFillColor(((Oval)this.body.get(0)).getFillColor());
            this.body.add(0, newCircle);
        }

    }

    public void moveRight() {
        if (!this.headLeft) {
            this.headUp = false;
            this.headDown = false;
            this.headRight = true;
            this.moveSnake(30, 0);
        }

    }

    public void moveLeft() {
        if (!this.headRight) {
            this.headUp = false;
            this.headDown = false;
            this.headLeft = true;
            this.moveSnake(-30, 0);
        }

    }

    public void moveUp() {
        if (!this.headDown) {
            this.headUp = true;
            this.headRight = false;
            this.headLeft = false;
            this.moveSnake(0, -30);
        }

    }

    public void moveDown() {
        if (!this.headUp) {
            this.headDown = true;
            this.headRight = false;
            this.headLeft = false;
            this.moveSnake(0, 30);
        }

    }

    public boolean gameOver() {
        if (!(this.head.getX() <= (double)0.0F) && !(this.head.getX() >= (double)500.0F) && !(this.head.getY() <= (double)0.0F) && !(this.head.getY() >= (double)500.0F)) {
            for(int i = 0; i < this.body.size(); ++i) {
                if (this.head.intersects((Shape)this.body.get(i)) && i != 0) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }

    public void onKeyPressed(String keyText, String e) {
        boolean var = false;
        if (!this.gameOver()) {
            if (keyText.equalsIgnoreCase("D") && !var) {
                this.moveRight();
            } else if (keyText.equalsIgnoreCase("A") && !var) {
                this.moveLeft();
            } else if (keyText.equalsIgnoreCase("W") && !var) {
                this.moveUp();
            } else if (keyText.equalsIgnoreCase("S") && !var) {
                this.moveDown();
            }
        }

    }

    public void move(Clip clip) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        for(; !this.gameOver(); TimeUnit.MILLISECONDS.sleep(250L)) {
            if (this.headRight) {
                this.moveRight();
            } else if (this.headLeft) {
                this.moveLeft();
            } else if (this.headUp) {
                this.moveUp();
            } else if (this.headDown) {
                this.moveDown();
            }
        }

        clip.stop();
        clip.close();
        File gameOverPath = new File("gameover.wav");
        AudioInputStream s2 = AudioSystem.getAudioInputStream(gameOverPath);
        clip.open(s2);
        clip.start();
        TimeUnit.SECONDS.sleep(2L);
        clip.stop();
        clip.close();
        this.setEventsEnabled(false);
        if (this.orangesEaten == 1) {
            new Text("1 ORANGE EATEN!", (double)15.0F, (double)50.0F, 50);
        } else {
            new Text(this.orangesEaten + " ORANGES EATEN!", (double)15.0F, (double)40.0F, 50);
        }

    }

    public static void main(String[] args) throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {
        Clip clip = AudioSystem.getClip();
        File themePath = new File("theme.wav");
        AudioInputStream s1 = AudioSystem.getAudioInputStream(themePath);
        clip.open(s1);
        clip.start();
        Color bodyC = new Color(93, 200, 253);
        SnakeE game = new SnakeE(bodyC);
        game.move(clip);
    }
}
