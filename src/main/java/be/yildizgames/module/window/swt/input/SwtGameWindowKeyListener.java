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

import be.yildizgames.module.window.input.ArrowKey;
import be.yildizgames.module.window.input.Key;
import be.yildizgames.module.window.input.SpecialKey;
import be.yildizgames.module.window.input.WindowInputListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Canvas;

/**
 * Class for the keyboard management. All event are catched here from SWT and
 * then sent to the observer.
 *
 * @author Grégory Van den Borre
 */
public final class SwtGameWindowKeyListener implements KeyListener {

    /**
     * Escape key code.
     */
    private static final int ESC = 27;

    /**
     * Tabulation key code.
     */
    private static final int TAB = 9;

    /**
     * CTRL key code.
     */
    private static final int CTRL = 262144;

    /**
     * Enter key code.
     */
    private static final int ENTER = 13;

    /**
     * Minimum ASCII value to be considered as normal char.
     */
    private static final int MIN = 30;

    /**
     * Maximum ASCII value to be considered as normal char.
     */
    private static final int MAX = 256;

    /**
     * Event dispatcher.
     */
    private final WindowInputListener dispatcher;

    /**
     * Full constructor.
     *
     * @param canvas     SWT canvas.
     * @param listener Event dispatcher
     */
    public SwtGameWindowKeyListener(final Canvas canvas, final WindowInputListener listener) {
        super();
        this.dispatcher = listener;
        canvas.addKeyListener(this);
        canvas.setFocus();
    }

    /**
     * Logic when pressing a key.
     *
     * @param event Event retrieved from the listener.
     */
    @Override
    public void keyPressed(final KeyEvent event) {
        if (event.keyCode > SwtGameWindowKeyListener.MIN && event.keyCode < SwtGameWindowKeyListener.MAX) {
            this.dispatcher.keyboardKeyPressed(event.character);
        } else {
            switch (event.keyCode) {
                case ENTER:
                    this.dispatcher.keyboardEnterKeyPressed();
                    this.dispatcher.keyPressed(Key.ENTER);
                    break;
                case SWT.BS:
                    this.dispatcher.keyboardDeleteKeyPressed();
                    break;
                case SWT.SHIFT:
                    this.dispatcher.specialKeyPressed(SpecialKey.SHIFT);
                    break;
                case SWT.KEYPAD_0:
                    this.dispatcher.keyboardNumberPressed(0);
                    this.dispatcher.keyboardKeyPressed('0');
                    break;
                case SWT.KEYPAD_1:
                    this.dispatcher.keyboardNumberPressed(1);
                    this.dispatcher.keyboardKeyPressed('1');
                    break;
                case SWT.KEYPAD_2:
                    this.dispatcher.keyboardNumberPressed(2);
                    this.dispatcher.keyboardKeyPressed('2');
                    break;
                case SWT.KEYPAD_3:
                    this.dispatcher.keyboardNumberPressed(3);
                    this.dispatcher.keyboardKeyPressed('3');
                    break;
                case SWT.KEYPAD_4:
                    this.dispatcher.keyboardNumberPressed(4);
                    this.dispatcher.keyboardKeyPressed('4');
                    break;
                case SWT.KEYPAD_5:
                    this.dispatcher.keyboardNumberPressed(5);
                    this.dispatcher.keyboardKeyPressed('5');
                    break;
                case SWT.KEYPAD_6:
                    this.dispatcher.keyboardNumberPressed(6);
                    this.dispatcher.keyboardKeyPressed('6');
                    break;
                case SWT.KEYPAD_7:
                    this.dispatcher.keyboardNumberPressed(7);
                    this.dispatcher.keyboardKeyPressed('7');
                    break;
                case SWT.KEYPAD_8:
                    this.dispatcher.keyboardNumberPressed(8);
                    this.dispatcher.keyboardKeyPressed('8');
                    break;
                case SWT.KEYPAD_9:
                    this.dispatcher.keyboardNumberPressed(9);
                    this.dispatcher.keyboardKeyPressed('9');
                    break;
                case SWT.KEYPAD_ADD:
                    this.dispatcher.keyboardKeyPressed('+');
                    break;
                case SWT.KEYPAD_DIVIDE:
                    this.dispatcher.keyboardKeyPressed('/');
                    break;
                case SWT.KEYPAD_MULTIPLY:
                    this.dispatcher.keyboardKeyPressed('*');
                    break;
                case SWT.KEYPAD_SUBTRACT:
                    this.dispatcher.keyboardKeyPressed('-');
                    break;
                case SWT.KEYPAD_CR:
                    this.dispatcher.keyboardEnterKeyPressed();
                    break;
                case SWT.KEYPAD_DECIMAL:
                    this.dispatcher.keyboardKeyPressed('.');
                    break;
                case SWT.ARROW_UP:
                    this.dispatcher.keyboardArrowPressed(ArrowKey.UP);
                    break;
                case SWT.ARROW_DOWN:
                    this.dispatcher.keyboardArrowPressed(ArrowKey.DOWN);
                    break;
                case SWT.ARROW_LEFT:
                    this.dispatcher.keyboardArrowPressed(ArrowKey.LEFT);
                    break;
                case SWT.ARROW_RIGHT:
                    this.dispatcher.keyboardArrowPressed(ArrowKey.RIGHT);
                    break;
                case CTRL:
                    this.dispatcher.specialKeyPressed(SpecialKey.CTRL);
                    break;
                case ESC:
                    this.dispatcher.specialKeyPressed(SpecialKey.ESC);
                    break;
                case TAB:
                    this.dispatcher.specialKeyPressed(SpecialKey.TAB);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Logic when releasing a key.
     *
     * @param e Event retrieved from the listener.
     */
    @Override
    public void keyReleased(final KeyEvent e) {
        this.dispatcher.keyReleased(e.keyCode);
    }
}
