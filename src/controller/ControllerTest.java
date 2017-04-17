package controller;

import Model.Point;
import org.junit.Test;
import view.BaseMainView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр on 16.04.2017.
 */
public class ControllerTest {
    @Test
    public void updatePoints() throws Exception {

        List<Double> epsDel = new ArrayList<>();
        List<Double> epsNedel = new ArrayList<>();
        Controller controller = new Controller(new BaseMainView() {
            @Override
            public void createGraph(List<Point> points) {

            }

            @Override
            public void updateGraph(List<Point> points, List<Point> explit, List<Point> implict) {
                if (epsNedel.size() == 0) {
                    for (int i = 0; i < points.size(); i++) {
                        System.out.println("x=" + points.get(i).getX() + " epsNedel = " + (points.get(i).getY() - explit.get(i).getY()));
                        epsNedel.add(points.get(i).getY() - explit.get(i).getY());
                    }
                } else {
                    for (int i = 0; i < points.size(); i++) {
                        //System.out.println(points.get(i).getY() - explit.get(i).getY());
                        System.out.println("x=" + points.get(i).getX() + " epsDel = " + (points.get(i).getY() - explit.get(i).getY()));
                        epsDel.add(points.get(i).getY() - explit.get(i).getY());
                    }
                }
                if (epsDel.size() > 0 && epsNedel.size() > 0) {
                    for (int i = 0; i < epsNedel.size(); i++) {
                        //System.out.println(points.get(i).getY() - explit.get(i).getY());
                        System.out.println("e/e = " + epsNedel.get(i) / epsDel.get(i * 2));
                    }
                }
            }

            @Override
            public void onShow(int n) {

            }
        });
        controller.updatePoints(100, 0.005, 1.84, 25, 0.065, 0, 0.0001, true, 1000, 250, false);
        controller.updatePoints(100, 0.005, 1.84, 25, 0.065, 0, 0.0001, true, 1000, 250, true);
    }

}