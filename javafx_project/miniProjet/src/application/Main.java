package application;
import javafx.scene.transform.Scale;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	
	private TableView<Student> table;


    @SuppressWarnings({ "static-access", "unchecked" })
	@Override
    public void start(Stage primaryStage) {
    	 //instancier la classe connecteurbdd pour pouvoir établir la connexion à la base de données
    	connecteurbdd connecteur = new connecteurbdd();
    	
    	
        // Création du GridPane principal
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(1000, 550);

        // Définition des colonnes et de leurs proportions
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(35);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(65);
        gridPane.getColumnConstraints().addAll(column1, column2);

        // Définition des lignes avec la même hauteur
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(100);
        gridPane.getRowConstraints().add(row);

        // Ajout du contenu à la partie gauche
        GridPane leftPane = new GridPane();
        leftPane.setStyle("-fx-background-color: lightblue;"); // Pour illustrer la partie gauche
        //leftPane.setAlignment(Pos.TOP_CENTER);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(20));
        leftPane.setHgap(15);
        leftPane.setVgap(15);
        gridPane.add(leftPane, 0, 0);

        // Ajout du titre en gras en haut
        Label titleLabel = new Label("Add Student");
        titleLabel.setFont(Font.font("Impact", 25));
        leftPane.add(titleLabel, 0, 0, 2, 1);

        // Définition des labels et des champs de saisie
        String[] labels = {"CNE ", "Birth date ", "Firstname ", "Lastname ", "Student Field ", "Current Level ", "Email "};
        for (int i = 0; i < labels.length; i++) {
        	Label label = new Label(labels[i]);
        	if (i == 1) {
                DatePicker datePicker = new DatePicker();
                datePicker.setId("tfbirthdate"); // Ajout de l'FX id pour le DatePicker
                leftPane.add(label, 0, i + 1);
                leftPane.add(datePicker, 1, i + 1);
                label.setFont(Font.font("Arial", 15));
            } else {
                TextField textField = new TextField();
                textField.setId("tf" + labels[i].toLowerCase().replaceAll("\\s+", "")); // Ajout de l'FX id pour le TextField
                leftPane.add(label, 0, i + 1);
                leftPane.add(textField, 1, i + 1);
                label.setFont(Font.font("Arial", 15));
            }
            
        }
        
     // Création du pane pour le bouton "Add"
        HBox buttonPane = new HBox();
        buttonPane.setAlignment(Pos.CENTER);
        Button addButton = new Button("Add");
        addButton.setId("btnadd");
        addButton.setPrefSize(80, 35);
        buttonPane.getChildren().add(addButton);

        // Ajout des marges pour le pane du bouton "Add"
        buttonPane.setMargin(addButton, new Insets(20, 0, 0, 0));
        addButton.setFont(Font.font("Arial", 18));
        addButton.setStyle(
                "-fx-background-color: white;" +
                "-fx-text-fill: black;" +
                "-fx-border-width: 0px;" +
                "-fx-border-radius: 5px;"
        );
        
        // Ajout du pane du bouton "Add" à la partie inférieure du leftPane
        leftPane.add(buttonPane, 0, labels.length + 1, 2, 1);
        
        // Ajout du contenu à la partie droite (rightPane)
        VBox rightPane = new VBox();
        rightPane.setStyle("-fx-background-color: white;"); // Pour illustrer la partie droite
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setSpacing(20);
        rightPane.setPadding(new Insets(20));
        gridPane.add(rightPane, 1, 0);

        // Ajout du titre "Student List"
        Label titleLabel2 = new Label("Students List");
        titleLabel2.setStyle("-fx-font-weight: bold;"
        		+ "-fx-font-size: 25px");
        rightPane.getChildren().add(titleLabel2);
        // Ajout du TableView
        table = new TableView<>();
        table.setStyle(
                "-fx-table-header-background: lightblue;" +
                "-fx-table-header-text-fill: black;"
        );
        table.setEditable(true);
        table.setId("tvstudents");

		// Preparation des colonnes de la table
		TableColumn<Student, String> cneCol = new TableColumn<Student, String>("CNE");
		cneCol.setId("colcne");
		TableColumn<Student, String> birthDateCol = new TableColumn<Student, String>("Birth Date");
		birthDateCol.setId("colbirthdate");
		TableColumn<Student, String> firstNameCol = new TableColumn<Student, String>("First Name");
		firstNameCol.setId("colfirstname");
		TableColumn<Student, String> lastNameCol = new TableColumn<Student, String>("Last Name");
		lastNameCol.setId("collastname");
	    TableColumn<Student, String> studentFieldCol = new TableColumn<Student, String>("Student Field");
	    studentFieldCol.setId("colstudentfield");
	    TableColumn<Student, String> currentLevelCol = new TableColumn<Student, String>("Current Level");
	    currentLevelCol.setId("colcurrentlevel");
	    TableColumn<Student, String> emailCol = new TableColumn<Student, String>("Email");
	    emailCol.setId("colemail");
	    
	    
     		    // Liaison entre colonnes et types de données
     	cneCol.setCellValueFactory(new PropertyValueFactory<>("cne"));
     	birthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
     	firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
     	lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
     	studentFieldCol.setCellValueFactory(new PropertyValueFactory<>("studentField"));
     	currentLevelCol.setCellValueFactory(new PropertyValueFactory<>("currentLevel"));
     	emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
     		      
     		    // Adding columns to my table
     	table.getColumns().addAll(cneCol,birthDateCol, firstNameCol, lastNameCol, studentFieldCol,currentLevelCol, emailCol);
        rightPane.getChildren().add(table);

        // Ajout des boutons "Update", "Delete", "View Profile"
        Button updateButton = new Button("Update");
        updateButton.setId("btnupdate");
        updateButton.setStyle(
                "-fx-background-color: lightblue;" +
                "-fx-text-fill: black;" +
                "-fx-border-width: 0px;" +
                "-fx-border-radius: 5px;"
        );
        Button deleteButton = new Button("Delete");
        deleteButton.setId("btndelete");
        deleteButton.setStyle(
                "-fx-background-color: lightblue;" +
                "-fx-text-fill: black;" +
                "-fx-border-width: 0px;" +
                "-fx-border-radius: 5px;"
        );
        Button viewProfileButton = new Button("View Profil");
        viewProfileButton.setStyle(
                "-fx-background-color: lightblue;" +
                "-fx-text-fill: black;" +
                "-fx-border-width: 0px;" +
                "-fx-border-radius: 5px;"
        );
        deleteButton.setId("btnview");
        HBox buttonsPane = new HBox(30);
        buttonsPane.setAlignment(Pos.CENTER);
        buttonsPane.getChildren().addAll(updateButton, deleteButton, viewProfileButton);
        rightPane.getChildren().add(buttonsPane);
        
      //-------------------------------------------------------------view profile---------------------------------------------------------------------//
        viewProfileButton.setOnAction(event -> {
            Student selectedProfile = table.getSelectionModel().getSelectedItem();
            if (selectedProfile != null) {

            	VBox profile_layout=new VBox(25);
            	Label cne= new Label("CNE:");	
            	Label birthdate= new Label("Birth date:");
            	Label field= new Label("Field:");
            	Label current_lvl= new Label("level:");
            	Label email= new Label("Email:");
            	cne.setStyle("-fx-font-size: 19px; -fx-text-fill: darkblue ;");
            	birthdate.setStyle("-fx-font-size: 19px; -fx-text-fill: darkblue ;");
            	field.setStyle("-fx-font-size: 19px; -fx-text-fill: darkblue ;");
            	current_lvl.setStyle("-fx-font-size: 19px; -fx-text-fill: darkblue ;");
            	email.setStyle("-fx-font-size: 19px; -fx-text-fill: darkblue ;");
            	
          
            	
            	//info
            	ImageView avatar = new ImageView(new Image("logo2-removebg-preview.png"));
            	avatar.setFitWidth(130);
            	avatar.setFitHeight(130);
            	Label cne_info= new Label(selectedProfile.getCne());
            	Label name_info= new Label(selectedProfile.getFirstName()+ " " +selectedProfile.getLastName());
            	Label birthdate_info= new Label(selectedProfile.getBirthDate());
            	Label field_info= new Label(selectedProfile.getStudentField());
            	Label current_lvl_info= new Label(selectedProfile.getCurrentLevel());
            	Label email_info= new Label(selectedProfile.getEmail()); 
            	cne_info.setStyle("-fx-text-align: center; -fx-font-size: 16px; -fx-text-fill: #777777;");
            	name_info.setStyle("-fx-text-align: center; -fx-font-size: 24px; -fx-text-fill: #333333;");
            	birthdate_info.setStyle("-fx-text-align: center; -fx-font-size: 16px; -fx-text-fill: #777777;");
            	field_info.setStyle("-fx-text-align: center; -fx-font-size: 16px; -fx-text-fill: #777777;");
            	current_lvl_info.setStyle("-fx-text-align: center; -fx-font-size: 16px; -fx-text-fill: #777777;");
            	email_info.setStyle("-fx-text-align: center; -fx-font-size: 16px; -fx-text-fill: #777777;");
            	//layouts
            	HBox cne_box= new HBox(20);
             	HBox name_box= new HBox(20);
            	HBox bd_box= new HBox(20);
            	HBox field_box = new HBox(20);
            	HBox lvl_box= new HBox(20);
            	HBox email_box = new HBox(20);
            	
            	cne_box.setStyle("-fx-text-align: center;");
            	cne_box.setAlignment(Pos.CENTER);
            	name_box.setStyle("-fx-text-align: center;");
            	name_box.setAlignment(Pos.CENTER);
            	bd_box.setStyle("-fx-text-align: center;");
            	bd_box.setAlignment(Pos.CENTER);
            	field_box.setStyle("-fx-text-align: center;");
            	field_box.setAlignment(Pos.CENTER);
            	lvl_box.setStyle("-fx-text-align: center;");
            	lvl_box.setAlignment(Pos.CENTER);
            	email_box.setStyle("-fx-text-align: center;");
            	email_box.setAlignment(Pos.CENTER);
            	
            	
            	cne_box.getChildren().addAll(cne,cne_info);
            	name_box.getChildren().addAll(name_info);
            	bd_box.getChildren().addAll(birthdate,birthdate_info);
            	field_box.getChildren().addAll(field,field_info);
            	lvl_box.getChildren().addAll(current_lvl,current_lvl_info);
            	email_box.getChildren().addAll(email,email_info);
            	
            	profile_layout.getChildren().addAll(avatar,name_box,cne_box,bd_box,field_box,lvl_box,email_box);
            	profile_layout.setAlignment(Pos.CENTER);
            	profile_layout.setStyle("-fx-background-color: white; -fx-padding: 20px; ");
            	
            	Scene profile_scene=new Scene(profile_layout,500,600);  
            	
            	primaryStage.setScene(profile_scene);
            	primaryStage.centerOnScreen();

            	Button goBack = new Button("Go Back");
            	goBack.setStyle(
                         "-fx-background-color: white;" +
                         "-fx-text-fill: black;" +
                         "-fx-border-width: 0px;" +
                         "-fx-border-radius: 5px;"
                 );
            	Button print = new Button("Print");
            	print.setStyle(
                        "-fx-background-color: white;" +
                        "-fx-text-fill: black;" +
                        "-fx-border-width: 0px;" +
                        "-fx-border-radius: 5px;"
                );

                HBox buttons = new HBox(35);
            	buttons.setStyle("-fx-background-color: lightblue; -fx-padding: 30px;   ");
                
            	
            	buttons.getChildren().addAll(goBack , print);
            	buttons.setPrefWidth(1000);
            	buttons.setAlignment(Pos.CENTER);
            	profile_layout.getChildren().add(buttons);
            	
            	//-->PRINT ACTION
            	print.setOnAction(event6 -> {
            		buttons.setStyle("-fx-background-color: white;");
            		goBack.setVisible(false);
            		print.setVisible(false);
            		
            		Printer printer = Printer.getDefaultPrinter();
                    PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);

                    double printableHeight = pageLayout.getPrintableHeight();
                    double desiredHeight = 700;
                    double scale = desiredHeight / printableHeight;

                    PrinterJob printerJob = PrinterJob.createPrinterJob(printer);
                    if (printerJob != null) {
                       
                        printerJob.getJobSettings().setPageLayout(pageLayout);

                        
                        Scale scaleTransform = new Scale(scale, scale);
                        primaryStage.getScene().getRoot().getTransforms().add(scaleTransform);

                        // Print the scene
                        boolean printed = printerJob.printPage(primaryStage.getScene().getRoot());
                        if (printed) {
                        	buttons.setStyle("-fx-background-color: lightblue; -fx-padding: 30px;   ");
                            printerJob.endJob();
                            goBack.setVisible(true);
                    		print.setVisible(true);
                    		avatar.setVisible(avatar.isVisible());
                        } else {
                        	buttons.setStyle("-fx-background-color: lightblue; -fx-padding: 30px;   ");
                            printerJob.cancelJob();
                            goBack.setVisible(true);
                    		print.setVisible(true);
                    		
                        }

                        primaryStage.getScene().getRoot().getTransforms().remove(scaleTransform);}});
	
            }
        });
