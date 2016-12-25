package Model;

import controller.BaseController;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * Created by Home on 12.11.2016.
 */
public class Solution {

    private List<Point> solution;
    BaseController controller;

    public static double getSum(double x, double t, int N, double k, double c, double R, double Uenv, double alpha) {
        double a2 = k / c;
        double b2 =alpha * 2 / (c * R);
        //double eps = 0.00001;
        double res = 0;
        for(int i = 1;i<N;i+=2)
            res+= (Coeffs.getBk(i, Uenv)*Coeffs.getSinKX(i,x,R) + Coeffs.getAk(i) * Coeffs.getCosKX(i, x,R)) * Math.exp(-a2 * Math.pow((i / R), 2) * t);
        return res;
    }

    public void calculateSolution(double t,double alpha,double c,double R,double k, double Uenv,double eps,boolean needEvaluation) {
        solution = new ArrayList<Point>();
        int N=0;
        int n;
        if(t==0) {
            t=0.1;
            n =getEvaluation(eps, t, R, k, c, alpha);
            System.out.println(n);
        }
        else {
            t++;
            n = getEvaluation(eps, t, R, k, c, alpha);
            System.out.println(n);
        }
        //double a2 = Params.k/Params.c;
        double b2 =alpha * 2 / (c * R);
        double i = Math.PI * R;
        double step = 2 * Math.PI * R / Coeffs.pointNumber;
        for (double j = -i; j <= i; j += step) {
           // if(needEvaluation)
            N = getEvaluationQuality(n,alpha,R,c,Uenv,t,k,eps,j);
            //else
           //     N = getEvaluation(n,alpha,R,c,Uenv,t);
            double U = (Coeffs.getA0(0) + getSum(j, t, N,k,c,R, Uenv, alpha)) * Math.exp(-b2 * t);
            Point p = new Point(j, U);
            solution.add(p);
            if((j<=-49.86 && j>=-50.1)||(j<=0.1 && j>=-0.1))
                System.out.println(N);


        }
        controller.onUpdatePoints(solution);
        controller.showN(N);
    }

    public Solution(BaseController controller) {
        this.controller = controller;


    }

    public static int getEvaluation(double eps, double t,double R,double k,double c, double alpha){
        int N = 1;
        double b2 =alpha * 2 / (c * R);
        double temp = -2*pow(R,2)/(Math.PI*pow(N,2)*(k/c)*t*exp(pow(N,2)*(k/c)*t/pow(R,2)))* Math.exp(-b2 * t);
        while(abs(temp)>eps){
            temp = -2*pow(R,2)/(Math.PI*pow(N,2)*(k/c)*t*exp(pow(N,2)*(k/c)*t/pow(R,2)))* Math.exp(-b2 * t);
            N++;
        }
        return N;
    }

    public static int getEvaluationQuality(int N,double alpha, double R, double c, double Uenv, double t, double k, double eps,double j){
        int n = N;
        double b2 =alpha * 2 / (c * R);
        double i = Math.PI * R;
        double U=0;
        double temp=0;
        double Usub = 0;
        //double step = 2 * Math.PI * R / Coeffs.pointNumber;

        do {
                U = (Coeffs.getA0(0) + getSum(j, t, n, k, c, R, Uenv, alpha)) * Math.exp(-b2 * t);
            n--;
                temp = (Coeffs.getA0(0) + getSum(j, t, n, k, c, R, Uenv, alpha)) * Math.exp(-b2 * t);
            Usub += abs(U-temp);
        } while(abs(Usub)<eps && n>1);
        return n;
    }
}
