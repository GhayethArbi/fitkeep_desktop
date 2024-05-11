package controllers;

import Entity.Commande;
import services.CommandeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import com.stripe.exception.StripeException;
import javafx.scene.text.Text;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PaymentController {

    public Text totalprice;
    public TextField coupon;
    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField expMonthField;

    @FXML
    private TextField expYearField;

    @FXML
    private TextField cvvField;

    @FXML
    private TextField amountField;

    // Twilio API Info Lena


    private static final String TWILIO_ACCOUNT_SID = "ACb5f7e848ec4cee3d3969be034b0ff378";
    private static final String TWILIO_AUTH_TOKEN = "7e81303e2231c5ed666cb92381409399";
    private static final String TWILIO_PHONE_NUMBER = "+17603174631";

    private double total = 10;

    // Stripe API key
    private static final String API_KEY = "sk_test_51PCnFuF0QHhSvMFCf0KK5O8xezLPaoSGp84ZxrBGM6YVeRtDVS9GJXDKrEEmme9NfwiLqLEtYoMUnzNfZy29X1Ox00c5922VWt";

    public void processPayment(ActionEvent actionEvent) {
        String cardNumber = cardNumberField.getText();
        Integer expMonth = Integer.parseInt(expMonthField.getText());
        Integer expYear = Integer.parseInt(expYearField.getText());
        String cvv = cvvField.getText();

        // Set your API key
        Stripe.apiKey = API_KEY;

        // Create token
        try {
            ChargeCreateParams params = ChargeCreateParams.builder()
                    .setAmount((long) total*10)
                    .setCurrency("usd")
                    .setSource("tok_visa") // You should create a token using user's card info
                    .setDescription("Payment for order")
                    .build();

            Charge charge = Charge.create(params);

            // Payment successful
            showAlert("Payment Successful", "Payment ID: " + charge.getId());
            sendSMS("Payment of " + total + " USD has been received. Thank you for your purchase!");
            CommandeService commandeService = new CommandeService();
            commande.setStatut("Payée");
            commandeService.updateCommande(commande);

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CommandDetails.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            CommandDetails controller = fxmlLoader.getController();
            controller.setCommand(commande);
            Stage stage = new Stage();
            stage.setTitle("Détails de la commande");
            stage.setScene(new Scene(root1));
            stage.show();
            currentStage.close();

        } catch (StripeException | IOException e) {
            // Payment failed
            showAlert("Payment Failed", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Commande commande;

    public void setVariable(double amount, Commande commande) {
        totalprice.setText(String.valueOf(amount));
        this.commande = commande;
        this.total = amount;
    }

    private void sendSMS(String message) {
        // Initialize Twilio
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);

        // Send SMS
        Message.creator(
                        new PhoneNumber("+21695844522"),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        message)
                .create();
    }

    public void applyCoupon(ActionEvent actionEvent) {
        String couponCode = coupon.getText();

        try {
            JSONParser parser = new JSONParser();
            //get absolute path
            String currentDir = System.getProperty("user.dir");

            // Print the absolute path of the current directory
            System.out.println("Current Directory: " + currentDir);

            JSONArray couponArray = (JSONArray) parser.parse(new FileReader("coupon.json"));

            for (Object obj : couponArray) {
                JSONObject couponObj = (JSONObject) obj;
                String coupon = (String) couponObj.get("coupon");
                if (coupon.equals(couponCode.toUpperCase())) {
                    double discount = Double.parseDouble((String) couponObj.get("discount"));
                    total = total * (100 - discount)/100 ;
                    totalprice.setText(String.valueOf(total));
                    showAlert("Coupon Applied", "Discount of " + discount + "% applied successfully.");
                    return;
                }
            }
            showAlert("Invalid Coupon", "The entered coupon code is invalid.");
        } catch (IOException | ParseException e) {
            showAlert("Error", "Failed to apply coupon: " + e.getMessage());
        }
    }
}
