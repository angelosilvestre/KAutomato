package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import automato.AutomatoFinito;

public class AutomatoIO {
	private static File file = null;

	private static void getFile(boolean save){
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "*.automato";
			}

			@Override
			public boolean accept(File arg0) {
				String name = 	arg0.getName().toLowerCase();
				return arg0.isDirectory() || name.endsWith(".automato") || name.endsWith(".grafo");
			}
		});
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int op ;
		if(save)
			op = fc.showSaveDialog(null);
		else 
			op = fc.showOpenDialog(null);

		if(op ==0)
			file = fc.getSelectedFile();

	}


	public static AutomatoFinito importaAutomato(){
		getFile(false);
		try {
			if(file != null){
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
				AutomatoFinito a = (AutomatoFinito) in.readObject();
				return a;}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static void salvaAutomato(AutomatoFinito a){		
		getFile(true);
		if(file != null){
			if(!file.getName().endsWith(".automato"))
				file = new File(file.getAbsolutePath()+".automato");
			try {
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
				out.writeObject(a);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Arquivo não Encontrado");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}