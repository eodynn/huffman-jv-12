import javax.swing.JFrame;

public class GUI {
	public GUI(){
		JFrame f = new JFrame("Huffman-Codierung");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.setResizable(true);
        /* Field field = new Field();
        f.add(field);
        f.pack();  
        //causes this window to be sized to fit the preferred size (and layouts of its subcomponents)
        f.addKeyListener(field); 
        */
        f.setVisible(true);
	}
}
