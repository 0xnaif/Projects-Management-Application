import java.util.ArrayList;
import java.util.Date;

public class Project {
	private int numOfStages;
	private String nodeID, customerID;
	private Date startDate, endDate;
	private Date createDate, changeDate;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public ArrayList<Stage> getStages() {
		return stages;
	}

	public void addStage(Stage stage) {
		stages.add(stage);
	}

}
