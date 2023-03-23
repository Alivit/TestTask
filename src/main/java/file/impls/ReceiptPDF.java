package file.impls;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import entity.Promotional;
import logic.OutputLogic;
import util.RequestUtil;
import file.interf.Inputable;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ReceiptPDF implements Inputable {
    /**
     * Это поле указывающее на файл хранящий чек
     */
    private final static File FILE_TEMPLATE = new File("src/main/resources/Clevertec_Template.pdf");
    private final static File FILE_RECEIPT = new File("src/main/resources/Receipt.pdf");

    private final static NumberFormat FORMATTER = new DecimalFormat("#0.00");

    public void inputInFile(RequestUtil request)  {

        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_RECEIPT));
            document.open();

            PdfReader reader = new PdfReader(String.valueOf(FILE_TEMPLATE));
            PdfImportedPage importedPage = writer.getImportedPage(reader,1);

            PdfContentByte contentByte = writer.getDirectContent();
            contentByte.addTemplate(importedPage, 0, 0);

            Paragraph paragraph = new Paragraph("\n".repeat(5));
            document.add(paragraph);

            addHeader(document);
            addProduct(document, request);
            addTotal(document, request);
            document.close();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void addHeader(Document document) throws DocumentException, IOException {

        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(60);

        List<String> check = OutputLogic.getCheck()
                .stream()
                .limit(10)
                .filter(s -> !"+--------------------------------------------------------+".equals(s))
                .toList();

        check.forEach(s -> {
            PdfPCell horizontalAlignCell = new PdfPCell(new Phrase(s));
            horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(horizontalAlignCell);
        });

        document.add(table);
    }

    private static void addProduct(Document document, RequestUtil request) throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(60);

        table.addCell(new PdfPCell(new Phrase("name")));
        table.addCell(new PdfPCell(new Phrase("price")));
        table.addCell(new PdfPCell(new Phrase("amount")));
        table.addCell(new PdfPCell(new Phrase("costs")));

        for (int i = 0; i < request.getPromotional().size(); i++) {
            table.addCell(new PdfPCell(new Phrase(request.getPromotional().get(i).getName())));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(request.getPromotional().get(i).getPrice()))));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(request.getPromotional().get(i).getAmount()))));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(FORMATTER.format(request.getPromotional().get(i).getNewPrice())))));
        }
        table.addCell(new PdfPCell(new Phrase("")));

        document.add(table);
    }

    private static void addTotal(Document document, RequestUtil request) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(60);

        double total = request.getPromotional().stream()
                .mapToDouble(Promotional::getNewPrice)
                .sum();
        double newTotal = request.discountCalculation(total);
        if(newTotal != total) {
            table.addCell(new PdfPCell(new Phrase("STARTING PRICE")));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(FORMATTER.format(total)))));

            table.addCell(new PdfPCell(new Phrase("DISCOUNT")));
            table.addCell(new PdfPCell(new Phrase(request.getDiscount() + "%")));
        }

        table.addCell(new PdfPCell(new Phrase("TOTAL")));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(FORMATTER.format(newTotal)))));
        document.add(table);

        table = new PdfPTable(1);
        table.setWidthPercentage(60);

        PdfPCell pdfPCell = new PdfPCell(new Phrase("THANK YOU FOR YOUR PURCHASE"));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(pdfPCell);

        document.add(table);
    }

}
