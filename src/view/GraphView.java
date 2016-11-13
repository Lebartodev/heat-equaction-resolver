package view;

import Model.Point;
import controller.BaseController;
import controller.Controller;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.List;

/**
 * Created by Александр on 13.11.2016.
 */
public class GraphView implements BaseMainView {
    private BaseController controller;

    //Должен создавать что-то типа new Controller(this); где this это view.BaseMainView
    public GraphView() {
        controller = new Controller(this);
    }

    //Этот метод вызывается из контроллера
    public void createGraph(List<Point> points) {
        XYSeries series = new XYSeries("sin(a)");

        for (Point point : points)
            series.add(point.getX(), point.getY());


        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart("U(x,t)", "x", "U",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        JFrame frame =
                new JFrame("MinimalStaticChart");
        // Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(400, 300);
        frame.show();
    }

    public void updateGraph(List<Point> points) {

    }
}
