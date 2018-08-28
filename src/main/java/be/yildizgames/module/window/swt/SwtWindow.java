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

import be.yildizgames.module.color.Color;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Grégory Van den Borre
 */
public final class SwtWindow {

    /**
     * Associated display.
     */
    private final Display display;

    private final Shell shell;

    /**
     * A map containing all the cursor file, use their file name to get them.
     */
    private final Map<String, Cursor> cursorList = new HashMap<>();

    public SwtWindow(final Shell shell) {
        super();
        this.shell = shell;
        this.display = shell.getDisplay();
    }

    public SwtWindow() {
        super();
        this.shell = new Shell();
        this.display = shell.getDisplay();
    }

    public SwtWindow(SwtWindow parent) {
        this(new Shell(parent.shell));
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

    public void createCursor(final String name, final String path) {
        this.createCursor(name, path, 0, 0);
    }

    public void createCursor(final String name, final String path, final int x, final int y) {
        final Image data = new Image(Display.getCurrent(), this.getClass().getClassLoader().getResourceAsStream(path));
        this.cursorList.put(name, new Cursor(Display.getCurrent(), data.getImageData(), x, y));
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
     * Create an image from a file stored in media.
     *
     * @param file File path.
     * @return The created image.
     */
    public Image getImage(final String file) {
        return new Image(this.display, this.getClass().getClassLoader().getResourceAsStream("media/" + file));
    }

    public void close() {
        this.shell.close();
        this.display.close();
    }

    /**
     * Execute a thread by the SWT manager to avoid error SWT thread access.
     *
     * @param r Thread to execute.
     */
    void execute(final Runnable r) {
        this.display.syncExec(r);
    }

    public void show() {
        this.shell.setVisible(true);
    }

    public void hide() {
        this.shell.setVisible(false);
    }

    public void run() {
        while (!shell.isDisposed() && shell.isVisible()) {
            if (!this.display.readAndDispatch())
                this.display.sleep();
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
}
