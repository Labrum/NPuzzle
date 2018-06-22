
import java.util.Stack;


public class SearchNode {
	Board state = null;
	SearchNode parent = null;
	int action = -1;
	double cost = 0;
	int depth = 0;
	

	public SearchNode(Board board){
		this.state = board;
	}
	
	public Stack<SearchNode> getPath(){
		
		Stack<SearchNode> path = new Stack<SearchNode>();
		SearchNode current = this;
		path.push(this);
		while(current.parent!= null){
			current = current.parent;
			path.push(current);
		}
		
		return path;
	}
	
	public String toString(){
		return state.toString();
	}
	
	public boolean equals(Object o){
		
		if(o instanceof SearchNode){
			SearchNode searchO = (SearchNode) o;
			
			if(searchO.equals(this.state)){
				return true;
			}		
		}

		return false;
	}
	
	public int hashCode() {
	    return java.util.Arrays.deepHashCode( state.board );
	}
	
	public SearchNode(Board board, SearchNode parent, int action,double stepCost){
		this.state = board;
		this.parent = parent;
		this.action = action;
		this.depth = parent.depth+1;
		this.cost =  stepCost;
		
	}
	
	public static void main(String [] args){
		
	}
}
