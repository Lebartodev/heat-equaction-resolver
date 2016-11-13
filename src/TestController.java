import Model.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр on 13.11.2016.
 */
public class TestController implements BaseController {
    private BaseMainView mainView;

    public TestController(BaseMainView mainView) {
        this.mainView = mainView;
        initPoints();

    }
    private void initPoints(){
        List<Point> points=new ArrayList<Point>();
        for(float i=-10;i<10;i+=0.1){
            points.add(new Point(i,func(i)));
        }
        mainView.createGraph(points);
    }
    private double func(float i){
        return Math.sin(i);
    }
}
