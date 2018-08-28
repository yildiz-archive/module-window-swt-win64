package be.yildizgames.module.window.swt;


import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

@FunctionalInterface
public interface SelectionBehavior extends SelectionListener {

    default void widgetDefaultSelected(SelectionEvent e) {
    }
}