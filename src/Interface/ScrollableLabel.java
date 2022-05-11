package Interface;

import javax.swing.*;
import java.awt.*;

public class ScrollableLabel extends JLabel implements Scrollable {

    public ScrollableLabel(ImageIcon i) {
        super(i);
    }

    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();

    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    public int getScrollableBlockIncrement(Rectangle r, int orietation, int direction) {
        return 10;

    }

    public boolean getScrollableTracksViewportHeight() {
        return true;

    }

    public boolean getScrollableTracksViewportWidth() {
        return true;

    }


}
