
public class DirectorsFilter implements Filter {
	private String myDirectors;
	
	public DirectorsFilter(String directors) {
		myDirectors = directors;
	}
	
	@Override
	public boolean satisfies(String id) {
		String directorArray[] = myDirectors.trim().split("\\s*,\\s*");
		for(String s: directorArray){
			if(MovieDatabase.getDirector(id).contains(s)){
				return true;
			}
		}

		return false;

		// return MovieDatabase.getGenres(id).contains(myDirectors);
	}

}
