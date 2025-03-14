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

public class SnakeA extends Pad {
    private ArrayList<Oval> body = new ArrayList();
    private Oval head;
    private Oval orange;
    private int orangesEaten;
    private static final int WIDTH = 30;
    private boolean headRight = true;
    private boolean headLeft;
    private boolean headUp;
    private boolean headDown;

    public SnakeA(Color c) {
        super(480, 480);
        this.setBackground((double)0.0F, (double)225.0F, (double)0.0F);
        this.makeSnake(c);
        this.makeOrange();
    }

    public void makeSnake(Color c) {
        this.head = new Oval((double)(this.getPadWidth() / 2), (double)(this.getPadWidth() / 2), (double)30.0F, (double)30.0F);
        this.head.setFillColor(Color.BLUE);

        for(int i = 1; i < 4; ++i) {
            Oval a = new Oval((double)(this.getPadWidth() / 2 - i * 30), (double)(this.getPadWidth() / 2), (double)30.0F, (double)30.0F);
            System.out.println(a.getX());
            a.setFillColor(c);
            this.body.add(0, a);
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
        int x = (int)(Math.random() * (double)this.getPadWidth() / (double)30.0F) * 30;
        int y = (int)(Math.random() * (double)this.getPadWidth() / (double)30.0F) * 30;
        this.orange = new Oval((double)x, (double)y, (double)30.0F, (double)30.0F);

        while(this.touchOrange()) {
            x = (int)(Math.random() * (double)this.getPadWidth() / (double)30.0F) * 30;
            y = (int)(Math.random() * (double)this.getPadWidth() / (double)30.0F) * 30;
            this.orange.setX((double)x);
            this.orange.setY((double)y);
        }

        this.orange.setFillColor(Color.ORANGE);
    }

    public boolean ateOrange() {
        if (this.head.intersects(this.orange)) {
            ++this.orangesEaten;
            this.removeShape(this.orange);
            return true;
        } else {
            return false;
        }
    }

    public void moveSnake(int changeX, int changeY) {
        double x = this.head.getX();
        double y = this.head.getY();
        this.head.setX(x + (double)changeX);
        this.head.setY(y + (double)changeY);
        Color r = ((Oval)this.body.get(0)).getFillColor();
        if (this.ateOrange()) {
            Oval o = new Oval(x, y, (double)30.0F, (double)30.0F);
            o.setFillColor(r);
            this.body.add(o);
            this.makeOrange();
        } else {
            Oval a = (Oval)this.body.remove(0);
            a.setX(x);
            a.setY(y);
            this.body.add(a);
        }

    }

    public void moveRight() {
        if (this.headLeft) {
            this.headRight = false;
        } else {
            this.headRight = true;
            this.headUp = false;
            this.headDown = false;
            this.moveSnake(30, 0);
        }

    }

    public void moveLeft() {
        if (this.headRight) {
            this.headLeft = false;
        } else {
            this.headLeft = true;
            this.headUp = false;
            this.headDown = false;
            this.moveSnake(-30, 0);
        }

    }

    public void moveUp() {
        if (this.headDown) {
            this.headUp = false;
        } else {
            this.headUp = true;
            this.headLeft = false;
            this.headRight = false;
            this.moveSnake(0, -30);
        }

    }

    public void moveDown() {
        if (this.headUp) {
            this.headDown = false;
        } else {
            this.headDown = true;
            this.headLeft = false;
            this.headRight = false;
            this.moveSnake(0, 30);
        }

    }

    public boolean gameOver() {
        if (!(this.head.getX() < (double)0.0F) && !(this.head.getY() < (double)0.0F) && !(this.head.getX() > (double)this.getPadWidth()) && !(this.head.getY() > (double)this.getPadWidth())) {
            for(int i = this.body.size() - 3; i >= 0; --i) {
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
        if (!keyText.equals("X") && !keyText.equals("x")) {
            if (!keyText.equals("A") && !keyText.equals("a")) {
                if (!keyText.equals("W") && !keyText.equals("w")) {
                    if (keyText.equals("S") || keyText.equals("s")) {
                        this.moveDown();
                    }
                } else {
                    this.moveUp();
                }
            } else {
                this.moveLeft();
            }
        } else {
            this.moveRight();
        }

    }

    public void move(Clip clip) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        for(; !this.gameOver(); TimeUnit.MILLISECONDS.sleep(550L)) {
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
        Color bodyC = new Color(93, 200, 253);
        SnakeA game = new SnakeA(bodyC);
        game.move(clip);
    }
}
