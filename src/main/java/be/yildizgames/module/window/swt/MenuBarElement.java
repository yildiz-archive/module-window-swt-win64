package be.yildizgames.module.window.swt;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MenuBarElement {

    final String title;

    private final List<MenuElement> children;

    public MenuBarElement(String title, MenuElement... elements) {
        super();
        this.title = title;
        if(elements == null) {
            children = Collections.emptyList();
        } else {
            children = Arrays.asList(elements);
        }
    }

    List<MenuElement> getChildren() {
        return this.children;
    }
}
