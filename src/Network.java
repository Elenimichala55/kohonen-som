import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Network {
	Node[][] Map;

	public Network(int dimension) {
		Map = new Node[dimension][dimension];
		
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				Node node = new Node();
				Map[i][j] = node;
			}
		}
	}
	
	/**
	 * Method that creates the file for the clustering
	 * @throws IOException
	 */
	public void clusteringText() throws IOException {
		FileWriter errorFile = new FileWriter("results/clustering.txt");
		int id = 0;
		for (int i = 0; i < Map.length; i++) {
			for (int j = 0; j < Map[0].length; j++) {
				errorFile.write("Position = " + i + "," + j + " and Node ID = " + id + " " + Map[i][j].printable());
				id++;
				errorFile.write("\n");
			}

		}
		errorFile.close();
	}

	/**
	 * Method that prints Labels
	 */
	public void printLabels() {
		for (int i = 0; i < Map.length; i++) {
			for (int j = 0; j < Map[0].length; j++) {
				System.out.print(Map[i][j].returnLabel() + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Method that is responsible for the labeling
	 * @param x 
	 * @param letter
	 */
	public void labeling(double x[], String letter) {
		double labeld = 0;
		for (int i = 0; i < this.Map.length; i++) {
			for (int j = 0; j < this.Map[0].length; j++) {
				for (int k = 0; k < x.length; k++) {
					labeld += (x[k] - (this.Map[i][j].returnWeight(k))) * (x[k] - (this.Map[i][j].returnWeight(k)));
				}
				
				if (Map[i][j].returnLabelDistance() > labeld) {
					Map[i][j].setLabelDistance(labeld);
					Map[i][j].setLabel(letter);
				}
				labeld = 0;

			}
		}
	}

	/**
	 * Method that returns winners distance
	 * @param winner
	 * @return
	 */
	public double winnerD(int winner[][]) {
		int i = winner[0][0];
		int j = winner[0][1];
		return this.Map[i][j].returnDistance();
	}

	/**
	 * Method that calculates the distance
	 * @param x
	 */
	public void calculateDistance(double x[]) {
		double d = 0;
		for (int i = 0; i < this.Map.length; i++) {
			for (int j = 0; j < this.Map[0].length; j++) {
				for (int k = 0; k < x.length; k++) {
					d += (x[k] - (this.Map[i][j].returnWeight(k))) * (x[k] - (this.Map[i][j].returnWeight(k)));
				}
				this.Map[i][j].setDistance(d);
				d = 0;
			}
		}
	}

	/**
	 * Method that calculates the neighbour function
	 * @param winner
	 * @param s
	 */
	public void calculateNeighbourFunction(int[][] winner, double s) {
		for (int i = 0; i < Map.length; i++) {
			for (int j = 0; j < Map.length; j++) {
				double tempDistance = -((winner[0][0] - i) * (winner[0][0] - i)
						+ ((winner[0][1] - j) * (winner[0][1] - j)));
				double temp = Math.pow(Math.E, (tempDistance / (2 * s * s)));
				this.Map[i][j].setNeighborhoudFunction(temp);
			}
		}
	}
	
	/**
	 * Method that finds the position of the winner and returns it
	 * @return position of the winner
	 */
	public int[][] findWinner() {
		int[][] temp = new int[1][2];
		double tempdist = 10000000;
		int tempX = 0;
		int tempY = 0;
		for (int i = 0; i < this.Map.length; i++) {
			for (int j = 0; j < this.Map[0].length; j++) {
				if (tempdist > this.Map[i][j].returnDistance()) {
					tempX = i;
					tempY = j;
					tempdist = this.Map[i][j].returnDistance();
				}
			}
		}
		temp[0][0] = tempX;
		temp[0][1] = tempY;
		return temp;
	}
	
	/**
	 * Update the weights
	 * @param x
	 * @param learningRate
	 */
	public void updateWeights(double x[], double learningRate) {
		for (int i = 0; i < Map.length; i++) {
			for (int j = 0; j < Map[0].length; j++) {
				for (int k = 0; k < x.length; k++) {
					double newWeight = Map[i][j].returnWeight(k) + learningRate *
							Map[i][j].returnNeighborhoudFunction() * (x[k] - Map[i][j].returnWeight(k));
					Map[i][j].setWeight(k, newWeight);
				}
			}
		}
	}

	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the dimension of the map:" + "\nFor example: if dimension = 100 --> grid = 100 x 100 ");
		int dimension = scan.nextInt();
		System.out.println("Enter the learning rate (between 0.5 and 1):");
		double n0 = scan.nextDouble();
		// Check input values
		while ((n0 < 0.5) || (n0 >= 1)) {
			System.out.println("The value of learning rate should be between 0.5 and 1 " + " \nPlease enter a new entry :  ");
			n0 = scan.nextDouble();
		}

		System.out.println("Enter the number of iterations:");
		int iter = scan.nextInt();

		// Scanner to count training lines
		Scanner trainScanCntLines = new Scanner(new File("datasets/training.txt"));
		int cntTrainingLines = 0;

		while (trainScanCntLines.hasNext()) {
			trainScanCntLines.nextLine();
			cntTrainingLines++;
		}
		trainScanCntLines.close();
		System.out.println("The training file has " + cntTrainingLines + " patterns");

		// Scanner to insert training data in the table
		Scanner trainScanTable = new Scanner(new File("datasets/training.txt"));
		String trainingTable[][] = new String[cntTrainingLines][17];
		String temp = null;
		int lineTrainTable = 0;
		int columnTrainTable = 0;

		while (trainScanTable.hasNext()) {
			temp = trainScanTable.next();
			trainingTable[lineTrainTable][columnTrainTable] = temp;
			columnTrainTable++;
			if (columnTrainTable == 17) {
				lineTrainTable++;
				columnTrainTable = 0;
			}

		}
		trainScanTable.close();

		// Scanner to count testing lines
		Scanner testScanCntLines = new Scanner(new File("datasets/test.txt"));
		int cntTestLines = 0;

		while (testScanCntLines.hasNext()) {
			testScanCntLines.nextLine();
			cntTestLines++;
		}
		trainScanCntLines.close();
		System.out.println("The testing file has " + cntTestLines + " patterns");

		// scanner to insert testing data in the table
		Scanner testScanTable = new Scanner(new File("datasets/test.txt"));
		String testingTable[][] = new String[cntTestLines][17];
		temp = null;
		int lineTestTable = 0;
		int columnTestTable = 0;

		while (testScanTable.hasNext()) {
			temp = testScanTable.next();
			testingTable[lineTestTable][columnTestTable] = temp;
			columnTestTable++;
			if (columnTestTable == 17) {
				lineTestTable++;
				columnTestTable = 0;
			}

		}
		testScanTable.close();

		// Table with the characteristics
		String trainTableCorrect[][] = new String[cntTrainingLines - 1][17];
		String testTableCorrect[][] = new String[cntTestLines - 1][17];
		for (int i = 1; i < trainingTable.length; i++) {
			for (int j = 0; j < trainingTable[0].length; j++) {
				trainTableCorrect[i - 1][j] = trainingTable[i][j];
			}
		}

		for (int i = 1; i < testingTable.length; i++) {
			for (int j = 0; j < testingTable[0].length; j++) {
				testTableCorrect[i - 1][j] = testingTable[i][j];
			}
		}
		int OnlyData[][] = new int[cntTrainingLines + cntTestLines - 2][16];

		for (int i = 0; i < testTableCorrect.length + trainTableCorrect.length; i++) {
			for (int j = 1; j < 17; j++) {
				if (i < trainTableCorrect.length) {
					OnlyData[i][j - 1] = Integer.parseInt(trainTableCorrect[i][j]);

				} else {
					OnlyData[i][j - 1] = Integer.parseInt(testTableCorrect[i - trainTableCorrect.length][j]);
				}
			}
		}

		// Normalization
		double norm[][] = new double[16][2];
		for (int i = 0; i < 16; i++) {
			norm[i][0] = 0.0;// Maximum
			norm[i][1] = 100;// Minimum

		}

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < OnlyData.length; j++) {
				if (norm[i][0] <= OnlyData[j][i]) {
					norm[i][0] = OnlyData[j][i];
				}
				if (norm[i][1] >= OnlyData[j][i]) {

					norm[i][1] = OnlyData[j][i];
				}
			}
		}
		double OnlyDataNorm[][] = new double[OnlyData.length][16];
		for (int i = 0; i < OnlyData.length; i++) {
			for (int j = 0; j < 16; j++) {
				OnlyDataNorm[i][j] = (OnlyData[i][j] - norm[j][1]) / (norm[j][0] - norm[j][1]);
			}
		}

		double trainDataSet[][] = new double[trainTableCorrect.length][16];
		double testDataSet[][] = new double[testTableCorrect.length][16];
		for (int i = 0; i < trainTableCorrect.length + testTableCorrect.length; i++) {
			for (int j = 0; j < 16; j++) {
				if (i < trainTableCorrect.length) {
					trainDataSet[i][j] = OnlyDataNorm[i][j];
				} else {
					testDataSet[i - trainTableCorrect.length][j] = OnlyDataNorm[i][j];
				}
			}
		}

		double trainTargetOutput[][] = new double[trainTableCorrect.length][26];
		double testTargetOutput[][] = new double[testTableCorrect.length][26];
		for (int i = 0; i < trainTableCorrect.length; i++) {
			for (int j = 0; j < 26; j++) {
				trainTargetOutput[i][j] = 0.0;
			}
		}
		for (int i = 0; i < testTableCorrect.length; i++) {
			for (int j = 0; j < 26; j++) {
				testTargetOutput[i][j] = 0.0;
			}
		}
		for (int i = 0; i < trainTableCorrect.length; i++) {
			if (trainTableCorrect[i][0].equals("A")) {
				trainTargetOutput[i][0] = 1.0;
			} else if (trainTableCorrect[i][0].equals("B")) {
				trainTargetOutput[i][1] = 1.0;
			} else if (trainTableCorrect[i][0].equals("C")) {
				trainTargetOutput[i][2] = 1.0;
			} else if (trainTableCorrect[i][0].equals("D")) {
				trainTargetOutput[i][3] = 1.0;
			} else if (trainTableCorrect[i][0].equals("E")) {
				trainTargetOutput[i][4] = 1.0;
			} else if (trainTableCorrect[i][0].equals("F")) {
				trainTargetOutput[i][5] = 1.0;
			} else if (trainTableCorrect[i][0].equals("G")) {
				trainTargetOutput[i][6] = 1.0;
			} else if (trainTableCorrect[i][0].equals("H")) {
				trainTargetOutput[i][7] = 1.0;
			} else if (trainTableCorrect[i][0].equals("I")) {
				trainTargetOutput[i][8] = 1.0;
			} else if (trainTableCorrect[i][0].equals("J")) {
				trainTargetOutput[i][9] = 1.0;
			} else if (trainTableCorrect[i][0].equals("K")) {
				trainTargetOutput[i][10] = 1.0;
			} else if (trainTableCorrect[i][0].equals("L")) {
				trainTargetOutput[i][11] = 1.0;
			} else if (trainTableCorrect[i][0].equals("M")) {
				trainTargetOutput[i][12] = 1.0;
			} else if (trainTableCorrect[i][0].equals("N")) {
				trainTargetOutput[i][13] = 1.0;
			} else if (trainTableCorrect[i][0].equals("O")) {
				trainTargetOutput[i][14] = 1.0;
			} else if (trainTableCorrect[i][0].equals("P")) {
				trainTargetOutput[i][15] = 1.0;
			} else if (trainTableCorrect[i][0].equals("Q")) {
				trainTargetOutput[i][16] = 1.0;
			} else if (trainTableCorrect[i][0].equals("R")) {
				trainTargetOutput[i][17] = 1.0;
			} else if (trainTableCorrect[i][0].equals("S")) {
				trainTargetOutput[i][18] = 1.0;
			} else if (trainTableCorrect[i][0].equals("T")) {
				trainTargetOutput[i][19] = 1.0;
			} else if (trainTableCorrect[i][0].equals("U")) {
				trainTargetOutput[i][20] = 1.0;
			} else if (trainTableCorrect[i][0].equals("V")) {
				trainTargetOutput[i][21] = 1.0;
			} else if (trainTableCorrect[i][0].equals("W")) {
				trainTargetOutput[i][22] = 1.0;
			} else if (trainTableCorrect[i][0].equals("X")) {
				trainTargetOutput[i][23] = 1.0;
			} else if (trainTableCorrect[i][0].equals("Y")) {
				trainTargetOutput[i][24] = 1.0;
			} else if (trainTableCorrect[i][0].equals("Z")) {
				trainTargetOutput[i][25] = 1.0;
			}
		}

		for (int i = 0; i < testTableCorrect.length; i++) {
			if (testTableCorrect[i][0].equals("A")) {
				testTargetOutput[i][0] = 1.0;
			} else if (testTableCorrect[i][0].equals("B")) {
				testTargetOutput[i][1] = 1.0;
			} else if (testTableCorrect[i][0].equals("C")) {
				testTargetOutput[i][2] = 1.0;
			} else if (testTableCorrect[i][0].equals("D")) {
				testTargetOutput[i][3] = 1.0;
			} else if (testTableCorrect[i][0].equals("E")) {
				testTargetOutput[i][4] = 1.0;
			} else if (testTableCorrect[i][0].equals("F")) {
				testTargetOutput[i][5] = 1.0;
			} else if (testTableCorrect[i][0].equals("G")) {
				testTargetOutput[i][6] = 1.0;
			} else if (testTableCorrect[i][0].equals("H")) {
				testTargetOutput[i][7] = 1.0;
			} else if (testTableCorrect[i][0].equals("I")) {
				testTargetOutput[i][8] = 1.0;
			} else if (testTableCorrect[i][0].equals("J")) {
				testTargetOutput[i][9] = 1.0;
			} else if (testTableCorrect[i][0].equals("K")) {
				testTargetOutput[i][10] = 1.0;
			} else if (testTableCorrect[i][0].equals("L")) {
				testTargetOutput[i][11] = 1.0;
			} else if (testTableCorrect[i][0].equals("M")) {
				testTargetOutput[i][12] = 1.0;
			} else if (testTableCorrect[i][0].equals("N")) {
				testTargetOutput[i][13] = 1.0;
			} else if (testTableCorrect[i][0].equals("O")) {
				testTargetOutput[i][14] = 1.0;
			} else if (testTableCorrect[i][0].equals("P")) {
				testTargetOutput[i][15] = 1.0;
			} else if (testTableCorrect[i][0].equals("Q")) {
				testTargetOutput[i][16] = 1.0;
			} else if (testTableCorrect[i][0].equals("R")) {
				testTargetOutput[i][17] = 1.0;
			} else if (testTableCorrect[i][0].equals("S")) {
				testTargetOutput[i][18] = 1.0;
			} else if (testTableCorrect[i][0].equals("T")) {
				testTargetOutput[i][19] = 1.0;
			} else if (testTableCorrect[i][0].equals("U")) {
				testTargetOutput[i][20] = 1.0;
			} else if (testTableCorrect[i][0].equals("V")) {
				testTargetOutput[i][21] = 1.0;
			} else if (testTableCorrect[i][0].equals("W")) {
				testTargetOutput[i][22] = 1.0;
			} else if (testTableCorrect[i][0].equals("X")) {
				testTargetOutput[i][23] = 1.0;
			} else if (testTableCorrect[i][0].equals("Y")) {
				testTargetOutput[i][24] = 1.0;
			} else if (testTableCorrect[i][0].equals("Z")) {
				testTargetOutput[i][25] = 1.0;
			}
		}

		double s = dimension / 2;
		double s0 = s;
		double n = n0;
		Network map = new Network(dimension);
		double error[][] = new double[iter][3];

		// error file
		for (int i = 0; i < iter; i++) {
			error[i][0] = i;
			double sumD = 0;
			
			s = s0 * Math.pow(Math.E, (-i / (iter / Math.log10(s0))));
			n = n0 * Math.pow(Math.E, (-i / iter));

			for (int j = 0; j < trainDataSet.length; j++) {
				map.calculateDistance(trainDataSet[j]);
				int winner[][] = map.findWinner();
				map.calculateNeighbourFunction(winner, s);
				map.updateWeights(trainDataSet[j], n);
				sumD += map.winnerD(winner);

			}

			double trainError = (sumD * sumD) / trainDataSet.length;
			System.out.println("Epoch " + i + ": Training error = " + trainError);
			error[i][1] = trainError;

			double sumDTest = 0;
			for (int j = 0; j < testDataSet.length; j++) {
				map.calculateDistance(testDataSet[j]);
				int winner[][] = map.findWinner();
				map.calculateNeighbourFunction(winner, s);
				sumDTest += map.winnerD(winner);

			}
			double testError = (sumDTest * sumDTest) / testDataSet.length;
			
			error[i][2] = testError;
			System.out.println("Epoch " + i + ": Testing error = " + testError);
			System.out.println();
		}

		for (int j = 0; j < testDataSet.length; j++) {
			map.labeling(testDataSet[j], testTableCorrect[j][0]);

		}
		map.printLabels();
		map.clusteringText();
		
		// Creating the file results.txt
		FileWriter resultsFile = new FileWriter("results/results.txt");
		for (int i = 0; i < iter; i++) {
			for (int j = 0; j < 3; j++) {
				resultsFile.write(error[i][j] + "	");
			}
			resultsFile.write("\n");
		}
		resultsFile.close();

		System.out.println("End");

		scan.close();
	}
}
