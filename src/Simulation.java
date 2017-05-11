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
    static double dt = 0.00001;
    double kT;
    double kN;
    static int jump = (int)((1/dt)/60);

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
        int counter = 0;
        for(double t = 0; t<time;t+=dt){
            Grid g = new RegularGrid(2*w,2*h,2*(open/5));
            g.setCells(particles);
            if(counter == jump)
                fl.write(particles.size() + "\n" + t + "\n");

            ArrayList<ArrayList<Particle>> neigh = g.checkNeighbors(0);
            List<Vector> forces = new ArrayList<>();
            for(Particle p :particles){
                forces.add(new Vector(0,-p.mass*9.8));
            }

            for(Particle p : particles){
                Vector force = forces.get(p.id);
                force.add(checkWalls(p));
                for(Particle nei : neigh.get(p.getId())){
                    if(Particle.dist2(p,nei) < (p.radius + nei.radius)*(p.radius + nei.radius) && nei.id > p.id) {
                        Vector f = getForce(p, nei);
                        force.add(f);
                        forces.get(nei.getId()).add(-f.getX(), -f.getY());
                    }
                }
                p.beemanCorrection(force,dt);
            }
            forces = new ArrayList<>();
            for(Particle p :particles){
                forces.add(new Vector(0,-p.mass*9.8));
            }
            for(Particle p : particles){
                if(counter == jump){
                    fl.write(p.toString());
                }
                Vector force = forces.get(p.id);
                force.add(checkWalls(p));
                for(Particle nei : neigh.get(p.getId())){
                    if(Particle.dist2(p,nei) < (p.radius + nei.radius)*(p.radius + nei.radius)) {
                        Vector f = getForce(p, nei);
                        force.add(f);
                        forces.get(nei.getId()).add(-f.getX(), -f.getY());
                    }
                    neigh.get(nei.getId()).remove(0);
                }
                p.beeman(force,dt);
            }
            for(Particle p : particles){
                //System.out.println("FUERZA X: " + p.f.x);
                //XSystem.out.println("FUERZA Y: " + p.f.y);
                p.setNewPositions();
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
            double dx = w - p.x;
            double dy = 0;
            double dist = Math.sqrt(dx*dx + dy*dy);
            double enx = dx/dist;
            double eny = dy/dist;
            double etx = -eny;
            double ety = enx;

            double csi = p.radius - dist;
            double fn = -kN*csi;
            double dvx = - p.vx;
            double dvy = - p.vy;
            double dvtx = dvx*etx;
            double dvty = dvy*ety;
            double ft = -kT*csi*Math.sqrt(dvtx*dvtx + dvty*dvty);
            double fx = fn*enx - ft*eny;
            double fy = fn*eny + ft*enx;
            f.add(fx,fy);
        } else if(p.x - p.radius < 0){
            double dx = -p.x;
            double dy = 0;
            double dist = Math.sqrt(dx*dx + dy*dy);
            double enx = dx/dist;
            double eny = dy/dist;
            double etx = -eny;
            double ety = enx;

            double csi = p.radius - dist;
            double fn = -kN*csi;
            double dvx = - p.vx;
            double dvy = - p.vy;
            double dvtx = dvx*etx;
            double dvty = dvy*ety;
            double ft = -kT*csi*Math.sqrt(dvtx*dvtx + dvty*dvty);
            double fx = fn*enx - ft*eny;
            double fy = fn*eny + ft*enx;
            f.add(fx,fy);
        }
        if( p.y + p.radius > h){
            double dx = 0;
            double dy = h - p.y;
            double dist = Math.sqrt(dx*dx + dy*dy);
            double enx = dx/dist;
            double eny = dy/dist;
            double etx = -eny;
            double ety = enx;

            double csi = p.radius - dist;
            double fn = -kN*csi;
            double dvx = - p.vx;
            double dvy = - p.vy;
            double dvtx = dvx*etx;
            double dvty = dvy*ety;
            double ft = -kT*csi*Math.sqrt(dvtx*dvtx + dvty*dvty);
            double fx = fn*enx - ft*eny;
            double fy = fn*eny + ft*enx;
            f.add(fx,fy);
        }else if(p.y - p.radius < 0){
            double dx = 0;
            double dy = -p.y;
            double dist = Math.sqrt(dx*dx + dy*dy);
            double enx = dx/dist;
            double eny = dy/dist;
            double etx = -eny;
            double ety = enx;

            double csi = p.radius - dist;
            double fn = -kN*csi;
            double dvx = - p.vx;
            double dvy = - p.vy;
            double dvtx = dvx*etx;
            double dvty = dvy*ety;
            double ft = -kT*csi*Math.sqrt(dvtx*dvtx + dvty*dvty);
            double fx = fn*enx - ft*eny;
            double fy = fn*eny + ft*enx;
            f.add(fx,fy);
        }
        return f;
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
        //double ftSinComponentes = -kT*csi*(dvtx + dvty);          //Math.sqrt(dvtx*dvtx + dvty*dvty);
        //ftSinComponentes*etx + ftSinComponentes*ety;
        double ft = -kT*csi*(Math.sqrt(nei.vx*nei.vx + nei.vy*nei.vy)-Math.sqrt(p.vx*p.vx + p.vy*p.vy));
        double fx = fn*enx - ft*eny;
        double fy = fn*eny + ft*enx;
        return new Vector(fx,fy);
    }


}
