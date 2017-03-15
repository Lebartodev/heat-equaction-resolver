package Model;

import org.junit.Test;

/**
 * Created by Александр on 15.03.2017.
 */
public class ImplictTest {
    @Test
    public void printMatrix() throws Exception {
        Implicit implict = new Implicit(20,20,1.2,0.1,0.1);
        implict.printMatrix();

    }

}