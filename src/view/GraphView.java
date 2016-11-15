package view;

import Model.Point;
import controller.BaseController;
import controller.Controller;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.tabbedui.VerticalLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Александр on 13.11.2016.
 */
public class GraphView implements BaseMainView {
    private BaseController controller;
    private XYSeries series;
    private XYDataset xyDataset;
    private JFreeChart chart;
    private JLabel label;

    //Должен создавать что-то типа new Controller(this); где this это view.BaseMainView
    public GraphView() {
        controller = new Controller(this);
    }

    //Этот метод вызывается из контроллера
    public void createGraph(List<Point> points) {
        label = new JLabel("t=0");

        series = new XYSeries("U(x,t)");

        for (Point point : points)
            series.add(point.getX(), point.getY());


        xyDataset = new XYSeriesCollection(series);
        chart = ChartFactory
                .createXYLineChart("U(x,t)", "x", "U",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        XYPlot plot = (XYPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAxisLineVisible(true);
        rangeAxis.setRange(0, 1.2);
        JFrame frame =
                new JFrame("MinimalStaticChart");
        frame.setLayout(new VerticalLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JSlider slider = new JSlider(0, 10000, 1);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {

                JSlider dd = (JSlider) e.getSource();
                if (!dd.getValueIsAdjusting()) {
                    label.setText("t=" + dd.getValue());
                    //textField.setText(String.valueOf(source.getValue()));
                    controller.updatePoints(dd.getValue());
                }


            }
        });

        Hashtable labelTable = new Hashtable();
        labelTable.put(new Integer(0), new JLabel("0"));
        labelTable.put(new Integer(5000), new JLabel("5000"));
        labelTable.put(new Integer(10000), new JLabel("10000"));
        slider.setLabelTable(labelTable);

        slider.setPaintLabels(true);


        // add to JPanel

        // Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(slider);
        frame.add(label);
        frame.setSize(1000, 1000);

        frame.show();
    }

    public void updateGraph(List<Point> points) {
        if (chart != null) {
            XYPlot plot = (XYPlot) chart.getPlot();
            series = new XYSeries("U(x,t)");

            for (Point point : points)
                series.add(point.getX(), point.getY());
            xyDataset = new XYSeriesCollection(series);

            plot.setDataset(xyDataset);
        } else
            createGraph(points);
    }
}
