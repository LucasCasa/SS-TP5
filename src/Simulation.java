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
    static double dt = 1;
    double kT;
    double kN;

    public Simulation(List<Particle> p,double w, double h, double open,double kT, double kN){
        this.particles = p;
        this.w = w;
        this.h = h;
        this.open = open;
        this.kT = kT;
        this.kN = kN;
    }


    public void simulate(double time) throws IOException{
        FileWriter fl = new FileWriter("out.txt");
        for(double t = 0; t<time;t+=dt){
            Grid g = new RegularGrid(w,h,2*(open/5));
            g.setCells(particles);
            fl.write(particles.size() + "\n" + t + "\n");
            ArrayList<ArrayList<Particle>> neigh = g.checkNeighbors(0);
            List<Vector> forces = new ArrayList<>();
            for(Particle p :particles){
                forces.add(new Vector(0,-p.mass*0.0098));
            }
            for(Particle p : particles){
                fl.write(p.toString());
                Vector force = forces.get(p.id);
                for(Particle nei : neigh.get(p.getId())){
                    Vector f = getForce(p,nei);
                    force.add(f);
                    forces.get(nei.getId()).add(-f.getX(),-f.getY());
                    neigh.get(nei.getId()).remove(0);
                }
                p.updatePosition(force,dt);
            }
            for(Particle p : particles){
                p.setNewPositions();
            }
        }
        fl.close();
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
        double fn = -kN*csi;
        double dvx = nei.vx - p.vx;
        double dvy = nei.vy - p.vy;
        double dvtx = dvx*etx;
        double dvty = dvy*ety;
        double ft = kT*csi*Math.sqrt(dvtx*dvtx + dvty*dvty);
        double fx = fn*enx - ft*eny;
        double fy = fn*eny + ft*enx;
        return new Vector(fx,fy);
    }


}
