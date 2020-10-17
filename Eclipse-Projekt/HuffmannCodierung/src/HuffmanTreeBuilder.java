import java.util.LinkedList;
import java.util.Iterator;
import java.util.Collections;

public class HuffmanTreeBuilder {
	
LinkedList<TNode> nodeList;
	
	public HuffmanTreeBuilder(HuffmanTable tab){
		nodeList = new LinkedList<TNode>();
		Iterator<String> it = tab.getKeys();
		
		//Erzeugt Wald aus Knoten anhand der HuffmanTable
		while(it.hasNext()){
			String key = it.next();
			TNode node = new TNode(key);
			MapItem mi = tab.map.get(key);
			mi.setNode(node);
			node.countSum = mi.count;
			nodeList.add(node);
		}
	}
	
	
	public TNode buildHuffmanTree(){
		TNode tn = null;
		while(nodeList.size() >= 2){
			tn = joinLowestNodes();
		}
		return tn;
	}

	public TNode joinLowestNodes(){
		Collections.sort(nodeList);
		TNode node1 = nodeList.removeFirst();
		TNode node2 = nodeList.removeFirst();
		TNode newNode = new TNode(node1, node2, node1.countSum + node2.countSum);
		node1.codeBit = 0;
		node2.codeBit = 1;
		node1.parent=node2.parent=newNode;
		nodeList.add(newNode);
		return newNode;
	}

}
