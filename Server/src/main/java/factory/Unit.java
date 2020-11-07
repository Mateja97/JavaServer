package factory;

import org.apache.commons.math3.distribution.UniformRealDistribution;


public class Unit {

    private final double value;
    int n;

    public Unit(int freq,int n) {
        this.n = n;

        UniformRealDistribution dist = new UniformRealDistribution((float)-1/(10*freq),(float)1/(10*freq));
        this.value = (double) n/freq + dist.sample();
    }

    public double getValue() {
        return value;
    }

}
