package controller;

import Model.Point;
import Model.Solution;
import controller.BaseController;
import view.BaseMainView;


import java.util.List;

/**
 * Created by Александр on 13.11.2016.
 */
public class Controller implements BaseController {
    private Solution solution;

    private BaseMainView view;

    public Controller(BaseMainView view) {
        solution = new Solution(this);
        this.view = view;
        updatePoints(0);

    }


    public void updatePoints(double t) {
        solution.calculateSolution(t);

    }

    public void onUpdatePoints(List<Point> points) {
        view.updateGraph(points);
    }
}
