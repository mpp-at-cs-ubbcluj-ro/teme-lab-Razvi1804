package ubb.scs.map.repository.memory;


import ubb.scs.map.domain.Entity;
import ubb.scs.map.repository.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/***
 * Clasa pentru repository care salveaza in memorie
 * @param <ID> Tipul de data pe care il vor folosi entitatile ca id
 * @param <E> Tipul de entitate cu care va lucra repository
 */
public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E> {

    protected Map<ID,E> entities;

    public InMemoryRepository() {
        entities=new HashMap<>();
    }

    /***
     * Metoda de gasire entitate cu id-ul dat
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return entitatea cu id-ul dat sau null daca nu exista
     */
    @Override
    public Optional<E> findOne(ID id) {
        return Optional.ofNullable(entities.get(id));
    }

    /***
     * Metoda care returneaza lista cu toate entitatiile
     * @return lista cu toate entitatiile
     */
    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    /***
     * Metoda de salvare in memorie a unei entitati
     * @param entity entitatea care va fi salvata
     *         entity must be not null
     * @return entitatea daca entitatea exista deja, null altfel
     */
    @Override
    public Optional<E> save(E entity) {
//        if(entities.containsKey(entity.getId()))
//            return Optional.of(entity);
//        else{
//            entities.put(entity.getId(),entity);
//            return null;
//        }
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(),entity));
    }


    /***
     * Metoda de staergere a unei entitati din memorie
     * @param id id-ul entitatii
     *      id must be not null
     * @return entitatea daca aceasta a fost stearsa sau null altfel
     */
    @Override
    public Optional<E> delete(ID id) {
        return Optional.ofNullable(entities.remove(id));
    }

    /***
     * Metoda de actualizare a unei entitati din memorie
     * @param entity Entitatea noua care o va inlocui pe cea veche
     *          entity must not be null
     * @return entitatea daca aceasta nu a fost actualizata sau null altfel
     */
    @Override
    public Optional<E> update(E entity) {
        if(findOne(entity.getId()).isPresent()){
            entities.put(entity.getId(),entity);
            return Optional.empty();
        }
        return Optional.of(entity);
    }
}
