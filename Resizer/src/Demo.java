



import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import zimnox.borderless.BorderlessScene;



public class Demo extends Application {


	
	
	public void start(Stage stage) {
		

		
		
		// Example
		BorderPane componentLayout = new BorderPane();
		componentLayout.setStyle("-fx-background-color:rgba(255,255,255,0.4);");
		ChoiceBox<String> fruits = new ChoiceBox<>(FXCollections.observableArrayList("Asparagus", "Beans", "Broccoli", "Cabbage", "Carrot", "Celery", "Cucumber", "Leek", "Mushroom" , "Pepper", "Radish", "Shallot", "Spinach", "Swede", "Turnip"));
		ListView<String> vegetables = new ListView<String>(FXCollections.observableArrayList("Apple", "Apricot", "Banana" ,"Cherry", "Date", "Kiwi", "Orange", "Pear", "Strawberry"));
		Button button = new Button("Exit");
		button.setOnAction(e -> {
			System.exit(0);
		});
		
		
		
		TableView tableView = new TableView();

        TableColumn<String, Person> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));


        TableColumn<String, Person> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));


        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);

        tableView.getItems().add(new Person("John", "Doe"));
        tableView.getItems().add(new Person("Jane", "Deer"));

        //VBox vbox = new VBox(tableView);
        
        

		
		componentLayout.setCenter(tableView);
		componentLayout.setLeft(fruits);
		componentLayout.setRight(vegetables);
		componentLayout.setBottom(button);
		
		HBox header = new HBox();
		header.setPrefHeight(30);
		header.setStyle("-fx-background-color:teal;");
		header.getChildren().add(new Label("You can drag me!"));
		header.setAlignment(Pos.CENTER);
		componentLayout.setTop(header);
		
		componentLayout.setStyle("-fx-background-color:gray;");

		
		
		BorderlessScene scene = new BorderlessScene(stage, componentLayout, 640, 480);
		stage.setScene(scene);
		stage.show();
		stage.setTitle("Demo");
		
		
		scene.setShadow(new DropShadow(35, Color.BLACK ));
		//scene.setShadow(new DropShadow(35, Color.CRIMSON));
		
		
		scene.setDragControl(header);
		

		
	}
	
	

	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
