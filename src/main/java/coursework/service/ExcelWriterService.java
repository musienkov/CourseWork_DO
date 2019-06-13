package coursework.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.*;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xddf.usermodel.chart.AxisCrosses;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;


@Service("excelWriterService")
public class ExcelWriterService {

    private static Map<Integer, Object[]> timesMap = new TreeMap<>();
    private static Map<Integer, Object[]> floydWarshallMap = new TreeMap<>();
    private static Map<Integer, Object[]> dfsMap = new TreeMap<>();


    /**
     * Write data into the .xls file
     */
    public void write() throws IOException, FileNotFoundException {
        XSSFWorkbook wb = new XSSFWorkbook();

            XSSFSheet sheet = wb.createSheet("Algorithms");
            Row row = sheet.createRow(0);
            Cell size = row.createCell(0);
            size.setCellValue("Size");
            Cell floydWarshall = row.createCell(1);
            floydWarshall.setCellValue("Floyd-Warshall");
            Cell dfs = row.createCell(2);
            dfs.setCellValue("DFS");

            fillTable(sheet,timesMap);
            createChart(sheet);



        try {
            FileOutputStream out = new FileOutputStream(new File("Output.xls"));
            wb.write(out);
            out.close();
            System.out.println("Output.xls created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Create chart
     * @param sheet sheet on which there will be a chart
     */
    private void createChart(XSSFSheet sheet){
        final int NUM_OF_ROWS = 5;//todo
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 6, 9, 23);
        XSSFChart chart = drawing.createChart(anchor);
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(org.apache.poi.xddf.usermodel.chart.LegendPosition.TOP_RIGHT);
        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(org.apache.poi.xddf.usermodel.chart.AxisPosition.BOTTOM);
        bottomAxis.setTitle("Number of vertex");
        XDDFValueAxis leftAxis = chart.createValueAxis(org.apache.poi.xddf.usermodel.chart.AxisPosition.LEFT);
        leftAxis.setTitle("Time of work (ns)");
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        XDDFDataSource<Double> xs = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, NUM_OF_ROWS, 0, 0));
        XDDFNumericalDataSource<Double> ys1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, NUM_OF_ROWS, 1, 1));
        XDDFNumericalDataSource<Double> ys2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, NUM_OF_ROWS, 2, 2));
        XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);
        XDDFLineChartData.Series series1 = (XDDFLineChartData.Series) data.addSeries(xs, ys1);
        series1.setTitle("Floyd-Warshall", null);
        series1.setMarkerStyle(MarkerStyle.STAR);
        XDDFLineChartData.Series series2 = (XDDFLineChartData.Series) data.addSeries(xs, ys2);
        series2.setTitle("DFS", null);
        series2.setMarkerStyle(MarkerStyle.TRIANGLE);

        chart.plot(data);
        solidLineSeries(data, 0, PresetColor.CHARTREUSE);
        solidLineSeries(data, 1, PresetColor.TURQUOISE);
    }

    /**
     * Set properties for table
     * @param data
     * @param index
     * @param color
     */
    private static void solidLineSeries(XDDFChartData data, int index, PresetColor color) {
        XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(color));
        XDDFLineProperties line = new XDDFLineProperties();
        line.setFillProperties(fill);
        XDDFChartData.Series series = data.getSeries().get(index);
        XDDFShapeProperties properties = series.getShapeProperties();
        if (properties == null) {
            properties = new XDDFShapeProperties();
        }
        properties.setLineProperties(line);
        series.setShapeProperties(properties);
    }


    public static void fillMap(int size, ArrayList<Long> times) {
        int i = 0;
        timesMap.put(size, new Object[]{size,times.get(i++),times.get(i++)});

    }


    public static void fillFloydWarshallMap(int size, ArrayList<Long> times) {
        int i = 0;
            floydWarshallMap.put(size, new Object[]{size,times.get(i++),times.get(i++),times.get(i++),times.get(i++),times.get(i++)});

    }

    public static void fillDFSMap(int size, ArrayList<Long> times) {
        int i = 0;
        dfsMap.put(size, new Object[]{size,times.get(i++),times.get(i++),times.get(i++),times.get(i++),times.get(i++)});

    }
    /**
     * Fill the table with data
     * @param sheet sheet on which there will be table
     * @param map map of data
     */
    private void fillTable(Sheet sheet, Map<Integer,Object[]> map) {
        Set<Integer> keySet = map.keySet();
        int rownum = 1;
        for (Integer key : keySet) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = map.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
                else if (obj instanceof Long)
                    cell.setCellValue((Long) obj);
            }
        }
    }





}
