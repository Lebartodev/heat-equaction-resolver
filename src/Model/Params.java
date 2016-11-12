package Model;

/**
 * Created by Home on 04.11.2016.
 */
public class Params {
    public static double c = 1.84;
    public static double alpha = 0.005;
    public static double k = 0.065;
    public static double R = 25;
    public static double Uenv = 0;
    public static double T = 50;

    public Params() {
    }

    public void setDefaults(){
        this.c = 1.84;
        this.alpha = 0.005;
        this.k = 0.065;
        this.R = 25;
        this.Uenv = 0;
        this.T = 50;
    }

    public static double getPsiX(double theta){
        if( (theta >= (-Math.PI/2)) && (theta <= (Math.PI/2)) )
            return 1;
        else return 0;
    }
}