package osl.examples.sorting;

import java.util.Random;
import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class QuicksortCopy extends Actor {
	private static final long serialVersionUID = -6367617467463969386L;

	private Random generator = new Random(999999);
	private ActorName parent = null;
	private int[] myPortion;
	private int myPivot = -1;
	private int responseCount = 0;

	@message
	public void boot(Integer size) throws RemoteCodeException {
		int A[] = new int[size];
		for (int i = 0; i < size; i++)
			A[i] = generator.nextInt(1000000);

		System.out.println("Before sorting: ");
		for (int i = 0; i < size; i++)
			System.out.print(A[i] + " ");
		System.out.println();

		ActorName sorter = create(QuicksortCopy.class);
		send(sorter, "sort", A, self());
	}

	@message
	public void sort(int[] A, ActorName parent) throws RemoteCodeException {
		if (A.length == 0) {
			send(parent, "sorted", A);
			return;
		}

		this.parent = parent;
		this.myPortion = A;
		int f = 0, l = A.length - 1;
		this.myPivot = partition(A, f, l);

		// make copies of two arrays
		int[] B = new int[myPivot - f], C = new int[l - myPivot];
		for (int i = f; i < myPivot; i++)
			B[i - f] = A[i];
		for (int i = myPivot + 1; i <= l; i++)
			C[i - myPivot - 1] = A[i];

		ActorName sorter1 = create(QuicksortCopy.class);
		ActorName sorter2 = create(QuicksortCopy.class);
		send(sorter1, "sort", B, self());
		send(sorter2, "sort", C, self());
	}

	@message
	public void sorted(int[] A) {
		if (parent != null) {
			responseCount++;

			if (A.length > 0) {
				if (A[0] <= myPortion[myPivot]) { // left side
					for (int i = 0; i < myPivot; i++)
						myPortion[i] = A[i];
				} else { // right side
					for (int i = myPivot + 1; i <= myPortion.length - 1; i++)
						myPortion[i] = A[i - myPivot - 1];
				}
			}

			if (responseCount == 2)
				send(parent, "sorted", myPortion);
		} else {
			System.out.println("After sorting: ");
			for (int i = 0; i < A.length; i++) {
				if (i > 0 && A[i - 1] > A[i]) {
					System.out.println("Error!!!");
					break;
				}
				System.out.print(A[i] + " ");
			}
			System.out.println();
		}
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
