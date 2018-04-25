package assignment7;

import java.io.File;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class cheaters implements Comparable<cheaters>{
	
	private File one;
	private File two;
	private int similarities = -1;
	
	/**
	 * Creates a cheaters object which acts as a pair of files and computes their similarity
	 * @param one File 1
	 * @param two File 2
	 */
	public cheaters(File one, File two) {
		this.one = one;
		this.two = two;
	}
	
	public void commonSequences(int seqLength) {
		
		
		
		
		
	}
	
	@Override
	public int compareTo(cheaters arg0) {
		if( this.similarities > arg0.similarities) {
			return -1;//we should be placed earlier in queue because we have more similarities
		}
		return 1;
	}
	
	
	public static File[] getFilesInPath(String path){
		ArrayList<File> fileNames = new ArrayList<File>();
		File f = new File(path);
		File files[] = f.listFiles();
		return files;
		
		
	}
	
	public static File openFile(String path) {
		
		
		
		return null;
		
	}
	
	
	
	public static void main(String args[]) {
		String filePath = args[0];
		int sequenceLength = Integer.parseInt(args[1]);
		File[] files;
		files = getFilesInPath(filePath);
		System.out.println(files);
		
		//Create all of our cheater pairs!
		//Everyone is a cheater, guilty until proven guilty.
		ArrayList<cheaters> cheaterList = new ArrayList<cheaters>();
		for(int i = 0; i < files.length-1; i++) {
			File one = files[i];
			for(int k = i+1; k < files.length; k++) {
				File two = files[k];
				cheaters newCheaters = new cheaters(one, two);

				cheaterList.add(newCheaters);
			}
		}
		
		//Ok now we have a list of our cheaters, all files paired up together...
		//now we just need to call their getSimilarities function with the passed sequence length.
		PriorityQueue<cheaters> sortedCheaters = new PriorityQueue<cheaters>();
		for(cheaters cheater : cheaterList) {
			cheater.commonSequences(sequenceLength);
			sortedCheaters.add(cheater);
		}
		
		System.out.println("hello");
	}

	
	
	
	

}
