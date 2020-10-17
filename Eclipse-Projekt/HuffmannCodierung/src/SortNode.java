import java.lang.Comparable;

public class SortNode implements Comparable<SortNode> {
	String key;
	int count;
	String code;

	public SortNode(String key, String code, int count) {
		this.key = key;
		this.code = code;
		this.count = count;
	}
	
	public int compareTo(SortNode other) {
		if (count == other.count) {
			if (this.code.length() < other.code.length())
				return -1;
			else if (this.code.length() == other.code.length())
				return 0;
			return 1;
		} else if (count > other.count) {
			return -1;
		}
		return 1;
	}
}
