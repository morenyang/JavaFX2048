package javaFx2048;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ControlPane extends BorderPane{
	public ControlPane() {
		// TODO Auto-generated constructor stub
		this.setStyle("-fx-background-color:#bbada0;");
		
		//ScoreCard
		VBox scoreCard = new VBox();
		StackPane scoreTitle = new StackPane();
		Text scoreTitleText = new Text("Score");
		scoreTitleText.setFill(Color.web("#8f7a66"));
		scoreTitleText.setFont(new Font(20));
		scoreTitle.getChildren().add(scoreTitleText);
		StackPane.setMargin(scoreTitleText, new Insets(10, 0, 0, 0));
		scoreTitle.setStyle("-fx-background-color: #f9f6f2");
		
		scoreCard.getChildren().addAll(scoreTitle, Game.manager);
		
		
		//Title
		StackPane titleCard = new StackPane();
		Text titleText = new Text("2048");
		titleText.setFill(Color.web("#f9f6f2"));
		titleText.setFont(new Font(50));
		titleCard.getChildren().add(titleText);
		StackPane.setMargin(titleText, new Insets(0,0,0,10));
		titleCard.setMaxHeight(50);
		
		BorderPane leftCard = new BorderPane();
		
		StackPane restartCard = new StackPane();
		Text restartText = new Text("restart");
		restartText.setFill(Color.web("#f9f6f2"));
		restartText.setFont(new Font(20));
		restartCard.getChildren().add(restartText);
		StackPane.setMargin(restartText, new Insets(70,10,0,0));
		restartCard.setMaxHeight(20);
		
		leftCard.setLeft(titleCard);
		leftCard.setRight(restartCard);
		leftCard.setMaxWidth(210);
		leftCard.setMinWidth(210);
		
		restartCard.setOnMouseClicked(e->{
			restartGame();
		});
		
		this.setMaxSize(330, 130);
		this.setLeft(leftCard);
		this.setRight(scoreCard);
		BorderPane.setMargin(scoreCard, new Insets(10));
	}
	
	public static void restartGame(){
		GamePane.GameController.restartGame();
	}
}
