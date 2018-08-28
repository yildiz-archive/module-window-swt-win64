package be.yildizgames.module.window.swt;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TreeElement {

    final String title;

    private final List<TreeElement> children;

    public TreeElement(String title, TreeElement... elements) {
        super();
        this.title = title;
        if(elements == null) {
            children = Collections.emptyList();
        } else {
            children = Arrays.asList(elements);
        }
    }

    List<TreeElement> getChildren() {
        return this.children;
    }
}
