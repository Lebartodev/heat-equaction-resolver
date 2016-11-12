package Model;

/**
 * Created by Home on 12.11.2016.
 */
public class Coeffs {

    public static double getA0(){
        double res = 1/(Math.PI*Params.R)*(1-Params.Uenv)*Math.PI*Params.R;
        return res;
    }

    public static double getAk(int k){
        double res = getA0()*getCosPiKX(k);
        return res;
    }

    public static double getCosPiKX(int k){
        double step = Math.PI*Params.R/1000;
        double res = 0;
        for(double i = -Math.PI*Params.R/2;i<Math.PI*Params.R/2;i+=step){
            res+= Math.cos(i*k/Params.R);
        }
        res*=Math.PI*Params.R/1000;
        return res;
    }

    public static double getSinPiKX(int k){
        double step = Math.PI*Params.R/1000;
        double res = 0;
        for(double i = -Math.PI*Params.R/2;i<Math.PI*Params.R/2;i+=step){
            res+= Math.sin(i*k/Params.R);
        }
        res*=Math.PI*Params.R/1000;
        return res;
    }

    public static double getBk(int k){
        double res = getA0()*getSinPiKX(k);
        return res;
    }
}
