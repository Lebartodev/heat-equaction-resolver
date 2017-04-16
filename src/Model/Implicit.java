package Model;

/**
 * Created by Александр on 14.03.2017.
 */
public class Implicit {
    private double[][] matrix;

    private double gamma(double a, double ht, double hx) {
        return a * a * ht / (hx * hx);

    }

    public Implicit(int I, int K, double a, double ht, double hx) {
        matrix = new double[I][K];
        //Заполнение матрицы
        initMatrix(I, K, a, ht, hx);


    }

    private void initMatrix(int I, int K, double a, double ht, double hx) {
        matrix[0][K - 1] = -gamma(a, ht, hx);
        matrix[I - 1][0] = -gamma(a, ht, hx);
        for (int i = 0; i < I; i++)
            for (int k = 0; k < K; k++) {
                {

                    if (i == k)
                        matrix[i][k] = 1 + 2 * gamma(a, ht, hx);
                    else if (i == k - 1)
                        matrix[i][k] = -gamma(a, ht, hx);
                    else if (i == k + 1)
                        matrix[i][k] = -gamma(a, ht, hx);
                    else
                        matrix[i][k] = 0;

                }
            }
    }

    /*private void neyavnaya(int I) {
        double[]alpha=new double[I-1];
        double[] beta=new double[I-1];
        for(int i =1;i<I;i++){
            alpha[i]=-matrix[i][i+1]/();

        }


        double[] x = new double[I];
        double m;


    }*/

    public void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[0].length; k++) {
                String str = String.format("%2.1f", matrix[i][k]);
                System.out.print(str + " ");
            }
            System.out.println();
        }

    }
}
