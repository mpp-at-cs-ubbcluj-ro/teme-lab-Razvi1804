namespace Lab3.domain;

public class Entity<T>
{
    public T Id { get; set; }

    public Entity() { }

    public Entity(T id)
    {
        Id = id;
    }
}