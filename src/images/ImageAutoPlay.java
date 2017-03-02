package images;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.util.StringTokenizer;

public class ImageAutoPlay extends Applet implements Runnable {
    MediaTracker tracker;
    int tracked;
    int frame_rate = 5;
    int current_img = 0;
    Thread motor;
    static final int MAXIMAGES = 10;
    Image img[] = new Image[MAXIMAGES];
    String name[] = new String[MAXIMAGES];
    volatile boolean stopFlag;
    String imgName = "apple+google+panda";
    
    public void init() {
        tracker = new MediaTracker(this);
        StringTokenizer st = new StringTokenizer(imgName, "+");
        while(st.hasMoreTokens() && tracked <= MAXIMAGES) {
            name[tracked] = st.nextToken();
            img[tracked] = getImage(getDocumentBase(), name[tracked] +".jpg");
            tracker.addImage(img[tracked], tracked);
            tracked++;
        }
    }
    
    public void paint(Graphics g) {
        String loaded = "";
        int donecount = 0;
        for(int i =0; i<tracked;i++){
            if(tracker.checkID(i, true)) {
                donecount++;
                loaded += name[i] + " ";
            }
        }
        Dimension d = getSize();
        int w = (int)d.getWidth();
        int h = (int)d.getHeight();
        if (donecount == tracked) {
            frame_rate = 1;
            Image i = img[current_img++];
            int iw = i.getWidth(null);
            int ih = i.getHeight(null);
            g.drawImage(i, (w - iw)/2, (h - ih)/2, null);
            if(current_img >= tracked)
                current_img = 0;
        } else {
            int x = w * donecount / tracked;
            g.setColor(Color.black);
            g.fillRect(0, h/3, x, 16);
            g.setColor(Color.white);
            g.fillRect(x, h/3, w-x, 16);
            g.setColor(Color.black);
            g.drawString(loaded, 10, h/2);
        }
    }
    
    public void start() {
        motor = new Thread(this);
        stopFlag = false;
        motor.start();
    }
    
    public void stop() {
        stopFlag = true;
    }
    
    public void run() {
        motor.setPriority(Thread.MAX_PRIORITY);
        while(true) {
            repaint();
            try {
                Thread.sleep(1000/frame_rate);
            } catch(InterruptedException e) {
                System.out.println("Interupted");
                return;
            }
            if(stopFlag)
                return;
        }
    }
}

