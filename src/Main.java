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
        double height = 2;
        double open = 0.2;
        double dMin = open/7;
        double dMax = open/5;
        double kT = 2*100000;
        double kN = 100000;
        double mass = 0.01;
        double v = 0;

        double rRand = Math.random()*((dMax/2.0) - (dMin/2.0)) + (dMin/2.0);
        double rand = Math.random()*2*Math.PI;
        double posY = Math.random()*(height-2*rRand) + rRand;
        double posX = Math.random()*(width-2*rRand) + rRand;


         List<Particle> lista = new ArrayList<>();
        //Particle i = new Particle(0, rRand, 0.1, 0.5,1,0,mass);
        //lista.add(i);
        List<Particle> p= g.generateParticles(width,height,2000,(dMin/2.0),(dMax/2.0),v,mass);
        System.out.println(p.size());
        /*double rRand = Math.random()*((dMax/2.0) - (dMin/2.0)) + (dMin/2.0);
        double rand = Math.random()*2*Math.PI;
        double posY = Math.random()*(height-2*rRand) + rRand;
        double posX = Math.random()*(width-2*rRand) + rRand;

        List<Particle> lista = new ArrayList<>();

        Particle i = new Particle(0, rRand, posX, posY,v*Math.cos(rand),v*Math.sin(rand),mass);
        Particle j = new Particle(1, rRand, posX, posY,v*Math.cos(rand),v*Math.sin(rand),mass);

        //for(int i = 0; i<width/dMax - 1;i++){

          //  Particle j = new Particle(i, rRand, i*dMax, posY,v*Math.cos(rand),v*Math.sin(rand),mass);
         lista.add(i);
         lista.add(j);
        //}
*/
       //System.out.println(p1.size());
        double totEnergy = 0;
        /*for(Particle pa : p){
            totEnergy+= 0.5*pa.mass*(pa.vx*pa.vx + pa.vy*pa.vy) + pa.mass*9.8*pa.y;
        }
        System.out.println(totEnergy);*/
        Simulation s = new Simulation(p,width,height,open,kT,kN);
        s.simulate(10);

        /*FileWriter fl = new FileWriter("out.txt");
        fl.write(p.size() + "\n" + p.size() + "\n");
        for(Particle pa : p){
            fl.write(pa.toString());
        }
        fl.close();*/
    }
}
