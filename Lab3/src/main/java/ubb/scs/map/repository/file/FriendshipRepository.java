package ubb.scs.map.repository.file;

import ubb.scs.map.domain.Friendship;
import ubb.scs.map.domain.Tuple;
import java.time.LocalDateTime;

/***
 * Clasa pentru repository friendships
 */
public class FriendshipRepository extends AbstractFileRepository<Tuple<Long,Long>, Friendship>{
    public FriendshipRepository(String fileName) {
        super(fileName);
    }

    /***
     * Metoda pentru citirea unei prietenii din fisier
     * @param line linia din fisier
     * @return prietenia rezultata de linia din fisier
     */
    @Override
    public Friendship createEntity(String line) {
        String[] splited = line.split(";");
        Long userId1 = Long.parseLong(splited[0]);
        Long userId2 = Long.parseLong(splited[1]);
        LocalDateTime date = LocalDateTime.parse(splited[2]);
        return new Friendship(userId1, userId2, date);
    }

    /***
     * Metoda pentru scrierea unei prietenii in fisier
     * @param entity prietenia care va fi scrisa in fisier
     * @return linia care va fi scrisa in fisier
     */
    @Override
    public String saveEntity(Friendship entity) {
        return entity.getId().getE1() + ";" + entity.getId().getE2() + ";" + entity.getDate().toString();
    }
}
