import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Klasa reprezentujaca pojedynczy kafelek symulacji.
 * Dziedziczy po JPanel, co pozwala na rysowanie grafiki.
 * Implementuje interfejs Runnable, aby kazdy kafelek dzialal jako niezalezny watek.
 */
public class SimulationCell extends JPanel implements Runnable{

    /** Bazowy czas opoznienia watku w milisekundach. */
	private int k;
	
	/** Prawdopodobienstwo wylosowania wlasnego koloru. */
	private double p;
	
	/** Referencja do glownej planszy symulacji. */
	private SimulationCell[][] board;
	
	/** Flaga okreslajaca, czy watek kafelka jest obecnie zapauzowany. */
	private boolean isPaused = false;

	/** Wspoldzielony generator liczb losowych. */
	private final Random random;
	
	/** Wspolrzedna X kafelka w tablicy (wiersz). */
	private final int x;
	
	/** Wspolrzedna Y kafelka w tablicy (kolumna). */
	private final int y;
	
	/** Unikalny identyfikator kafelka do celow debugowania. */
	private final int id;

    /**
	 * Aktualizuje parametr czasu opoznienia.
	 * @param k Nowy czas w milisekundach.
	 */
    public void setK(int k){
        this.k=k;
    }

    /**
	 * Aktualizuje parametr prawdopodobienstwa.
	 * @param p Nowe prawdopodobienstwo (0.0 - 1.0).
	 */
    public void setP(double p){
        this.p=p;
    }

    /**
	 * Aktualizuje referencje do glownej tablicy (uzywane przy zmianie rozmiaru okna).
	 * @param board Nowa matryca kafelkow.
	 */
    public void setBoard(SimulationCell[][] board){
        this.board=board;
    }

    /**
	 * Konstruktor inicjalizujacy pojedynczy kafelek.
	 * @param k Bazowy czas opoznienia watku w milisekundach.
	 * @param p Prawdopodobienstwo wylosowania wlasnego koloru (od 0.0 do 1.0).
	 * @param random Wspoldzielony generator liczb losowych.
	 * @param x Wspolrzedna wiersza kafelka w glownej siatce.
	 * @param y Wspolrzedna kolumny kafelka w glownej siatce.
	 * @param board Dwuwymiarowa tablica przechowujaca referencje do wszystkich kafelkow na planszy.
	 * @param id Unikalny identyfikator kafelka wykorzystywany do wypisywania operacji.
	 */
    public SimulationCell(int k, double p, Random random, int x, int y, SimulationCell[][] board,int id){
        this.k=k;
		this.p=p;
		this.random=random;
		this.x=x;
		this.y=y;
		this.board=board;
		this.id=id;
        setupCell();
    }

    /**
	 * Nadaje kafelkowi calkowicie losowy kolor RGB.
	 */
    public synchronized void setRandomColor(){
        Color randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        setBackground(randomColor);
    }

    private void setupCell(){
        setRandomColor();
        this.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mousePressed(java.awt.event.MouseEvent e){
                togglePause();
            }
        });
    }
    
    /**
	 * Przelacza stan pauzy kafelka i wybudza watek, jesli pauza zostala wylaczona.
	 */
    public synchronized void togglePause(){
        isPaused=!isPaused;
        if(!isPaused){
            notify();
        }           
        repaint();
    }

    /**
	 * Sprawdza, czy kafelek jest aktualnie zapauzowany.
	 * @return true, jesli watek spi, false w przeciwnym wypadku.
	 */
    public synchronized boolean getIsPaused(){
        return isPaused;
    }

    /**
	 * Zwraca aktualny kolor tla kafelka.
	 * @return Obiekt Color reprezentujacy obecna barwe.
	 */
    public synchronized Color getColor(){
        return getBackground();
    }

    @Override
    protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(isPaused){
			g.setColor(Color.BLACK);
			g.drawLine(0,0,getWidth(),getHeight());
			g.drawLine(0,getHeight(),getWidth(),0);
		}
	}

    private void updateAverageColor(){
        System.out.println("Start " + id);
        int n = board.length;
        int m = board[0].length;

        SimulationCell up = board[(x-1+n)%n][y];
        SimulationCell down=board[(x+1)%n][y];
		SimulationCell left=board[x][(y-1+m)%m];
		SimulationCell right=board[x][(y+1)%m];

        int sumR=0,sumG=0,sumB=0,count=0;

		if(!up.getIsPaused()){
			Color c=up.getColor();
			sumR+=c.getRed();
			sumG+=c.getGreen();
			sumB+=c.getBlue();
			count++;
		}
		if(!down.getIsPaused()){
			Color c=down.getColor();
			sumR+=c.getRed();
			sumG+=c.getGreen();
			sumB+=c.getBlue();
			count++;
		}
		if(!left.getIsPaused()){
			Color c=left.getColor();
			sumR+=c.getRed();
			sumG+=c.getGreen();
			sumB+=c.getBlue();
			count++;
		}
		if(!right.getIsPaused()){
			Color c=right.getColor();
			sumR+=c.getRed();
			sumG+=c.getGreen();
			sumB+=c.getBlue();
			count++;
		}

		if(count>0){
			setBackground(new Color(sumR/count,sumG/count,sumB/count));
		}
		System.out.println("End: "+id);
    }

    /**
	 * Glowna petla zycia watku.
	 * Odpowiada za usypianie watku, losowanie prawdopodobienstwa i zmiane kolorow (wlasnych lub usrednionych).
	 * Obsluguje rowniez bezpieczne wstrzymywanie dzialania za pomoca mechanizmu wait/notify.
	 */
    @Override
    public void run(){
        while(true){
            try{
                synchronized(this){
                    while(isPaused){
                        wait();
                    }
                }
                int delay = (int) (0.5*k + random.nextInt(k+1));
                Thread.sleep(delay);
				if(getIsPaused()){
					continue;
				}

                double draw=random.nextDouble();
                if(draw<p){
                    setRandomColor();
                }
                else{
                    updateAverageColor();
                }
                repaint();
            }
            catch(InterruptedException e){
                break;
            }
        }
    }
}