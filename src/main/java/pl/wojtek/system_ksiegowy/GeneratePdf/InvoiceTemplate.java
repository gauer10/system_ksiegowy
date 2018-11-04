package pl.wojtek.system_ksiegowy.GeneratePdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;
import pl.wojtek.system_ksiegowy.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class InvoiceTemplate
{
    private String getPathToTemplate;

    public void GenerateInvoice(Contractor contractor, Company company, Invoice invoice, Service service, Contract contract) throws Exception
    {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        Date currentDate = new Date();
        File file = new File("src/main/resources/templates/Szablon_faktury.pdf");
        FileInputStream fis = new FileInputStream(file);
        File pathToSaveInvoice = new File("faktury/" + year.format(currentDate) + "/" + month.format(currentDate) + "/" + day.format(currentDate));
        pathToSaveInvoice.mkdirs();
        PdfReader reader = new PdfReader(fis);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pathToSaveInvoice + "/FR" + format1.format(currentDate) + "_" + invoice.getId() + ".pdf"));
        PdfContentByte text = stamper.getUnderContent(1);

        addText(text, 510, 809, format.format(currentDate) + " r.");
        addText(text, 425, 795, "Data sprzedaży:      " + format.format(currentDate) + " r.");
        addText(text, 42, 795, company.getFullName());
        addText(text, 42, 782, company.companyAddress(company));
        addText(text, 42, 769, company.getPost() + " " + company.getCity());
        addText(text, 42, 756, "NIP: " + company.getNIP());
        addText(text, 42, 660, contractor.getFullName());
        addText(text, 42, 647, contractor.contractorAddress(contractor));
        addText(text, 42, 634, contractor.getPost() + " " + contractor.getCity());
        addText(text, 42, 621,  "NIP: " + contractor.getNIP());
        addText(text, 389, 660, contractor.getFullName());
        addText(text, 389, 647, contractor.contractorAddress(contractor));
        addText(text, 389, 634, contractor.getPost() + " " + contractor.getCity());
        addText(text, 389, 621, "NIP: " + contractor.getNIP());
        addText(text, 87, 541.5f, company.getBankAccountNumber());
        addText(text, 122, 528, format.format(invoice.getDueDate()));

        final float tableWidth = reader.getPageSize(1).getWidth() - (2 * -15);

        PdfPTable invoiceNumber = new PdfPTable(1);
        invoiceNumber.setTotalWidth(new float[]{10});

        PdfPCell cell = new PdfPCell(new Phrase("Faktura numer " + invoice.getInvoiceNumber(), Calibri12()));
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        invoiceNumber.addCell(cell);

        ColumnText invoiceNum = new ColumnText(stamper.getOverContent(1));
        Rectangle rectangle2 = new Rectangle(-22, 42, tableWidth, 590);
        invoiceNum.setSimpleColumn(rectangle2);
        invoiceNum.addElement(invoiceNumber);
        invoiceNum.go();

        PdfPTable invoiceItems = new PdfPTable(7);
        invoiceItems.setTotalWidth(new float[]{3, 17, 8, 5, 10, 8, 8});

        addHeaders(invoiceItems);

        addContent(invoiceItems, 1, service.getServiceName(), "sztuki", 1, invoice.getNetPrice(), invoice.getNetPrice(), invoice.getGrossPrice());

        invoiceItems.setSpacingAfter(20f);

        PdfPTable summary = new PdfPTable(1);

        addSumarry(summary, invoice);

        ColumnText columnText = new ColumnText(stamper.getOverContent(1));
        Rectangle rectangle1 = new Rectangle(-22, 42, tableWidth, 480);
        columnText.setSimpleColumn(rectangle1);
        columnText.addElement(invoiceItems);
        columnText.addElement(summary);
        columnText.go();


        stamper.setFormFlattening(true);
        stamper.close();
        reader.close();
    }
    private void addCell(Font cellFont, String text, PdfPTable table)
    {
        PdfPCell cell;
        cell = new PdfPCell(new Phrase(text, cellFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }
    private void addText(PdfContentByte content, float x, float y, String text) throws Exception
    {
        BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/Calibri.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
        content.beginText();
        content.setFontAndSize(baseFont, 11);
        content.moveText(x, y);
        content.showText(text);
        content.endText();
    }
    private void addSumarry(PdfPTable table, Invoice invoice) throws Exception
    {
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Razem do zapłaty: " + invoice.getGrossPrice() + " zł", Calibri12()));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorderColor(BaseColor.WHITE);
        table.addCell(cell);
        cell = new PdfPCell(Image.getInstance("src/main/resources/static/files/Podpis.png"));
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(" "));
        cell.setBorderColor(BaseColor.WHITE);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Osoba upoważniona do wystawienia faktury"));
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
    }
    private void addHeaders(PdfPTable table) throws Exception
    {
        addCell(Calibri12(), "Lp.", table);
        addCell(Calibri12(), "Nazwa towaru lub usługi", table);
        addCell(Calibri12(), "Jednostka", table);
        addCell(Calibri12(), "Ilość", table);
        addCell(Calibri12(), "Cena jedn. netto", table);
        addCell(Calibri12(), "Cena netto", table);
        addCell(Calibri12(), "Cena brutto", table);
    }
    private void addContent(PdfPTable table, int i, String name, String  unit, int count, String netUnitPrice, String netPrice, String grossPrice) throws Exception
    {
        addCell(Calibri12(), String.valueOf(i), table);
        addCell(Calibri12(), name, table);
        addCell(Calibri12(), unit, table);
        addCell(Calibri12(), String.valueOf(count), table);
        addCell(Calibri12(), String.valueOf(netUnitPrice) + " zł", table);
        addCell(Calibri12(), String.valueOf(netPrice) + " zł", table);
        addCell(Calibri12(), String.valueOf(grossPrice) + " zł", table);
    }
    private Font Calibri12() throws Exception
    {
        BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/Calibri.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
        Font cellFont = new Font(baseFont, 12, Font.NORMAL);
        return cellFont;
    }

}
