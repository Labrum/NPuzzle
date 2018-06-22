import java.util.Comparator;


public class Fifo implements Comparator<SearchNode> {

	@Override
	public int compare(SearchNode arg0, SearchNode arg1) {
		
		if(arg0.depth>arg1.depth){
			return 1;
		}else if(arg0.depth==arg1.depth){
			return 0;
		}
		
		return -1;
	}

}
