package javaFx2048;

import java.util.Random;

public class RandomManager {
	public static int randomCardNum() {
		int max = 100;
		int min = 1;
		Random random = new Random();
		int randomNum = random.nextInt(max - min + 1) + min;
		if(randomNum > 11){
			return 2;
		}else {
			return 4;
		}
	}
	
	public static int[] randomPos(){
		int max = 3;
		int min = 0;
		Random random = new Random();
		int randomPos[] = new int[2];
		randomPos[0] = random.nextInt(max - min + 1) + min;
		randomPos[1] = random.nextInt(max - min + 1) + min;
		return randomPos;
	}
}
