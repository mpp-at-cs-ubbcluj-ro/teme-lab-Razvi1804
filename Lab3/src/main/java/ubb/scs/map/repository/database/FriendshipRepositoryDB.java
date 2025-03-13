package ubb.scs.map.repository.database;
import ubb.scs.map.domain.Friendship;
import ubb.scs.map.domain.Tuple;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.repository.Repository;
import java.util.stream.StreamSupport;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FriendshipRepositoryDB implements Repository<Tuple<Long,Long>, Friendship> {
    @Override
    public Optional<Friendship> findOne(Tuple<Long,Long> ID) {
        String query = "SELECT * FROM Friendship WHERE ID1 = ? AND ID2 = ?";
        Friendship friendship = null;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Map","postgres","razvi1966");
             PreparedStatement statement = connection.prepareStatement(query);) {

            statement.setLong(1, ID.getE1());
            statement.setLong(2, ID.getE2());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long idFriend1 = resultSet.getLong("ID1");
                Long idFriend2 = resultSet.getLong("ID2");
                Timestamp date = resultSet.getTimestamp("F_DATE");
                LocalDateTime localDateTime = date.toLocalDateTime();
                friendship = new Friendship(idFriend1, idFriend2, localDateTime);
                friendship.setId(new Tuple<>(idFriend1, idFriend2));
            }

        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(friendship);
    }

    @Override
    public Iterable<Friendship> findAll() {
        Map<Tuple<Long,Long>, Friendship> friendships = new HashMap<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Map","postgres","razvi1966");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Friendship");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long idFriend1 = resultSet.getLong("ID1");
                Long idFriend2 = resultSet.getLong("ID2");
                Timestamp date = resultSet.getTimestamp("F_DATE");
                LocalDateTime localDateTime = date.toLocalDateTime();
                Friendship friendship = new Friendship(idFriend1, idFriend2, localDateTime);
                friendship.setId(new Tuple<>(idFriend1, idFriend2));
                friendships.put(friendship.getId(), friendship);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return friendships.values();
    }

    @Override
    public Optional<Friendship> save(Friendship entity) {
        String query = "INSERT INTO Friendship(ID1, ID2, F_DATE) VALUES (?,?,?)";

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Map","postgres","razvi1966");
             PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setLong(1, entity.getId().getE1());
            statement.setLong(2, entity.getId().getE2());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(entity.getDate()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(entity);
    }

    @Override
    public Optional<Friendship> delete(Tuple<Long,Long> ID) {
        String query = "DELETE FROM Friendship WHERE ID1 = ? AND ID2 = ?";
        Friendship friendshipToDelete = StreamSupport.stream(findAll().spliterator(), false)
                .filter(user -> Objects.equals(user.getId(), ID))
                .findFirst()
                .orElse(null);
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Map","postgres","razvi1966");
             PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setLong(1, ID.getE1());
            statement.setLong(2, ID.getE2());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            return Optional.empty();
        }
//        Friendship friendshipToDelete = null;
//        for (Friendship friendship : findAll()) {
//            if (Objects.equals(friendship.getId(), ID)) {
//                friendshipToDelete = friendship;
//            }
//        }
        return Optional.ofNullable(friendshipToDelete);
    }

    @Override
    public Optional<Friendship> update(Friendship entity) {
        return Optional.empty();
    }
}
