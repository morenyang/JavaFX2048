package javaFx2048;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class NumCard extends StackPane{
	private Text text = new Text("");
	private int cardWidth = 70;
	
	private Rectangle rectangle = new Rectangle(cardWidth,cardWidth);
	
	
	public NumCard() {
		// TODO Auto-generated constructor stub
	}
	
	public NumCard(int num) {
		Font font = new Font(30);
		text.setFill(Color.WHITE);
		text.setFont(font);
		rectangle.setArcHeight(10);
		rectangle.setArcWidth(10);
		switch (num) {
		case 0:
			text.setText("");
			rectangle.setFill(Color.web("#f9f6f2"));
			this.getChildren().addAll(rectangle,text);
			break;
		case 2:
			text.setText(""+num);
			text.setFill(Color.web("776e65"));
			rectangle.setFill(Color.web("#eee4da"));
			this.getChildren().addAll(rectangle,text);
			break;
		case 4:
			text.setText(""+num);
			text.setFill(Color.web("776e65"));
			rectangle.setFill(Color.web("#ede0c8"));
			this.getChildren().addAll(rectangle,text);
			break;
		case 8:
			text.setText(""+num);
			rectangle.setFill(Color.web("#f2b179"));
			this.getChildren().addAll(rectangle,text);
			break;
		case 16:
			text.setText(""+num);
			rectangle.setFill(Color.web("#f59563"));
			this.getChildren().addAll(rectangle,text);
			break;
		case 32:
			text.setText(""+num);
			rectangle.setFill(Color.web("#f67c5f"));
			this.getChildren().addAll(rectangle,text);
			break;
		case 64:
			text.setText(""+num);
			rectangle.setFill(Color.web("#f65e3b"));
			this.getChildren().addAll(rectangle,text);
			break;
		case 128:
			text.setText(""+num);
			rectangle.setFill(Color.web("#edcf72"));
			this.getChildren().addAll(rectangle,text);
			break;
		case 256:
			text.setText(""+num);
			rectangle.setFill(Color.web("#edcc61"));
			this.getChildren().addAll(rectangle,text);
			break;
		case 512:
			text.setText(""+num);
			rectangle.setFill(Color.web("#edc850"));
			this.getChildren().addAll(rectangle,text);
			break;
		case 1024:
			text.setText(""+num);
			rectangle.setFill(Color.web("#edc53f"));
			this.getChildren().addAll(rectangle,text);
			break;
		case 2048:
			text.setText(""+num);
			rectangle.setFill(Color.web("#edc22e"));
			this.getChildren().addAll(rectangle,text);
			break;

		default:
			text.setText(""+num);
			rectangle.setFill(Color.web("#d3d3d3"));
			this.getChildren().addAll(rectangle,text);
			break;
		}
	}

	public int getCardWidth() {
		return cardWidth;
	}

	public void setCardWidth(int cardWidth) {
		this.cardWidth = cardWidth;
	}
	
	
}
