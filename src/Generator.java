import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcasagrande on 05/05/17.
 */
public class Generator {

    List<Particle> generateParticles(double x, double y, int n, double rMin, double rMax, double v, double m){
        List<Particle> ps = new ArrayList<>();
        int counter = 0;

        for(int i = 0; i<n;i++){
            boolean collision;
            double rand = Math.random()*2*Math.PI;
            double rRand = Math.random()*(rMax - rMin) + rMin;
            Particle p = new Particle(i,rRand,Math.random()*(x-2*rRand) + rRand,Math.random()*(y-2*rRand) + rRand,v*Math.cos(rand),v*Math.sin(rand),m);
            counter = 1;
            do {
                collision = false;
                for(Particle op : ps){
                    if(Particle.dist2(p,op) < (p.getRadius()+op.getRadius())*(p.getRadius()+op.getRadius())){
                        collision = true;
                        p.setX(Math.random()*(x-2*rRand) + rRand);
                        p.setY(Math.random()*(y-2*rRand) + rRand);
                        break;
                    }
                }
                if(counter >= 1000){
                    return ps;
                }
                counter++;
            }while(collision);
            ps.add(p);
        }
        return ps;
    }

}
