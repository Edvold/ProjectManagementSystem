import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@FunctionalInterface
public interface KeyPressedListener extends KeyListener {

    @Override
    default void keyReleased(KeyEvent e) {
        //not used
    }

    @Override
    default void keyTyped(KeyEvent e) {
        //not used
    }

}
