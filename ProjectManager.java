import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.scene.control.Cell;
import javafx.scene.control.TableView;

public class ProjectManager {
	private static ArrayList<Project> projects = new ArrayList<Project>();

	public static void main(String[] args) throws IOException {
		read();
	}

	public ProjectManager() {

	}

	public static ArrayList<Project> getProjects() {
		return projects;
	}

	public static void read() throws IOException {
		readProjectFile("Projects.xls");
		readStageFile("Stages.xls", "Stages_Detailed.xls");

	}

	private static void readProjectFile(String fileName) throws IOException {
		FileInputStream input = new FileInputStream(new File(fileName));
		HSSFWorkbook wb = new HSSFWorkbook(input);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(1);
		int i = 1;
		int day, month, year;

		while (row != null) {
			Project project = new Project();
			String nodeID = row.getCell(0).getStringCellValue();
			String customerID = row.getCell(1).getStringCellValue();
			int numOfStages = (int) row.getCell(2).getNumericCellValue();
			HSSFCell cell = row.getCell(3);
			Date startDate;
			if (cell == null || cell.getCellType() == CellType.BLANK) {
				startDate = null;
			} else
				startDate = cell.getDateCellValue();

			cell = row.getCell(4);
			Date endDate;
			if (cell == null || cell.getCellType() == CellType.BLANK) {
				endDate = null;
			} else
				endDate = cell.getDateCellValue();

			String crDate = row.getCell(7).getStringCellValue();
			day = Integer.parseInt(crDate.substring(0, 2));
			month = Integer.parseInt(crDate.substring(3, 5));
			year = Integer.parseInt(crDate.substring(6, 10));
			Date createDate = new Date(year, month, day);

			String chDate = row.getCell(8).getStringCellValue();
			day = Integer.parseInt(chDate.substring(0, 2));
			month = Integer.parseInt(chDate.substring(3, 5));
			year = Integer.parseInt(chDate.substring(6, 10));
			Date changeDate = new Date(year, month, day);

			project.setNodeID(nodeID);
			project.setCustomerID(customerID);
			project.setNumOfStages(numOfStages);
			project.setStartDate(startDate);
			project.setEndDate(endDate);
			project.setCreateDate(createDate);
			project.setChangeDate(changeDate);
			addProjects(project);
			i += 1;

			row = sheet.getRow(i);
		}

	}

	public static void addProjects(Project projcet) {
		projects.add(projcet);
	}

	private static void readStageFile(String fileName1, String fileName2) throws IOException {
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
			Stage stage = new Stage();

			String objectID = sheet1Row.getCell(0).getStringCellValue();
			int docNum = (int) sheet1Row.getCell(1).getNumericCellValue();
			int newValue = (int) sheet1Row.getCell(5).getNumericCellValue();
			int oldValue;
			HSSFCell cell = sheet1Row.getCell(6);
			if (cell == null || cell.getCellType() == CellType.BLANK) {
				oldValue = 0;
			} else
				oldValue = (int) cell.getNumericCellValue();
			Date endDate = sheet2Row.getCell(3).getDateCellValue();
			stage.setObjectID(objectID);
			stage.setDocNum(docNum);
			stage.setNewValue(newValue);
			stage.setOldValue(oldValue);
			stage.setEndDate(endDate);

			for (Project e : projects)
				if (e.getNodeID().equals(stage.getObjectID()))
					e.addStage(stage);

			i += 1;
			sheet1Row = sheet1.getRow(i);
			sheet2Row = sheet2.getRow(i);
		}
	}
}
