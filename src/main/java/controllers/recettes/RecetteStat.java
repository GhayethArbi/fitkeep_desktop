package controllers.recettes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import models.Recette;
import services.ServiceRecette;

import java.sql.SQLException;
import java.util.List;

public class RecetteStat {

    private final ServiceRecette recetteService = new ServiceRecette();


    @FXML
    private PieChart reettePieChart;;

    public void refreshPieChart() throws SQLException {
        // Retrieve all products from the database
        List<Recette> rec = recetteService.selectAll();

        // Initialize counters for each category
        int breakfastCount = 0, brunchCount = 0, lunchCount = 0, snacksCount = 0, dinnerCount = 0;

        // Count the number of recipes for each category
        for (Recette r : rec) {
            String category = r.getCategory();
            switch (category) {
                case "Breakfast":
                    breakfastCount++;
                    break;
                case "Brunch":
                    brunchCount++;
                    break;
                case "Lunch":
                    lunchCount++;
                    break;
                case "Snacks":
                    snacksCount++;
                    break;
                case "Dinner":
                    dinnerCount++;
                    break;
                default:
                    break;
            }
        }

        // Create an observable list of pie chart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Breakfast", breakfastCount),
                new PieChart.Data("Brunch", brunchCount),
                new PieChart.Data("Lunch", lunchCount),
                new PieChart.Data("Snacks", snacksCount),
                new PieChart.Data("Dinner", dinnerCount)
        );

        // Set the data to the pie chart
        reettePieChart.setData(pieChartData);
    }


}
