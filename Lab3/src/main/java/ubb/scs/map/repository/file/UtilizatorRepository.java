package ubb.scs.map.repository.file;

import ubb.scs.map.domain.Utilizator;

/***
 * Clasa pentru repository utilizatori
 */
public class UtilizatorRepository extends AbstractFileRepository<Long, Utilizator>{
    public UtilizatorRepository(String fileName) {
        super(fileName);
    }

    /***
     * Metoda pentru citirea unui utilizator din fisier
     * @param line linia din fisier
     * @return utilizatorul rezultat de linia din fisier
     */
    @Override
    public Utilizator createEntity(String line) {
        String[] splited = line.split(";");
        Utilizator u = new Utilizator(splited[1], splited[2]);
        u.setId(Long.parseLong(splited[0]));
        return u;
    }

    /***
     * Metoda pentru scrierea unui utilizator in fisier
     * @param entity utilizatorul care va fi scris in fisier
     * @return linia care va fi scrisa in fisier
     */
    @Override
    public String saveEntity(Utilizator entity) {
        return entity.getId() + ";" + entity.getFirstName() + ";" + entity.getLastName();
    }
}
