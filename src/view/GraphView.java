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
    private JTextField editEps;
    private JSlider slider;
    private JLabel labelN;


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
            double eps = Double.parseDouble(editEps.getText());
            double alp = Double.parseDouble(editalpha.getText());

            controller.updatePoints(t, alp, c, R, k,eps);
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
        labelN=new JLabel("");

        editEps = new JTextField();

        editC.setText("1.84");
        editR.setText("25");
        editalpha.setText("0.005");
        editK.setText("0.065");
        editEps.setText("0.0001");
        editC.getDocument().addDocumentListener(editListener);
        editR.getDocument().addDocumentListener(editListener);
        editalpha.getDocument().addDocumentListener(editListener);
        editK.getDocument().addDocumentListener(editListener);
        editEps.getDocument().addDocumentListener(editListener);
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
        final JPanel panelEps = new JPanel();
        final JPanel panelT = new JPanel();
        final JPanel panelalpha = new JPanel();
        final JPanel panelC = new JPanel();
        final JPanel panelK = new JPanel();

        panelR.setLayout(new BoxLayout(panelR, BoxLayout.X_AXIS));
        panelEps.setLayout(new BoxLayout(panelEps, BoxLayout.X_AXIS));
        panelT.setLayout(new BoxLayout(panelT, BoxLayout.X_AXIS));
        panelalpha.setLayout(new BoxLayout(panelalpha, BoxLayout.X_AXIS));
        panelC.setLayout(new BoxLayout(panelC, BoxLayout.X_AXIS));
        panelK.setLayout(new BoxLayout(panelK, BoxLayout.X_AXIS));
        panel1.setLayout(new GridLayout(1, 2, -1, -1));
        panel2.setLayout(new VerticalLayout());
        panelC.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelK.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelR.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelEps.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelalpha.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //frame.setLayout(new VerticalLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        slider = new JSlider(0, 1000, 1);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {

                JSlider dd = (JSlider) e.getSource();
               // if (!dd.getValueIsAdjusting()) {
                    updatePoints();
                label.setText("t = "+dd.getValue());
                //}


            }
        });

        Hashtable labelTable = new Hashtable();
        labelTable.put(new Integer(0), new JLabel("0"));
        labelTable.put(new Integer(500), new JLabel("500"));
        labelTable.put(new Integer(1000), new JLabel("1000"));
        slider.setLabelTable(labelTable);

        slider.setPaintLabels(true);


        // add to JPanel

        // Помещаем график на фрейм
        panel1.add(new ChartPanel(chart));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        labelN.setHorizontalAlignment(SwingConstants.CENTER);
        labelN.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel1.add(panel2);
        //panel1.add(button1, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2.add(panelR);
        panelR.add(new JLabel("R:"));
        panelR.add(editR);

        panelC.add(new JLabel("C:"));
        panelC.add(editC);

        panelalpha.add(new JLabel("Alpha:"));
        panelalpha.add(editalpha);

        panelEps.add(new JLabel("eps:"));
        panelEps.add(editEps);
        panelK.add(new JLabel("K:"));
        panelK.add(editK);


        panel2.add(panelC);
        panel2.add(panelK);
        panel2.add(panelT);
        panel2.add(panelalpha);
        panel2.add(panelEps);
        panel2.add(label);


        panel2.add(slider);
        panel2.add(labelN);

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

    public void onShow(int n) {
        labelN.setText("N = "+n);

    }
}
