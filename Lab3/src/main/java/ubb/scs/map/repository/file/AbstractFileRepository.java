package ubb.scs.map.repository.file;

import ubb.scs.map.domain.Entity;
import ubb.scs.map.repository.memory.InMemoryRepository;

import java.io.*;
import java.util.Optional;

/***
 * Clasa pentru repository abstract care salveaza in fisier
 * @param <ID> Tipul de data pe care il vor folosi entitatile ca id
 * @param <E> Tipul de entitate cu care va lucra repository
 */
public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E>{
    private final String filename;

    public AbstractFileRepository(String fileName) {
        filename=fileName;
        loadData();
    }

    /***
     * Metoda abstraca pentru citirea unei entitati din fisier
     * @param line linia din fisier
     * @return entitatea creata dupa citirea linii
     */
    public abstract E createEntity(String line);

    /***
     * Metoda abstracta pentru scrierea unei entitati in fisier
     * @param entity entitatea care va fi scrisa
     * @return linia din fisier
     */
    public abstract String saveEntity(E entity);

    /***
     * Metoda de salvare in fiser
     * @param entity entitatea care va fi salvata
     *         entity must be not null
     * @return null daca entitatea poate fi salvata sau entitatea altfel
     */
    @Override
    public Optional<E> save(E entity) {
        Optional<E> e = super.save(entity);
        if (e.isEmpty())
            writeToFile();
        return e;
    }

    /***
     * Metoda de scriere datele din memortie in fisier
     */
    private void writeToFile() {

        try  ( BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            for (E entity: entities.values()) {
                String ent = saveEntity(entity);
                writer.write(ent);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /***
     * Citeste datele din fiser si le incarca in memorie
     */
    private void loadData(){
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                E entity = createEntity(line);
                super.save(entity);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Metoda pentru stergerea unei entitati dupa id
     * @param id id-ul entitatii
     *      id must be not null
     * @return entitatea stearsa sau null altfel
     */
    @Override
    public Optional<E> delete(ID id) {
        Optional<E> entity = super.delete(id);
        if (entity.isPresent()) writeToFile();
        return entity;
    }

    /***
     * Metoda de actualizare a unei entitati
     * @param entity Entitatea noua
     *          entity must not be null
     * @return entitatea noua daca nu exista entitate cu id-ul entitatii noi, null altfel
     */
    @Override
    public Optional<E> update(E entity) {
        Optional<E> entity2 = super.update(entity);
        if (entity2.isEmpty()) writeToFile();
        return entity2;
    }
}
