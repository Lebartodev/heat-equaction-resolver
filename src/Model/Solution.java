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

    public static double getSum(double x, double t, int N,double k,double c,double R,double Uenv) {
        double a2 = k / c;
        //double eps = 0.00001;
        double res = 0;
        for(int i = 1;i<N;i+=2)
            res+= (Coeffs.getBk(i, Uenv)*Coeffs.getSinKX(i,x,R) + Coeffs.getAk(i) * Coeffs.getCosKX(i, x,R)) * Math.exp(-a2 * Math.pow((i / R), 2) * t);
        return res;
    }

    public void calculateSolution(double t,double alpha,double c,double R,double k, double Uenv,double eps) {
        solution = new ArrayList<Point>();
        int N;
        int n;
        if(t==0) {
            n = 500;//getEvaluation(eps,1,R,k,c);
            N = getEvaluationQuality(n,alpha,R,c,Uenv,t,k,eps);
        }
        else {
            n = getEvaluation(eps, t, R, k, c);
            N = getEvaluationQuality(n,alpha,R,c,Uenv,t,k,eps);
            System.out.println(n);
            System.out.println(N);
        }
        //double a2 = Params.k/Params.c;
        double b2 =alpha * 2 / (c * R);
        double i = Math.PI * R;
        double step = 2 * Math.PI * R / Coeffs.pointNumber;
        for (double j = -i; j <= i; j += step) {
            double U = (Coeffs.getA0(0) + getSum(j, t, N,k,c,R, Uenv)) * Math.exp(-b2 * t);
            Point p = new Point(j, U);
            solution.add(p);
            //System.out.println("U" + String.valueOf(U));

        }
        controller.onUpdatePoints(solution);
        controller.showN(N);
    }

    public Solution(BaseController controller) {
        this.controller = controller;


    }

    public static int getEvaluation(double eps, double t,double R,double k,double c){
        int N = 1;
        double temp = -2*pow(R,2)/(Math.PI*pow(N,2)*(k/c)*t*exp(pow(N,2)*(k/c)*t/pow(R,2)));
        while(abs(temp)>eps){
            temp = -2*pow(R,2)/(Math.PI*pow(N,2)*(k/c)*t*exp(pow(N,2)*(k/c)*t/pow(R,2)));
            N++;
        }
        return N;
    }

    public static int getEvaluationQuality(int N,double alpha, double R, double c, double Uenv, double t, double k, double eps){
        int n = N;
        double b2 =alpha * 2 / (c * R);
        double i = Math.PI * R;
        double U=0;
        double temp=0;
        double Usub = 0;
        double step = 2 * Math.PI * R / Coeffs.pointNumber;
        for (double j = -i; j <= i; j += step) {
            U = getSum(j, t, N, k, c, R, Uenv);
        }
        do {
            n--;
            for (double j = -i; j <= i; j += step) {
                temp = getSum(j, t, n, k, c, R, Uenv);
            }
            Usub += U-temp;
        } while(abs(Usub)<eps);
        return n;
    }
}
