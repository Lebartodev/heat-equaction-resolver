package view;

import Model.Point;

import java.util.List;

/**
 * Created by Александр on 12.11.2016.
 */
public interface BaseMainView {
    void createGraph(List<Point> points);
    void updateGraph(List<Point> points,List<Point> explit);
    void onShow(int n);

}
