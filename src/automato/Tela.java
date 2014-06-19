package automato;

import grafo.Aresta;
import grafo.Estado;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.LinkedList;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import util.AutomatoIO;
import util.Desenho;
import util.Otimizador;





public class Tela extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel area;
	public static AutomatoFinito automato;

	boolean vertice = true, primeiro = true,moverVertice = false, aresta = false;
	public Point inicio;
	public Point fim;
	private JPopupMenu popup;
	private JMenuItem removerEstado;
	public static final int RAIO = 15;
	@SuppressWarnings("unused")
	private Point2D p1, p2;
	private JCheckBox reconhecedor;
	private Estado origem, destino,selecionado;
	private JToolBar toolbar;
	private JButton bVertice, bAresta, bNovo,bMover,bRemoverInalcansaveis, bRemoverMortos, bVerifica,bExportaGrafo, bExportaImg, bImportaGrafo;
	private JPanel painel;
	private LinkedList<Aresta> desenhadas;
	private Cursor cVertice, cAresta, cMove;
	private JTextField tVerifica;


	public static void main(String[] args) {
		new Tela();
	}



	public Tela(){		
		super("KAutômato");
		initialize();	


		setContentPane(painel);	
		setSize(850, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void adicionaListeners(){

		bNovo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				automato = new AutomatoFinito();
				repaint();
			}
		});
		
		bVertice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				vertice = true;
				aresta = false;
				area.setCursor(cVertice);
			}
		});

		reconhecedor.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				selecionado.setReconhecedor(reconhecedor.isSelected());
				if(reconhecedor.isSelected())
					automato.getEstadosReconhecedores().add(selecionado);				
				repaint();
			}

		});


		bExportaImg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Desenho.salvarImagem(area);			
				repaint();

			}
		});


		bAresta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vertice = false;		
				moverVertice =false;
				primeiro = true;
				aresta = true;
				area.setCursor(cAresta);
			}
		});

		bVerifica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				verificarCadeia();

			}
		});

		tVerifica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				verificarCadeia();

			}
		});
		
		bRemoverInalcansaveis.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent arg0) {
				Otimizador.removeEstadosInalcansaveis(automato);	
				repaint();
			}
		});


		bRemoverMortos.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent arg0) {
				Otimizador.removeEstadosMortos(automato);
				repaint();
			}
		});


		bMover.addActionListener(new ActionListener(
		) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				primeiro = vertice = false;
				moverVertice = true;
				aresta = false;
				area.setCursor(cMove);
			}
		});

		
		removerEstado.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				automato.removeEstado(selecionado);
				selecionado = null;
				//				atualizaTela();
				repaint();				
			}


		});

		bExportaGrafo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AutomatoIO.salvaAutomato(automato);				
			}
		});

		bImportaGrafo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				automato = AutomatoIO.importaAutomato();
				repaint();

			}
		});


		

		
	}


	private void verificarCadeia(){
		if(automato.possuiReconhecedor()){
			String texto = tVerifica.getText();
			try{
				if(texto.length()>0){
					boolean ok =automato.verificaCadeia(texto);
					JOptionPane.showMessageDialog(null, ok ? "Cadeia Válida" : "Cadeia Inválida");
					tVerifica.setText(null);
				}}
			catch(NullPointerException e){
				System.out.println(e);
			}
		}
	}

	private void initialize(){	
		automato = new AutomatoFinito();
	
		painel = new JPanel(new BorderLayout(5,5));		
		removerEstado = new JMenuItem("Remover Estado");
		toolbar = new JToolBar();

		bNovo = new JButton(new ImageIcon((ClassLoader.getSystemResource("img/novo.png"))));
		bVertice = new JButton(new ImageIcon(ClassLoader.getSystemResource("img/circle.png")));
		bAresta = new JButton(new ImageIcon(ClassLoader.getSystemResource("img/aresta.png")));
		bMover = new JButton(new ImageIcon(ClassLoader.getSystemResource("img/move.png")));
		bRemoverInalcansaveis = new JButton(new ImageIcon(ClassLoader.getSystemResource("img/find.png")));
		bRemoverMortos = new JButton(new ImageIcon(ClassLoader.getSystemResource("img/skull.PNG")));
		bExportaGrafo = new JButton(new ImageIcon(ClassLoader.getSystemResource("img/export.png")));
		bExportaImg = new JButton(new ImageIcon(ClassLoader.getSystemResource("img/image.png")));
		bImportaGrafo = new JButton(new ImageIcon(ClassLoader.getSystemResource("img/import.png")));
		tVerifica = new JTextField(30);

		bVerifica = new JButton("OK");

		toolbar.add(bNovo);
		toolbar.add(bVertice);
		toolbar.add(bAresta);			
		toolbar.addSeparator();		
		toolbar.add(bMover);
		toolbar.add(bRemoverInalcansaveis);
		toolbar.add(bRemoverMortos);
		toolbar.addSeparator();
		toolbar.add(bImportaGrafo);
		toolbar.add(bExportaGrafo);
		toolbar.add(bExportaImg);
		toolbar.addSeparator();


		
		
		JPanel painelVerifica = new JPanel();
		painel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		painelVerifica.add(new JLabel("Verificar Cadeia"));
		painelVerifica.add(tVerifica);
		painelVerifica.add(bVerifica);

		painel.add(painelVerifica,BorderLayout.SOUTH);

		bNovo.setToolTipText("Novo Autômato");
		bVertice.setToolTipText("Adicionar Estado");
		bAresta.setToolTipText("Adicionar Transição");
		bMover.setToolTipText("Mover");
		bRemoverInalcansaveis.setToolTipText("Remover Estados Inalcansáveis");
		bRemoverMortos.setToolTipText("Remover Estados Mortos");
		bExportaImg.setToolTipText("Exportar como imagem");
		bImportaGrafo.setToolTipText("Importar Autômato");
		bExportaGrafo.setToolTipText("Exportar Autômato");




		popup = new JPopupMenu();
		reconhecedor = new JCheckBox("Reconhecedor");
		popup.add(reconhecedor);
		popup.add(removerEstado);

		Image im;
		im = new ImageIcon(ClassLoader.getSystemResource("img/cursorvertice.png")).getImage();
		cVertice = Toolkit.getDefaultToolkit().createCustomCursor(im, new Point(0,0), "ADDVERTICE")  ;
		im = new ImageIcon(ClassLoader.getSystemResource("img/aresta.gif")).getImage();
		cAresta = Toolkit.getDefaultToolkit().createCustomCursor(im, new Point(7,0), "ADDARESTA")  ;
		im = new ImageIcon(ClassLoader.getSystemResource("img/cursormove.png")).getImage();
		cMove = Toolkit.getDefaultToolkit().createCustomCursor(im, new Point(5, 5), "MOVE");



	
		area = new JPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g){
				super.paintComponent(g);

				Graphics2D g2d = (Graphics2D) g.create();

				g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, 
						RenderingHints.VALUE_ANTIALIAS_ON );
				desenhadas = new LinkedList<Aresta>();


				for(Aresta a: automato.getArestas()){       

					String entradas = "";
					for(char c: a.getEntradas())
						entradas+=c;
					if(a.getFirst() != a.getLast()){
						int x1 = a.getFirst().getPoint().x;
						int x2 = a.getLast().getPoint().x;
						int y1 = a.getFirst().getPoint().y;
						int y2 = a.getLast().getPoint().y;						



						if(a.possuiArestaConcorrente(automato) && desenhadas.contains(a.getConcorrente())){	

							int xCentro = (x1+x2)/2;
							int yCentro = (y1+y2)/2 -50;
							QuadCurve2D q = new QuadCurve2D.Float();							
							q.setCurve(x1 ,y1 , xCentro, yCentro -50, x2,y2);						
							g2d.draw(q);
							Desenho.drawArrow(g2d, (x1+x2)/2,(int) q.getCtrlPt().getY() , x2,y2, RAIO);
							desenhadas.add(a);
							g2d.drawString(entradas+"", (a.getFirst().getPoint().x + a.getLast().getPoint().x)/2 - entradas.length()*3, (a.getFirst().getPoint().y + a.getLast().getPoint().y)/2-60) ;

						}else{
							g2d.draw( new Line2D.Double(a.getFirst().getPoint().x , a.getFirst().getPoint().y , a.getLast().getPoint().x, a.getLast().getPoint().y));
							Desenho.drawArrow(g2d, x1,y1  , x2,y2, RAIO);
							desenhadas.add(a);
							g2d.drawString(entradas+"", (a.getFirst().getPoint().x + a.getLast().getPoint().x)/2 - entradas.length()*3, (a.getFirst().getPoint().y + a.getLast().getPoint().y)/2-10) ;

						}







					}
					else{
						g2d.draw( new Ellipse2D.Double(
								a.getFirst().getPoint().x  ,a.getFirst().getPoint().y-25, 30, 30 ) );
						g2d.drawString(entradas+"", (a.getFirst().getPoint().x  - entradas.length()*3), a.getFirst().getPoint().y - RAIO-7) ;

					}


				}

				for(Estado v: automato.getEstados()){		
					g2d.setColor(Color.WHITE);				
					g2d.fillOval(v.getPoint().x - RAIO, v.getPoint().y - RAIO, RAIO * 2, RAIO * 2);
					g2d.setColor(Color.BLACK);
					g2d.drawString(v.getNome(), v.getPoint().x - v.getNome().length()*3, v.getPoint().y+5);
					g2d.drawOval(v.getPoint().x - RAIO, v.getPoint().y - RAIO, RAIO * 2, RAIO * 2);
					if(v.isReconhecedor())
						g2d.drawOval(v.getPoint().x - RAIO + 3, v.getPoint().y - RAIO + 3, RAIO * 2 - 5, RAIO * 2 - 5);
					if(automato.getEstados().getFirst() == v){
						int inicioSeta = v.getPoint().x - RAIO-1;
						int[] xValues = new int[]{inicioSeta,inicioSeta - 10, inicioSeta - 10};
						int[] yValues = new int[]{v.getPoint().y,v.getPoint().y -10, v.getPoint().y + 10};
						g2d.drawPolygon(xValues, yValues,3);
					}
					if(selecionado != null)
						Desenho.desenhaEstadoSelecionado(g2d, selecionado);

				}
				
				boolean f = automato.possuiReconhecedor();
				bVerifica.setEnabled(f);
				tVerifica.setEnabled(f);
				
			
				bVerifica.setToolTipText(f?"Verificar Cadeia" : "Necessário ao menos um estado reconhecedor para verificar uma cadeia");
				g2d.dispose();
			}

		}

		;

		area.setBackground(Color.WHITE);	

		area.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		area.setCursor(cVertice);



		painel.add(toolbar,BorderLayout.NORTH);
		JScrollPane js = new JScrollPane(area);
		js.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//painel.add(area,BorderLayout.CENTER);
		painel.add(js,BorderLayout.CENTER);

		adicionaListeners();	
		adicionaMouseListener();

	}


	public  void salvarTela(){
		//Desenho.salvarImagem(this);
	}


	private void adicionaMouseListener(){

		area.addMouseListener(new MouseAdapter() {	

			public void mousePressed(MouseEvent e){
				if(moverVertice){					
					inicio = e.getPoint();	
					selecionado = automato.getEstadoAt(inicio);	
					primeiro = false;
					checkPopup(e);					
					repaint();
				} else if(aresta && e.getButton() == MouseEvent.BUTTON1){
					//					if(primeiro){
					inicio = e.getPoint();	
					origem = automato.getEstadoAt(inicio);
					selecionado = origem;
					p1 = (Point2D) inicio.clone();
					if(origem != null)
						primeiro = false;
					
					repaint();
					//}
				}

			}



			public void mouseReleased(MouseEvent e){

				if(moverVertice){
					checkPopup(e);

					if(!popup.isShowing()){
						selecionado =  null;
						inicio = null;
						repaint();
					}
				} else if(aresta && selecionado != null){
					fim = e.getPoint();	
					p2 = (Point2D) fim.clone();
					//System.out.println((int)p1.distance(p2));
					destino = automato.getEstadoAt(fim);
					if(destino != null){
						try{
							String peso = JOptionPane.showInputDialog("Peso da Aresta");
							if(peso != null){
								if(peso.length() == 0 || peso.startsWith(" "))
									throw new IllegalArgumentException();
								StringTokenizer st = new StringTokenizer(peso,"..");
								if(st.countTokens() == 2){
									char a = st.nextToken().charAt(0);
									char b = st.nextToken().charAt(0);
									String aux = "";
									for(char caracter = a; caracter <= b;caracter++){
										aux+= caracter;										
									}
									peso = aux;									
								}
								LinkedList<Character> caracteres = new LinkedList<Character>();
								for(int i = 0; i< peso.length(); i++){
									Character a = peso.charAt(i);
									if(!caracteres.contains(a))
										caracteres.add(a);
								}
								boolean arestaExistente = false;;
								Aresta nova = new Aresta(origem, destino,caracteres);
								for(Aresta a: automato.getArestas()){
									if(a.equals(nova)){
										a.addEntradas(nova.getEntradas());
										nova = null;
										arestaExistente = true;
										break;
									}								
								}
								if(!arestaExistente)
									automato.addAresta(origem, destino,caracteres);
								//automato.addAresta(origem, destino,new char[]{' '});
								primeiro = true;	
								selecionado = null;						
								repaint();		
							}
						}catch(IllegalArgumentException n){
							JOptionPane.showMessageDialog(null, "Valor Inválido");
							origem = destino = null;
							primeiro = true;
						}
					}
					selecionado =  null;
					inicio = null;
					//					atualizaTela();
					repaint();

				}


			}
			private void checkPopup(MouseEvent e){
				if(e.isPopupTrigger() && moverVertice && selecionado !=null){					
					reconhecedor.setSelected(selecionado.isReconhecedor());
					popup.show(e.getComponent(),e.getX(), e.getY());
				}
			}

			public void mouseClicked(MouseEvent e){	
				if(moverVertice && e.getClickCount() == 2 &&  e.getButton() == 1){					
					selecionado = automato.getEstadoAt(e.getPoint());
					if(selecionado != null){
						String nome = JOptionPane.showInputDialog(null, "Nome do Vértice");
						if(nome != null && nome.length() > 0)
							selecionado.setNome(nome);
					}
					repaint();
				}

				if(e.getButton() == 3){
					inicio = e.getPoint();
					selecionado = automato.getEstadoAt(inicio);
					



				}else{
					if(vertice){
						Point p = e.getPoint();
						automato.addEstado(new Estado("E" + (automato.getEstados().size()+ 1), p));
						area.repaint();	
					}

				}
				//				atualizaTela();
			}

		});		


		area.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDragged(MouseEvent arg0) {	
				if(!moverVertice){				
					if(aresta && origem != null){	
						repaint();
						Graphics2D g2d = (Graphics2D) area.getGraphics();
						g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, 
								RenderingHints.VALUE_ANTIALIAS_ON );
						g2d.setStroke(new BasicStroke(1.3f));						
						g2d.draw( new Line2D.Double(selecionado.getPoint().x, selecionado.getPoint().y, arg0.getX(), arg0.getY()));			
						Desenho.drawArrow(g2d, selecionado.getPoint().x, selecionado.getPoint().y, arg0.getX(), arg0.getY(), 0);


					}
				}
				else if(selecionado != null  ){
					selecionado.setPoint(arg0.getPoint());
					repaint();
				}

			}

		});



	}

}

