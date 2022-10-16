import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ProjectManager {
	private static ArrayList<Project> projects = new ArrayList<Project>();

	public ProjectManager() {

	}

	public void read() throws IOException {
		readProjectFile("test.xlsx");
		readStageFile("test2.xlsx", "test3.xlsx");
	}

	private static void readProjectFile(String fileName) throws IOException {
		FileInputStream input = new FileInputStream(new File(fileName));
		XSSFWorkbook wb = new XSSFWorkbook(input);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row = sheet.getRow(1);
		int i = 1;
		int day, month, year;

		while (row != null) {
			Project project = new Project();

			String nodeID = row.getCell(0).getStringCellValue();
			String customerID = row.getCell(1).getStringCellValue();
			int numOfStages = (int) row.getCell(2).getNumericCellValue();

			Date startDate = row.getCell(3).getDateCellValue();
			Date endDate = row.getCell(4).getDateCellValue();

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
			projects.add(project);

			row = sheet.getRow(++i);
		}

	}

	private static void readStageFile(String fileName1, String fileName2) throws IOException {
		FileInputStream input1 = new FileInputStream(new File(fileName1));
		FileInputStream input2 = new FileInputStream(new File(fileName2));
		XSSFWorkbook wb1 = new XSSFWorkbook(input1);
		XSSFWorkbook wb2 = new XSSFWorkbook(input2);

		XSSFSheet sheet1 = wb1.getSheetAt(0);
		XSSFSheet sheet2 = wb2.getSheetAt(0);
		XSSFRow sheet1Row = sheet1.getRow(1);
		XSSFRow sheet2Row = sheet2.getRow(1);
		int i = 1;

		while (sheet1Row != null) {
			Stage stage = new Stage();

			String objectID = sheet1Row.getCell(0).getStringCellValue();
			int docNum = (int) sheet1Row.getCell(1).getNumericCellValue();
			int newValue = (int) sheet1Row.getCell(5).getNumericCellValue();
			int oldValue = (int) sheet1Row.getCell(6).getNumericCellValue();
			Date endDate = sheet2Row.getCell(3).getDateCellValue();
			stage.setObjectID(objectID);
			stage.setDocNum(docNum);
			stage.setNewValue(newValue);
			stage.setOldValue(oldValue);
			stage.setEndDate(endDate);

			for (Project e : projects) {
				if (e.getNodeID().equals(stage.getObjectID())) {
					e.setStage(stage);
				}
			}

			i += i;
			sheet1Row = sheet1.getRow(i);
			sheet2Row = sheet2.getRow(i);
		}
	}
}
