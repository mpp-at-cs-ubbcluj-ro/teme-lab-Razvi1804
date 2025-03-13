package ubb.scs.map.domain;

/***
 * Clasa generala pentru entitati
 * @param <ID> Tipul de data pe care il va avea ID-ul entitati
 */
public class Entity<ID>  {

    private ID id;
    public ID getId() {
        return id;
    }
    public void setId(ID id) {
        this.id = id;
    }
}