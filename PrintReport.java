/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeapp.practice;

/**
 *
 * @author hp
 */
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PrintReport {
    public static ArrayList<ArrayList<Object>> listData = new ArrayList<ArrayList<Object>>();
    public static void getPDFReport(ArrayList<ArrayList<Object>> listData) {
        int f = 0;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/temple","root","root123");
            Statement st= conn.createStatement();
            ResultSet rs;
            rs= st.executeQuery("select COUNT(*) from print");
            while(rs.next()) {
                f= Integer.parseInt(rs.getString(1));
            }
             
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        String abc = "report";
        String bc = Integer.toString(f);
        String ac = abc+bc;

        String pdfFilename = ac+".pdf";
        PrintReport printReport = new PrintReport();

        PrintReport.listData = listData;
        printReport.createPDF(pdfFilename);

    }

    private void createPDF(String pdfFilename) {

        Document doc = new Document();
        PdfWriter docWriter = null;

        try {

            //special font sizes
            Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12);

            //file path
            String path = "C:\\Users\\hp\\Desktop\\Report\\" + pdfFilename;
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));

            //document header attributes
            doc.addAuthor("tech");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("creator");
            doc.addTitle("Report with Column Headings");
            doc.setPageSize(PageSize.LETTER);

            //open document
            doc.open();
            Rectangle rect= new Rectangle(585,770,24,15); // (witdth,height, , )
            rect.enableBorderSide(1);
            rect.enableBorderSide(2);
            rect.enableBorderSide(4);
            rect.enableBorderSide(8);
            rect.setBorderColor(BaseColor.BLACK);
            rect.setBorderWidth(1);
            doc.add(rect);
            
            
            doc.add(new Paragraph(new Date().toString()));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));


            //create a paragraph
            Paragraph paragraph = new Paragraph("                                =====================Report====================");
            
            

            //specify column widths
            float[] columnWidths = {1.5f, 1.5f, 1.5f, 1.5f,1.5f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(60f);

            //insert column headings
            insertCell(table, "Name", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Amount", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Mode", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Category", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Date", Element.ALIGN_RIGHT, 1, bfBold12);
            table.setHeaderRows(1);

            //insert an empty row
            
            //create section heading by cell merging
            
            //just some random data to fill 
            for (int x = 0; x < PrintReport.listData.size(); x++) {
                
                insertCell(table, PrintReport.listData.get(x).get(0).toString(), Element.ALIGN_RIGHT, 1, bf12);
                insertCell(table, PrintReport.listData.get(x).get(1).toString(), Element.ALIGN_LEFT, 1, bf12);
                insertCell(table, PrintReport.listData.get(x).get(2).toString(), Element.ALIGN_LEFT, 1, bf12);
                insertCell(table, PrintReport.listData.get(x).get(3).toString(), Element.ALIGN_LEFT, 1, bf12);
                insertCell(table, PrintReport.listData.get(x).get(4).toString(), Element.ALIGN_LEFT, 1, bf12);

            }
           //add the PDF table to the paragraph 
            paragraph.add(table);
            // add the paragraph to the document
            doc.add(paragraph);
            

        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                //close the document
                doc.close();
            }
            if (docWriter != null) {
                //close the writer
                docWriter.close();
            }
        }
    }

    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);

    }

}
