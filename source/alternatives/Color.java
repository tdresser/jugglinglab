package alternatives;

public class Color {
    int value;

    public static final Color white     = new Color(255, 255, 255);
    public static final Color lightGray = new Color(192, 192, 192);
    public static final Color gray      = new Color(128, 128, 128);
    public static final Color darkGray  = new Color(64, 64, 64);
    public static final Color black     = new Color(0, 0, 0);
    public static final Color red       = new Color(255, 0, 0);
    public static final Color pink      = new Color(255, 175, 175);
    public static final Color orange    = new Color(255, 200, 0);
    public static final Color yellow    = new Color(255, 255, 0);
    public static final Color green     = new Color(0, 255, 0);
    public static final Color magenta   = new Color(255, 0, 255);
    public static final Color cyan      = new Color(0, 255, 255);
    public static final Color blue      = new Color(0, 0, 255);

    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public Color(int r, int g, int b, int a) {
        value = ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF) << 0);
    }

    public Color(int rgb) {
        value = 0xff000000 | rgb;
    }

    public int getRGB() {
        return value;
    }

    public int getRed() {
        return (getRGB() >> 16) & 0xFF;
    }

    public int getGreen() {
        return (getRGB() >> 8) & 0xFF;
    }

    public int getBlue() {
        return (getRGB() >> 0) & 0xFF;
    }
    
    public int getAlpha() {
        return (getRGB() >> 24) & 0xff;
    }
}

