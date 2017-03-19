package controller;

import Model.Explicit;
import Model.Point;
import Model.Solution;
import view.BaseMainView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр on 13.11.2016.
 */
public class Controller implements BaseController {
    // private Explicit solution=new Explicit();
    private Solution solution = new Solution();
    private Explicit solutionExplicit = new Explicit();
    private BaseMainView view;

    public Controller(BaseMainView view) {
        //solution = new Solution(this);
        this.view = view;
            /*public static double c = 1.84;
    public static double alpha = 0.005;
    public static double k = 0.065;
    public static double R = 25;
    public static double Uenv = 0;
    public static double T = 50;*/
        updatePoints(0, 0.005, 1.84, 25, 0.065, 0, 0.0001, false);

    }


    public void updatePoints(double t, double alpha, double c, double R, double k, double Uenv, double eps, boolean needQuality) {
        solutionExplicit.calculateSolution(1000, 1000, R, 1000, alpha, (int) t).subscribe(points -> {
            solution.calculateSolution(t, alpha, c, R, k, Uenv, eps, needQuality).subscribe(pointsExplicit -> {
                onUpdatePoints(points,pointsExplicit);
            });
        });


    }

    public void onUpdatePoints(List<Point> points, List<Point> explit) {
        view.updateGraph(points, explit);
    }

    public void showN(int n) {
        view.onShow(n);
    }
}
