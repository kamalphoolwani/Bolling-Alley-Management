package Model;

public class ScoreCalculator {

	private int[][] cumulScores;  //reference to be mutated.

	 /**
	 * Constructor
	 */
	public ScoreCalculator(int[][] scores){
		this.cumulScores = scores;  // Operate on Lane's reference
	}

	protected void calculateGame(int[] bowlersScores, int bowlerIndex, int frameNumber, int roll, int pinsDown ){
		int currentRoll = (2 * frameNumber);
		currentRoll+= roll;
		// Reset the previous record of scores
		int j=0;
		while(j<cumulScores[bowlerIndex].length) {
			cumulScores[bowlerIndex][j] = 0;
			j++;
		}

		int frameIndex;
		int prevScore = 0;

		for(int i = 0; i < currentRoll; i++){
			boolean even =(i%2==1)?false:true;

		

			// Check Strike
			if(even && bowlersScores[i] == 10 && bowlersScores[i + 1] == -1){ // If all ten are down, and the roll we are on is even (starting at zero)
				prevScore = prevScore + strike(i, bowlersScores);
			}
			// Check Spare
			else if( !even && (bowlersScores[i - 1] + bowlersScores[i] == 10) ){ // If the roll we are on an odd roll, and the past two rolls add to ten
				prevScore = prevScore+spare(i, bowlersScores);
			}
			// Check if Normal
			else if(bowlersScores[i] >= 0){ // Only display after the second throw of the frame
				if (!even || i == 20){
					prevScore = prevScore+normal(i, bowlersScores);
				}
			}

			frameIndex = i / 2 ;
			if(i > 19){ // Last frame could have three throws
				frameIndex = 9;
			}
			this.cumulScores[bowlerIndex][frameIndex] = prevScore;

		}
	}

	/*
	* This is the strike helper method to calculate the stike score of the
	* current bolwer.
	*
	* @param index         : the current throw or roll.
	* @param currentScores : the most up to date record of scores.
	*/
	private int strike(int index, int[] currentScores){
		if(index > 17){
			return (10);
		}
		int count = 0;
		int scoreSum = 10; // Start at ten since it was a strike
		 // If a strike is thrown, then also add the next two rolls
		int i=1;
		while(i<=4) {
			int temp=index+i;
			if(currentScores[temp] >= 0 && count < 2){ // Add only the next two valid scores
				scoreSum += currentScores[temp];
				count++;
			}
			i++;
		}
		return scoreSum;
	}

	/*
	* This is the spare helper method to calculate the stike score of the
	* current bolwer.
	*
	* @param index         : the current throw or roll.
	* @param currentScores : the most up to date record of scores.
	*/
	private int spare(int index, int[] currentScores){
		if(index > 17){
			return (10);
		}
		int count = 0;
		int scoreSum = 10; // Start at ten since it was a strike
// If a strike is thrown, then also add the next two rolls
			
		int i=1;
		while(i<=4) {
			int temp=index+i;
			if(currentScores[temp] >= 0 && count < 1){ // Add only the next two valid scores
				scoreSum += currentScores[temp];
				count++;
			}
			i++;
		}
		
		return scoreSum;
	}

	/*
	* This is the normal case helper method to calculate the stike score of the
	* current bolwer.
	*
	* @param index         : the current throw or roll.
	* @param currentScores : the most up to date record of scores.
	*/
	private int normal(int index, int[] currentScores){
		int temp=index-1;
		if(index % 2==1 && currentScores[temp] >= 0){
			return(currentScores[index] + currentScores[temp]);
		}
		return(currentScores[index]);
	}
}