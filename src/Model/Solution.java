package Model;

import controller.BaseController;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by Home on 12.11.2016.
 */
public class Solution {

    private List<Point> solution;
    BaseController controller;

    public double getSum(double x, double t) {
        double a2 = Params.k / Params.c;
        double eps = 0.00001;
        double res = 0;
        double temp;
        int k = 1;
        do {
            temp = Coeffs.getAk(k) * Coeffs.getCosKX(k, x) * Math.exp(-a2 * Math.pow((k / Params.R), 2) * t);
            res += temp;
            k += 2;
        } while (abs(temp) > eps);
        return res;
    }

    public void calculateSolution(double t) {
        solution = new ArrayList<Point>();
        //double a2 = Params.k/Params.c;
        double b2 = Params.alpha * 2 / (Params.c * Params.R);
        double i = Math.PI * Params.R;
        double step = 2 * Math.PI * Params.R / 400;
        for (double j = -i; j <= i; j += step) {
            double U = (0.5 + getSum(j, t)) * Math.exp(-b2 * t);
            Point p = new Point(j, U);
            solution.add(p);
            //System.out.println("U" + String.valueOf(U));

        }
        controller.onUpdatePoints(solution);
    }

    public Solution(BaseController controller) {
        this.controller = controller;


    }

    ;
}
