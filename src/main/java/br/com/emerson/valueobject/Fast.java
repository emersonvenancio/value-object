package br.com.emerson.valueobject;

import java.util.Arrays;
/**
 * 
 * @author emers
 *
 */
public class Fast {

	private int[] x;
	private int[] y;
	private int freeIndex = -1;
	private int pointer = -1;

	public Fast() {
		this(15);
	}

	public Fast(int size) {
		x = new int[size];
		y = new int[size];
	}

	public int add(int x, int y) {
		if (this.x.length -1 == freeIndex) {
			grow();
		}
		this.x[++freeIndex] = x;
		this.y[freeIndex] = y;
		return freeIndex;
	}

	public int getX() {
		return x[pointer];
	}

	public void setX(int x) {
		this.x[pointer] = x;
	}

	public int getY() {
		return y[pointer];
	}

	public void setY(int y) {
		this.y[pointer] = y;
	}

	private void grow() {
		x = Arrays.copyOf(x, (int) (x.length * 1.5));
		y = Arrays.copyOf(y, (int) (y.length * 1.5));
	}

	public boolean next() {
		if (pointer < freeIndex) {
			pointer++;
			return true;
		}
		return false;
	}

	public void reset() {
		pointer = -1;
	}

}
