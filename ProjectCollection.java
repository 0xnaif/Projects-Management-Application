import java.util.ArrayList;

public class ProjectCollection {

	private static ArrayList<Project> projects;

	public ProjectCollection() {
		projects = new ArrayList<Project>();
	}

	public static void addProjects(Project projcet) {
		projects.add(projcet);
	}

	public static ArrayList<Project> getProjects() {
		return projects;
	}
}
