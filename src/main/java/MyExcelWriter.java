import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyExcelWriter {

    private Workbook workbook;
    private Sheet sheet;
    private String path;
    private int rowCount=1;

    public MyExcelWriter(String path){
        this.path=path;
        SimpleDateFormat formatter= new SimpleDateFormat("MM-dd 'at' HH-mm-ss z");
        Date date = new Date(System.currentTimeMillis());
        //wenn ein file existiert, lade dies
        try {
            InputStream inp = new FileInputStream(path);
            workbook = WorkbookFactory.create(inp);
        }
        //kein file vorhanden dann erstell eins
        catch (Exception e) {
            //csv
            workbook = new XSSFWorkbook();
        }
        sheet = workbook.createSheet(formatter.format(date));
        Row row = sheet.createRow(0);
        Cell c1 = row.createCell(1);
        c1.setCellValue("Anzahl der Knoten");
        Cell c2 = row.createCell(2);
        c2.setCellValue("Anzahl der extra Kanten");
        Cell c3 = row.createCell(3);
        c3.setCellValue("Test Durchlaeufe");
        Cell c4 = row.createCell(4);
        c4.setCellValue("Gefundene Loesung");
        Cell c5 = row.createCell(5);
        c5.setCellValue("Durchlaeufe ohne Loesung");
        Cell c6 = row.createCell(6);
        c6.setCellValue("Min loops fuer Loesung");
        Cell c7 = row.createCell(7);
        c7.setCellValue("Max loops fuer Loesung");
        Cell c8 = row.createCell(8);
        c8.setCellValue("Avg. loops fuer Loesung");
        }

    public synchronized void addEntry(int nodes, int edges, int runs, int sol, int fails, int min, int max, double avg){
        Row row = sheet.createRow(rowCount++);
        Cell c1 = row.createCell(1);
        c1.setCellValue(nodes);
        Cell c2 = row.createCell(2);
        c2.setCellValue(edges);
        Cell c3 = row.createCell(3);
        c3.setCellValue(runs);
        Cell c4 = row.createCell(4);
        c4.setCellValue(sol);
        Cell c5 = row.createCell(5);
        c5.setCellValue(fails);
        Cell c6 = row.createCell(6);
        c6.setCellValue(min);
        Cell c7 = row.createCell(7);
        c7.setCellValue(max);
        Cell c8 = row.createCell(8);
        c8.setCellValue(avg);
    }

    public void save(){
        try (FileOutputStream outputStream = new FileOutputStream(path)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

