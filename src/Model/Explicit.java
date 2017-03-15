package Model;

/**
 * Created by overl on 14.03.2017.
 */
public class Explicit {
    int i;
    int k;

    public double[][] getExplicitScheme(int i, int k, double R, int t, double a){
        double hX = (2 * Math.PI * R) / i;
        double hT = t / k;
        double[][] res = new double[i+1][k+1];

        for(int j = 0;j<i+1;i++)
            res[j][0] = getZeroFunc(j*hX, R);
        for(int l = 1;l<k;l++){
            for(int j = 0;j<i+1;j++){
                if(j==0){
                    res[0][l] = (a*a) * hT *(res[1][l-1] - 2*res[0][l-1] + res[i+1][l-1]) - hX * hX * res[0][l-1];
                }
                else if(j==i+1){
                    res[i+1][l] = (a*a) * hT *(res[0][l-1] - 2*res[i+1][l-1] + res[i][l-1]) - hX * hX * res[i+1][l-1];
                }
                else res[j][l] = (a*a) * hT *(res[j+1][l-1] - 2*res[j][l-1] + res[j-1][l-1]) - hX * hX * res[j][l-1];
            }
        }
    return res;
    }

    public double getZeroFunc(double x, double R){
        if((x>(Math.PI * R)/2) && (x<(Math.PI * R)/2))
            return 1;
        else
            return 0;
    }
}
