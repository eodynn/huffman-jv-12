import java.util.HashMap;
import java.lang.Exception;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

public class HuffmanTable implements Serializable {

	private static final long serialVersionUID = -1492669811500767615L;
	HashMap<String, MapItem> map;
	int totalCount;

	public HuffmanTable() {
		map = new HashMap<String, MapItem>();
		totalCount = 0;
	}

	public void fillTable(String inc) {
		//Statistische Information aus dem eingehenden Text
		//werden in einer Tabelle gesammelt.
		for (int i = 0; i < inc.length(); i++) {
			String currentChar = String.valueOf(inc.charAt(i));
			if (map.get(currentChar) == null) {
				map.put(currentChar, new MapItem(1));
			} else {
				map.get(currentChar).incrementCount();
			}
			totalCount++;
		}
	}

	public void writeMapToFile(String filename) throws IOException {
		// Speichert map als eine Datei
		ObjectOutput out = new ObjectOutputStream(
				new FileOutputStream(filename));
		out.writeObject(this.map);
		out.close();
	}

	public void importMapFromFile(String filename) throws IOException,
			ClassNotFoundException {
		//Liest map aus einer Datei
		File file = new File(filename);
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		this.map = (HashMap<String, MapItem>) in.readObject();
		in.close();
	}

	public MapItem getMapItem(String s) throws Exception {
		if (map.get(s) == null) {
			throw new Exception("ERROR: Unkown Character");
		}
		MapItem mit = map.get(s);
		return mit;
	}

	public TNode addHuffmanCode() {

		HuffmanTreeBuilder tb = new HuffmanTreeBuilder(this);
		TNode tn = tb.buildHuffmanTree();
		Iterator<String> it = getKeys();
		
		// Weist allen MapItems das jeweilige Codewort zu.
		while (it.hasNext()) { 
			MapItem mi = map.get(it.next());
			mi.addCode();
		}
		return tn;
	}

	public Iterator<String> getKeys() {
		return this.map.keySet().iterator();
	}

	public int getTotalCount() {
		return totalCount;
	}

}
