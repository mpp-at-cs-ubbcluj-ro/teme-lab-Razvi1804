namespace Lab3.domain;

public class Inscriere : Entity<long>
{
    public Participant Participant { get; set; }
    public Proba Proba { get; set; }

    public Inscriere(Participant participant, Proba proba)
    {
        Participant = participant;
        Proba = proba;
    }

    public Inscriere() { }
}