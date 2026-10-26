import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Glowna klasa aplikacji zarzadzajaca oknem symulacji, siatka kafelkow oraz calym interfejsem uzytkownika.
 * Przechowuje globalny stan programu i inicjalizuje poszczegolne watki.
 */
public class ColorBoard{

    static int n=10;
    static int m=15;
    static SimulationCell[][] board;
    static int idCounter=1;
    static boolean isRunning = false;

    /**
	 * Glowna metoda uruchamiajaca program.
	 * Pobiera od uzytkownika poczatkowe wymiary planszy (z okienek dialogowych), buduje interfejs graficzny
	 * w oparciu o biblioteke Swing i definiuje logike przyciskow oraz suwakow.
	 * @param args Argumenty wiersza polecen (nie sa aktywnie uzywane przez program).
	 */
    public static void main(String[] args){

        String nInput = JOptionPane.showInputDialog("Podaj liczbe wierszy (n): ");
        String mInput = JOptionPane.showInputDialog("Podaj liczbe kolumn (m): ");

        if(nInput!=null&&mInput!=null){
            try{
                n=Integer.parseInt(nInput);
                m=Integer.parseInt(mInput);
                if(n<0 || m<0){
                    JOptionPane.showMessageDialog(null,"Wymiary musza byc wieksze dodatnie!\nUzywam domyslnych 10x15.","Blad",JOptionPane.WARNING_MESSAGE);                    
                    n=10;
                    m=15;
                }
            }
            catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Niepoprawne dane!\nUzywam domyslnych 10x15.","Blad",JOptionPane.ERROR_MESSAGE);    
                n=10;
                m=15;
            }
        }
        
        int k=1000;
        double p=0.5;

        Random sharedRandom = new Random();
        board = new SimulationCell[n][m];

        SwingUtilities.invokeLater(() -> {
            
            JFrame frame = new JFrame("ColorBoard");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());

            JPanel gridPanel = new JPanel(new GridLayout(n,m));
            JPanel controlPanel = new JPanel();

            JSlider kSlider = new JSlider(100,2000,k);
            JSlider pSlider = new JSlider(0, 100, (int)(p*100));

            JButton startButton = new JButton("Start");
            JButton addRowBtn = new JButton ("+ Wiersz");
            JButton addColBtn = new JButton ("+ Kolumna");

            controlPanel.add(new JLabel("k (ms): "));
            controlPanel.add(kSlider);
            controlPanel.add(new JLabel("p (%):"));
			controlPanel.add(pSlider);
			controlPanel.add(startButton);
            controlPanel.add(addRowBtn);
            controlPanel.add(addColBtn);

            for(int i=0; i<n; i++){
                for(int j=0; j<m; j++){
                    board[i][j] = new SimulationCell(k,p,sharedRandom,i,j,board,idCounter++);
                    gridPanel.add(board[i][j]);
                }
            }

            kSlider.addChangeListener(e->{
				for(int i=0;i<n;i++){
					for(int j=0;j<m;j++){
						board[i][j].setK(kSlider.getValue());
					}
				}
			});

			pSlider.addChangeListener(e->{
				double newP=pSlider.getValue()/100.0;
				for(int i=0;i<n;i++){
					for(int j=0;j<m;j++){
						board[i][j].setP(newP);
					}
				}
			});

			startButton.addActionListener(e->{
                isRunning = true;
				startButton.setEnabled(false);
				for(int i=0;i<n;i++){
					for(int j=0;j<m;j++){
						Thread thread=new Thread(board[i][j]);
						thread.start();
					}
				}
			});

            addRowBtn.addActionListener(e->{
				n++;
				SimulationCell[][] newBoard=new SimulationCell[n][m];
				for(int i=0;i<n-1;i++){
					for(int j=0;j<m;j++){
						newBoard[i][j]=board[i][j];
						newBoard[i][j].setBoard(newBoard);
					}
				}
				for(int j=0;j<m;j++){
					newBoard[n-1][j]=new SimulationCell(kSlider.getValue(),pSlider.getValue()/100.0,sharedRandom,n-1,j,newBoard,idCounter++);
				}
				board=newBoard;
				updateGrid(gridPanel);
                if(isRunning){
                    for(int j=0;j<m;j++){
                        new Thread(board[n-1][j]).start();
                    }
                }
			});

			addColBtn.addActionListener(e->{
				m++;
				SimulationCell[][] newBoard=new SimulationCell[n][m];
				for(int i=0;i<n;i++){
					for(int j=0;j<m-1;j++){
						newBoard[i][j]=board[i][j];
						newBoard[i][j].setBoard(newBoard);
					}
					newBoard[i][m-1]=new SimulationCell(kSlider.getValue(),pSlider.getValue()/100.0,sharedRandom,i,m-1,newBoard,idCounter++);
				}
				board=newBoard;
				updateGrid(gridPanel);
                if(isRunning){
                    for(int i=0;i<n;i++){
                        new Thread(board[i][m-1]).start();
                    }
                }
			});

            frame.add(controlPanel,BorderLayout.NORTH);
			frame.add(gridPanel,BorderLayout.CENTER);
			frame.setVisible(true);
        });
    }
    
    /**
	 * Metoda narzedziowa odswiezajaca widok siatki w interfejsie graficznym.
	 * Jest wywolywana po kazdym dodaniu nowego wiersza lub kolumny w celu zaktualizowania okna.
	 * @param gridPanel Glowny panel przechowujacy wyswietlane kafelki.
	 */
    static void updateGrid(JPanel gridPanel){
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(n,m));
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                gridPanel.add(board[i][j]);
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }
}