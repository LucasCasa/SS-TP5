import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcasagrande on 05/05/17.
 */
public class Simulation {
    List<Particle> particles;
    double w;
    double h;
    double open;
    double putX = 0;
    static double dt = 0.00001;
    double kT;
    double kN;
    double gamma = 10;
    static int jump = 10000;
    List<Particle> toRemove = new ArrayList<>();

    public Simulation(List<Particle> p,double w, double h, double open,double kT, double kN){
        this.particles = p;
        this.w = w;
        this.h = h;
        this.open = open;
        this.kT = kT;
        this.kN = kN;
        dt = 0.005*Math.sqrt(p.get(0).mass/kN);
        jump = (int)((1/dt)/60);
        putX = open/5;
    }


    public void simulate(double time) throws IOException{
        FileWriter fl = new FileWriter("out.txt");
        int counter = 0;
        Grid g = new RegularGrid(w,h + h/10,2*(open/5));
        g.setCells(particles);
        List<Vector> forces = new ArrayList<>();
        for(Particle p :particles){
            forces.add(new Vector());
        }
        double iterTime = System.currentTimeMillis();
        for(double t = 0; t<time;t+=dt){
            //g.setCells(particles);
            if(counter == jump) {
                fl.write(particles.size() + "\n" + t + "\n");
                System.out.println(t + " Tiempo: " + (System.currentTimeMillis() - iterTime));
                iterTime = System.currentTimeMillis();
            }

            ArrayList<ArrayList<Particle>> neigh = g.checkNeighbors(0);
            for(Particle p :particles){
                forces.get(p.id).set(0,-p.mass*9.8);
            }

            for(Particle p : particles){
                Vector force = forces.get(p.id);
                force.add(checkWalls(p));
                for(Particle nei : neigh.get(p.getId())){
                    if(nei.id > p.id) {
                        Vector f = getForce(p, nei);
                        force.add(f);
                        forces.get(nei.getId()).add(-f.getX(), -f.getY());
                    }
                }
                p.beemanCorrection(force,dt);
            }
            for(Particle p :particles){
                forces.get(p.id).set(0,-p.mass*9.8);
            }
            for(Particle p : particles){
                if(counter == jump){
                    fl.write(p.toString());
                }
                Vector force = forces.get(p.id);
                force.add(checkWalls(p));
                for(Particle nei : neigh.get(p.getId())){
                    if(nei.id > p.id) {
                        Vector f = getForce(p, nei);
                        force.add(f);
                        forces.get(nei.getId()).add(-f.getX(), -f.getY());
                    }
                }
                p.beeman(force,dt);
            }
            for(Particle p : particles){
                p.setNewPositions();
                g.updateParticle(p);
            }
            if(counter == jump){
                counter = 0;
            }else{
                counter++;
            }

        }
        fl.close();
    }

    private Vector checkWalls(Particle p) {
        Vector f = new Vector(0,0);
        if(p.x + p.radius > w){
            f.add(collideWall(-p.vy,-p.vx,1,0,p.radius - w + p.x));
        } else if(p.x - p.radius < 0){
            f.add(collideWall(-p.vy,-p.vx,-1,0,p.radius -p.x ));
        }
        if( p.y + p.radius > h + h/10){
            f.add(collideWall(-p.vy,-p.vx,0,1,p.radius - (h + h/10) + p.y ));
        }else if((p.y - p.radius) < (h/10)){
            if(((p.x + p.radius) > (w/2 + open/2) || (p.x - p.radius) < (w/2 - open/2)) && p.y - p.radius - h/10 >= -0.01) {
                f.add(collideWall(-p.vy, -p.vx, 0, -1, p.radius + h / 10 - p.y));
            }else if(p.y - p.radius <= 0){
                p.setY(h-0.01);
                p.setX(putX);
                putX+= open/5;
                if(putX > w - open/5){
                    putX = open/5;
                }
                p.setSpeedX(0);
                p.setSpeedY(0);

            }
        }
        return f;
    }
    private Vector collideWall(double dvy, double dvx, double enx, double eny,double csi){
        double dvn = dvx*enx + dvy*eny;
        double fn = -kN*csi + gamma*dvn;
        double dvt = dvy*enx - dvx*eny;
        double ft = kT*csi*dvt;
        double fx = fn*enx - ft*eny;
        double fy = fn*eny + ft*enx;
        return new Vector(fx,fy);
    }
    private Vector getForce(Particle p, Particle nei) {
        double dx = nei.x - p.x;
        double dy = nei.y - p.y;
        double dist = Math.sqrt(dx*dx + dy*dy);
        double enx = dx/dist;
        double eny = dy/dist;
        double etx = -eny;
        double ety = enx;

        double csi = nei.radius + p.radius - dist;
        double dvx = nei.vx - p.vx;
        double dvy = nei.vy - p.vy;

        double dvn = dvx*enx + dvy*eny;
        double fn = -kN*csi + gamma*dvn;

        double dvt = dvy*ety + dvx*etx;
        double ft = kT*csi*dvt;
        double fx = fn*enx - ft*eny;
        double fy = fn*eny + ft*enx;
        return new Vector(fx,fy);
    }


}
