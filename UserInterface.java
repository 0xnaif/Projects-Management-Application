import java.io.IOException;

public class UserInterface {
	private static ProjectManager prjectManger;
	
	public static void main(String[] args) throws IOException {
		prjectManger = new ProjectManager();
		
		prjectManger.read();
	}
}
