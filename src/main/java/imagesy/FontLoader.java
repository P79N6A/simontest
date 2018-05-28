package imagesy;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FontLoader {

    protected final Lock lock = new ReentrantLock();
    protected String name;
    protected int fontType;
    protected String path;
    private volatile Font font;

    public FontLoader(String name, int fontType, String path) {
        this.name = name;
        this.fontType = fontType;
        this.path = path;
    }

    public FontLoader(String name, String path) {
        this(name, Font.TRUETYPE_FONT, path);
    }

    protected Font loadFont(String path) {
        return loadFont(Font.TRUETYPE_FONT, path);
    }

    public String getName() {
        return name;
    }

    public Font getFont() {
        if (font == null) {
            lock.lock();
            try {
                if (font == null) {
                    font = loadFont(fontType, path);
                }
            } finally {
                lock.unlock();
            }
        }

        return font;
    }

    protected Font loadFont(int fontType, String path) {
        InputStream fontStream = null;
        try {
            fontStream = FontLoader.class.getResourceAsStream(path);

            return Font.createFont(fontType, fontStream);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fontStream != null) {
                try {
                    fontStream.close();
                } catch (IOException ignore) {

                }
            }
        }
    }
}


