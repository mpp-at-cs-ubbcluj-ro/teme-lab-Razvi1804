package ubb.scs.map.domain.validators;


import ubb.scs.map.domain.Friendship;
import java.util.function.Predicate;

/***
 * Clasa validator pentru friendships
 */
public class FriendshipValidator implements Validator<Friendship> {
    /***
     * Metoda de validare al unui friendship
     * @param entity Friendshipul care va fi validat
     * @throws ValidationException daca friendship-ul este intre un utilizator si el insusi
     */
    @Override
    public void validate(Friendship entity) throws ValidationException {
        Predicate<Friendship> p = (Friendship f) -> f.getId().getE1().equals(f.getId().getE2());
        if(p.test(entity)) {
            throw new ValidationException("Friendship IDs must not be the same");
        }
    }
}
