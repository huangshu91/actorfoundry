package osl.examples.sor;

import java.util.Random;

public class SorUtil {
	public static Double[][] randomMatrix(int M, int N) {
		Double A[][] = new Double[M][N];

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				A[i][j] = R.nextDouble() * 1e-6;
			}
		return A;
	}

	static Random R = new Random(SorUtil.RANDOM_SEED);
	
	static final double REF_VAL[] = { 0.498574406322512, 1.1234778980135105,
			1.9954895063582696 };
	public static int DATA_SIZES[] = { 80, 1500, 2000 };
	public static final Integer JACOBI_NUM_ITER = 100;
	public static final Double OMEGA = 1.25;
	public static final long RANDOM_SEED = 10101010;

	public static void jgfValidate(double gTotal, int size) {
		double dev = Math.abs(gTotal - REF_VAL[size]);
		if (dev > 1.0e-12) {
			System.out.println("Validation failed");
			System.out.println("Gtotal = " + gTotal + "  " + dev + "  " + size);
		}
		else {
			System.out.println("Validation OK!");
		}
	}

}
