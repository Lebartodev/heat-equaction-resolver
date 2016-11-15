package Model;

import controller.BaseController;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.exp;
import static java.lang.Math.pow;

/**
 * Created by Home on 12.11.2016.
 */
public class Solution {

    private List<Point> solution;
    BaseController controller;

    public double getSum(double x, double t, int N) {
        double a2 = Params.k / Params.c;
        //double eps = 0.00001;
        double res = 0;
        for(int i = 1;i<N;i+=2)
            res+= Coeffs.getAk(i) * Coeffs.getCosKX(i, x) * Math.exp(-a2 * Math.pow((i / Params.R), 2) * t);
        return res;
    }

    public void calculateSolution(double t) {
        solution = new ArrayList<Point>();
        int N;
        if(t==0)
            N  = 10000;
        else
            N = getEvaluation(0.0001,t);
        //double a2 = Params.k/Params.c;
        double b2 = Params.alpha * 2 / (Params.c * Params.R);
        double i = Math.PI * Params.R;
        double step = 2 * Math.PI * Params.R / 400;
        for (double j = -i; j <= i; j += step) {
            double U = (0.5 + getSum(j, t, N)) * Math.exp(-b2 * t);
            Point p = new Point(j, U);
            solution.add(p);
            //System.out.println("U" + String.valueOf(U));

        }
        controller.onUpdatePoints(solution);
    }

    public Solution(BaseController controller) {
        this.controller = controller;


    }

    public static int getEvaluation(double eps, double t){
        int N = 1;
        double temp = -2*pow(Params.R,2)/(Math.PI*pow(N,2)*(Params.k/Params.c)*t*exp(pow(N,2)*(Params.k/Params.c)*t/pow(Params.R,2)));
        while(abs(temp)>eps){
            temp = -2*pow(Params.R,2)/(Math.PI*pow(N,2)*(Params.k/Params.c)*t*exp(pow(N,2)*(Params.k/Params.c)*t/pow(Params.R,2)));
            N++;
        }
        return N;
    }

    ;
}
