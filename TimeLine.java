import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TimeLine {
	private Project project;
	private Pane pane = new Pane();

	public TimeLine(Project project) {
		this.project = project;
	}

	public Pane draw() {
		drawLine();
		drawReWork(project);
		return pane;
	}
	
	private void drawLine() {
		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec" };
		Dictionary<Integer, Integer> m_To_d = new Hashtable<>(); // every month against its number of days
		m_To_d.put(0, 31);
		m_To_d.put(1, 28);
		m_To_d.put(2, 31);
		m_To_d.put(3, 30);
		m_To_d.put(4, 31);
		m_To_d.put(5, 30);
		m_To_d.put(6, 31);
		m_To_d.put(7, 31);
		m_To_d.put(8, 30);
		m_To_d.put(9, 31);
		m_To_d.put(10, 30);
		m_To_d.put(11, 31);

		int size = project.getStages().size(); // number of stages in a project
		Stage firstStage = project.getStages().get(0); // use name of package containing Stage class to avoid conflict with javafx stage class
		Stage lastStage = project.getStages().get(size - 1);
		int firstMonth = firstStage.getEndDate().getMonth();
		int firstYear = firstStage.getEndDate().getYear();
		int lastYear = project.getStages().get(size - 1).getEndDate().getYear();
		int lastMonth = project.getStages().get(size - 1).getEndDate().getMonth();
		int monthIndex = firstMonth, monthsRange;

		if ((firstYear - lastYear) < 0) { // if a project starts and ends in a different year
			monthsRange = 11 - firstMonth + 2;
			monthsRange += lastMonth; // identify number of months a project has been worked on
		} else
			monthsRange = lastMonth - firstMonth + 1;
		int year = project.getStages().get(0).getEndDate().getYear() - 100;

		int dashedLinePointer = 100, stageValuePointer = 0, lastDayPointer = 0, firstStagePointer = 0,
				lastStagePointer = 0;
		for (int month = 0; month < monthsRange; month++) {
			int monthDays = m_To_d.get(monthIndex);

			for (int day = 1; day <= monthDays; day++) {
				Line dashedLine = new Line(); // each dashed line represents a day
				dashedLine.setStroke(Color.BLACK);
				for (Stage stage : project.getStages()) { // identify end date of a stage
					if (compareStageDate(stage, monthIndex, day)) { // compare current day with stage end date day
						if (firstStage.getDocNum() == stage.getDocNum()) // identify first stage pointer to use it in
							firstStagePointer = dashedLinePointer;
						else if (lastStage.getDocNum() == stage.getDocNum()) // identify last stage pointer to use it in
							lastStagePointer = dashedLinePointer;

						stageValuePointer += 10;
						drawStageDetails(stage, dashedLinePointer, stageValuePointer); // draw stage date and its value

					}
				}
				if (day == 1) {
					dashedLine.setStartX(dashedLinePointer);
					dashedLine.setStartY(246 - stageValuePointer);
					dashedLine.setEndX(dashedLinePointer);
					dashedLine.setEndY(254);
					Label dateLabel = new Label(months[monthIndex] + " 20" + year);
					dateLabel.setFont(new Font(8));
					dateLabel.setTranslateX(dashedLinePointer - 15);
					dateLabel.setTranslateY(255);
					pane.getChildren().add(dateLabel);

					dashedLinePointer += 10;
				}

				else {
					if (month == monthsRange - 1 && day == monthDays) { // to draw a dashed line for last day of last month
						lastDayPointer = 4;
						stageValuePointer = 2;
					}
					dashedLine.setStartX(dashedLinePointer);
					dashedLine.setStartY(248 - stageValuePointer);
					dashedLine.setEndX(dashedLinePointer);
					dashedLine.setEndY(250 + lastDayPointer);

					dashedLinePointer += 10;
					if (monthIndex == months.length - 1 && day == monthDays) { // if the month is last month of the year
						monthIndex = 0;
						year += 1;
					}

					else if (day == monthDays)
						monthIndex += 1;

				}
				stageValuePointer = 0;
				pane.getChildren().addAll(dashedLine);
			}

		}
		Line line = new Line(100, 250, dashedLinePointer - 10, 250);
		line.setStroke(Color.BLACK);
		pane.getChildren().add(line);
		
		long timeDiff = Math.abs(firstStage.getEndDate().getTime() - lastStage.getEndDate().getTime()); // identify duration of a project
		long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

		if (daysDiff != 0) // if a project has only one stage or more than one with same end date
			drawDurationLine(firstStagePointer, lastStagePointer, daysDiff);
	}

	private boolean compareStageDate(Stage s, int month, int day) {
		if (s.getEndDate().getDate() == day && s.getEndDate().getMonth() == month)
			return true;
		return false;
	}

	private void drawStageDetails(Stage stage, int hPointer, int vPointer) {
		Label stgaeDate = new Label();
		Label stageValue = new Label();
		Line stageValueline = new Line();
		stageValue.setText(stage.getNewValue() + "");
		String date = (stage.getEndDate().getMonth() + 1) + "/" + stage.getEndDate().getDate();
		stgaeDate.setText(date);

		new Indicator().indicator(stage.getOldValue(), stage.getNewValue(), stgaeDate, stageValue, stageValueline); // identify direction of change in a tage

		stgaeDate.setFont(new Font(8));
		stageValue.setFont(new Font(8));
		stgaeDate.setTranslateX(hPointer - 5);
		stgaeDate.setTranslateY(265);

		stageValue.setTranslateX(hPointer + 2);
		stageValue.setTranslateY(246 - (vPointer + 5));

		stageValueline.setStartX(hPointer);
		stageValueline.setStartY(248 - vPointer);
		stageValueline.setEndX(hPointer + 2);
		stageValueline.setEndY(248 - vPointer);

		pane.getChildren().addAll(stgaeDate, stageValue, stageValueline);
	}

	private void drawDurationLine(int firstStagePointer, int lastStagePointer, long daysDiff) {
		Line durationLine = new Line();
		Line leftDashedLine = new Line(firstStagePointer, 95, firstStagePointer, 105);
		Line rightDashedLine = new Line(lastStagePointer, 95, lastStagePointer, 105);

		Label durationLabel = new Label("Duration: " + daysDiff + " days");
		durationLabel.setFont(new Font(10));
		durationLine.setStroke(Color.RED);
		leftDashedLine.setStroke(Color.RED);
		rightDashedLine.setStroke(Color.RED);

		durationLabel.setTextFill(Color.RED);

		durationLine.setStartX(firstStagePointer);
		durationLine.setStartY(100);
		durationLine.setEndX(lastStagePointer);
		durationLine.setEndY(100);

		durationLabel.setTranslateX(((lastStagePointer + firstStagePointer) / 2) - 40);
		durationLabel.setTranslateY(75);
		pane.getChildren().addAll(durationLine, leftDashedLine, rightDashedLine, durationLabel);
	}

	public void drawReWork(Project project) {
		Label l1 = new Label("Reworks");
		Label l2 = new Label();
		Label l3 = new Label("Before Award");
		Label l4 = new Label("After Award");
		int num_Of_befor_reWroks = 0, num_Of_after_reWroks = 0;
		boolean flag = true;
		for (Stage s : project.getStages()) {
			if (s.getNewValue() < s.getOldValue())
				if (flag)
					num_Of_befor_reWroks += 1;
				else
					num_Of_after_reWroks += 1;
			if (s.getNewValue() == 5)
				flag = false;

		}
		
		l1.setTranslateX(150);
		l1.setTranslateY(350);
		l1.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
		l1.setTextFill(Color.WHITE);
		l1.setBackground(new Background(new BackgroundFill(Color.web("#8B4513"), CornerRadii.EMPTY, Insets.EMPTY)));
		l1.setPadding(new Insets(3, 65, 3, 65));
		
		l2.setTranslateX(150);
		l2.setTranslateY(374);
		l2.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
		l2.setTextFill(Color.BLACK);
		l2.setBackground(new Background(new BackgroundFill(Color.web("#FFF5EE"), CornerRadii.EMPTY, Insets.EMPTY)));
		l2.setPadding(new Insets(10, 50, 10, 50));
		l2.setText(num_Of_befor_reWroks + "                  " + num_Of_after_reWroks);
		
		l3.setTranslateX(150);
		l3.setTranslateY(412);
		l3.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
		l3.setTextFill(Color.WHITE);
		l3.setBackground(new Background(new BackgroundFill(Color.web("#5F9EA0"), CornerRadii.EMPTY, Insets.EMPTY)));
		l3.setPadding(new Insets(3, 10, 3, 10));
		
		l4.setTranslateX(250);
		l4.setTranslateY(412);
		l4.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
		l4.setTextFill(Color.WHITE);
		l4.setBackground(new Background(new BackgroundFill(Color.web("#5F9EA0"), CornerRadii.EMPTY, Insets.EMPTY)));
		l4.setPadding(new Insets(3, 10, 3, 10));
		
		pane.getChildren().addAll(l1, l2, l3, l4);
	}

}
