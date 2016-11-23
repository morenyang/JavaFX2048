package javaFx2048;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class rankManager extends StackPane {
	private static int rank = 0;
	static Text rankText = new Text("");

	public rankManager() {
		// TODO Auto-generated constructor stub
		this.setMinSize(100, 50);
		this.setMaxSize(100, 50);
		Font font = new Font(25);
		rankText.setFill(Color.web("#8f7a66"));
		rankText.setFont(font);
		rankText.setText("" + rank);
		this.getChildren().add(rankText);
		this.setStyle("-fx-background-color: #f9f6f2;");
	}

	public static int getRank() {
		return rank;
	}

	public void addRank(int actionRank) {
		if(actionRank != 0){
			Line path = new Line();
			path.setStartX(rankText.getX() + 10);
			path.setEndX(rankText.getX() + 10);
			path.setStartY(rankText.getY() - 10);
			path.setEndY(rankText.getY() - 100);

			Text actionRankText = new Text("+" + actionRank);
			actionRankText.setFill(Color.web("#8f7a66"));
			actionRankText.setFont(new Font(25));
			this.getChildren().add(actionRankText);

			PathTransition pt = new PathTransition();
			pt.setDuration(Duration.millis(800));
			pt.setPath(path);
			pt.setNode(actionRankText);
			pt.setAutoReverse(false);
			pt.setCycleCount(1);

			FadeTransition ft = new FadeTransition();
			ft.setNode(actionRankText);
			ft.setDuration(Duration.millis(800));
			ft.setFromValue(1.0);
			ft.setToValue(0);
			ft.setAutoReverse(false);
			ft.setCycleCount(1);

			pt.play();
			ft.play();

			rank += actionRank;
			rankText.setText("" + rank);
		}
	}
	
	public void reset() {
		rank = 0;
		rankText.setText("" + rank);
	}

}
