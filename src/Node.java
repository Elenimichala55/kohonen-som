import java.util.Random;

public class Node {

	double[] weight_vector; // weight vector with 16 positions (as the number of the inputs)
	double distance;
	double neighborhoudFunction; // value of the Neighbourhood method Hci
	double labelDistance; // label distance (least distance from a pattern)
	String label; // label of the letter which is closest to the letter

	/**
	 * The constructor method of the class
	 */
	public Node() {
		this.weight_vector = new double[16];
		labelDistance = 100000;
		label = null;
		this.distance = 0;
		this.neighborhoudFunction = 0;
		Random rand = new Random();
		// init weights
		for (int i = 0; i < 16; i++) {
			
			this.weight_vector[i] = rand.nextDouble();
		}
	}

	/**
	 * A method which returns the string of the node
	 * 
	 * @return string of node
	 */
	public String printable() {
		String k = "and the weights of the node are";
		for (int i = 0; i < weight_vector.length; i++) {
			k = k + this.weight_vector[i] + " ";
		}
		k = k + "and the label is " + this.label;
		return k;
	}

	/**
	 * A method which returns the label distance
	 * 
	 * @return labelDsitance
	 */
	public double returnLabelDistance() {
		return this.labelDistance;
	}

	/**
	 * A setter method which sets the labels distance
	 * 
	 * @param lb
	 */
	public void setLabelDistance(double lb) {
		this.labelDistance = lb;
	}

	/**
	 * A method which returns the label of the node
	 * 
	 * @return the label of the node
	 */

	public String returnLabel() {
		return this.label;
	}

	/**
	 * A setter method which sets the label of the node
	 * 
	 * @param l
	 */
	public void setLabel(String l) {
		this.label = l;
	}

	/**
	 * A getter method which returns the value of the weight in a certain position
	 * 
	 * @param pos
	 */
	public double returnWeight(int pos) {
		return this.weight_vector[pos];
	}

	/**
	 * A setter method which sets the weight at a certain position with a certain
	 * value
	 * 
	 * @param pos   position
	 * @param value value
	 */
	public void setWeight(int pos, double value) {
		this.weight_vector[pos] = value;
	}

	/**
	 * A setter method which sets the value of the distance
	 * 
	 * @param d
	 */
	public void setDistance(double d) {
		this.distance = d;
	}

	/**
	 * A getter method which returns the distances value
	 * 
	 * @return distance
	 */
	public double returnDistance() {
		return this.distance;
	}

	/**
	 * A getter method which returns the neighborhoods method value
	 * 
	 * @return neighborhoods method value
	 */
	public double returnNeighborhoudFunction() {
		return this.neighborhoudFunction;
	}

	/**
	 * A setter method which sets the neighborhoods method value
	 * 
	 * @param num
	 */
	public void setNeighborhoudFunction(double num) {
		this.neighborhoudFunction = num;
	}

}
