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
import be.yildizgames.module.window.input.WindowInputListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Grégory Van den Borre
 */
public class SwtGameWindowKeyListenerTest {

    @Test
    void testPressChar() {
        ListenerTest lt = new ListenerTest();
        SwtGameWindowKeyListener l = SwtGameWindowKeyListener.create(lt);
        l.keyPressedImpl(65, 'A');
        Assertions.assertEquals('A', lt.character);
    }

    @Test
    void testPressEnter() {
        ListenerTest lt = new ListenerTest();
        SwtGameWindowKeyListener l = SwtGameWindowKeyListener.create(lt);
        l.keyPressedImpl(13, '\0');
        Assertions.assertEquals(Key.ENTER, lt.key);
    }

    @Test
    void testPressArrow() {
        ListenerTest lt = new ListenerTest();
        SwtGameWindowKeyListener l = SwtGameWindowKeyListener.create(lt);
        l.keyPressedImpl(16777219, '\0');
        Assertions.assertEquals(Key.LEFT, lt.key);
    }

    @Test
    void testPressNumber() {
        ListenerTest lt = new ListenerTest();
        SwtGameWindowKeyListener l = SwtGameWindowKeyListener.create(lt);
        l.keyPressedImpl(16777264, '\0');
        Assertions.assertEquals('0', lt.character);
    }

    @Test
    void testReleaseChar() {
        ListenerTest lt = new ListenerTest();
        SwtGameWindowKeyListener l = SwtGameWindowKeyListener.create(lt);
        l.keyReleasedImpl(65, 'A');
        Assertions.assertEquals('A', lt.releasedCharacter);
    }

    @Test
    void testReleaseEnter() {
        ListenerTest lt = new ListenerTest();
        SwtGameWindowKeyListener l = SwtGameWindowKeyListener.create(lt);
        l.keyReleasedImpl(13, '\0');
        Assertions.assertEquals(Key.ENTER, lt.releasedKey);
    }

    public static class ListenerTest implements WindowInputListener {

        char character;

        Key key;

        char releasedCharacter;

        Key releasedKey;

        @Override
        public void keyboardKeyPressed(char character) {
            this.character = character;
        }

        @Override
        public void specialKeyPressed(Key key) {
            this.key = key;
        }

        @Override
        public void keyboardKeyReleased(char character) {
            this.releasedCharacter = character;
        }

        @Override
        public void specialKeyReleased(Key key) {
            this.releasedKey = key;
        }
    }
}
