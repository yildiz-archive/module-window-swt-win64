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

package be.yildizgames.module.window.swt;

import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.window.Cursor;
import be.yildizgames.module.window.ScreenSize;
import be.yildizgames.module.window.WindowEngine;
import be.yildizgames.module.window.WindowHandle;
import be.yildizgames.module.window.input.WindowInputListener;
import be.yildizgames.module.window.swt.input.SwtGameWindowKeyListener;
import be.yildizgames.module.window.swt.input.SwtGameWindowMouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import java.util.HashMap;
import java.util.Map;

/**
 * SWT implementation for the window engine.
 *
 * @author Grégory Van den Borre
 */
public final class SwtWindowEngine implements WindowEngine {

    /**
     * SWT display object.
     */
    private final SwtDisplayWrapper display = new SwtDisplayWrapper();

    /**
     * SWT game window.
     */
    private final SwtGameWindow gameWindow = new SwtGameWindow(true);

    /**
     * A map containing all the cursor file, use their file name to get them.
     */
    private final Map<Cursor, org.eclipse.swt.graphics.Cursor> cursorList = new HashMap<>();


    /**
     * Simple constructor.
     */
    public SwtWindowEngine() {
        super();
        System.setProperty("SWT_GTK3", "0");
        this.gameWindow.initialize(this.display.buildShell());
        this.hideCursor();
        this.display.execute(this.gameWindow::open);
    }

    @Override
    public final void setWindowTitle(final String title) {
        this.gameWindow.setTitle(title);
    }

    @Override
    public final void setWindowIcon(final String file) {
        this.gameWindow.setIcon(file);
    }

    @Override
    public final void createCursor(final Cursor cursor) {
        final Image data = this.gameWindow.getImage(cursor.getPath());
        this.cursorList.put(cursor, new org.eclipse.swt.graphics.Cursor(Display.getCurrent(), data.getImageData(), cursor.getX(), cursor.getY()));
    }

    @Override
    public final void updateWindow() {
        this.display.checkForEvent();
    }

    /**
     * @return The handle to link the game window and the 3d context.
     */
    public final WindowHandle getHandle() {
        return new WindowHandle(this.gameWindow.getCanvas().handle);
    }

    /**
     * Delete the resources used during loading.
     */
    @Override
    public final void deleteLoadingResources() {
        this.gameWindow.deleteLoadingResources();
        this.gameWindow.showCursor();
    }

    @Override
    public final void setCursor(final Cursor cursor) {
        this.gameWindow.setCursor(this.cursorList.get(cursor));
    }

    @Override
    public final void showCursor() {
        this.gameWindow.showCursor();
    }

    @Override
    public final void hideCursor() {
        this.gameWindow.hideCursor();
    }

    @Override
    public final ScreenSize getScreenSize() {
        return this.gameWindow.getScreenSize();
    }

    @Override
    public void registerInput(final WindowInputListener listener) {
        new SwtGameWindowMouseListener(this.gameWindow.getCanvas(), listener);
        new SwtGameWindowKeyListener(this.gameWindow.getCanvas(), listener);

    }
}
