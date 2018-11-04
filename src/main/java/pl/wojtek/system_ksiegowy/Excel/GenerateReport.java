package pl.wojtek.system_ksiegowy.Excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import pl.wojtek.system_ksiegowy.model.Contractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.List;

@Component
public class GenerateReport
{
    public void contractorsReport(List<Contractor> contractors, String date) throws Exception
    {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Raport");

        int rowNum = 0;

        for (Contractor contractor : contractors)
        {
            Row row = sheet.createRow(rowNum++);
            Cell cell = row.createCell(0);
            cell.setCellValue(contractor.getFullName());
            cell = row.createCell(1);
            cell.setCellValue(contractor.getStreet());
            cell = row.createCell(2);
            cell.setCellValue(contractor.getHouseNumber());
            cell = row.createCell(3);
            cell.setCellValue(contractor.getApartmentNumber());
            cell = row.createCell(4);
            cell.setCellValue(contractor.getPost());
            cell = row.createCell(5);
            cell.setCellValue(contractor.getCity());
            cell = row.createCell(6);
            cell.setCellValue(contractor.getPhoneNumber());
            cell = row.createCell(7);
            cell.setCellValue(contractor.getEmail());
            cell = row.createCell(8);
            cell.setCellValue(contractor.getContactPerson());
        }

        File output = new File("raporty");
        output.mkdirs();
        FileOutputStream fos = new FileOutputStream(output + "/" + date + ".xlsx");
        workbook.write(fos);
        fos.close();
    }
}
