package javaFx2048;

import javafx.geometry.Insets;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class GamePane extends VBox{
	private static NumPane numPane = new NumPane();
	public ControlPane controlPane = new ControlPane();
	
	public GamePane() {
		// TODO Auto-generated constructor stub
		this.getChildren().addAll(controlPane,numPane);
		this.setStyle("-fx-background-color: #bbada0;");
		VBox.setMargin(numPane, new Insets(10));
	}
	
	public void moveCardManager(KeyEvent e){
		numPane.moveCardManager(e);
	}
	
	public static class GameController{
		public static void restartGame(){
			numPane.reset();
			Game.manager.reset();
		}
	}
}
