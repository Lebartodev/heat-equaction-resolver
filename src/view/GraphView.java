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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    private JTextField editC;
    private JTextField editalpha;
    private JTextField editK;
    private JTextField editR;
    private JTextField editUenv;
    private JTextField editT;
    private JSlider slider;

    DocumentListener editListener = new DocumentListener() {
        public void insertUpdate(DocumentEvent e) {
            updatePoints();
        }

        public void removeUpdate(DocumentEvent e) {
            updatePoints();
        }

        public void changedUpdate(DocumentEvent e) {
            updatePoints();
        }
    };

    private void updatePoints() {
        try {
            double t = slider.getValue();
            double R = Double.parseDouble(editR.getText());
            double c = Double.parseDouble(editC.getText());
            double k = Double.parseDouble(editK.getText());
            double Uenv = Double.parseDouble(editUenv.getText());
            double alp = Double.parseDouble(editalpha.getText());

            controller.updatePoints(t, alp, c, R, k);
        }
        catch (Exception e){
            System.out.println("Exception");
        }
    }

    //Должен создавать что-то типа new Controller(this); где this это view.BaseMainView
    public GraphView() {
        controller = new Controller(this);
    }

    private void initFields() {
        editR = new JTextField();
        editalpha = new JTextField();
        editC = new JTextField();
        editK = new JTextField();
        editT = new JTextField();
        editUenv = new JTextField();

        editC.setText("1.84");
        editR.setText("25");
        editalpha.setText("0.005");
        editK.setText("0.065");
        editUenv.setText("0");
        editT.setText("50");
        editC.getDocument().addDocumentListener(editListener);
        editR.getDocument().addDocumentListener(editListener);
        editalpha.getDocument().addDocumentListener(editListener);
        editK.getDocument().addDocumentListener(editListener);
        editUenv.getDocument().addDocumentListener(editListener);
        editT.getDocument().addDocumentListener(editListener);
    }

    //Этот метод вызывается из контроллера
    public void createGraph(List<Point> points) {
        label = new JLabel("t=0");
        initFields();


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
        //
        final JPanel panel1 = new JPanel();
        final JPanel panel2 = new JPanel();

        final JPanel panelR = new JPanel();
        final JPanel panelUenv = new JPanel();
        final JPanel panelT = new JPanel();
        final JPanel panelalpha = new JPanel();
        final JPanel panelC = new JPanel();
        final JPanel panelK = new JPanel();

        panelR.setLayout(new BoxLayout(panelR, BoxLayout.X_AXIS));
        panelUenv.setLayout(new BoxLayout(panelUenv, BoxLayout.X_AXIS));
        panelT.setLayout(new BoxLayout(panelT, BoxLayout.X_AXIS));
        panelalpha.setLayout(new BoxLayout(panelalpha, BoxLayout.X_AXIS));
        panelC.setLayout(new BoxLayout(panelC, BoxLayout.X_AXIS));
        panelK.setLayout(new BoxLayout(panelK, BoxLayout.X_AXIS));
        panel1.setLayout(new GridLayout(1, 2, -1, -1));
        panel2.setLayout(new VerticalLayout());
        panelC.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelK.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelR.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelT.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelUenv.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelalpha.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //frame.setLayout(new VerticalLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        slider = new JSlider(0, 10000, 1);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {

                JSlider dd = (JSlider) e.getSource();
                if (!dd.getValueIsAdjusting()) {
                    updatePoints();
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
        panel1.add(new ChartPanel(chart));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel1.add(panel2);
        //panel1.add(button1, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2.add(panelR);
        panelR.add(new JLabel("R:"));
        panelR.add(editR);

        panelC.add(new JLabel("C:"));
        panelC.add(editC);

        panelalpha.add(new JLabel("Alpha:"));
        panelalpha.add(editalpha);

        panelUenv.add(new JLabel("Uenv:"));
        panelUenv.add(editUenv);
        panelK.add(new JLabel("K:"));
        panelK.add(editK);
        panelT.add(new JLabel("T:"));
        panelT.add(editT);


        panel2.add(panelC);
        panel2.add(panelK);
        panel2.add(panelT);
        panel2.add(panelalpha);
        panel2.add(panelUenv);


        panel2.add(slider);

        frame.add(panel1);
        frame.setSize(1000, 400);

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
