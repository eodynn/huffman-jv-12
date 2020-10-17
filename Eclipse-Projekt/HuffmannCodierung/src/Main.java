import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collections;

public class Main {
	public static void printTableSorted(HuffmanTable huffmanTab) {
		LinkedList<SortNode> slist = new LinkedList<SortNode>();
		Iterator<String> it = huffmanTab.getKeys();
		while (it.hasNext()) {
			String key = it.next();
			MapItem mi = huffmanTab.map.get(key);
			SortNode sn = new SortNode(key, mi.code, mi.count);
			slist.add(sn);
		}
		Collections.sort(slist);
		Iterator<SortNode> slistIt = slist.iterator();
		while (slistIt.hasNext()) {
			SortNode sn = slistIt.next();
			String key = sn.key;
			char keychr = key.charAt(0);
			String kstr = Character.isISOControl(keychr) ? " " : key;
			System.out.println(kstr + "(" + Integer.toString(keychr) + "):\t"
					+ sn.code + ", c=" + Integer.toString(sn.count));
		}
	}

	private static String encode(HuffmanTable tab, String inp) {
		int slen = inp.length();
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < slen; j++) {
			String ch = String.valueOf(inp.charAt(j));
			MapItem mi = tab.map.get(ch);
			sb.append(mi.code);
		}
		return sb.toString();
	}

	private static String decode(TNode topnode, String encoded) {
		int j = 0;

		StringBuffer decoded = new StringBuffer();
		while (j < encoded.length()) {
			TNode current = topnode;
			while (current.key == null) {
				current = encoded.charAt(j++) == '0' ? current.left
						: current.right;
				// kompaktes if
			}
			decoded.append(current.key);
		}
		return decoded.toString();
	}

	private static void importMap(HuffmanTable huffmanTab)
			throws ClassNotFoundException {
		try {
			huffmanTab.importMapFromFile("german.map");
		} catch (IOException e) {
			System.out.println("Failed to write File!");
			e.printStackTrace();
		}
	}

	private static void writeCurrMap(HuffmanTable huffmanTab) {
		try { // write current map to file
			huffmanTab.writeMapToFile("german.map");
		} catch (IOException e) {
			System.out.println("Failed to write File!");
			e.printStackTrace();
		}
	}

	private static void verifyEncoding(StringBuffer sb, TNode topnode,
			String encoded) throws IOException {
		String decoded = decode(topnode, encoded);
		String Instr = sb.toString();
		if (Instr.length() != decoded.length()) {
			System.out.println("Original length:" + Instr.length());
			System.out.println("Decoded length:" + decoded.length());
			return;
		}
		int diffcount = 0;
		for (int j = 0; j < Instr.length(); j++) {
			if (Instr.charAt(j) != decoded.charAt(j)) {
				//Gibt Unterschiede zwischen Quell- und decodierter Ausgabedatei an
				if (diffcount < 10)
					System.out.println("Difference at position " + j);
				diffcount++;
				System.out.println("Differences found: " + diffcount);
			}
		}
		if (diffcount > 0)
			System.out.println("Error in Huffmann encoding/decoding.");
		else
			System.out
					.println("The input file and the decoded version are identical.");
	}

	private static void reportCompressionResult(StringBuffer sb, String encoded) {
		//Gibt Inhalt der Tabelle zusammen mit einem Vergleich der Dateigrößen aus
		int size_compressed = encoded.length();
		double p = size_compressed / (8.0 * sb.length());
		System.out.println("Original size  : " + 8 * sb.length() + " bits");
		DecimalFormat f = new DecimalFormat("##.00");
		System.out.println("Compressed size: " + size_compressed + " bits ("
				+ f.format(p * 100) + "%)");
	}

	public static void main(String[] args) throws Exception {
		
		String incfile = args[0];
		FileReader fr = new FileReader(incfile);
		BufferedReader br = new BufferedReader(fr);
		StringBuffer incString = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null)
			incString.append(line + "\r\n");
		
		HuffmanTable huffmanTab = new HuffmanTable();
		huffmanTab.fillTable(incString.toString());
		TNode topnode = huffmanTab.addHuffmanCode();

		printTableSorted(huffmanTab);
		String encoded = encode(huffmanTab, incString.toString());
		if (args.length == 2) {
			// Schreibt komprimierten Code in eine Text-Datei
			FileWriter fw = new FileWriter(args[1]);
			fw.write(encoded);
			fw.close();
		}
		reportCompressionResult(incString, encoded); 	// Gibt Ergebnis in Konsole aus.
		verifyEncoding(incString, topnode, encoded);
		// Zwecks Überprüfung wird der komprimierte Code dekodiert
		// und mit der Quelle verglichen.
	}

}
