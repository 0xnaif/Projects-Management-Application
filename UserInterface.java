
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class UserInterface extends Application {
	private static ProjectManager projectManager = new ProjectManager();
	private static TableView<Project> table = new TableView<Project>();

	public static void main(String[] args) throws IOException {
		projectManager.read();
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub

		TableColumn<Project, String> col1 = new TableColumn<>("Project-ID");
		col1.setCellValueFactory(new PropertyValueFactory<>("customerID"));
		TableColumn<Project, Integer> col2 = new TableColumn<>("Stage");
		col2.setCellValueFactory(new PropertyValueFactory<>("numOfStages"));

		Label label = new Label("Project ID");
		label.setTranslateX(190);
		label.setTranslateY(73);
		label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
		label.setStyle("-fx-background-color: #f4f4f4;");
		label.setScaleY(1.2);

		TextField textField = new TextField();
		textField.setTranslateX(260);
		textField.setTranslateY(70);
		textField.setPrefWidth(80);

		Button button = new Button("Track");
		button.setTranslateX(350);
		button.setTranslateY(70);
		Group pane = new Group();

		table.getColumns().add(col1);
		table.getColumns().add(col2);
		table.setPrefSize(160, 300);

		col1.setPrefWidth(100);
		col2.setPrefWidth(45);

		table.setTranslateX(20);
		table.setTranslateY(20);

		table.getItems().addAll(projectManager.getProjects());

		pane.getChildren().add(table);
		pane.getChildren().add(textField);
		pane.getChildren().add(label);
		pane.getChildren().add(button);
		Scene scene = new Scene(pane, 900, 500);
		stage.setTitle("Test");
		stage.setScene(scene);
		stage.show();

		button.setOnAction((ActionEvent e) -> {
			if (textField.getText().equals("")) {
				Alert alertError = new Alert(AlertType.ERROR);
				alertError.setTitle("Error");
				alertError.setHeaderText("Input Required");
				alertError.showAndWait();

			} else {
				boolean flag = true;
				String input = textField.getText();
				for (Project prject : projectManager.getProjects()) {
					if (prject.getCustomerID().equals(input)) {
						table.scrollTo(prject);
						table.getSelectionModel().select(prject);
						flag = false;
					}

				}
				if (flag) {
					Alert alertError = new Alert(AlertType.ERROR);
					alertError.setTitle("Error");
					alertError.setHeaderText("No Project With Same Customer ID");
					alertError.showAndWait();
				}
			}
		});
	}
}
