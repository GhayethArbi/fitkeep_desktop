package controllers;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import models.Product;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class PDFExporter {

    public static void exportTableViewToPDF(TableView<Product> tableView, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float rowHeight = 20;
                float tableHeight = rowHeight * (tableView.getItems().size() + 1);
                float cellMargin = 5;

                // Draw table borders
                drawTableBorders(contentStream, margin, yStart, tableWidth, tableHeight, rowHeight);

                // Draw headers
                drawTableHeaders(contentStream, margin, yStart, tableView.getColumns(), rowHeight, cellMargin);

                // Draw table data
                drawTableData(contentStream, margin, yStart, tableView.getColumns(), rowHeight, cellMargin, tableView.getItems());
            }

            document.save(filePath);
            System.out.println("TableView exported to PDF: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void drawTableBorders(PDPageContentStream contentStream, float xStart, float yStart, float tableWidth, float tableHeight, float rowHeight) throws IOException {
        contentStream.addRect(xStart, yStart - tableHeight, tableWidth, tableHeight);
        contentStream.stroke();
    }

    private static void drawTableHeaders(PDPageContentStream contentStream, float xStart, float yStart, ObservableList<TableColumn<Product, ?>> columns, float rowHeight, float cellMargin) throws IOException {
        float nextX = xStart + cellMargin;
        float nextY = yStart - rowHeight - cellMargin;
        for (TableColumn<Product, ?> column : columns) {
            contentStream.beginText();
            contentStream.newLineAtOffset(nextX, nextY);
            contentStream.showText(column.getText());
            contentStream.endText();
            nextX += column.getWidth() + cellMargin;
        }
    }

    private static void drawTableData(PDPageContentStream contentStream, float xStart, float yStart, ObservableList<TableColumn<Product, ?>> columns, float rowHeight, float cellMargin, ObservableList<Product> items) throws IOException {
        float nextY = yStart - rowHeight - cellMargin;
        for (Product item : items) {
            float nextX = xStart + cellMargin;
            for (TableColumn<Product, ?> column : columns) {
                Object cellData = column.getCellData(item);
                String cellValue = (cellData != null) ? cellData.toString() : ""; // Check for null
                contentStream.beginText();
                contentStream.newLineAtOffset(nextX, nextY);
                contentStream.showText(cellValue);
                contentStream.endText();
                nextX += column.getWidth() + cellMargin;
            }
            nextY -= rowHeight;
        }
    }
}
