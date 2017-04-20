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
         int I = 64;
        double epsDel,epsNedel;
        int K=1280;
        int Xk = I/2+5;
        Controller controller = new Controller(new BaseMainView() {
            @Override
            public void createGraph(List<Point> points) {

            }

            @Override
            public void updateGraph(List<Point> points, List<Point> explit, List<Point> implict) {
                System.out.println("Eps для х= "+points.get(Xk).getX()+" | "+explit.get(Xk).getX()+"   :\n"+(points.get(Xk).getY()-explit.get(Xk).getY()));

            }

            @Override
            public void onShow(int n) {

            }
        });
        Controller controller2 = new Controller(new BaseMainView() {
            @Override
            public void createGraph(List<Point> points) {

            }

            @Override
            public void updateGraph(List<Point> points, List<Point> explit, List<Point> implict) {
                System.out.println("Eps для х= "+points.get(Xk*2).getX()+" | "+explit.get(Xk*2).getX()+"   :\n"+(points.get(Xk*2).getY()-explit.get(Xk*2).getY()));

            }

            @Override
            public void onShow(int n) {

            }
        });
        controller.updatePoints(100, 0.005, 1.84, 25, 0.065, 0, 0.0001, true, K, I, false);
        controller2.updatePoints(100, 0.005, 1.84, 25, 0.065, 0, 0.0001, true, K, I, true);
    }

}