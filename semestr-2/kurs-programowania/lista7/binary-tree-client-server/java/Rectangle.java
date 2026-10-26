/**
 * Reprezentuje prostokat, pozwala na porownywanie po polu powierzchni.
 */
public class Rectangle implements Comparable<Rectangle>{
	private double width;
	private double height;

	public Rectangle(double w, double h){
		width = w;
		height = h;
	}

	public double getArea(){
		return width * height;
	}

	@Override
	public int compareTo(Rectangle other){
		return Double.compare(this.getArea(), other.getArea());
	}

	@Override
	public String toString(){
		return width + "x" + height + "(" + getArea() + ")";
	}

	/**
	 * Tworzy obiekt Rectangle na podstawie tekstu.
	 * @param input Tekst w formacie np. "4x5".
	 * @return Nowy obiekt Rectangle.
	 */
	public static Rectangle parse(String input){
		String[] parts = input.toLowerCase().split("x");
		double w = Double.parseDouble(parts[0]);
		double h = Double.parseDouble(parts[1]);
		return new Rectangle(w, h);
	}
}