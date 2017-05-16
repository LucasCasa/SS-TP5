import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 3/14/17.
 */
public class RegularGrid extends Grid {

    public RegularGrid(double w,double h, double cellSide){
        super(w,h, cellSide);
        /*for(int i = 0; i<grid.length;i++){
            for(int j = 0;j<grid[0].length;j++){
                if(i+1 < grid.length){
                    grid[i][j].setSouth(grid[i+1][j]);
                }
                if(j+1 < grid[0].length){
                    grid[i][j].setEast(grid[i][j+1]);
                }
                if(i+1 < grid.length && j+1 < grid[0].length){
                    grid[i][j].setSouthEast(grid[i+1][j+1]);
                }
            }
        }*/
    }

    @Override
    public ArrayList<ArrayList<Particle>> checkNeighbors(double rc) {
        ArrayList<ArrayList<Particle>> neigh = new ArrayList<>();
        for(int i = 0; i< super.particles.size();i++){
            neigh.add(new ArrayList<>());
        }
        int count = 0;
        for(int i = 0; i<grid.length;i++){
            for(int j = 0; j<grid.length;j++){
                for(int k = 0;k<grid[i][j].getParticleList().size();k++){
                    Particle p = grid[i][j].getParticleList().get(k);
                    for(int l =k+1;l<grid[i][j].getParticleList().size();l++){
                        Particle p1 = grid[i][j].getParticleList().get(l);
                        count++;
                        if (Particle.dist2(p, p1) < (p.radius + p1.radius + rc)*(p.radius + p1.radius + rc)) {
                            neigh.get(p.getId()).add(p1);
                            neigh.get(p1.getId()).add(p);
                        }
                    }

                    if(i+1 <= grid.length -1) {
                        for (Particle p1 : grid[i + 1][j].getParticleList()) {
                            count++;
                            if (Particle.dist2(p, p1) <= (p.radius + p1.radius + rc)*(p.radius + p1.radius + rc)) {
                                neigh.get(p.getId()).add(p1);
                                neigh.get(p1.getId()).add(p);
                            }
                        }
                    }
                    if(i-1 >= 0 && j+1 <= grid.length-1) {
                        for (Particle p1 : grid[i - 1][j + 1].getParticleList()) {
                            count++;
                            if (Particle.dist2(p, p1) <= (p.radius + p1.radius + rc)*(p.radius + p1.radius + rc)) {
                                neigh.get(p.getId()).add(p1);
                                neigh.get(p1.getId()).add(p);
                            }
                        }

                    }
                    if(j+1 <= grid.length-1) {
                        for (Particle p1 : grid[i][j + 1].getParticleList()) {
                            count++;
                            if (Particle.dist2(p, p1) <= (p.radius + p1.radius + rc)*(p.radius + p1.radius + rc)) {
                                neigh.get(p.getId()).add(p1);
                                neigh.get(p1.getId()).add(p);
                            }
                        }
                    }
                    if(j+1 <= grid.length -1 && i+1 <= grid.length-1) {
                        for (Particle p1 : grid[i + 1][j + 1].getParticleList()) {
                            count++;
                            if (Particle.dist2(p, p1) <= (p.radius + p1.radius + rc)*(p.radius + p1.radius + rc)) {
                                neigh.get(p.getId()).add(p1);
                                neigh.get(p1.getId()).add(p);
                            }
                        }
                    }
                }
            }
        }
        return neigh;

    }
    public void updateParticle(Particle p){
        int newx = (int)(p.x / cellSide);
        int newy = (int)(p.y / cellSide);
        if(p.cellx != newx || newy != p.celly){
            Cell s = grid[p.cellx][p.celly];
            int size = s.getParticleList().size();
            s.getParticleList().remove(p);
            if(size == s.getParticleList().size()){
                System.out.println("EEROR");
            }
            grid[newx][newy].getParticleList().add(p);
            p.cellx = newx;
            p.celly = newy;
        }
    }


}
