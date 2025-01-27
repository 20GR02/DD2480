package decide.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Parameters {
    private final double length1;
    private final double radius1;
    private final double epsilon;
    private final double area1;
    private final int qPoints;
    private final int quads;
    private final double dist;
    private final int nPoints;
    private final int kPoints;
    private final int aPoints;
    private final int bPoints;
    private final int cPoints;
    private final int dPoints;
    private final int ePoints;
    private final int fPoints;
    private final int gPoints;
    private final double length2;
    private final double radius2;
    private final double area2;

    @JsonCreator
    public Parameters(
            @JsonProperty("length1") double length1,
            @JsonProperty("radius1") double radius1,
            @JsonProperty("epsilon") double epsilon,
            @JsonProperty("area1") double area1,
            @JsonProperty("qPoints") int qPoints,
            @JsonProperty("quads") int quads,
            @JsonProperty("nPoints") int nPoints,
            @JsonProperty("dist") double dist,
            @JsonProperty("kPoints") int kPoints,
            @JsonProperty("aPoints") int aPoints,
            @JsonProperty("bPoints") int bPoints,
            @JsonProperty("cPoints") int cPoints,
            @JsonProperty("dPoints") int dPoints,
            @JsonProperty("ePoints") int ePoints,
            @JsonProperty("fPoints") int fPoints,
            @JsonProperty("gPoints") int gPoints,
            @JsonProperty("length2") double length2,
            @JsonProperty("radius2") double radius2,
            @JsonProperty("area2") double area2) {
        this.length1 = length1;
        this.radius1 = radius1;
        this.epsilon = epsilon;
        this.area1 = area1;
        this.qPoints = qPoints;
        this.quads = quads;
        this.dist = dist;
        this.nPoints = nPoints;
        this.kPoints = kPoints;
        this.aPoints = aPoints;
        this.bPoints = bPoints;
        this.cPoints = cPoints;
        this.dPoints = dPoints;
        this.ePoints = ePoints;
        this.fPoints = fPoints;
        this.gPoints = gPoints;
        this.length2 = length2;
        this.radius2 = radius2;
        this.area2 = area2;
    }

    public double getLength1() {
        return length1;
    }

    public double getRadius1() {
        return radius1;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public double getArea1() {
        return area1;
    }

    public int getqPoints() {
        return qPoints;
    }

    public int getQuads() {
        return quads;
    }

    public double getDist() {
        return dist;
    }

    public int getnPoints() {
        return nPoints;
    }

    public int getkPoints() {
        return kPoints;
    }

    public int getaPoints() {
        return aPoints;
    }

    public int getbPoints() {
        return bPoints;
    }

    public int getcPoints() {
        return cPoints;
    }

    public int getdPoints() {
        return dPoints;
    }

    public int getePoints() {
        return ePoints;
    }

    public int getfPoints() {
        return fPoints;
    }

    public int getgPoints() {
        return gPoints;
    }

    public double getLength2() {
        return length2;
    }

    public double getRadius2() {
        return radius2;
    }

    public double getArea2() {
        return area2;
    }
}
