import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
// Written by Bjarke Bak Jensen s214957
public interface MouseClickedListener extends MouseListener {
    // Written by Bjarke Bak Jensen s214957

    @Override
    public default void mouseEntered(MouseEvent e) {}
    // Written by Bjarke Bak Jensen s214957
    @Override
    public default void mouseExited(MouseEvent e) {}
    // Written by Bjarke Bak Jensen s214957
    @Override
    public default void mousePressed(MouseEvent e) {}
    // Written by Bjarke Bak Jensen s214957
    @Override
    public default void mouseReleased(MouseEvent e) {}
}
