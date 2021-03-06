package controller;

import Model.Point;

import java.util.List;

/**
 * Created by Александр on 12.11.2016.
 */
public interface BaseController {
    void updatePoints(double t, double alpha, double c, double R, double k, double Uenv, double eps, boolean needQuality, int K, int I,boolean differentEps);

    void onUpdatePoints(List<Point> points, List<Point> explicit);

    void showN(int n);


}
