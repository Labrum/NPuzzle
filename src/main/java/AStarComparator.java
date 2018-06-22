import java.util.Comparator;


public class AStarComparator implements Comparator<SearchNode> {

	@Override
	public int compare(SearchNode arg0, SearchNode arg1) {
		
		if(arg0.cost>arg1.cost){
			return 1;
		} else if (arg0.cost == arg1.cost){
			return 0;
		}
		
		return -1;
	}

}
