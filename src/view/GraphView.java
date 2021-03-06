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
import org.jfree.chart.renderer.xy.DeviationRenderer;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Александр on 13.11.2016.
 */
public class GraphView implements BaseMainView {
    private BaseController controller;
    private XYSeries series, seriesExplit;
    private XYDataset xyDataset, explitDataset;
    private JFreeChart chart;
    private JLabel label;
    private JTextField editC;
    private JTextField editalpha;
    private JTextField editK;
    private JTextField editKmax;
    private JTextField editImax;
    private JTextField editR;
    private JTextField editEps;
    private JTextField editUenv;
    private JCheckBox qualityBox;
    private JSlider slider;
    private JLabel labelN;
    private int maxTime=1000;


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
            double uenv = Double.parseDouble(editUenv.getText());
            int Kmax = Integer.parseInt(editKmax.getText());
            double hT = (double) 1000 / (double) Kmax;

            maxTime=Kmax;
            int Imax = Integer.parseInt(editImax.getText());
            boolean q = qualityBox.isSelected();
            //slider.setMaximum(Kmax);


            Hashtable labelTable = new Hashtable();
            labelTable.put(new Integer(0), new JLabel("0"));
            labelTable.put(new Integer(maxTime/2), new JLabel(String.valueOf(maxTime/2)));
            labelTable.put(new Integer(maxTime), new JLabel(String.valueOf(maxTime)));
            slider.setMaximum(maxTime);
            slider.setLabelTable(labelTable);
            label.setText("k = " + t+" ;hT="+hT+" ;t="+t*hT);
            controller.updatePoints(t, alp, c, R, k, uenv, eps, q, Kmax, Imax,false);
        } catch (Exception e) {
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
        labelN = new JLabel("");
        qualityBox = new JCheckBox("Quality");
        editEps = new JTextField();
        editUenv = new JTextField();
        editKmax = new JTextField();
        editImax = new JTextField();

        editC.setText("1.84");
        editR.setText("25");
        editalpha.setText("0.005");
        editK.setText("0.065");
        editEps.setText("0.0001");
        editUenv.setText("0");
        editKmax.setText(String.valueOf(maxTime));
        editImax.setText("250");
        editC.getDocument().addDocumentListener(editListener);
        editR.getDocument().addDocumentListener(editListener);
        editalpha.getDocument().addDocumentListener(editListener);
        editK.getDocument().addDocumentListener(editListener);
        editEps.getDocument().addDocumentListener(editListener);
        editUenv.getDocument().addDocumentListener(editListener);
        editKmax.getDocument().addDocumentListener(editListener);
        editImax.getDocument().addDocumentListener(editListener);
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
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAxisLineVisible(true);
        // rangeAxis.setRange(0, 5);
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
        final JPanel panelQ = new JPanel();
        final JPanel panelUenv = new JPanel();
        final JPanel panelImax = new JPanel();
        final JPanel panelKmax = new JPanel();

        panelR.setLayout(new BoxLayout(panelR, BoxLayout.X_AXIS));
        panelEps.setLayout(new BoxLayout(panelEps, BoxLayout.X_AXIS));
        panelT.setLayout(new BoxLayout(panelT, BoxLayout.X_AXIS));
        panelalpha.setLayout(new BoxLayout(panelalpha, BoxLayout.X_AXIS));
        panelC.setLayout(new BoxLayout(panelC, BoxLayout.X_AXIS));
        panelK.setLayout(new BoxLayout(panelK, BoxLayout.X_AXIS));
        panelUenv.setLayout(new BoxLayout(panelUenv, BoxLayout.X_AXIS));
        panelQ.setLayout(new BoxLayout(panelQ, BoxLayout.X_AXIS));
        panelKmax.setLayout(new BoxLayout(panelKmax, BoxLayout.X_AXIS));
        panelImax.setLayout(new BoxLayout(panelImax, BoxLayout.X_AXIS));
        panel1.setLayout(new GridLayout(1, 2, -1, -1));
        panel2.setLayout(new VerticalLayout());
        panelC.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelUenv.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelK.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelR.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelEps.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelalpha.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelImax.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelKmax.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelQ.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //frame.setLayout(new VerticalLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        slider = new JSlider(0, 1000, 0);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {

                JSlider dd = (JSlider) e.getSource();
                if (!dd.getValueIsAdjusting()) {
                    updatePoints();
                    //label.setText("t = " + dd.getValue());
                }


            }
        });
        qualityBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                updatePoints();
            }
        });

        Hashtable labelTable = new Hashtable();
        labelTable.put(new Integer(0), new JLabel("0"));
        labelTable.put(new Integer(maxTime/2), new JLabel(String.valueOf(maxTime/2)));
        labelTable.put(new Integer(maxTime), new JLabel(String.valueOf(maxTime)));
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
        panelQ.add(qualityBox);
        panelUenv.add(new JLabel("U env: "));
        panelUenv.add(editUenv);
        panelalpha.add(new JLabel("Alpha: "));
        panelalpha.add(editalpha);

        panelEps.add(new JLabel("eps: "));
        panelEps.add(editEps);
        panelK.add(new JLabel("K: "));
        panelK.add(editK);
        panelKmax.add(new JLabel("Kmax: "));
        panelKmax.add(editKmax);
        panelImax.add(new JLabel("Imax: "));
        panelImax.add(editImax);


        panel2.add(panelC);
        panel2.add(panelK);
        panel2.add(panelT);
        panel2.add(panelalpha);
        panel2.add(panelEps);
        panel2.add(panelQ);
        panel2.add(panelKmax);
        panel2.add(panelImax);
        panel2.add(label);



        panel2.add(slider);
        panel2.add(labelN);

        frame.add(panel1);
        frame.setSize(1000, 600);

        frame.show();

    }

    public void updateGraph(List<Point> points, List<Point> expilPoints,List<Point> implicitPoints) {


        System.out.println("sizepoints: "+points.size());
       for (int i = 0; i < points.size(); i++) {
           System.out.println("x="+points.get(i).getX()+" y="+points.get(i).getY());

        }

        System.out.println("size: "+expilPoints.size());
        for (int i = 0; i < expilPoints.size(); i++) {
            System.out.println("x="+expilPoints.get(i).getX()+" y="+expilPoints.get(i).getY());

        }
        if (chart != null) {
            XYPlot plot = (XYPlot) chart.getPlot();
            series = new XYSeries("U(x,t)");

            for (Point point : points)
                series.add(point.getX(), point.getY());
            xyDataset = new XYSeriesCollection(series);

            seriesExplit = new XYSeries("Explit(x,t)");


            for (Point point : expilPoints)
                seriesExplit.add(point.getX(), point.getY());
            explitDataset = new XYSeriesCollection(seriesExplit);


            plot.setDataset(1, xyDataset);
            plot.setDataset(0, explitDataset);
            DeviationRenderer renderer = new DeviationRenderer(true, false);
            plot.setRenderer(0, renderer);
            DeviationRenderer renderer2 = new DeviationRenderer(true, false);
            plot.setRenderer(1, renderer2);
            plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(0, Color.red);
            plot.getRendererForDataset(plot.getDataset(1)).setSeriesPaint(0, Color.blue);
            plot.setBackgroundPaint(Color.white);
            plot.setDomainGridlinePaint(Color.GRAY);
            plot.setRangeGridlinePaint(Color.GRAY);
        } else
            createGraph(points);


    }

    public void onShow(int n) {
        labelN.setText("N = " + n);

    }
}
