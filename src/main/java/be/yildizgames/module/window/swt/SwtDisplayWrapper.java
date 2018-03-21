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
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Wrapper class for the SWT Display object, just provide necessary fields to the user.
 *
 * @author Grégory Van den Borre
 */
final class SwtDisplayWrapper {

    /**
     * Wrapped SWT display object.
     */
    private final Display display;

    /**
     * Simple constructor.
     */
    SwtDisplayWrapper() {
        super();
        this.display = new Display();
        Display.setAppName("Yildiz engine");
    }

    /**
     * Execute a thread by the SWT manager to avoid error swt thread access.
     *
     * @param r Thread to execute.
     */
    void execute(final Runnable r) {
        this.display.syncExec(r);
    }

    /**
     * @return a new SWT Shell object to build a window.
     */
    Shell buildShell() {
        return new Shell(this.display, SWT.NONE);
    }

    /**
     * Update the window.
     */
    void checkForEvent() {
        this.display.readAndDispatch();
    }

    /**
     * Make the display sleep to avoid burn cpu.
     */
    void sleep() {
        this.display.sleep();
    }

    /**
     * Create a SWT image from a file.
     *
     * @param file File name.
     * @return The SWT generated image.
     */
    Image buildImage(final String file) {
        try {
            return new Image(this.display, file);
        } catch (final SWTException fnfe) {
            throw new ResourceMissingException("File " + file + " not found");
        }
    }

    /**
     * Create a SWT image from a image data.
     *
     * @param data Data to build the image.
     * @return The SWT generated image.
     */
    Image buildImage(final ImageData data) {
        return new Image(this.display, data);
    }
}
