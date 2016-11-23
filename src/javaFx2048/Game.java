package javaFx2048;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Game extends Application{
	GamePane gamePane = new GamePane();
	public static rankManager manager = new rankManager();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		gamePane.setOnKeyReleased(e -> {
			gamePane.moveCardManager(e);
		});
		gamePane.setMaxSize(330, 440);
		
		Scene scene = new Scene(gamePane, 330, 440);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setTitle("2048");
		gamePane.requestFocus();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
