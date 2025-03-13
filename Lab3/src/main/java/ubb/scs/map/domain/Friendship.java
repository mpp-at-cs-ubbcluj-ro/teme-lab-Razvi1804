package ubb.scs.map.domain;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/***
 * Clasa pentru friendship
 */
public class Friendship extends Entity<Tuple<Long,Long>> {
    private final LocalDateTime date;

    /***
     * Constructor friendship fara data
     * @param userId1 id utilizator nr 1
     * @param userId2 id utilizator nr 2
     */
    public Friendship(Long userId1, Long userId2) {
        date = LocalDateTime.now();
        setId(new Tuple<>(userId1, userId2));
    }
    /***
     * Constructor friendship cu data pentru scrierea din fisier
     * @param userId1 id utilizator nr 1
     * @param userId2 id utilizator nr 2
     */
    public Friendship(Long userId1, Long userId2, LocalDateTime date) {
        setId(new Tuple<>(userId1, userId2));
        this.date = date;
    }


    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(date, that.date) && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }
}