//-----------------------------------------------------------------END---------------------------------------------------------------------------------------// 
        
        // un gestionnaire d'événements au bouton "Add" (addButton) pour effectuer l'ajout de l'étudiant 
        addButton.setOnAction(e -> {
            // Récupérer les valeurs des TextField
            String cne = ((TextField)leftPane.lookup("#tfcne")).getText();
            String birthDate = ((DatePicker)leftPane.lookup("#tfbirthdate")).getValue().toString();
            String firstName = ((TextField)leftPane.lookup("#tffirstname")).getText();
            String lastName = ((TextField)leftPane.lookup("#tflastname")).getText();
            String studentField = ((TextField)leftPane.lookup("#tfstudentfield")).getText();
            String currentLevel = ((TextField)leftPane.lookup("#tfcurrentlevel")).getText();
            String email = ((TextField)leftPane.lookup("#tfemail")).getText();

            // Créer un objet Student avec les valeurs récupérées
            Student student = new Student(null, cne, birthDate, firstName, lastName, studentField, currentLevel, email);

            // Ajouter l'étudiant au TableView
            table.getItems().add(student);
            

            // Ajouter l'étudiant à la base de données
            Connection connection = connecteur.getConnection();
            if (connection != null) {
                try {
                    // Préparer la requête SQL d'insertion
                    String query = "INSERT INTO students (cne, birthDate, firstName, lastName, studentField, currentLevel, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, cne);
                    statement.setString(2, birthDate);
                    statement.setString(3, firstName);
                    statement.setString(4, lastName);
                    statement.setString(5, studentField);
                    statement.setString(6, currentLevel);
                    statement.setString(7, email);

                    // Exécuter la requête d'insertion
                    statement.executeUpdate();

                    // Fermer la déclaration
                    statement.close();

                    System.out.println("Étudiant ajouté avec succès !");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Erreur lors de l'ajout de l'étudiant !");
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                System.out.println("Échec de la connexion à la base de données !");
            }
        });
        
     // Gestionnaire d'événements pour le bouton "Delete"
        deleteButton.setOnAction(e -> {
            // Vérifier si une ligne est sélectionnée dans la table
            Student selectedStudent = table.getSelectionModel().getSelectedItem();
            if (selectedStudent != null) {
                // Supprimer l'étudiant de la table
                table.getItems().remove(selectedStudent);

                // Supprimer l'étudiant de la base de données
                Connection connection = connecteur.getConnection();
                if (connection != null) {
                    try {
                        // Préparer la requête SQL de suppression
                        String query = "DELETE FROM students WHERE id=?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setFloat(1, selectedStudent.getId());

                        // Exécuter la requête de suppression
                        statement.executeUpdate();

                        // Fermer la déclaration
                        statement.close();

                        System.out.println("Étudiant supprimé avec succès !");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        System.out.println("Erreur lors de la suppression de l'étudiant !");
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("Échec de la connexion à la base de données !");
                }
            } else {
                System.out.println("Aucun étudiant sélectionné !");
            }
        });
        
     // Gestionnaire d'événements pour le bouton "Update"
        updateButton.setOnAction(e -> {
            // Vérifier si une ligne est sélectionnée dans la table
            Student selectedStudent = table.getSelectionModel().getSelectedItem();
            if (selectedStudent != null) {
                // Récupérer les nouvelles valeurs des TextField
                String newCne = ((TextField) leftPane.lookup("#tfcne")).getText();
                String newBirthDate = ((DatePicker) leftPane.lookup("#tfbirthdate")).getValue().toString();
                String newFirstName = ((TextField) leftPane.lookup("#tffirstname")).getText();
                String newLastName = ((TextField) leftPane.lookup("#tflastname")).getText();
                String newStudentField = ((TextField) leftPane.lookup("#tfstudentfield")).getText();
                String newCurrentLevel = ((TextField) leftPane.lookup("#tfcurrentlevel")).getText();
                String newEmail = ((TextField) leftPane.lookup("#tfemail")).getText();

                // Mettre à jour l'objet Student avec les nouvelles valeurs
                selectedStudent.setCne(newCne);
                selectedStudent.setBirthDate(newBirthDate);
                selectedStudent.setFirstName(newFirstName);
                selectedStudent.setLastName(newLastName);
                selectedStudent.setStudentField(newStudentField);
                selectedStudent.setCurrentLevel(newCurrentLevel);
                selectedStudent.setEmail(newEmail);

                // Mettre à jour la base de données
                Connection connection = connecteur.getConnection();
                if (connection != null) {
                    try {
                        // Préparer la requête SQL de mise à jour
                        String query = "UPDATE students SET cne=?, birthDate=?, firstName=?, lastName=?, studentField=?, currentLevel=?, email=? WHERE id=?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, newCne);
                        statement.setString(2, newBirthDate);
                        statement.setString(3, newFirstName);
                        statement.setString(4, newLastName);
                        statement.setString(5, newStudentField);
                        statement.setString(6, newCurrentLevel);
                        statement.setString(7, newEmail);
                        statement.setFloat(8, selectedStudent.getId());

                        // Exécuter la requête de mise à jour
                        statement.executeUpdate();

                        // Mettre à jour la ligne sélectionnée dans le TableView
                        int selectedIndex = table.getSelectionModel().getSelectedIndex();
                        table.getItems().set(selectedIndex, selectedStudent);

                        // Fermer la déclaration
                        statement.close();

                        System.out.println("Étudiant mis à jour avec succès !");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        System.out.println("Erreur lors de la mise à jour de l'étudiant !");
                    } finally {
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("Échec de la connexion à la base de données !");
                }
            } else {
                System.out.println("Aucun étudiant sélectionné !");
            }
        });
        
     // Récupérer les données de la base de données
        Connection connection = connecteur.getConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String cne = resultSet.getString("cne");
                    String birthDate = resultSet.getString("birthDate");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String studentField = resultSet.getString("studentField");
                    String currentLevel = resultSet.getString("currentLevel");
                    String email = resultSet.getString("email");

                    // Créer un objet Student avec les données récupérées
                    Student student = new Student(id, cne, birthDate, firstName, lastName, studentField, currentLevel, email);

                    // Ajouter l'étudiant au TableView
                    table.getItems().add(student);
                }
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Échec de la connexion à la base de données !");
        }
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(30);
        root.setPadding(new Insets(20));
		Scene firstScene = new Scene(root, 1000, 550);
        // Create a title label
        Label titleLabel1 = new Label("Welcome to Our  App");
        titleLabel1.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        ImageView logo = new ImageView(new Image("logo_ensao1.png"));
    	logo.setFitWidth(130);
    	logo.setFitHeight(130);

        // Create an ImageView for the image
        
        
        //Create button to switch to the next scene
        Button proceed = new Button("Proceed");
        proceed.setStyle("-fx-padding:4 20; -fx-border-radius:3px;");
        proceed.setStyle("-fx-background-color:lightblue; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 10px 20px;");
        proceed.setOnAction(event12->{
        	Scene scene = new Scene(gridPane);
        	primaryStage.setScene(scene);
        });
        
        // Create a paragraph of text
        Text paragraphText = new Text("This app groups all ENSAO students who are studying currently in the engineering cycle.");
        paragraphText.setFont(Font.font("Arial", 16));

        // Add the title, image, and paragraph to the root layout
        root.getChildren().addAll(logo,titleLabel1,  paragraphText,proceed);  
        primaryStage.setScene(firstScene);
        primaryStage.setTitle("formulaire");
     
        primaryStage.show();
        // Création de la scène et affichage de la fenêtre principale
        
        
        
    }
 
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
