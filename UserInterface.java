
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.stage.Stage;

public class UserInterface extends Application {
	private static ProjectManager projectManager = new ProjectManager();
	private static TableView<Project> table = new TableView<Project>(); // tableview contains the projects

	public static void main(String[] args) throws IOException {
		ProjectManager.read(); // read the three files provided
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Group pane = new Group();

		TableColumn<Project, String> col1 = new TableColumn<>("Project-ID"); // create columns of the tableview
		col1.setCellValueFactory(new PropertyValueFactory<>("customerID"));
		TableColumn<Project, Integer> col2 = new TableColumn<>("Stage");
		col2.setCellValueFactory(new PropertyValueFactory<>("numOfStages"));

		Label label = new Label("Project ID");
		label.setTranslateX(185);
		label.setTranslateY(70);
		label.setFont(Font.font(null, FontWeight.BOLD, 15));
		label.setStyle("-fx-background-color: #f4f4f4;");
//		label.setScaleY(1.2);

		TextField textField = new TextField(); // create a textfield to enter Customer Project ID for searching for a
												// project
		textField.setTranslateX(260);
		textField.setTranslateY(70);
		textField.setPrefWidth(80);

		Button button = new Button("Track"); // create a button for searching for a project and drawing a timeline
		button.setTranslateX(350);
		button.setTranslateY(70);

		table.getColumns().add(col1);
		table.getColumns().add(col2);
		table.setPrefSize(160, 400);

		col1.setPrefWidth(100);
		col2.setPrefWidth(45);

		table.setTranslateX(20);
		table.setTranslateY(20);

		table.getItems().addAll(projectManager.getProjectCollection().getProjects()); // fill the tableview with
																						// projects

		pane.getChildren().add(table);
		pane.getChildren().add(textField);
		pane.getChildren().add(label);
		pane.getChildren().add(button);

		button.setOnAction((ActionEvent e) -> { // if the rack button is clicked
			if (textField.getText().equals("")) { // if the textfield is empty
				Alert alertError = new Alert(AlertType.ERROR); // show an appropriate alert
				alertError.setTitle("Error");
				alertError.setHeaderText("Input Required");
				alertError.showAndWait();

			} else {
				boolean flag = true;
				String input = textField.getText();
				for (Project prject : projectManager.getProjectCollection().getProjects()) { // search for a project
																								// based on Customer
																								// Project ID
					if (prject.getCustomerID().equals(input)) {
						table.scrollTo(prject);
						table.getSelectionModel().select(prject);
						flag = false;
					}

				}
				if (flag) { // a project is not found
					Alert alertError = new Alert(AlertType.ERROR); // show an appropriate alert
					alertError.setTitle("Error");
					alertError.setHeaderText("No Project With Same Customer ID");
					alertError.showAndWait();
				}

				else { // a project is found
					textField.setText(""); // set the textfield
					Project project = table.getSelectionModel().getSelectedItem();
					DrawTimeLine d = new DrawTimeLine(project);
					ScrollPane root = new ScrollPane();

					root.setContent(d.draw());
					Scene secnodScene = new Scene(root, 1000, 500); // create another scene and stage to show the
																	// timeline
					Stage secondStage = new Stage();
					secondStage.setTitle(project.getCustomerID());
					secondStage.setScene(secnodScene);
					secondStage.show();

				}
			}
		});

		Scene scene = new Scene(pane, 1000, 480);
		stage.setTitle("Projects Tracker");
		stage.setScene(scene);
		stage.show();

	}

}
