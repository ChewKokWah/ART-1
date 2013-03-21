/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.neuroph.netbeans.main.easyneurons.errorgraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.logging.Logger;
import org.jfree.chart.ChartPanel;
import org.nugs.graph2d.Chart2D;
import org.nugs.graph2d.Graph2DProperties;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

@TopComponent.Description(preferredID = "JFreeChartTopComponent",
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "org.neuroph.netbeans.main.easyneurons.errorgraph.JFreeChartTopComponent")
@ActionReference(path = "Menu/Window")
@TopComponent.OpenActionRegistration(displayName = "#CTL_JFreeChartTopComponentAction")
@Messages({
    "CTL_JFreeChartTopComponentAction=Nerwork Error Graph",
    "CTL_JFreeChartTopComponent=Network Error Graph",
    "HINT_JFreeChartTopComponent=This is a Network Error Graph"
})
public final class JFreeChartTopComponent extends TopComponent {

    private static JFreeChartTopComponent instance;
    private static final String PREFERRED_ID = "JFreeChartTopComponent";
    private BufferedReader br;
    private static final String FILE_URL = "errorarray.txt";
    int[] chartDataX;
    double[] chartDataY;

    public JFreeChartTopComponent() {
        initComponents();
        setName(Bundle.CTL_JFreeChartTopComponent());
        setToolTipText(Bundle.HINT_JFreeChartTopComponent());
        putClientProperty(TopComponent.PROP_DRAGGING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(596, 383));

        jPanel1.setPreferredSize(new java.awt.Dimension(0, 0));
        jPanel1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        drawChartFromFile();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ONLY_OPENED;
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        if (instance == null) {
            instance = this;
        }
        instance.readPropertiesImpl(p);
        return instance;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files
     * only, i.e. deserialization routines; otherwise you could get a
     * non-deserialized instance. To obtain the singleton instance, use
     * {@link #findInstance}.
     */
    public static synchronized JFreeChartTopComponent getDefault() {
        if (instance == null) {
            instance = new JFreeChartTopComponent();
        }
        return instance;
    }
    
    /**
     * Obtain the JFreeChartTopComponent instance. Never call
     * {@link #getDefault} directly!
     */
    public static synchronized JFreeChartTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(JFreeChartTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof JFreeChartTopComponent) {
            return (JFreeChartTopComponent) win;
        }
        Logger.getLogger(JFreeChartTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }
    /*
    Interface Graph2D
	create2DChart(double[] x, double[]y)
*/
    //   drawChart2D( double[][])
//    drawHist2D( double[][])            
    public void drawChartFromFile() {
        
        jPanel1.removeAll();

        readChartDataFromFileBuffer();

        Chart2D chart2D = new Chart2D();
        Graph2DProperties prop = new Graph2DProperties("Network Error Graph", "Iteration", "Total network error");
        prop.setTooltipsVisible(true);
        
        ChartPanel chartPanel = chart2D.create2dChart(chartDataX, chartDataY, prop);
        jPanel1.add(chartPanel);

//        jPanel1.revalidate();
        revalidate();

    }

    /**
     *  Read chart data into chartDataXY arrays from file buffer
     */
    public void readChartDataFromFileBuffer() { // double[][]  , int[][],  drawChart3Ddouble[][][], int[][][]

        try {
            if (br == null) {
                br = new BufferedReader(new FileReader(FILE_URL));
            }
            String currentLine;
            int counter = 0;

            // Initialize arrays to number of lines in the file
            LineNumberReader lnr = new LineNumberReader(new FileReader(FILE_URL));
            lnr.skip(Long.MAX_VALUE);
            chartDataX = new int[lnr.getLineNumber()];
            chartDataY = new double[lnr.getLineNumber()];

            // Read from file and write to arrays
            while ((currentLine = br.readLine()) != null) {
                String[] values = currentLine.split(",");
                chartDataX[counter] = Integer.parseInt(values[0]);
                chartDataY[counter++] = Double.parseDouble(values[1]);
            }

            // Close BufferReader
            br = null;
//
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
/*
    protected ChartPanel create2DChart(double[] iterations, double[] errors) {
        ChartPanel panel;

        // Add points from arrays to dataset of linegraph...
        XYSeries series1 = new XYSeries("(Iteration, Error)");
        for (int i = 0; i < errors.length; i++) {
            for (int j = i; j < iterations.length; j++) {
                series1.add(iterations[j], errors[i]);
                break;
            }
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);

        // Create the linechart...
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Network Error Graph", // chart title
                "Iteration", // x axis label (domain axis)
                "Total network error", // y axis label (range axis)
                dataset, // data
                PlotOrientation.VERTICAL,
                false, // include legend
                true, // tooltips
                false // urls
                );

        // Additional settings...
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(0xF5F5F5));  // Lighter gray background   
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.getRenderer().setSeriesStroke(0, new BasicStroke(2f)); // Bold line
        plot.getRenderer().setSeriesPaint(0, Color.orange); // Orange line
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); // Iterations are integer

        //  Regulation of range scale manually...
        //domain.setTickUnit(new NumberTickUnit(5)); // Iteration range scale is 5

        panel = new ChartPanel(chart);
        return panel;
    }
    
    */
}
