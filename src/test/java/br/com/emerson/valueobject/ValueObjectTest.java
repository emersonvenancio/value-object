package br.com.emerson.valueobject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ValueObjectTest {
	private static final int INT = 100000;

	@Test
	public void testApp() {
		List<Long> slowAllocation = new ArrayList<Long>();
		List<Long> fastAllocation = new ArrayList<Long>();
		List<Long> slowIteration = new ArrayList<Long>();
		List<Long> fastIteration = new ArrayList<Long>();
		for (int x = 0; x < 200; x++) {
			slowIterator(slowIteration,  slowAllocation(slowAllocation));
			System.gc();

			fastIterator( fastIteration, fastAllocation(fastAllocation));
			System.gc();
		}
		double slowAllocationAvg = slowAllocation.stream().mapToLong(l -> l)//
				.average().getAsDouble();
		double fastAllocationAvg = fastAllocation.stream().mapToLong(l -> l)//
				.average().getAsDouble();
		System.out.println("Slow Allocation: " + slowAllocationAvg + " Fast Allocation: " + fastAllocationAvg+" = "+(slowAllocationAvg / fastAllocationAvg));

		Assert.assertTrue((slowAllocationAvg / fastAllocationAvg) > 2.0);
		
		double slowIteratorAvg = slowIteration.stream().mapToLong(l -> l)//
				.average().getAsDouble();
		double fastIteratorAvg = fastIteration.stream().mapToLong(l -> l)//
				.average().getAsDouble();

		System.out.println("Slow Iterator: " + slowIteratorAvg + " Fast Iterator: " + fastIteratorAvg+" = "+(slowIteratorAvg / fastIteratorAvg));

		Assert.assertTrue((slowIteratorAvg / fastIteratorAvg) > 3.0);
	}

	private Fast fastAllocation(List<Long> fastTimes) {
		long init = System.nanoTime();
		Fast f = new Fast(INT);
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

	private ArrayList<Slow> slowAllocation(List<Long> slowTimes) {
		long init = System.nanoTime();
		ArrayList<Slow> list = new ArrayList<Slow>(INT);
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
