package be.yildizgames.module.window.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;

public class SwtWindowUtils {

    public void setText(final Button b, final String text, final Font font) {
        Image background = new Image(b.getShell().getDisplay(), 128, 30);
        org.eclipse.swt.graphics.Color c = new org.eclipse.swt.graphics.Color(b.getDisplay(), 220, 220, 220);
        org.eclipse.swt.graphics.Color h = new org.eclipse.swt.graphics.Color(b.getDisplay(), 255, 255, 255);
        org.eclipse.swt.graphics.Color t = new org.eclipse.swt.graphics.Color(b.getDisplay(), 10, 10, 10);
        SwtWindowUtils.ButtonHover bh = new SwtWindowUtils.ButtonHover();
        b.addMouseTrackListener(new MouseTrackListener() {
            @Override
            public void mouseHover(final MouseEvent e) {
                bh.hover = true;
            }

            @Override
            public void mouseExit(final MouseEvent e) {
                bh.hover = false;
            }

            @Override
            public void mouseEnter(final MouseEvent e) {
                bh.hover = true;
            }
        });
        b.addPaintListener(new PaintListener() {

            @Override
            public void paintControl(final PaintEvent e) {
                GC gcBack = new GC(background);
                gcBack.setAntialias(SWT.ON);
                gcBack.setBackground(bh.hover ? h : c);
                gcBack.setAlpha(255);
                gcBack.fillRectangle(0, 0, b.getBounds().width, b.getBounds().height);
                gcBack.setForeground(t);
                gcBack.setFont(font);
                gcBack.drawText(text, 40, 8, true);
                gcBack.setAlpha(255);
                e.gc.drawImage(background, 0, 0);
                gcBack.dispose();
            }

        });
    }

    /**
     * Simple wrapper to be used in anonymous class.
     *
     * @author Van den Borre Grégory
     */
    private final class ButtonHover {

        /**
         * Flag if mouse is over button or not.
         */
        private boolean hover = false;
    }

    public enum ColorValue {
        WHITE(SWT.COLOR_WHITE);

        final int value;

        @SuppressWarnings("UnnecessaryEnumModifier")
        ColorValue(final int color) {
            this.value = color;
        }
    }
}
