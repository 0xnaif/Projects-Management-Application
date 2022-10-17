import java.io.IOException;

public class UserInterface {
	private static ProjectManager projectManager = new ProjectManager();
	
	public static void main(String[] args) throws IOException {
		projectManager.read();

	}
}
