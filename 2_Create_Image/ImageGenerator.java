import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class ImageGenerator extends Applet {
    Image img;
    public void init() {
		// getting the screen size
        Dimension d = getSize();
        int w = (int)d.getWidth();
        int h = (int)d.getHeight();
		// int array to hold pixel color
        int pixels[] = new int[w * h];
        int i = 0;
        
		// generating r, g, b value in a sequence and filling pixel with that value
        for(int y=0; y<h; y++) {
            for(int x=0; x<w; x++) {
                int r = (x^y)&0xff;
                int g = (x*2^y*2)&0xff;
                int b = (x*4^y*4)&0xff;
                pixels[i++] = (255 << 24) | (r << 16) | (g << 8) | b;
            }
        }
		// creating image from MemoryImageSource constructor
        img = createImage(new MemoryImageSource(w, h, pixels, 0, w));
    }
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, this);
    }
}
