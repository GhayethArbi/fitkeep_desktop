package controllers;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Product;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;

public class PDFExporter {

    public static void exportTableViewToPDF(TableView<Product> tableView, String imagePath, String filePath, PDDocument document) {
        try {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;

                // Draw image at the top
                drawImage(contentStream, imagePath, margin, yStart - 50, 100, 100, document);

                // Calculate position for table data
                float tableYStart = yStart - 150; // Adjust this value as needed

                // Draw table headers
                float headerEndY = drawTableHeaders(contentStream, margin, tableYStart, 20, 5, tableView.getColumns());

                // Draw table data
                drawTableData(contentStream, margin, headerEndY - 5, 20, 5, 5, tableView.getColumns(), tableView.getItems());
            }

            document.save(filePath);
            System.out.println("TableView exported to PDF: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void drawImage(PDPageContentStream contentStream, String imagePath, float x, float y, float width, float height, PDDocument document) throws IOException {
        try {
            File file = new File(imagePath);
            if (file.exists()) {
                PDImageXObject imageXObject = PDImageXObject.createFromFile(imagePath, document);
                contentStream.drawImage(imageXObject, x, y, width, height);
            } else {
                System.err.println("Image file not found: " + imagePath);
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static float drawTableHeaders(PDPageContentStream contentStream, float xStart, float yStart, float rowHeight, float cellMargin, ObservableList<TableColumn<Product, ?>> columns) throws IOException {
        float nextX = xStart + cellMargin;
        float nextY = yStart - rowHeight - cellMargin;

        // Draw table headers
        for (TableColumn<Product, ?> column : columns) {
            contentStream.setNonStrokingColor(255, 0, 0); // Red
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(nextX, nextY + rowHeight - cellMargin); // Move text up slightly for centering
            contentStream.showText(column.getText());
            contentStream.endText();
            nextX += column.getWidth() + cellMargin;
        }

        // Return the Y position after drawing headers
        return nextY;
    }

    private static void drawTableData(PDPageContentStream contentStream, float xStart, float yStart, float rowHeight, float cellMargin, float rowSpacing, ObservableList<TableColumn<Product, ?>> columns, ObservableList<Product> items) throws IOException {
        float nextY = yStart - rowHeight - cellMargin;

        // Draw table data
        for (Product product : items) {
            float nextX = xStart + cellMargin;

            contentStream.setNonStrokingColor(0, 0, 0); // Black
            for (TableColumn<Product, ?> column : columns) {
                Object cellData = column.getCellData(product);
                String cellValue = (cellData != null) ? cellData.toString() : "";
                contentStream.beginText();
                contentStream.newLineAtOffset(nextX, nextY); // Align to top-left corner of cell
                contentStream.showText(cellValue);
                contentStream.endText();
                nextX += column.getWidth() + cellMargin;
            }

            nextY -= (rowHeight + rowSpacing); // Add spacing between rows
        }
    }
}
