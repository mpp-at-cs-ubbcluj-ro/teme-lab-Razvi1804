package ubb.scs.map;

import ubb.scs.map.domain.validators.FriendshipValidator;
import ubb.scs.map.domain.validators.UtilizatorValidator;
import ubb.scs.map.repository.database.FriendshipRepositoryDB;
import ubb.scs.map.repository.database.UtilizatorRepositoryDB;
import ubb.scs.map.repository.file.FriendshipRepository;
import ubb.scs.map.repository.file.UtilizatorRepository;
import ubb.scs.map.service.Service;
import ubb.scs.map.ui.UI;

import java.time.LocalDateTime;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        System.out.println(LocalDateTime.now());
//        UtilizatorRepository repoUtil = new UtilizatorRepository("C:\\Users\\x64\\Desktop\\lab3\\data\\utilizatori.txt");
//        FriendshipRepository repoFr = new FriendshipRepository("C:\\Users\\x64\\Desktop\\lab3\\data\\friendship.txt");
        UtilizatorRepositoryDB repoUtil = new UtilizatorRepositoryDB();
        FriendshipRepositoryDB repoFr = new FriendshipRepositoryDB();
        UtilizatorValidator validatorUtil = new UtilizatorValidator();
        FriendshipValidator validatorFr = new FriendshipValidator();
        Service service = new Service(validatorUtil, validatorFr,repoUtil, repoFr);
        UI ui = new UI(service);
        ui.run();
        System.out.println();
    }
}