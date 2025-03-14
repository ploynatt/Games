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

public class SnakeN extends Pad {
    private ArrayList<Oval> body;
    private Oval head;
    private Oval orange;
    private int orangesEaten;
    private final int W = 30;
    private boolean headRight;
    private boolean headLeft;
    private boolean headUp;
    private boolean headDown;

    public SnakeN(Color a) {
        super(500, 500);
        this.setBackground((double)0.0F, (double)225.0F, (double)0.0F);
        this.makeSnake(a);
        this.makeOrange();
    }

    public void makeSnake(Color a) {
        this.body = new ArrayList();
        this.head = new Oval((double)175.0F, (double)250.0F, (double)30.0F, (double)30.0F);
        this.head.setFillColor(Color.RED);
        this.headRight = true;

        for(int i = 0; i < 3; ++i) {
            this.body.add(new Oval((double)(145 - i * 30), (double)250.0F, (double)30.0F, (double)30.0F));
            ((Oval)this.body.get(i)).setFillColor(a);
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
        int x = (int)(Math.random() * (double)441.0F + (double)30.0F);
        int y = (int)(Math.random() * (double)441.0F + (double)30.0F);

        for(this.orange = new Oval((double)x, (double)y, (double)30.0F, (double)30.0F); this.touchOrange(); this.orange = new Oval((double)x, (double)y, (double)30.0F, (double)30.0F)) {
            this.removeShape(this.orange);
            x = (int)(Math.random() * (double)441.0F + (double)30.0F);
            y = (int)(Math.random() * (double)441.0F + (double)30.0F);
        }

        this.orange.setFillColor(Color.ORANGE);
    }

    public boolean ateOrange() {
        if (this.head.intersects(this.orange)) {
            this.removeShape(this.orange);
            ++this.orangesEaten;
            this.makeOrange();
            return true;
        } else {
            return false;
        }
    }

    public void moveSnake(int changeX, int changeY) {
        double x = this.head.getX();
        double y = this.head.getY();
        this.removeShape(this.head);
        this.head = new Oval((double)changeX + x, (double)changeY + y, (double)30.0F, (double)30.0F);
        this.head.setFillColor(Color.RED);
        if (this.ateOrange()) {
            this.body.add(0, new Oval(x, y, (double)30.0F, (double)30.0F));
            ((Oval)this.body.get(0)).setFillColor(((Oval)this.body.get(1)).getFillColor());
        } else {
            Color c = ((Oval)this.body.get(this.body.size() - 1)).getFillColor();
            this.removeShape((Shape)this.body.get(this.body.size() - 1));
            this.body.remove(this.body.size() - 1);
            this.body.add(0, new Oval(x, y, (double)30.0F, (double)30.0F));
            ((Oval)this.body.get(0)).setFillColor(c);
        }

    }

    public void moveRight() {
        if (!this.headLeft) {
            this.headRight = true;
            this.headUp = false;
            this.headDown = false;
            this.moveSnake(30, 0);
        }
    }

    public void moveLeft() {
        if (!this.headRight) {
            this.headLeft = true;
            this.headUp = false;
            this.headDown = false;
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
            this.headLeft = false;
            this.headRight = false;
            this.moveSnake(0, 30);
        }
    }

    public boolean gameOver() {
        if (!(this.head.getX() + (double)15.0F > (double)500.0F) && !(this.head.getY() + (double)15.0F > (double)500.0F) && !(this.head.getX() - (double)15.0F < (double)0.0F) && !(this.head.getY() - (double)15.0F < (double)0.0F)) {
            for(int i = 1; i < this.body.size(); ++i) {
                if (this.head.intersects((Shape)this.body.get(i))) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }

    public void onKeyPressed(String keyText, String e) {
        if (!this.gameOver()) {
            if (keyText.equals("D") && !this.headRight) {
                this.moveRight();
            } else if (keyText.equals("W") && !this.headUp) {
                this.moveUp();
            } else if (keyText.equals("A") && !this.headLeft) {
                this.moveLeft();
            } else if (keyText.equals("S") && !this.headDown) {
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
            new Text("1 ORANGE EATEN!", (double)30.0F, (double)50.0F, 50);
        } else {
            new Text(this.orangesEaten + " ORANGES EATEN!", (double)30.0F, (double)50.0F, 50);
        }

    }

    public static void main(String[] args) throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {
        Clip clip = AudioSystem.getClip();
        File themePath = new File("theme.wav");
        AudioInputStream s1 = AudioSystem.getAudioInputStream(themePath);
        clip.open(s1);
        clip.start();
        SnakeN game = new SnakeN(Color.BLUE);
        game.move(clip);
    }
}
