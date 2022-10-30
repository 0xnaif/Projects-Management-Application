import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import stage.Stage;

public class ProjectManager {
	private static ProjectCollection projectcollection;

	public static void main(String[] args) throws IOException {
		read();
	}

	public ProjectManager() {
		projectcollection = new ProjectCollection();
	}

	public ProjectCollection getProjectCollection() {
		return projectcollection;
	}

	public static void read() throws IOException {
		readProjectFile("Projects.xls");
		readStageFiles("Stages.xls", "Stages_Detailed.xls");

	}

	private static void readProjectFile(String fileName) throws IOException {
		FileInputStream input = new FileInputStream(new File(fileName));
		HSSFWorkbook wb = new HSSFWorkbook(input);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(1);
		int i = 1;
		int day, month, year;

		while (row != null) {
			Project project = new Project(); // get project info from project file
			String nodeID = row.getCell(0).getStringCellValue();
			String customerID = row.getCell(1).getStringCellValue();
			int numOfStages = (int) row.getCell(2).getNumericCellValue();
			HSSFCell cell = row.getCell(3);
			project.setNodeID(nodeID); // add project info to a project object
			project.setCustomerID(customerID);
			project.setNumOfStages(numOfStages);
			projectcollection.addProjects(project); // add a project to projects arraylist

			i += 1;

			row = sheet.getRow(i);
		}

	}

	private static void readStageFiles(String fileName1, String fileName2) throws IOException {
		FileInputStream input1 = new FileInputStream(new File(fileName1));
		FileInputStream input2 = new FileInputStream(new File(fileName2));
		HSSFWorkbook wb1 = new HSSFWorkbook(input1);
		HSSFWorkbook wb2 = new HSSFWorkbook(input2);

		HSSFSheet sheet1 = wb1.getSheetAt(0);
		HSSFSheet sheet2 = wb2.getSheetAt(0);
		HSSFRow sheet1Row = sheet1.getRow(1);
		HSSFRow sheet2Row = sheet2.getRow(1);
		int i = 1;

		while (sheet1Row != null) {
			Stage stage = new Stage(); // get the stage info from stage files
			String objectID = sheet1Row.getCell(0).getStringCellValue();
			int docNum = (int) sheet1Row.getCell(1).getNumericCellValue();
			String changeIndicator = sheet1Row.getCell(3).getStringCellValue();
			int newValue = (int) sheet1Row.getCell(5).getNumericCellValue();
			int oldValue;
			HSSFCell cell = sheet1Row.getCell(6);
			if (cell == null || cell.getCellType() == CellType.BLANK) {
				oldValue = 0;
			} else
				oldValue = (int) cell.getNumericCellValue();
			Date endDate = sheet2Row.getCell(2).getDateCellValue();
			stage.setObjectID(objectID); // add stage info to a stage object
			stage.setDocNum(docNum);
			stage.setChangeIndicator(changeIndicator);
			stage.setNewValue(newValue);
			stage.setOldValue(oldValue);
			stage.setEndDate(endDate);

			for (Project e : projectcollection.getProjects()) // identify the project a stage belongs to based on project id
				if (e.getNodeID().equals(stage.getObjectID())) {
					e.addStage(stage);
					break;
				}

			i += 1;
			sheet1Row = sheet1.getRow(i);
			sheet2Row = sheet2.getRow(i);
		}
	}
}
