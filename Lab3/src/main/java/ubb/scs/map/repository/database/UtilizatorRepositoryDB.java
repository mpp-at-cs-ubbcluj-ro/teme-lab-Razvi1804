package ubb.scs.map.repository.database;
import java.util.stream.StreamSupport;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.repository.Repository;

import java.sql.*;
import java.util.*;

public class UtilizatorRepositoryDB implements Repository<Long, Utilizator> {

    @Override
    public Optional<Utilizator> findOne(Long ID) {
        String query = "select * from Utilizator WHERE ID = ?";
        Utilizator user = null;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Map","postgres","razvi1966");
             PreparedStatement statement = connection.prepareStatement(query);) {

            statement.setLong(1, ID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("FIRST_NAME");
                String lastName = resultSet.getString("LAST_NAME");
                user = new Utilizator(firstName, lastName);
                user.setId(ID);
            }

        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Iterable<Utilizator> findAll() {
        HashMap<Long, Utilizator> users = new HashMap<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Map","postgres","razvi1966");
             PreparedStatement statement = connection.prepareStatement("select * from Utilizator");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String nume = resultSet.getString("FIRST_NAME");
                String prenume = resultSet.getString("LAST_NAME");
                Utilizator user = new Utilizator(nume, prenume);
                user.setId(id);

                users.put(user.getId(), user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users.values();
    }

    @Override
    public Optional<Utilizator> save(Utilizator entity) {
        String query = "INSERT INTO Utilizator(FIRST_NAME, LAST_NAME) VALUES (?,?)";

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Map","postgres","razvi1966");
             PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(entity);
    }

    @Override
    public Optional<Utilizator> delete(Long ID) {

        String query = "DELETE FROM Utilizator WHERE ID = ?";
        Utilizator userToDelete = StreamSupport.stream(findAll().spliterator(), false)
                .filter(user -> Objects.equals(user.getId(), ID))
                .findFirst()
                .orElse(null);
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Map","postgres","razvi1966");
             PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setLong(1, ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        Utilizator userToDelete = null;
//        for (Utilizator user : findAll()) {
//            if (Objects.equals(user.getId(), ID)) {
//                userToDelete = user;
//            }
//        }
        return Optional.ofNullable(userToDelete);
    }

    @Override
    public Optional<Utilizator> update(Utilizator entity) {
        return Optional.empty();
    }
}
