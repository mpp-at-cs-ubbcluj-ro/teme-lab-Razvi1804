import domain.User;
import repository.UsersDBRepository;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class MainDB {
    public static void main(String[] args) {
        Properties props = new Properties();
        String prop="";
        try (BufferedReader br = new BufferedReader(new FileReader("config.txt"))) {
            prop = br.readLine(); // Citește prima linie
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            props.load(new FileInputStream("C:\\Users\\x64\\Desktop\\teme-lab-Razvi1804\\Lab3\\bd.config"));
        } catch (IOException e){
            System.out.println("Cannot find bd.config "+e.getMessage());
        }
        UsersDBRepository usersDBRepository = new UsersDBRepository(prop);
        usersDBRepository.save(new User("VasileXoXo","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e5a04368f0ad418"));
        Iterable<User> users = usersDBRepository.findAll();
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Username: " + user.getUsername());
        }
        User user = new User("RazviRaz","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e5a04368f0ad418");
        user.setId(1);
        usersDBRepository.update(user);
        User user2 = usersDBRepository.findByUsername("RazviRaz").get();
        System.out.println("ID: " + user2.getId() + ", Username: " + user2.getUsername());
    }
}
