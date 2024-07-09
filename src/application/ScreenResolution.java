package application;
import java.awt.*;

public class ScreenResolution {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        System.out.println("Screen Resolution: " + screenWidth + "x" + screenHeight);
    }
}
