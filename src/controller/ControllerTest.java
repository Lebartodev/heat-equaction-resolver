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

        List<Double> eps = new ArrayList<>();
        Controller controller = new Controller(new BaseMainView() {
            @Override
            public void createGraph(List<Point> points) {

            }

            @Override
            public void updateGraph(List<Point> points, List<Point> explit, List<Point> implict) {
                if (eps.size() == 0) {
                    for (int i = 0; i < points.size(); i++) {
                        System.out.println(points.get(i).getY() - explit.get(i).getY());
                        eps.add(points.get(i).getY() - explit.get(i).getY());
                    }
                }
                else{
                    for (int i = 0; i < points.size(); i++) {
                        //System.out.println(points.get(i).getY() - explit.get(i).getY());
                        System.out.println(eps.get(i)/(points.get(i).getY() - explit.get(i).getY()));
                    }
                }

            }

            @Override
            public void onShow(int n) {

            }
        });
        controller.updatePoints(100, 0.005, 1.84, 25, 0.065, 0, 0.0001, false, 20, 8, true);
        controller.updatePoints(100, 0.005, 1.84, 25, 0.065, 0, 0.0001, false, 20, 8, false);
    }

}