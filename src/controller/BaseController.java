package controller;

import Model.Point;

import java.util.List;

/**
 * Created by Александр on 12.11.2016.
 */
public interface BaseController {
    void updatePoints(double t,double alpha,double c,double R,double k,double eps);
    void onUpdatePoints(List<Point> points);
    void showN(int n);


}
