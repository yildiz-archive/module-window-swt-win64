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

import be.yildizgames.module.window.input.WindowInputListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

/**
 * Class for the keyboard management. All event are catched here from SWT and
 * then sent to the observer.
 *
 * @author Grégory Van den Borre
 */
public final class SwtGameWindowKeyListener implements KeyListener {

    /**
     * Event dispatcher.
     */
    private final WindowInputListener dispatcher;

    private final SwtKeyValue values = new SwtKeyValue();

    /**
     * Full constructor.
     *
     * @param listener Event dispatcher
     */
    private SwtGameWindowKeyListener(final WindowInputListener listener) {
        super();
        this.dispatcher = listener;
    }

    public static SwtGameWindowKeyListener create(final WindowInputListener listener) {
        return new SwtGameWindowKeyListener(listener);
    }


    /**
     * Logic when pressing a key.
     *
     * @param event Event retrieved from the listener.
     */
    @Override
    public void keyPressed(final KeyEvent event) {
        this.keyPressedImpl(event.keyCode, event.character);
    }

    /**
     * Generic method, easier to be tested.
     * @param code Key code.
     * @param c Character.
     */
    void keyPressedImpl(int code, char c) {
        if (values.isKeyboard(code)) {
            this.dispatcher.keyboardKeyPressed(c);
        } else if (values.isNumpad(code)) {
            this.dispatcher.keyboardKeyPressed(values.fromNumpad(code));
        } else {
            this.dispatcher.specialKeyPressed(this.values.getKey(code));
        }
    }

    /**
     * Logic when releasing a key.
     *
     * @param event Event retrieved from the listener.
     */
    @Override
    public void keyReleased(final KeyEvent event) {
        if (values.isKeyboard(event.keyCode)) {
            this.dispatcher.keyboardKeyPressed(event.character);
        } else {
            this.dispatcher.specialKeyPressed(this.values.getKey(event.keyCode));
        }
    }
}
