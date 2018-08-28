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

import be.yildizgames.common.exception.technical.ResourceMissingException;
import be.yildizgames.module.window.ScreenSize;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import java.io.InputStream;

/**
 * Game main rendering window.
 *
 * @author Grégory Van den Borre
 */
public final class SwtGameWindow {

    /**
     * Flag to enable or not the full screen mode when building the window.
     */
    private boolean fullScreenMode;

    /**
     * The SWT shell.
     */
    private Shell shell;

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
     * Game window size.
     */
    private ScreenSize screenSize;

    /**
     * Full constructor.
     *
     * @param fullScreen True if full screen must be enabled.
     */
    SwtGameWindow(final boolean fullScreen) {
        super();
        this.fullScreenMode = fullScreen;
    }

    /**
     * Build the window in the SWT thread.
     *
     * @param swtShell Shell to use as container this window.
     */
    public void initialize(final Shell swtShell) {
        this.shell = swtShell;
        this.shell.setBackgroundMode(SWT.INHERIT_DEFAULT);

        final Color white = this.shell.getDisplay().getSystemColor(SWT.COLOR_WHITE);
        final Color black = this.shell.getDisplay().getSystemColor(SWT.COLOR_BLACK);
        final PaletteData palette = new PaletteData(new RGB[]{white.getRGB(), black.getRGB()});
        final ImageData sourceData = new ImageData(16, 16, 1, palette);
        sourceData.transparentPixel = 0;
        this.invisibleCursor = new Cursor(this.shell.getDisplay(), sourceData, 0, 0);
        if (this.fullScreenMode) {
            this.setFullScreen();
        }
        Image tmpImage = this.getImage("engine.png");

        this.loadingBackground = new Image(this.shell.getDisplay(), tmpImage.getImageData().scaledTo(this.shell.getBounds().width, this.shell.getBounds().height));
        this.shell.setCursor(this.invisibleCursor);
        this.shell.setBackgroundImage(this.loadingBackground);
        this.canvas = new Canvas(this.shell, SWT.NONE);
        this.canvas.setSize(this.shell.getSize());
        this.shell.setLayout(new FillLayout());
    }

    /**
     * Retrieve an image, the file is expected to be in same directory as sources, as well in file system than wrapped in a jar file.
     *
     * @param path Relative path from the source folder.
     * @return An image created from the file.
     */
    Image getImage(final String path) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new ResourceMissingException("Cannot find image " + path);
        }
        return new Image(this.shell.getDisplay(), is);
    }

    /**
     * Use a new cursor in the window.
     *
     * @param cursor Cursor to use.
     */
    void setCursor(final Cursor cursor) {
        this.currentCursor = cursor;
        this.shell.setCursor(this.currentCursor);
    }

    /**
     * Make the window use all the screen and remove the title bar.
     */
    private void setFullScreen() {
        this.shell.setFullScreen(true);
        this.shell.setFocus();
        final Monitor m = Display.getDefault().getPrimaryMonitor();
        this.shell.setBounds(-1, -1, m.getBounds().width + 2, m.getBounds().height + 2);
        this.screenSize = new ScreenSize(m.getBounds().width, m.getBounds().height);
    }

    /**
     * Open the window.
     */
    void open() {
        this.shell.open();
    }

    /**
     * @return The SWT canvas.
     */
    Canvas getCanvas() {
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
    void showCursor() {
        this.shell.setCursor(this.currentCursor);
    }

    /**
     * Set the mouse cursor invisible.
     */
    void hideCursor() {
        this.shell.setCursor(this.invisibleCursor);
    }

    /**
     * Set the game window title.
     *
     * @param title Title to display.
     */
    void setTitle(final String title) {
        this.shell.setText(title);
        Display.setAppName(title);
    }

    /**
     * Set the window icon.
     *
     * @param file Path of the file to use.
     */
    void setIcon(final String file) {
        this.shell.setImage(this.getImage(file));
    }

    /**
     * @return The game window size.
     */
    public ScreenSize getScreenSize() {
        return this.screenSize;
    }
}
