package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 12.11.2016.
 */
public class Solution {

    public List<Point> solution;

    public static double getSum(double x){
        double eps = 0.0001;
        double res = 0;
        double temp;
        int k =1;
        do{
            temp = Coeffs.getAk(k)*Coeffs.getCosKX(k,x);
            res += temp;
            k++;
        }while (temp>eps);
        return res;
    }

    public Solution(double t){
        solution = new ArrayList<Point>();
        double a2 = Params.k/Params.c;
        double b2 = Params.alpha*2/(Params.c*Params.R);
        double i = -Math.PI*Params.R;
        double step = 2*Math.PI*Params.R/1000;
        for(double j = -i;j<=i;j+=step){
            double U = 1/2 + getSum(j)*Math.exp(-a2*Math.pow((Params.k/Params.R),2)*t)*Math.exp(-b2*t);
            Point p = new Point(j,U);
            solution.add(p);
        }
    }

    public List<Point> getSolution(){
        return solution;
    }
}
