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

public class Snake extends Pad {
    private ArrayList<Oval> body = new ArrayList();
    private Oval head;
    private Oval orange;
    private int eatenOranges;
    private static final int WIDTH = 30;
    private boolean headRight;
    private boolean headLeft;
    private boolean headUp;
    private boolean headDown;

    public Snake(Color c) {
        super(500, 500);
        this.setBackground((double)0.0F, (double)255.0F, (double)0.0F);
        this.makeSnake(c);
        this.makeOrange();
    }

    public void makeSnake(Color c) {
        this.head = new Oval((double)235.0F, (double)235.0F, (double)30.0F, (double)30.0F);
        this.head.setFillColor((double)0.0F, (double)0.0F, (double)255.0F);
        this.headRight = true;

        for(int i = 1; i <= 3; ++i) {
            Oval temp = new Oval((double)(235 - 30 * i), (double)235.0F, (double)30.0F, (double)30.0F);
            temp.setFillColor(c);
            this.body.add(temp);
        }

    }

    public boolean touchOrange() {
        if (this.head.intersects(this.orange)) {
            return true;
        } else {
            for(Oval i : this.body) {
                if (i.intersects(this.orange)) {
                    return true;
                }
            }

            return false;
        }
    }

    public void makeOrange() {
        int rx = (int)(Math.random() * (double)471.0F) + 15;
        int ry = (int)(Math.random() * (double)471.0F) + 15;
        this.orange = new Oval((double)rx, (double)ry, (double)30.0F, (double)30.0F);
        if (this.touchOrange()) {
            this.removeShape(this.orange);
            this.makeOrange();
        } else {
            this.orange.setFillColor(Color.ORANGE);
        }

    }

    public boolean ateOrange() {
        if (this.head.intersects(this.orange)) {
            ++this.eatenOranges;
            this.removeShape(this.orange);
            return true;
        } else {
            return false;
        }
    }

    public void moveSnake(int changeX, int changeY) {
        double headX = this.head.getX();
        double headY = this.head.getY();
        this.head.move((double)changeX, (double)changeY);
        if (this.ateOrange()) {
            Oval a = new Oval(headX, headY, (double)30.0F, (double)30.0F);
            a.setFillColor(((Oval)this.body.get(1)).getFillColor());
            this.body.add(0, a);
            this.makeOrange();
        } else {
            Oval a = (Oval)this.body.remove(this.body.size() - 1);
            a.setX(headX);
            a.setY(headY);
            this.body.add(0, a);
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
            this.headLeft = true;
            this.headUp = false;
            this.headDown = false;
            this.moveSnake(-30, 0);
        }
    }

    public void moveUp() {
        if (!this.headDown) {
            this.headLeft = false;
            this.headUp = true;
            this.headRight = false;
            this.moveSnake(0, -30);
        }
    }

    public void moveDown() {
        if (!this.headUp) {
            this.headLeft = false;
            this.headDown = true;
            this.headRight = false;
            this.moveSnake(0, 30);
        }
    }

    public boolean gameOver() {
        if (!(this.head.getLocation().getX() > (double)470.0F) && !(this.head.getLocation().getX() < (double)15.0F) && !(this.head.getLocation().getY() < (double)15.0F) && !(this.head.getLocation().getX() > (double)470.0F)) {
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
        if (keyText.equals("↑")) {
            this.moveUp();
        }

        if (keyText.equals("↓")) {
            this.moveDown();
        }

        if (keyText.equals("←")) {
            this.moveLeft();
        }

        if (keyText.equals("→")) {
            this.moveRight();
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

        this.setEventsEnabled(false);
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
        if (this.eatenOranges == 1) {
            new Text("1 ORANGE EATEN!", (double)30.0F, (double)50.0F, 50);
        } else {
            new Text(this.eatenOranges + " ORANGES EATEN!", (double)30.0F, (double)50.0F, 50);
        }

    }

    public static void main(String[] args) throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {
        Clip clip = AudioSystem.getClip();
        File themePath = new File("theme.wav");
        AudioInputStream s1 = AudioSystem.getAudioInputStream(themePath);
        clip.open(s1);
        clip.start();
        Color bodyC = new Color(93, 200, 253);
        Snake game = new Snake(bodyC);
        game.move(clip);
    }
}
