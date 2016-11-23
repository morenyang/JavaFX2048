package javaFx2048;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javaFx2048.Game;

public class NumPane extends Pane {
	protected static int num[][] = new int[4][4];
	protected static NumCard numCard[][] = new NumCard[4][4];
	protected static boolean controlLegal = true;

	private int cardMargin = 10;
	private int transitionTime = 150;

	public NumPane() {
		// TODO Auto-generated constructor stub
		this.setStyle("-fx-background-color: #bbada0;");
		this.setMaxSize(310, 310);

		cardPaneRefresh();
		initPane();
		cardPaneRefresh();
	}

	public void reset() {
		controlLegal = true;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				num[i][j] = 0;
			}
		}
		cardPaneRefresh();
		initPane();
		cardPaneRefresh();
	}

	private void printNumArray() {
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				System.out.print(" " + NumPane.num[row][col]);
			}
			System.out.println();
		}
	}

	// display NumPane
	private void displayNumPane() {
		for (int c = 0; c < 4; c++) {
			for (int r = 0; r < 4; r++) {
				numCard[r][c] = new NumCard(num[r][c]);
				numCard[r][c].setLayoutX(c * (numCard[r][c].getCardWidth() + this.cardMargin));
				numCard[r][c].setLayoutY(r * (numCard[r][c].getCardWidth() + this.cardMargin));
				this.getChildren().add(numCard[r][c]);
			}
		}
	}

	// crate NumCard
	private void crateNumCardWithTransition(int num, int row, int col) {
		NumCard newCard = new NumCard(num);
		newCard.setLayoutX(col * (newCard.getCardWidth() + this.cardMargin));
		newCard.setLayoutY(row * (newCard.getCardWidth() + this.cardMargin));
		this.getChildren().add(newCard);

		FadeTransition ft = new FadeTransition();
		ft.setNode(newCard);
		ft.setDuration(Duration.millis(transitionTime - 50));
		ft.setFromValue(0.7);
		ft.setToValue(1.0);
		ft.setAutoReverse(false);
		ft.setCycleCount(1);
		ft.play();
	}

	private void crateNumCardWithoutTransition(int num, int row, int col) {
		NumCard newCard = new NumCard(num);
		newCard.setLayoutX(col * (newCard.getCardWidth() + this.cardMargin));
		newCard.setLayoutY(row * (newCard.getCardWidth() + this.cardMargin));
		this.getChildren().add(newCard);
	}

	// refresh NumPane
	private void cardPaneRefresh() {
		this.getChildren().clear();
		this.displayNumPane();
	}

	// init NumPane
	private void initPane() {
		for (int i = 0; i < 2; i++) {
			addNewCard();
		}
	}

	private void addNewCard() {
		int pos[] = new int[2];
		int newNum = RandomManager.randomCardNum();
		boolean posLegal = false;
		while (!posLegal) {
			pos = RandomManager.randomPos();
			if (num[pos[0]][pos[1]] == 0) {
				crateNumCardWithTransition(newNum, pos[0], pos[1]);
				num[pos[0]][pos[1]] = newNum;
				posLegal = true;
			}
		}
	}
	
	public boolean isWin(){
		for (int c = 0; c < 4; c++) {
			for (int r = 0; r < 4; r++) {
				if (num[r][c] == 2048) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isContinueable() {
		int judgeArray[][] = new int[6][6];
		for (int c = 0; c < 4; c++) {
			for (int r = 0; r < 4; r++) {
				if (num[r][c] != 0) {
					judgeArray[r + 1][c + 1] = num[r][c];
				} else {
					return true;
				}
			}
		}
		for (int c = 1; c < 5; c++) {
			for (int r = 1; r < 5; r++) {
				if (judgeArray[r][c] == judgeArray[r + 1][c] || judgeArray[r][c] == judgeArray[r - 1][c] || judgeArray[r][c] == judgeArray[r][c + 1]
						|| judgeArray[r][c] == judgeArray[r][c - 1]) {
					return true;
				}
			}
		}
		return false;
	}

	protected void moveCardManager(KeyEvent e) {
		if (controlLegal) {
			switch (e.getCode()) {
			case LEFT: {
				boolean isMoveLegal = left();
				gameManager(isMoveLegal);

			}
				break;
			case RIGHT: {
				boolean isMoveLegal = right();
				gameManager(isMoveLegal);

			}
				break;
			case UP: {
				boolean isMoveLegal = up();
				gameManager(isMoveLegal);
				;
			}
				break;
			case DOWN: {
				boolean isMoveLegal = down();
				gameManager(isMoveLegal);
			}
				break;
			default:
				break;
			}
		}
	}

	private void gameManager(boolean isMoveLegal) {
		if(isWin()){
			win();
		}else {
			if (isContinueable()) {
				if (isMoveLegal) {
					addNewCard();
				}
			}else {
				lose();
			}
		}
	}

	// Direct
	/**
	 * 从移动到反方向开始 计算每个数字之前的0的个数 然后移动到每个数字之前都没有0 然后再进行叠加计算
	 * 
	 * @return
	 */
	private boolean up() {
		boolean isMoveLegal = false;
		int actionRank = 0;
		int num1[][] = new int[4][4];
		num1 = num;
		// 计算0的个数
		for (int col = 0; col < 4; col++) {
			int moveCount[] = new int[4];// 技术原单元格需要移动的个数
			int notZeroNum[] = new int[4];// 记录某个数是这一组里的第几个非零数
			int notZero = 0;// 记录非零数的个数
			int countZero = 0;// 记录零到个数
			for (int row = 0; row < 4; row++) {
				// 如果当前数非零 则记录下之前为另的数到个数
				if (num1[row][col] != 0) {
					notZero++;
					moveCount[row] = countZero;
					notZeroNum[row] = notZero;
					System.out.println(moveCount[row]);
					if (moveCount[row] != 0) {
						isMoveLegal = true;
					}
				} else {
					countZero++;
				}
			}

			if (countZero == 4) {
				continue;
			}
			// 执行去除空格操作
			for (int row = 1; row < 4; row++) {
				if (num1[row][col] != 0) {
					num1[row - moveCount[row]][col] = num1[row][col];
					if (moveCount[row] != 0) {
						num1[row][col] = 0;
					}
				}
			}

			// 合并相同数字
			System.out.println();
			System.out.println(notZero);
			System.out.println();
			printNumArray();
			System.out.println();

			for (int row = 0; row < notZero && row != 3; row++) {
				int doCount = 0;
				if (num1[row][col] == num1[row + 1][col]) {
					num1[row][col] += num1[row + 1][col];
					isMoveLegal = true;
					doCount++;
					actionRank += num1[row][col];

					System.out.println("movecount col before " + col);
					for (int i = 0; i < 4; i++) {
						System.out.print(" " + moveCount[i]);
					}
					System.out.println();
					System.out.println();

					for (int k = row + 1; k < notZero; k++) {
						moveCount[findIndexinNotZeroArray(k, notZeroNum)]++;
						if (k != 3) {
							num1[k][col] = num1[k + 1][col];
						}
					}

					// 将应该空白的地方归零
					for (int k = 0; k < doCount; k++) {
						num1[notZero - k - 1][col] = 0;
						crateNumCardWithoutTransition(0, notZero - k - 1, col);
					}

					for (int k = 0; k < 4; k++) {
						if (notZeroNum[k] != 0) {
							moveUp(numCard[k][col], moveCount[k]);
						}
						crateNumCardWithTransition(num1[k][col], k, col);
					}
				}
			}

			System.out.println("movecount col " + col);
			for (int i = 0; i < 4; i++) {
				System.out.print(" " + moveCount[i]);
			}
			System.out.println();
			System.out.println();
		}
		num = num1;
		displayNumPane();
		Game.manager.addRank(actionRank);
		return isMoveLegal;
	}

	private boolean down() {
		boolean isMoveLegal = false;
		int actionRank = 0;
		int num1[][] = new int[4][4];
		num1 = num;
		for (int col = 0; col < 4; col++) {
			int moveCount[] = new int[4];// 计算每格需要移动的格数
			int notZeroNum[] = new int[4];// 标记该数为第几个非0数
			int notZero = 0;// 统计非零数数量
			int countZero = 0;// 统计零的数量
			for (int row = 3; row >= 0; row--) {
				// 计算每个数之前有多少个非零数
				if (num1[row][col] != 0) {
					notZero++;
					moveCount[row] = countZero;
					notZeroNum[row] = notZero;
					if (moveCount[row] != 0) {
						isMoveLegal = true;
					}
				} else {
					countZero++;
				}
			}
			if (countZero == 4) {
				continue;
			}
			// 删去每个数之前的0
			for (int row = 3; row >= 0; row--) {
				if (num1[row][col] != 0) {
					num1[row + moveCount[row]][col] = num1[row][col];
				}
				if (moveCount[row] != 0) {
					num1[row][col] = 0;
				}
			}
			//
			// System.out.println("array");
			// printNumArray();
			// System.out.println();

			// 合并相同的数字
			System.out.println("MoveCount " + col);
			for (int i : moveCount) {
				System.out.print(" " + i);
			}
			System.out.println();
			System.out.println("notzero " + col);
			for (int i : notZeroNum) {
				System.out.print(" " + i);
			}
			System.out.println();

			for (int row = 3; row >= 4 - notZero && row != 0; row--) {
				int doCount = 0;// 统计进行了几次合并
				if (num1[row][col] == num1[row - 1][col]) {
					num1[row][col] += num1[row - 1][col];
					doCount++;
					actionRank += num1[row][col];
					isMoveLegal = true;
					for (int k = row - 1; k >= 4 - notZero; k--) {
						moveCount[findIndexinNotZeroArrayFormEnd(k, notZeroNum)]++;
						if (k != 0) {
							num1[k][col] = num1[k - 1][col];
						}
					}
					// 清空不需要的位置
					for (int k = 0; k < doCount; k++) {
						num1[(4 - notZero) + k][col] = 0;
						crateNumCardWithoutTransition(0, 4 - notZero + k, col);
					}

					for (int k = 0; k < 4; k++) {
						if (notZeroNum[k] != 0) {
							moveDown(numCard[k][col], moveCount[k]);
						}
						crateNumCardWithTransition(num1[k][col], k, col);
					}
				}
			}
		}
		System.out.println();

		num = num1;
		displayNumPane();
		Game.manager.addRank(actionRank);
		return isMoveLegal;
	}

	private boolean left() {
		boolean isMoveLegal = false;
		int actionRank = 0;
		int num1[][] = new int[4][4];
		num1 = num;
		// 计算0的个数
		for (int row = 0; row < 4; row++) {
			int moveCount[] = new int[4];
			int notZeroNum[] = new int[4];
			int notZero = 0;
			int countZero = 0;
			for (int col = 0; col < 4; col++) {
				// 如果当前数字非零，记录下之前为0的数的个数
				if (num1[row][col] != 0) {
					notZero++;
					moveCount[col] = countZero;
					notZeroNum[col] = notZero;
					if (moveCount[row] != 0) {
						isMoveLegal = true;
					}
				} else {
					countZero++;
				}
			}

			if (countZero == 4) {
				continue;
			}

			// 去除空格
			for (int col = 1; col < 4; col++) {
				if (num1[row][col] != 0) {
					num1[row][col - moveCount[col]] = num1[row][col];
					if (moveCount[col] != 0) {
						num1[row][col] = 0;
					}
				}
			}

			// 合并相同数字
			for (int col = 0; col < notZero && col != 3; col++) {
				int doCount = 0;
				if (num1[row][col] == num1[row][col + 1]) {
					num1[row][col] += num1[row][col + 1];
					doCount++;
					actionRank += num1[row][col];
					isMoveLegal = true;
					for (int k = col + 1; k < notZero; k++) {
						moveCount[findIndexinNotZeroArray(k, notZeroNum)]++;
						if (k != 3) {
							num1[row][k] = num1[row][k + 1];
						}
					}

					// 将应该空白的地方清零
					for (int k = 0; k < doCount; k++) {
						num1[row][notZero - k - 1] = 0;
						crateNumCardWithoutTransition(0, row, notZero - k - 1);
					}

					for (int k = 0; k < 4; k++) {
						if (notZeroNum[k] != 0) {
							moveLeft(numCard[row][col], moveCount[k]);
						}
						crateNumCardWithTransition(num[row][k], row, k);
					}
				}
			}
		}

		displayNumPane();
		Game.manager.addRank(actionRank);
		return isMoveLegal;
	}

	private boolean right() {
		boolean isMoveLegal = false;
		int actionRank = 0;
		int num1[][] = new int[4][4];
		num1 = num;
		for (int row = 0; row < 4; row++) {
			int moveCount[] = new int[4];// 计算每格需要移动的格数
			int notZeroNum[] = new int[4];// 标记该数为第几个非0数
			int notZero = 0;// 统计非零数数量
			int countZero = 0;// 统计零的数量
			for (int col = 3; col >= 0; col--) {
				if (num1[row][col] != 0) {
					notZero++;
					moveCount[col] = countZero;
					notZeroNum[col] = notZero;
					if (moveCount[row] != 0) {
						isMoveLegal = true;
					}
				} else {
					countZero++;
				}
			}
			if (countZero == 4) {
				continue;
			}
			// 删除每个数之前的0
			for (int col = 3; col >= 0; col--) {
				if (num1[row][col] != 0) {
					num1[row][col + moveCount[col]] = num1[row][col];
				}
				if (moveCount[col] != 0) {
					num1[row][col] = 0;
				}
			}

			// 合并相同数字
			for (int col = 3; col >= 4 - notZero && col != 0; col--) {
				int doCount = 0;
				if (num1[row][col] == num1[row][col - 1]) {
					num1[row][col] += num1[row][col - 1];
					doCount++;
					actionRank += num1[row][col];
					isMoveLegal = true;
					for (int k = col - 1; k >= 4 - notZero; k--) {
						moveCount[findIndexinNotZeroArrayFormEnd(k, notZeroNum)]++;
						if (k != 0) {
							num1[row][k] = num1[row][k - 1];
						}
					}

					// 清空不需要的位置
					for (int k = 0; k < doCount; k++) {
						num1[row][4 - notZero + k] = 0;
						crateNumCardWithoutTransition(0, row, 4 - notZero + k);
					}
					for (int k = 0; k < 4; k++) {
						if (notZeroNum[k] != 0) {
							moveRight(numCard[row][k], moveCount[k]);
						}
						crateNumCardWithTransition(num1[row][k], row, k);
					}
				}
			}
		}
		displayNumPane();
		Game.manager.addRank(actionRank);
		return isMoveLegal;
	}

	// 返回第n个非零数在原数组中的下标
	private int findIndexinNotZeroArray(int nowIndex, int[] notZeroArray) {
		int count = 0;
		for (int i = 0; i < 4; i++) {
			if (notZeroArray[i] != 0) {
				count++;
				if (count == nowIndex + 1)
					return i;
			}
		}
		return -1;
	}

	private int findIndexinNotZeroArrayFormEnd(int nowIndex, int[] notZeroArray) {
		int count = 4;
		for (int i = 3; i >= 0; i--) {
			if (notZeroArray[i] != 0) {
				count--;
				if (count == nowIndex + 1)
					return i;
			}
		}
		return -1;
	}

	// move action
	private void moveUp(NumCard card, int setp) {
		Line path = new Line();
		path.setStartX(0 + card.getWidth() / 2);
		path.setStartY(0 + card.getHeight() / 2);
		path.setEndX(card.getWidth() / 2);
		path.setEndY(card.getHeight() / 2 - setp * (card.getWidth() + cardMargin));

		PathTransition pt = new PathTransition();
		pt.setDuration(Duration.millis(transitionTime));
		pt.setPath(path);
		pt.setNode(card);
		pt.setAutoReverse(false);
		pt.setCycleCount(1);

		FadeTransition ft = new FadeTransition();
		ft.setNode(card);
		ft.setDuration(Duration.millis(transitionTime));
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.setAutoReverse(false);
		ft.setCycleCount(1);

		pt.play();
		ft.play();

	}

	private void moveDown(NumCard card, int setp) {
		Line path = new Line();
		path.setStartX(0 + card.getWidth() / 2);
		path.setStartY(0 + card.getHeight() / 2);
		path.setEndX(card.getWidth() / 2);
		path.setEndY(card.getHeight() / 2 + setp * (card.getWidth() + cardMargin));

		PathTransition pt = new PathTransition();
		pt.setDuration(Duration.millis(transitionTime));
		pt.setPath(path);
		pt.setNode(card);
		pt.setAutoReverse(false);
		pt.setCycleCount(1);

		FadeTransition ft = new FadeTransition();
		ft.setNode(card);
		ft.setDuration(Duration.millis(transitionTime));
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.setAutoReverse(false);
		ft.setCycleCount(1);

		pt.play();
		ft.play();
	}

	private void moveLeft(NumCard card, int setp) {
		Line path = new Line();
		path.setStartX(0 + card.getWidth() / 2);
		path.setStartY(0 + card.getHeight() / 2);
		path.setEndX(card.getWidth() / 2 - setp * (card.getWidth() + cardMargin));
		path.setEndY(card.getHeight() / 2);

		PathTransition pt = new PathTransition();
		pt.setDuration(Duration.millis(transitionTime));
		pt.setPath(path);
		pt.setNode(card);
		pt.setAutoReverse(false);
		pt.setCycleCount(1);

		FadeTransition ft = new FadeTransition();
		ft.setNode(card);
		ft.setDuration(Duration.millis(transitionTime));
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.setAutoReverse(false);
		ft.setCycleCount(1);

		pt.play();
		ft.play();
	}

	private void moveRight(NumCard card, int setp) {
		Line path = new Line();
		path.setStartX(0 + card.getWidth() / 2);
		path.setStartY(0 + card.getHeight() / 2);
		path.setEndX(card.getWidth() / 2 + setp * (card.getWidth() + cardMargin));
		path.setEndY(card.getHeight() / 2);

		PathTransition pt = new PathTransition();
		pt.setDuration(Duration.millis(transitionTime));
		pt.setPath(path);
		pt.setNode(card);
		pt.setAutoReverse(false);
		pt.setCycleCount(1);

		FadeTransition ft = new FadeTransition();
		ft.setNode(card);
		ft.setDuration(Duration.millis(transitionTime));
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.setAutoReverse(false);
		ft.setCycleCount(1);

		pt.play();
		ft.play();
	}

	private void lose() {
		StackPane losePane = new StackPane();

		losePane.setLayoutX(0);
		losePane.setLayoutY(0);
		losePane.setMinSize(310, 310);
		Text loseText = new Text("You Lose");
		loseText.setFont(new Font(50));
		loseText.setFill(Color.web("#bbada0"));

		Rectangle inner = new Rectangle(310, 310);
		inner.setFill(Color.WHITE);
		losePane.getChildren().addAll(inner, loseText);

		this.getChildren().add(losePane);

		FadeTransition ft = new FadeTransition();
		ft.setNode(losePane);
		ft.setDuration(Duration.millis(800));
		ft.setFromValue(0);
		ft.setToValue(0.8);
		ft.setAutoReverse(false);
		ft.setCycleCount(1);
		ft.play();
		controlLegal = false;
	}

	private void win() {
		StackPane winPane = new StackPane();

		winPane.setLayoutX(0);
		winPane.setLayoutY(0);
		winPane.setMinSize(310, 310);
		Text winText = new Text("You Win");
		winText.setFont(new Font(50));
		winText.setFill(Color.web("#bbada0"));

		Rectangle inner = new Rectangle(310, 310);
		inner.setFill(Color.WHITE);
		winPane.getChildren().addAll(inner, winText);

		this.getChildren().add(winPane);

		FadeTransition ft = new FadeTransition();
		ft.setNode(winPane);
		ft.setDuration(Duration.millis(800));
		ft.setFromValue(0);
		ft.setToValue(0.8);
		ft.setAutoReverse(false);
		ft.setCycleCount(1);
		ft.play();
		controlLegal = false;
	}
}
