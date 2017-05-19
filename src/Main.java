import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcasagrande on 05/05/17.
 */
public class Main {

    public static void main(String[] args)  throws IOException{
        Generator g = new Generator();
        double width = 1;
        double height = 1.1;
        double open = 0.2;
        double dMin = open/7;
        double dMax = open/5;
        double kT = 2*100000;
        double kN = 100000;
        double mass = 0.01;
        double v = 0;

        List<Particle> p= g.generateParticles(width,height,326,(dMin/2.0),(dMax/2.0),v,mass);
        System.out.println(p.size());

        Simulation s = new Simulation(p,width,height,open,kT,kN);
        s.simulate(30);
    }
}
