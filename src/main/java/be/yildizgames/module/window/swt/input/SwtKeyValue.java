/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2018 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
 * to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE  SOFTWARE.
 */

package be.yildizgames.module.window.swt.input;

import be.yildizgames.module.window.input.Key;
import org.eclipse.swt.SWT;

import java.util.HashMap;
import java.util.Map;

class SwtKeyValue {

    /**
     * Minimum ASCII value to be considered as normal char.
     */
    private static final int MIN = 30;

    /**
     * Maximum ASCII value to be considered as normal char.
     */
    private static final int MAX = 256;

    private Map<Integer, Key> keyMap = new HashMap<>();

    SwtKeyValue() {
        keyMap.put(27, Key.ESC);
        keyMap.put(SWT.ALT, Key.ALT);
        keyMap.put(9, Key.TAB);
        keyMap.put(SWT.CTRL, Key.CTRL);
        keyMap.put(13, Key.ENTER);
        keyMap.put(SWT.KEYPAD_CR, Key.ENTER);
        keyMap.put(SWT.SHIFT, Key.SHIFT);
        keyMap.put(SWT.PAGE_UP, Key.PAGE_UP);
        keyMap.put(SWT.PAGE_DOWN, Key.PAGE_DOWN);
        keyMap.put(SWT.HOME, Key.HOME);
        keyMap.put(SWT.END, Key.END);
        keyMap.put(SWT.INSERT, Key.INSERT);
        keyMap.put(SWT.F1, Key.F1);
        keyMap.put(SWT.F2, Key.F2);
        keyMap.put(SWT.F3, Key.F3);
        keyMap.put(SWT.F4, Key.F4);
        keyMap.put(SWT.F5, Key.F5);
        keyMap.put(SWT.F6, Key.F6);
        keyMap.put(SWT.F7, Key.F7);
        keyMap.put(SWT.F8, Key.F8);
        keyMap.put(SWT.F9, Key.F9);
        keyMap.put(SWT.F10, Key.F10);
        keyMap.put(SWT.F11, Key.F11);
        keyMap.put(SWT.F12, Key.F12);
        keyMap.put(SWT.PRINT_SCREEN, Key.PRINT_SCREEN);
        keyMap.put(SWT.BREAK, Key.BREAK);
        keyMap.put(SWT.PAUSE, Key.PAUSE);
        keyMap.put(SWT.ARROW_LEFT, Key.LEFT);
        keyMap.put(SWT.ARROW_UP, Key.UP);
        keyMap.put(SWT.ARROW_RIGHT, Key.RIGHT);
        keyMap.put(SWT.ARROW_DOWN, Key.DOWN);
    }

    Key getKey(int v) {
        return keyMap.getOrDefault(v, Key.EMPTY);
    }

    boolean isKeyboard(int keyCode) {
        return keyCode > MIN && keyCode < MAX;
    }

    boolean isNumpad(int keyCode) {
        return keyCode >= SWT.KEYPAD_MULTIPLY && keyCode <= SWT.KEYPAD_EQUAL;
    }

    char fromNumpad(int code) {
        switch (code) {
            case SWT.KEYPAD_MULTIPLY: return '*';
            case SWT.KEYPAD_ADD: return '+';
            case SWT.KEYPAD_SUBTRACT: return '-';
            case SWT.KEYPAD_DECIMAL: return '.';
            case SWT.KEYPAD_DIVIDE: return '/';
            case SWT.KEYPAD_0: return '0';
            case SWT.KEYPAD_1: return '1';
            case SWT.KEYPAD_2: return '2';
            case SWT.KEYPAD_3: return '3';
            case SWT.KEYPAD_4: return '4';
            case SWT.KEYPAD_5: return '5';
            case SWT.KEYPAD_6: return '6';
            case SWT.KEYPAD_7: return '7';
            case SWT.KEYPAD_8: return '8';
            case SWT.KEYPAD_9: return '9';
            case SWT.KEYPAD_EQUAL: return '=';
            default: return '?';
        }
    }
}
