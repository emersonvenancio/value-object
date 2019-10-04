package br.com.emerson.valueobject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ValueObjectTest {
	private static final int INT = 100000;

	@Test
	public void testApp() {
		List<Long> slowAlocation = new ArrayList<Long>();
		List<Long> fastAlocation = new ArrayList<Long>();
		List<Long> slowIteration = new ArrayList<Long>();
		List<Long> fastIteration = new ArrayList<Long>();
		for (int x = 0; x < 300; x++) {
			slowIterator(slowIteration,  slowAlocation(slowAlocation));
			System.gc();

			fastIterator( fastIteration, fastAlocation(fastAlocation));
			System.gc();
		}
		double slowAlocationAvg = slowAlocation.stream().mapToLong(l -> l)//
				.average().getAsDouble();
		double fastAlocationAvg = fastAlocation.stream().mapToLong(l -> l)//
				.average().getAsDouble();
		System.out.println("Slow Alocation: " + slowAlocationAvg + " Fast Alocation: " + fastAlocationAvg+" = "+(slowAlocationAvg / fastAlocationAvg));

		Assert.assertTrue((slowAlocationAvg / fastAlocationAvg) > 2.8);
		
		double slowIteratorAvg = slowIteration.stream().mapToLong(l -> l)//
				.average().getAsDouble();
		double fastIteratorAvg = fastIteration.stream().mapToLong(l -> l)//
				.average().getAsDouble();

		System.out.println("Slow Iterator: " + slowIteratorAvg + " Fast Iterator: " + fastIteratorAvg+" = "+(slowIteratorAvg / fastIteratorAvg));

		Assert.assertTrue((slowIteratorAvg / fastIteratorAvg) > 1.3);
	}

	private Fast fastAlocation(List<Long> fastTimes) {
		long init = System.nanoTime();
		Fast f = new Fast();
		for (int i = 0; i < INT; i++) {
			f.add(i, i);
		}
		fastTimes.add(System.nanoTime() - init);
		return f;
	}

	private void fastIterator(List<Long> fastIteration, Fast f) {
		long total = 0;
		long init = System.nanoTime();
		while (f.next()) {
			total = f.getX() + f.getY();
		}
		fastIteration.add(System.nanoTime() - init);
	}

	private ArrayList<Slow> slowAlocation(List<Long> slowTimes) {
		long init = System.nanoTime();
		ArrayList<Slow> list = new ArrayList<Slow>();
		for (int i = 0; i < INT; i++) {
			list.add(new Slow(i, i));
		}
		slowTimes.add(System.nanoTime() - init);
		return list;

	}

	private void slowIterator(List<Long> slowIteration, ArrayList<Slow> list) {
		long total = 0;
		long init = System.nanoTime();
		for (Slow slow : list) {
			total = slow.getX() + slow.getY();
		}
		slowIteration.add(System.nanoTime() - init);
	}
}
