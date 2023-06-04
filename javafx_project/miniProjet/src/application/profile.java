package application;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class profile extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Profile");
        
        // Create a GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        
        // Add labels and text fields
        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField();
        Label ageLabel = new Label("Age:");
        TextField ageTextField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailTextField = new TextField();
        
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTextField, 1, 0);
        gridPane.add(ageLabel, 0, 1);
        gridPane.add(ageTextField, 1, 1);
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailTextField, 1, 2);
        
        // Add a submit button
        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, 3);
        gridPane.getChildren().add(submitButton);
        
        // Handle submit button action
        submitButton.setOnAction(event -> {
            String name = nameTextField.getText();
            String age = ageTextField.getText();
            String email = emailTextField.getText();
            
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Email: " + email);
        });
        
        Scene scene = new Scene(gridPane, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}