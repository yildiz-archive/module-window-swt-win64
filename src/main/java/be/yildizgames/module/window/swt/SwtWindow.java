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
import be.yildizgames.module.color.Color;
import be.yildizgames.module.window.ScreenSize;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author Grégory Van den Borre
 */
public final class SwtWindow {


    private final Shell shell;

    private ScreenSize screenSize;

    public SwtWindow(final Shell shell) {
        super();
        this.shell = shell;
        this.screenSize = new ScreenSize(this.shell.getSize().x,this.shell.getSize().y);
    }

    public SwtWindow() {
        this(new Shell());
    }

    public SwtWindow(SwtWindow parent) {
        this(new Shell(parent.shell));
    }

    /**
     * Make the window use all the screen and remove the title bar.
     */
    void setFullScreen() {
        this.shell.setFullScreen(true);
        this.shell.setFocus();
        final Monitor m = Display.getDefault().getPrimaryMonitor();
        this.shell.setBounds(-1, -1, m.getBounds().width + 2, m.getBounds().height + 2);
        this.screenSize = new ScreenSize(m.getBounds().width, m.getBounds().height);
    }

    public void addMouseMoveListener(final Listener listener) {
        this.shell.addListener(SWT.MouseMove, listener);
    }

    public void addMouseClickListener(final Listener listener) {
        this.shell.addListener(SWT.MouseDown, listener);
        this.shell.addListener(SWT.MouseUp, listener);
    }

    public void setWindowTitle(final String title) {
        this.shell.setText(title);
        Display.setAppName(title);
    }

    public void setBackground(final String background) {
        this.shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
        this.shell.setBackgroundImage(this.getImage(background));
    }

    public void setBackground(final Color background) {
        this.shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
        this.shell.setBackground(new org.eclipse.swt.graphics.Color(this.shell.getDisplay(), background.red, background.green, background.blue));
    }

    public void setBackground(Image background) {
        this.shell.setBackgroundImage(background);
    }

    /**
     * Set the background color.
     * @deprecated use setBackground instead
     *
     * @param background color to set as background.
     */
    @Deprecated
    public void setBackgroundColor(final Color background) {
        this.setBackground(background);
    }

    public void setWindowIcon(final String file) {
        this.shell.setImage(this.getImage(file));
    }

    public Button createButton(final String background, final String hover) {
        return this.createButton(this.getImage(background), this.getImage(hover));
    }

    public Button createButton() {
        return new Button(this.shell, SWT.SMOOTH);
    }

    public Button createButton(final Image background, final Image hover) {
        Button button = new Button(this.shell, SWT.SMOOTH);
        button.setImage(background);
        button.addListener(SWT.MouseEnter, e -> button.setImage(hover));
        button.addListener(SWT.MouseExit, e -> button.setImage(background));
        return button;
    }

    public Tree createTree(int w, int h, TreeElement... elements) {
        Tree tree = new Tree(this.shell, SWT.NONE);
        tree.setSize(w, h);
        tree.setBackground(this.shell.getBackground());
        for(TreeElement element : elements) {
            TreeItem item = new TreeItem(tree, 0);
            item.setText(element.title);
            element.getChildren().forEach(e -> generate(item, e));
        }
        tree.setEnabled(true);
        return tree;
    }

    private static void generate(TreeItem parent, TreeElement element) {
        TreeItem item = new TreeItem(parent, 0);
        item.setText(element.title);
        for(TreeElement e : element.getChildren()) {
            generate(item, e);
        }
    }

    public Label createLabel(final String text, final SwtWindowUtils.ColorValue color, final Font font) {
        Label label = new Label(this.shell, SWT.NONE);
        label.setFont(font);
        label.setForeground(this.shell.getDisplay().getSystemColor(color.value));
        label.setText(text);
        return label;
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

    public void close() {
        this.shell.close();
        this.shell.getDisplay().close();
    }

    Cursor getCursor() {
        return this.shell.getCursor();
    }

    /**
     * Execute a thread by the SWT manager to avoid error SWT thread access.
     *
     * @param r Thread to execute.
     */
    void execute(final Runnable r) {
        this.shell.getDisplay().syncExec(r);
    }

    public void show() {
        this.shell.setVisible(true);
    }

    public void hide() {
        this.shell.setVisible(false);
    }

    public void run() {
        Display d = shell.getDisplay();
        while (!shell.isDisposed() && shell.isVisible()) {
            if (!d.readAndDispatch())
                d.sleep();
        }
    }

    public int getWidth() {
        return this.shell.getSize().x;
    }

    public int getHeight() {
        return this.shell.getSize().y;
    }

    public Text createInputBox() {
        return new Text(this.shell, SWT.SINGLE);
    }

    public Combo createDropdown() {
        return new Combo(this.shell, SWT.READ_ONLY);
    }

    public Combo createDropdown(Object[] items) {
        Combo c = this.createDropdown();
        c.setItems(Arrays.stream(items).map(Object::toString).toArray(String[]::new));
        c.select(0);
        return c;
    }

    public Label createTextLine() {
        return new Label(this.shell, SWT.NONE);
    }

    public Menu createMenuBar(MenuBarElement... barElements) {
        Menu menu = new Menu(this.shell, SWT.BAR);
        this.shell.setMenuBar(menu);
        for(MenuBarElement e : barElements) {
            this.createMenu(menu, e.title, e.getChildren());
        }

        return menu;
    }

    private void createMenu(Menu menu, String titleText, List<MenuElement> elements) {
        MenuItem title = new MenuItem(menu, SWT.CASCADE);
        title.setText("&" + titleText);
        Menu sub = new Menu(this.shell, SWT.DROP_DOWN);
        title.setMenu(sub);
        elements.forEach(elmt -> createMenuElement(sub, elmt));
    }

    private static void createMenuElement(Menu parent, MenuElement e) {
        MenuItem p = new MenuItem(parent, SWT.PUSH);
        p.setText("&" + e.title);
        p.addSelectionListener(e.behavior);
    }

    public FileDialog createOpenFileDialog(String title) {
        FileDialog fd = new FileDialog(this.shell, SWT.OPEN);
        fd.setText(title);
        return fd;
    }

    public Canvas createCanvas(int width, int height) {
        Canvas canvas = new Canvas(this.shell, SWT.NONE);
        canvas.setSize(width, height);
        return canvas;
    }

    void open() {
        this.shell.open();
    }

    Shell getShell() {
        return this.shell;
    }

    public ScreenSize getScreenSize() {
        return this.screenSize;
    }

    void checkForEvent() {
        this.shell.getDisplay().readAndDispatch();
    }

    org.eclipse.swt.graphics.Color getSystemColor(int c) {
        return this.shell.getDisplay().getSystemColor(c);
    }

    void setCursor(Cursor cursor) {
        this.shell.setCursor(cursor);
    }
}
