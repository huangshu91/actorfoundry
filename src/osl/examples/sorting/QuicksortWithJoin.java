/**
 * Developed by: The Open Systems Lab
 *             University of Illinois at Urbana-Champaign
 *             Department of Computer Science
 *             Urbana, IL 61801
 *             http://osl.cs.uiuc.edu
 *
 * Contact: http://osl.cs.uiuc.edu/af
 *
 * Copyright (c) 1998-2009
 * The University of Illinois Board of Trustees.
 *    All Rights Reserved.
 * 
 * Distributed under license: http://osl.cs.uiuc.edu/af/LICENSE
 * 
 */
package osl.examples.sorting;

import java.util.Random;

import osl.examples.join.CountCustomer;
import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * @author Rajesh Karmani <rkumar8@cs.uiuc.edu>
 *
 */
public class QuicksortWithJoin extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5021339002556170557L;

	private Random generator = new Random(999999);

	@message
	public void boot(Integer size) throws RemoteCodeException {
		int A[] = new int[size];
		for (int i = 0; i < size; i++)
			A[i] = generator.nextInt(1000000);

		System.out.println("Before sorting: ");
		for (int i = 0; i < size; i++)
			System.out.print(A[i] + " ");
		System.out.println();

		ActorName sorter = create(QuicksortWithJoin.class);

		callByRef(sorter, "sort", A, 0, size - 1);

		System.out.println("After sorting: ");
		for (int i = 0; i < size; i++) {
			if (i > 0 && A[i - 1] > A[i]) {
				System.out.println("Error!!!");
				break;
			}
			System.out.print(A[i] + " ");
		}
		System.out.println();
	}

	@message
	public void sort(int A[], Integer f, Integer l) throws RemoteCodeException {
		if (f >= l)
			return;
		int pivot_index = partition(A, f, l);
		ActorName sorter1 = create(QuicksortWithJoin.class);
		ActorName sorter2 = create(QuicksortWithJoin.class);

		join(CountCustomer.class, msgByRef(sorter1, "sort", A, f,
				pivot_index - 1), msgByRef(sorter2, "sort", A, pivot_index + 1,
				l));
	}

	private int partition(int A[], int f, int l) {
		int pivot = A[f];
		int low = f + 1;

		for (int i = low; i <= l; i++) {
			if (A[i] <= pivot) {
				swap(A, i, low);
				low++;
			}
		}
		swap(A, f, low - 1);
		return low - 1;
	}

	private void swap(int A[], int x, int y) {
		if (x == y)
			return;
		int temp = A[x];
		A[x] = A[y];
		A[y] = temp;
	}

}
