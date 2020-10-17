import java.lang.Comparable;
import java.io.Serializable;

public class TNode implements Comparable<TNode> , Serializable
{
	
	TNode left;
	TNode right;
	TNode parent;
	int codeBit;
	String key;
	int countSum;
	
	public TNode(TNode left, TNode right, TNode parent, int codeBit, String key, int countSum){
		this.left=left;
		this.right=right;
		this.parent=parent;
		this.codeBit=codeBit;
		this.countSum=countSum;
		this.key=key;
	}
	
	public TNode(TNode left, TNode right, int countSum){
		this.left=left;
		this.right=right;
		this.countSum=countSum;
	}
	
	public TNode(String key){
		this.key=key;
	}
	
	public int compareTo(TNode other){
		if(countSum==other.countSum){
			return 0;
		}
		else if(countSum>other.countSum){
			return 1;
		}
		return -1;
	}
}
