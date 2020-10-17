import java.io.Serializable;

public class MapItem implements Serializable{
	

	int count;
	String code;
	TNode node;
	
	public MapItem(int count){
		this.count = count;
		code = "";
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	public void incrementCount(){
		this.count++;
	}
	
	public void setNode(TNode node){
		this.node = node;
	}

	public void addCode() {
		TNode tn = node;
		while(tn.parent != null){
			code = tn.codeBit + code;
			tn = tn.parent;
		}
	}
}
