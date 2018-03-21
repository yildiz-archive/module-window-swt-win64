/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildizgames.module.window.swt.input;

import be.yildizgames.module.window.input.GameWindowMouseListener;
import be.yildizgames.module.window.input.WindowInputListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.widgets.Control;

/**
 * Class for the mouse management. All event are catch here from SWT and then sent to the observer.
 *
 * @author Grégory Van den Borre
 */
public final class SwtGameWindowMouseListener extends GameWindowMouseListener implements MouseListener, MouseMoveListener, MouseWheelListener {

    /**
     * Constant value for mouse left button.
     */
    private static final int LEFT_BUTTON = 1;

    /**
     * Constant value for mouse right button.
     */
    private static final int RIGHT_BUTTON = 3;

    /**
     * Constant value for mouse wheel button.
     */
    private static final int WHEEL_BUTTON = 2;


    /**
     * Full constructor.
     *
     * @param c          SWT control element, it can be any widget with mouse interaction capability.
     * @param dispatcher Input listener
     */
    public SwtGameWindowMouseListener(final Control c, final WindowInputListener dispatcher) {
        super(dispatcher);
        c.addMouseMoveListener(this);
        c.addMouseListener(this);
        c.addMouseWheelListener(this);
    }

    /**
     * Notify that mouse button is up.
     *
     * @param e Event retrieved from the listener.
     */
    @Override
    public void mouseUp(final MouseEvent e) {
        if (e.button == SwtGameWindowMouseListener.LEFT_BUTTON) {
            this.mouseLeftReleased();
        } else if (e.button == SwtGameWindowMouseListener.RIGHT_BUTTON) {
            this.mouseRightReleased();
        } else if (e.button == SwtGameWindowMouseListener.WHEEL_BUTTON) {
            this.mouseWheelReleased();
        }
    }

    /**
     * Mouse button press actions: If the left button is pressed: camera ray selection is called. If the right button is pressed, the camera will rotate or make an action for a unit.
     *
     * @param e Event retrieved from the listener.
     */
    @Override
    public void mouseDown(final MouseEvent e) {
        if (e.button == SwtGameWindowMouseListener.LEFT_BUTTON) {
            this.mouseLeftPressed();
        } else if (e.button == SwtGameWindowMouseListener.RIGHT_BUTTON) {
            this.mouseRightPressed();
        } else if (e.button == SwtGameWindowMouseListener.WHEEL_BUTTON) {
            this.mouseWheelPressed();
        }
    }

    @Override
    public void mouseDoubleClick(final MouseEvent e) {
        if (e.button == SwtGameWindowMouseListener.LEFT_BUTTON) {
            this.mouseLeftDouble();
        }
    }

    /**
     * Moving mouse actions: check if a button is pressed or not and call appropriate logic.
     *
     * @param e Event retrieved from the listener.
     */
    @Override
    public void mouseMove(final MouseEvent e) {
        this.mouseMove(e.x, e.y);
    }

    @Override
    public void mouseScrolled(final MouseEvent e) {
        this.mouseScrolled(e.count);
    }
}
