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

import be.yildizgames.module.window.input.Key;
import org.eclipse.swt.SWT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Grégory Van den Borre
 */
class SwtKeyValueTest {

    @Nested
    class GetKey {

        /**
         * Test all possible matches for a SWT key and an engine key.
         */
        @Test
        void map() {
            SwtKeyValue v = new SwtKeyValue();
            Assertions.assertEquals(Key.ALT, v.getKey(SWT.ALT));
            Assertions.assertEquals(Key.SHIFT, v.getKey(SWT.SHIFT));
            Assertions.assertEquals(Key.CTRL, v.getKey(SWT.CTRL));
            Assertions.assertEquals(Key.ESC, v.getKey(27));
            Assertions.assertEquals(Key.TAB, v.getKey(9));
            Assertions.assertEquals(Key.ENTER, v.getKey(13));
            Assertions.assertEquals(Key.ENTER, v.getKey(SWT.KEYPAD_CR));
            Assertions.assertEquals(Key.PAGE_UP, v.getKey(SWT.PAGE_UP));
            Assertions.assertEquals(Key.PAGE_DOWN, v.getKey(SWT.PAGE_DOWN));
            Assertions.assertEquals(Key.HOME, v.getKey(SWT.HOME));
            Assertions.assertEquals(Key.END, v.getKey(SWT.END));
            Assertions.assertEquals(Key.INSERT, v.getKey(SWT.INSERT));
            Assertions.assertEquals(Key.F1, v.getKey(SWT.F1));
            Assertions.assertEquals(Key.F2, v.getKey(SWT.F2));
            Assertions.assertEquals(Key.F3, v.getKey(SWT.F3));
            Assertions.assertEquals(Key.F4, v.getKey(SWT.F4));
            Assertions.assertEquals(Key.F5, v.getKey(SWT.F5));
            Assertions.assertEquals(Key.F6, v.getKey(SWT.F6));
            Assertions.assertEquals(Key.F7, v.getKey(SWT.F7));
            Assertions.assertEquals(Key.F8, v.getKey(SWT.F8));
            Assertions.assertEquals(Key.F9, v.getKey(SWT.F9));
            Assertions.assertEquals(Key.F10, v.getKey(SWT.F10));
            Assertions.assertEquals(Key.F11, v.getKey(SWT.F11));
            Assertions.assertEquals(Key.F12, v.getKey(SWT.F12));
            Assertions.assertEquals(Key.PRINT_SCREEN, v.getKey(SWT.PRINT_SCREEN));
            Assertions.assertEquals(Key.BREAK, v.getKey(SWT.BREAK));
            Assertions.assertEquals(Key.PAUSE, v.getKey(SWT.PAUSE));
            Assertions.assertEquals(Key.LEFT, v.getKey(SWT.ARROW_LEFT));
            Assertions.assertEquals(Key.UP, v.getKey(SWT.ARROW_UP));
            Assertions.assertEquals(Key.RIGHT, v.getKey(SWT.ARROW_RIGHT));
            Assertions.assertEquals(Key.DOWN, v.getKey(SWT.ARROW_DOWN));
        }
    }
}
