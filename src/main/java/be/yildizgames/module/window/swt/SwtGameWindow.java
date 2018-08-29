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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;

/**
 * Game main rendering window.
 *
 * @author Grégory Van den Borre
 */
public final class SwtGameWindow {



    /**
     * The SWT shell.
     */
    private SwtWindow window;

    /**
     * Canvas for the window and the 3d context.
     */
    private Canvas canvas;

    /**
     * Image to use when the engine is loading.
     */
    private Image loadingBackground;

    /**
     * Cursor currently used.
     */
    private Cursor currentCursor;

    /**
     * Invisible cursor.
     */
    private Cursor invisibleCursor;

    /**
     * Constructor.
     */
    public SwtGameWindow() {
        super();
    }

    /**
     * Build the window in the SWT thread.
     *
     * @param window Window to use as container.
     */
    public void initialize(final SwtWindow window, boolean fullScreenMode) {
        this.window = window;
        this.window.getShell().setBackgroundMode(SWT.INHERIT_DEFAULT);

        final Color white = this.window.getSystemColor(SWT.COLOR_WHITE);
        final Color black = this.window.getSystemColor(SWT.COLOR_BLACK);
        final PaletteData palette = new PaletteData(white.getRGB(), black.getRGB());
        final ImageData sourceData = new ImageData(16, 16, 1, palette);
        sourceData.transparentPixel = 0;
        this.invisibleCursor = new Cursor(window.getShell().getDisplay(), sourceData, 0, 0);
        if (fullScreenMode) {
            this.window.setFullScreen();
        }
        Image tmpImage = this.window.getImage("engine.png");

        this.loadingBackground = new Image(this.window.getShell().getDisplay(), tmpImage.getImageData().scaledTo(this.window.getShell().getBounds().width, this.window.getShell().getBounds().height));
        this.currentCursor = this.window.getCursor();
        this.window.setCursor(this.invisibleCursor);
        this.window.setBackground(be.yildizgames.module.color.Color.rgb(100,0,0));
        this.window.setBackground(this.loadingBackground);
        this.canvas = window.createCanvas(window.getWidth(), window.getHeight());
      //  this.window.getShell().setLayout(new FillLayout());
    }

    /**
     * Use a new cursor in the window.
     *
     * @param cursor Cursor to use.
     */
    void setCursor(final Cursor cursor) {
        this.currentCursor = cursor;
        this.window.setCursor(this.currentCursor);
    }

    /**
     * @return The SWT canvas.
     */
    public Canvas getCanvas() {
        return this.canvas;
    }

    /**
     * Remove resources used during loading.
     */
    void deleteLoadingResources() {
        this.loadingBackground.dispose();
    }

    /**
     * Set the mouse cursor visible.
     */
    public void showCursor() {
        this.window.setCursor(this.currentCursor);
    }

    /**
     * Set the mouse cursor invisible.
     */
    void hideCursor() {
        this.window.setCursor(this.invisibleCursor);
    }
}
