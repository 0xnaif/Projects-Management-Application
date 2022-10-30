import java.util.ArrayList;
import java.util.Date;

import stage.Stage;

public class Project {
	private int numOfStages;
	private String nodeID, customerID;
	private ArrayList<Stage> stages = new ArrayList<>();

	public Project() {

	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public int getNumOfStages() {
		return numOfStages;
	}

	public void setNumOfStages(int numOfStages) {
		this.numOfStages = numOfStages;
	}

	public ArrayList<Stage> getStages() {
		return stages;
	}

	public void addStage(Stage stage) {
		stages.add(stage);
	}

}
