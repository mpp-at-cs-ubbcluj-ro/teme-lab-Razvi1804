namespace Lab3.domain;

public class User : Entity<long>
{
    public string Username { get; set; }
    public string HashedPassword { get; set; }

    public User() { }

    public User(string username, string hashedPassword)
    {
        Username = username;
        HashedPassword = hashedPassword;
    }
}