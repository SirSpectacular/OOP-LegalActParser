package Entities;

import Assets.ActComponent;
import Assets.ParentOfComponents;

public class Viewer {
    private ParentOfComponents componentToView;

    Viewer(ParentOfComponents componentToView) {
        if (componentToView != null) {
            this.componentToView = componentToView;
        } else throw new NullPointerException("There is nothing to view");
    }

    public void printComponent(int level) {
        if (componentToView != null) {
            if (componentToView instanceof ActComponent && (((ActComponent) componentToView).getWeight() <= level || level == 0))
                System.out.println(componentToView.toString());
            if (!componentToView.subComponents.isEmpty()) {
                for (ActComponent component : componentToView.subComponents) {
                    new Viewer(component).printComponent(level);
                }
            }
        }
    }
}

