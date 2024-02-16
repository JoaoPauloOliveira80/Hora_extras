package application.utils;

import java.awt.Window;
import java.time.Duration;

import javax.swing.JFrame;

public class Utilitarios {
    public  void fecharTodasJFrames() {
        for (Window window : Window.getWindows()) {
            if (window instanceof JFrame) {
                window.dispose();
            }
        }
    }
    
    
    
}
