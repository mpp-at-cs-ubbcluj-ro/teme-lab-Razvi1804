package ubb.scs.map.service;
import java.util.stream.StreamSupport;
import ubb.scs.map.domain.*;
import ubb.scs.map.domain.validators.ValidationException;
import ubb.scs.map.domain.validators.Validator;
import ubb.scs.map.repository.Repository;
import ubb.scs.map.repository.database.FriendshipRepositoryDB;
import ubb.scs.map.repository.database.UtilizatorRepositoryDB;
import ubb.scs.map.repository.file.FriendshipRepository;
import ubb.scs.map.repository.file.UtilizatorRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/***
 * Clasa pentru service
 */
public class Service {
    private final Validator<Utilizator> validatorUtilizator;
    private final Validator<Friendship> validatorFriendship;
    private final UtilizatorRepositoryDB utilizatorRepository;
    private final FriendshipRepositoryDB friendshipRepository;

    /***
     * Constructor pentru service
     * @param validatorUtilizator validator pentru utilizator
     * @param validatorFriendship validator pentru friendship
     * @param utilizatorRepository repository pentru utilizatori
     * @param friendshipRepository repository pentru friendship
     */
    public Service(Validator<Utilizator> validatorUtilizator, Validator<Friendship> validatorFriendship, UtilizatorRepositoryDB utilizatorRepository, FriendshipRepositoryDB friendshipRepository) {
        this.validatorUtilizator = validatorUtilizator;
        this.validatorFriendship = validatorFriendship;
        this.utilizatorRepository = utilizatorRepository;
        this.friendshipRepository = friendshipRepository;
    }

    /***
     * Metoda pentru adaugarea unui utilizator
     * @param firstName prenumele utilizatorului
     * @param lastName numele utilizatorului
     * @throws ServiceException daca utilizatorul nu este valid
     */
    public void addUser(String firstName, String lastName) throws ServiceException {
        Utilizator utilizator = new Utilizator(firstName, lastName);
        try {
            validatorUtilizator.validate(utilizator);
        }
        catch (ValidationException e) {
            throw new ServiceException(e);
        }
        utilizatorRepository.save(utilizator);
    }

    /***
     * Metoda de stergerea a unui utilizator
     * @param ID ID-ul utilizatorului
     * @throws ServiceException daca utilizatorul nu exista
     */
    public void removeUser(Long ID) throws ServiceException {
        Optional<Utilizator> deleted = utilizatorRepository.delete(ID);
        deleted.orElseThrow(() -> new ServiceException("User not found"));
//        List<Tuple<Long,Long>> list = new ArrayList<>();
//        friendshipRepository.findAll().forEach((Friendship f) -> {
//            if(ID.equals(f.getId().getE1()) || ID.equals(f.getId().getE2())){
//                list.add(f.getId());
//            }
//        });
//        list.forEach(friendshipRepository::delete);
    }

    /***
     * Metoda de adaugare prietenie intre doi utilizatori
     * @param userID1 id-ul primului utilizator
     * @param userID2 id-ul celui de al doilea utilizator
     * @throws ServiceException daca deja exista prietenie intre cei doi
     */
    public void addFriendship(Long userID1, Long userID2) throws ServiceException {
        Friendship friendship = new Friendship(userID1, userID2);
        Friendship friendship2 = new Friendship(userID2, userID1);
        if(friendshipRepository.findOne(friendship.getId()).isPresent() || friendshipRepository.findOne(friendship2.getId()).isPresent()){
            throw new ServiceException("Friendship already exists");
        }
        if(utilizatorRepository.findOne(userID1).isEmpty() || utilizatorRepository.findOne(userID2).isEmpty()){
            throw new ServiceException("Users not found");
        }
        try {
            validatorFriendship.validate(friendship);
        }
        catch (ValidationException e) {
            throw new ServiceException(e);
        }
        friendshipRepository.save(friendship);
    }

    /***
     * Metoda de sterge a unei prieteni intre doi utilizatori
     * @param userID1 id-ul primului utilizator
     * @param userID2 id-ul celui de al doilea utilizator
     * @throws ServiceException daca nu exista prietenie intre cei doi utilizatori
     */
    public void removeFriendship(Long userID1, Long userID2) throws ServiceException {
        Optional<Friendship> deleted1 = friendshipRepository.delete(new Tuple<>(userID1, userID2));
        Optional<Friendship> deleted2 = friendshipRepository.delete(new Tuple<>(userID2, userID1));
        if(deleted1.isEmpty() && deleted2.isEmpty()){
            throw new ServiceException("Friendship not found");
        }
    }

    /***
     * Metoda care calculeaza numarul de comunitati
     * @return nnumarul de comunitati
     */
    public Integer numarComunitati(){
        Graph graph = new Graph();
//        for(Friendship f : friendshipRepository.findAll()) {
//            graph.addEdge(f.getId().getE1(), f.getId().getE2());
//        }
        friendshipRepository.findAll().forEach((Friendship f) -> graph.addEdge(f.getId().getE1(), f.getId().getE2()));
        return graph.findConnectedComponents().size();
    }

    /***
     * Metoda care calculeaza cea mai sociablia comunitrate
     * @return cea mai sociabila comunitate
     */
    public List<Optional<Utilizator>> ceaMaiSociabila(){
        Graph graph = new Graph();
//        for(Friendship f : friendshipRepository.findAll()) {
//            graph.addEdge(f.getId().getE1(), f.getId().getE2());
//        }
        friendshipRepository.findAll().forEach((Friendship f) -> graph.addEdge(f.getId().getE1(), f.getId().getE2()));
        List<Long> rezId;
//        for(List<Long> l : graph.findConnectedComponents()){
//            if(l.size()>rezId.size())
//                rezId = l;
//        }
        rezId = graph.findConnectedComponents().stream()
                .max(Comparator.comparingInt(List::size))
                .orElseGet(ArrayList::new);
        List<Optional<Utilizator>> rez = new ArrayList<>();
//        for(Long uId : rezId) {
//            rez.add(utilizatorRepository.findOne(uId));
//        }
        rezId.forEach((Long uId) -> rez.add(utilizatorRepository.findOne(uId)));
        return rez;
    }
}
