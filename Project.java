import java.util.ArrayList;

public class Project {
	private int numOfStages;
	private String nodeID, customerID;
	private ArrayList<Stage> stages = new ArrayList<>();

	public Project() {

	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setNumOfStages(int numOfStages) {
		this.numOfStages = numOfStages;
	}

	public int getNumOfStages() {
		return numOfStages;
	}

	public ArrayList<Stage> getStages() {
		return stages;
	}

	public void addStage(Stage stage) {
		stages.add(stage);
	}

}
