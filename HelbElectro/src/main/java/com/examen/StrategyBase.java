package com.examen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class StrategyBase implements IStrategy {

    protected ProductController productController;
    protected ComponentController componentController;

    Timeline timeline;

    public StrategyBase(ProductController productController, ComponentController componentController) {
        this.productController = productController;
        this.componentController = componentController;

        this.timeline = new Timeline(new KeyFrame(Duration.seconds(100), event -> startProduction()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.play();
    }

    @Override
    public abstract void startProduction(); // Provient de l'interface IStrategy

    @Override
    public void stopProduction() { // Provient de l'interface IStretegy
        if (timeline != null) {
            timeline.stop();
        }
    }

    protected boolean isDroneOk() {
        return countComponentC1() > 0 && countComponentC2() > 0 && countComponentC3() > 0;
    }

    protected boolean isRobotOk() {
        return countComponentC2() > 0 && countComponentC3() > 0;
    }

    protected boolean isVoitureOk() {
        return countComponentC1() > 0 && countComponentC3() > 0;
    }

    protected boolean isAlarmeOk() {
        return countComponentC1() > 0 && countComponentC2() > 0;
    }

    protected boolean isMoteurOk() {
        return countComponentC3() > 0;
    }

    protected boolean isCapteurOk() {
        return countComponentC2() > 0;
    }

    protected boolean isBatterieOk() {
        return countComponentC1() > 0;
    }

    protected int countComponentC1() {
        int count = 0;
        for (Component c : componentController.myComponentsList) {
            if (c instanceof C1) {
                count++;
            }
        }
        return count;
    }

    protected int countComponentC2() {
        int count = 0;
        for (Component c : componentController.myComponentsList) {
            if (c instanceof C2) {
                count++;
            }
        }
        return count;
    }

    protected int countComponentC3() {
        int count = 0;
        for (Component c : componentController.myComponentsList) {
            if (c instanceof C3) {
                count++;
            }
        }
        return count;
    }

    protected List<Component> removeComponents(Component... components) {
        List<Component> removedComponents = new ArrayList<>();
        for (Component component : components) {
            for (Iterator<Component> iterator = componentController.myComponentsList.iterator(); iterator.hasNext();) {
                Component c = iterator.next();
                if (c.getClass() == component.getClass()) {
                    iterator.remove();
                    removedComponents.add(c);
                    break;
                }
            }
        }
        return removedComponents;
    }
}
