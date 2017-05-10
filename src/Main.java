import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by lcasagrande on 05/05/17.
 */
public class Main {

    public static void main(String[] args)  throws IOException{
        Generator g = new Generator();
        double width = 1;
        double height = 3;
        double open = 0.2;
        double rMin = open/7;
        double rMax = open/5;
        double kT = 2;
        double kN = 10;
        double mass = 0.01;
        double v = 0;
        List<Particle> p= g.generateParticles(width,height,Integer.MAX_VALUE,rMin,rMax,v,mass);
        System.out.println(p.size());
        Simulation s = new Simulation(p,width,height,open,kT,kN);
        s.simulate(30);

        /*FileWriter fl = new FileWriter("out.txt");
        fl.write(p.size() + "\n" + p.size() + "\n");
        for(Particle pa : p){
            fl.write(pa.toString());
        }
        fl.close();*/
    }
}
