package lab2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerManagerApp extends Application {

    @Override
    public void start(Stage stage) {
        Label welcome = new Label("Welcome, Bwalya Musonda - ID: 202503581");
        welcome.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter Customer Name");

        ComboBox<String> provinceBox = new ComboBox<>();
        provinceBox.getItems().addAll("Central", "Lusaka", "Copperbelt");
        provinceBox.setPromptText("Choose a province");

        Button saveButton = new Button("Save Customer");
        Button deleteButton = new Button("Delete Customer");
        Label statusLabel = new Label("Ready");

        TableView<Customer> table = new TableView<>();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        table.setItems(customers);

        TableColumn<Customer, String> nameCol = new TableColumn<>("Customer Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> provinceCol = new TableColumn<>("Province");
        provinceCol.setCellValueFactory(new PropertyValueFactory<>("province"));

        table.getColumns().addAll(nameCol, provinceCol);

        saveButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String province = provinceBox.getValue();
            if (name.isEmpty()) {
                statusLabel.setText("Enter the customer name.");
                nameField.requestFocus();
                return;
            }
            if (province == null) {
                statusLabel.setText("Choose a province.");
                provinceBox.requestFocus();
                return;
            }
            customers.add(new Customer(name, province));
            statusLabel.setText("Customer saved.");
            nameField.clear();
            provinceBox.setValue(null);
        });

        deleteButton.setOnAction(e -> {
            Customer selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                statusLabel.setText("Select a customer to delete.");
                return;
            }
            Alert ask = new Alert(Alert.AlertType.CONFIRMATION,
                    "Delete " + selected.getName() + "?",
                    ButtonType.OK, ButtonType.CANCEL);
            ask.setHeaderText("Confirm deletion");
            if (ask.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                customers.remove(selected);
                statusLabel.setText("Customer deleted.");
            }
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(
                welcome,
                new Label("Customer Name:"), nameField,
                new Label("Province:"), provinceBox,
                saveButton, deleteButton, statusLabel, table
        );

        Scene scene = new Scene(root, 500, 600);
        stage.setTitle("Customer Manager - Bwalya Musonda");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}