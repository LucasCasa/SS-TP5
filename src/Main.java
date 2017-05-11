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
        double dMin = open/7;
        double dMax = open/5;
        double kT = 2;
        double kN = 10;
        double mass = 0.01;
        double v = 0;
        List<Particle> p= g.generateParticles(width,height,500,(dMin/2.0),(dMax/2.0),v,mass);
        //double rRand = Math.random()*(rMax - rMin) + rMin;
        //double rand = Math.random()*2*Math.PI;
        //double posX = Math.random()*(width-2*rRand) + rRand;
       // Particle p1 = new Particle(0, rRand, posX, Math.random()*(height-2*rRand) + rRand,v*Math.cos(rand),v*Math.sin(rand),mass);
       // Particle p2 = new Particle(1, rRand, posX, Math.random()*(height-2*rRand) + rRand,v*Math.cos(rand),v*Math.sin(rand),mass);
       //System.out.println(p1.size());

        //List<Particle> lista = new ArrayList<>();
        //lista.add(p1);
        //lista.add(p2);
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
