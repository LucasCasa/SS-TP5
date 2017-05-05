
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 3/11/17.
 */
public class Cell {
    double side;
    List<Particle> particleList = new ArrayList<Particle>();

    public Cell(double side){
        this.side = side;
    }

    public double getArea(){
        return side*side;
    }

    public double getSide(){
        return side;
    }


    public List<Particle> getParticleList(){
        return particleList;
    }


}
