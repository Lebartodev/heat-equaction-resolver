package controller;

import Model.Explicit;
import Model.Point;
import Model.Solution;
import rx.Single;
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
        this.view = view;
        //Чтобы заработало раскоммить
      //  updatePoints(0, 0.005, 1.84, 25, 0.065, 0, 0.0001, false, 100, 25,false);

    }


    public void updatePoints(double t, double alpha, double c, double R, double k, double Uenv, double eps, boolean needQuality, int K, int I,boolean differentEps) {

        if(differentEps)
        {
            I*=2.0;
            k*=4.0;
            K*=4.0;

        }
        Single.zip(solutionExplicit.calculateSolution(I, K, R, 1000, k, c, (int) t, alpha),
                solution.calculateSolution(t * ((double) 1000 / (double) K), alpha, c, R, k, Uenv, eps, needQuality,I), (points, points2) -> {
                    onUpdatePoints(points2, points);



                    return null;

                }).subscribe();


    }

    public void onUpdatePoints(List<Point> points, List<Point> explit) {
        view.updateGraph(points, explit,new ArrayList<>());
    }

    public void showN(int n) {
        view.onShow(n);
    }
}
