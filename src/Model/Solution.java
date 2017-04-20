package Model;

import rx.Observable;
import rx.Single;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * Created by Home on 12.11.2016.
 */
public class Solution {

    private List<Point> solution;

    public static double getSum(double x, double t, int N, double k, double c, double R, double Uenv, double alpha) {

        double a2 = k / c;
        double b2 = alpha * 2 / (c * R);
        //double eps = 0.00001;
        double res = 0;
        for (int i = 1; i < N; i += 2)
            res += (Coeffs.getBk(i, Uenv) * Coeffs.getSinKX(i, x, R) + Coeffs.getAk(i) * Coeffs.getCosKX(i, x, R)) * Math.exp(-a2 * Math.pow((i / R), 2) * t);
        return res;
    }

    public Single<List<Point>> calculateSolution(double t, double alpha, double c,
                                                 double R, double k, double Uenv,
                                                 double eps, boolean needEvaluation, int I) {
        return Single.create(e -> {
            System.out.println("--------------------");
            solution = new ArrayList<Point>();
            int N = 0;
            int n;
            double tmp_t = t;
            if (tmp_t == 0) {
                tmp_t = 0.1;
                n = getEvaluation(eps, tmp_t, R, k, c, alpha);
            } else {
                tmp_t++;
                n = getEvaluation(eps, tmp_t, R, k, c, alpha);
            }
            System.out.println("Nt = " + n);
            double b2 = alpha * 2 / (c * R);
            double i = Math.PI * R;
            double step = 2. * Math.PI * R / I;

            if (needEvaluation) {
                for (double j = -i; j <= i; j += step) {
                    N = getEvaluationQuality(n, alpha, R, c, Uenv, t, k, eps, j);
                    double U = (Coeffs.getA0(0) + getSum(j, t, N, k, c, R, Uenv, alpha)) * Math.exp(-b2 * t);
                    Point p = new Point(j, U);
                    solution.add(p);
                    /*if (j <= -49.86 && j >= -50.1) {
                        System.out.println("x = " + -50 + " Ne = " + N);
                    } else if (j <= 0.1 && j >= -0.1)
                        System.out.println("x = " + 0 + " Ne = " + N);*/
                }
                e.onSuccess(solution);
                // controller.onUpdatePoints(solution);
                // controller.showN(N);
            } else {
                for (double j = -i; j <= i; j += step) {
                    double U = (Coeffs.getA0(0) + getSum(j, t, n, k, c, R, Uenv, alpha)) * Math.exp(-b2 * t);
                    Point p = new Point(j, U);
                    solution.add(p);
                    //if ((j <= -49.86 && j >= -50.1) || (j <= 0.1 && j >= -0.1))
                    //System.out.println(n);
                }
                e.onSuccess(solution);
                // controller.onUpdatePoints(solution);
                // controller.showN(n);

            }
        });

        //System.out.println("--------------------");
    }

    public Solution() {

    }

    public static int getEvaluation(double eps, double t, double R, double k, double c, double alpha) {
        int N = 1;
        double b2 = alpha * 2 / (c * R);
        double temp = -2 * pow(R, 2) / (Math.PI * pow(N, 2) * (k / c) * t * exp(pow(N, 2) * (k / c) * t / pow(R, 2))) * Math.exp(-b2 * t);
        while (abs(temp) > eps) {
            temp = -2 * pow(R, 2) / (Math.PI * pow(N, 2) * (k / c) * t * exp(pow(N, 2) * (k / c) * t / pow(R, 2))) * Math.exp(-b2 * t);
            N++;
        }
        return N;
    }

    public static int getEvaluationQuality(int N, double alpha, double R, double c, double Uenv, double t, double k, double eps, double j) {
        int n = N;
        double b2 = alpha * 2 / (c * R);
        double i = Math.PI * R;
        double U = 0;
        double temp = 0;
        double Usub = 0;
        //double step = 2 * Math.PI * R / Coeffs.pointNumber;

        do {
            U = (Coeffs.getA0(0) + getSum(j, t, n, k, c, R, Uenv, alpha)) * Math.exp(-b2 * t);
            n--;
            temp = (Coeffs.getA0(0) + getSum(j, t, n, k, c, R, Uenv, alpha)) * Math.exp(-b2 * t);
            Usub += abs(U - temp);
        } while (abs(Usub) < eps && n > 1);
        return n;
    }
}
