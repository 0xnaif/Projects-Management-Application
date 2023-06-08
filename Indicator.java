import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Indicator {
	
	public void indicator(int oldVlue, int newValue, Label stgaeDate, Label stageValue, Line stageValueline) {
		if (oldVlue < newValue) { 
			stgaeDate.setTextFill(Color.GREEN);
			stageValue.setTextFill(Color.GREEN);
			stageValueline.setStroke(Color.GREEN);
		} else {
			stgaeDate.setTextFill(Color.RED);
			stageValue.setTextFill(Color.RED);
			stageValueline.setStroke(Color.RED);
		}
	}
	
}
