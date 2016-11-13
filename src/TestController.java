import Model.Point;
import Model.Solution;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр on 13.11.2016.
 */
public class TestController implements BaseController {
    private BaseMainView mainView;

    public TestController(BaseMainView mainView,double t) {
        this.mainView = mainView;
        initPoints(t);

    }
    private void initPoints(double t){
        Solution solution = new Solution(t);
        List<Point> points=new ArrayList<Point>();
        points = solution.getSolution();
        mainView.createGraph(points);
    }
    private double func(float i){
        return Math.sin(i);
    }
}
