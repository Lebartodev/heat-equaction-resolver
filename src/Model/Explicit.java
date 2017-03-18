package Model;

import rx.Single;

import java.util.Arrays;
import java.util.List;

/**
 * Created by overl on 14.03.2017.
 */
public class Explicit {

    public Single<List<Point>> calculateSolution(int I, int K, double R, int t, double a, int k) {

        return Single.create(e -> {
            List<Point> points = Arrays.asList(getExplicitScheme(I, K, R, t, a, k));
            e.onSuccess(points);
        });
    }

    public Explicit() {
        //this.matrix=getExplicitScheme(i,k,R,t,a);

    }

    public Point[] getExplicitScheme(int I, int K, double R, int t, double a, int k) {
        System.out.println("I = "+I+" R= "+R+" t="+t+" a="+a+" k="+k);

        double hX = (2. * Math.PI * R) / (double)I;
        double hT =(double) t / (double)K;
        double gamma = (a * a) * hT / (hX * hX);
        //double[][] res = new double[i + 1][k + 1];
        Point[] res = new Point[I + 1];
        Point[] tmp = new Point[I + 1];

        System.out.println("ht= "+hT+"  hX= "+hX+" gamma="+gamma);
        for (int j = 0; j < I + 1; j++) {
          //  System.out.println(-Math.PI * R + j * hX + ":" + getZeroFunc(-Math.PI * R + j * hX, R));
            tmp[j] = new Point(-Math.PI * R + j * hX, getZeroFunc(-Math.PI * R + j * hX, R));
        }
        if (k == 0) {
            return tmp;
        }

        for (int l = 1; l <= k; l++) {

            for (int j = 1; j < I; j++) {

                    res[j] = new Point(-Math.PI * R + j * hX, l*gamma * (tmp[j + 1].getY() - 2 * tmp[j].getY() + tmp[j - 1].getY()) + tmp[j].getY());
            }
            res[0] = new Point(-Math.PI * R,l* gamma * (tmp[1].getY() - 2 * tmp[0].getY() + tmp[I-1].getY()) / (hX * hX) + tmp[0].getY());
            res[I] = new Point(-Math.PI * R + I * hX,l* gamma * (tmp[1].getY() - 2 * tmp[I].getY() + tmp[I - 1].getY()) + tmp[I].getY());
            for (int i = 0; i < res.length; i++) {
                tmp[i] = new Point(res[i].getX(), res[i].getY());
            }
        }
        return res;
    }

    public double getZeroFunc(double x, double R) {
        if ((x > (-Math.PI * R) / 2) && (x < (Math.PI * R) / 2))
            return 1;
        else
            return 0;
    }
}
