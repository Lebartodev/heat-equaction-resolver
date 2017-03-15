package Model;

/**
 * Created by Александр on 14.03.2017.
 */
public class Implicit {
    private double[][] matrix;

    private double sigma(double a, double ht, double hx) {
        return a * a * ht / (hx * hx);

    }

    public Implicit(int I, int K, double a, double ht, double hx) {
        matrix = new double[I][K];
        //Заполнение матрицы
        for (int i = 0; i < I; i++)
            for (int k = 0; k < K; k++) {
                {
                    matrix[0][K-1] = -sigma(a, ht, hx);
                    matrix[I-1][0] = -sigma(a, ht, hx);
                    if (i == k)
                        matrix[i][k] = 1 + 2 * sigma(a, ht, hx);
                    else
                    if (i == k - 1)
                        matrix[i][k] = -sigma(a, ht, hx);
                    else
                    if (i == k + 1)
                        matrix[i][k] = -sigma(a, ht, hx);
                    else
                        matrix[i][k]=0;

                }
            }

    }
    public void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[0].length; k++) {
                String str = String.format("%2.1f", matrix[i][k] );
                System.out.print(str+" ");
            }
            System.out.println();
        }

    }
}
