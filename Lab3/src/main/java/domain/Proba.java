package domain;

public class Proba extends Entity<Long> {
    private String distanta;
    private String stil;

    public Proba() {}

    public Proba(String distanta, String stil) {
        this.distanta = distanta;
        this.stil = stil;
    }

    public String getDistanta() {
        return distanta;
    }

    public void setDistanta(String distanta) {
        this.distanta = distanta;
    }

    public String getStil() {
        return stil;
    }

    public void setStil(String stil) {
        this.stil = stil;
    }
}
