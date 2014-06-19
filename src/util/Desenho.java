package util;

import grafo.Estado;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import automato.Tela;

public class Desenho {

	final int D    = 24;
	final int BARB = 10;
	final double PHI = Math.toRadians(20);

	final static float ANGLE = .3f;

	final static int STROKE = 5;

	final static int RAIO = Tela.RAIO;

	//	final static int RAIO = Tela.RAIO;

	private static int yCor(int len, double dir){
		return (int)(len*Math.cos(dir));
	}

	private static int xCor(int len, double dir){
		return (int)(len*Math.sin(dir));
	}


	public static void drawArrow(Graphics2D g2d, int xCenter, int yCenter, int x, int y, int raio) {

		double aDir=Math.atan2(xCenter-x,yCenter-y);
		g2d.setStroke(new BasicStroke(1f));                   
		Polygon tmpPoly=new Polygon();
                   

		int i1=12+(int)(STROKE * 2);
		int i2=6+(int)STROKE;                           

		x = x+xCor(raio,aDir);
		y = y+yCor(raio,aDir);

		tmpPoly.addPoint(x,y);                           
		tmpPoly.addPoint(x+xCor(i1,aDir+ANGLE),y+yCor(i1,aDir+ANGLE));
		tmpPoly.addPoint(x+xCor(i2,aDir),y+yCor(i2,aDir));
		tmpPoly.addPoint(x+xCor(i1,aDir-ANGLE),y+yCor(i1,aDir-ANGLE));
		tmpPoly.addPoint(x,y);                          
		g2d.drawPolygon(tmpPoly);
		g2d.fillPolygon(tmpPoly);                      
	}

	public static void desenhaEstadoSelecionado(Graphics2D g2d, Estado v){
		g2d.setColor(new Color(220, 245, 255 ));
		g2d.fillOval(v.getPoint().x - RAIO, v.getPoint().y - RAIO, RAIO * 2 + 2, RAIO * 2+2);
		g2d.setColor(Color.BLACK);
		g2d.drawString(v.getNome(), v.getPoint().x - v.getNome().length()*3, v.getPoint().y+5);
		g2d.drawOval(v.getPoint().x - RAIO, v.getPoint().y - RAIO, RAIO * 2 + 2, RAIO * 2 + 2);
	}


	public static void salvarImagem(JPanel painel){
		Robot robot;
		try {
			robot = new Robot();
			Rectangle r =
				new Rectangle(painel.getLocationOnScreen(), new Dimension(painel.getWidth(),painel.getHeight()));
			BufferedImage imagem =robot.createScreenCapture(r);			
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileFilter() {

				@Override
				public String getDescription() {
					return "*.JPEG";
				}

				@Override
				public boolean accept(File arg0) {
					String name = 	arg0.getName().toLowerCase();
					return arg0.isDirectory() || name.endsWith(".jpg") || name.endsWith(".jpeg");
				}
			});
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int op = fc.showSaveDialog(null);

			File file = null;
			if(op ==0){

				file = fc.getSelectedFile();
				if(!file.getName().endsWith(".jpg") && !file.getName().endsWith(".jpeg"))
					file = new File(file.getAbsolutePath() + ".jpg");
				ImageIO.write(imagem, "JPEG",file);}
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
