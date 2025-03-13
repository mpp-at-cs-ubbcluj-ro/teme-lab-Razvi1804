namespace Lab3.domain;

using System.Collections.Generic;


public class Participant : Entity<long>
{
    public string Name { get; set; }
    public int Age { get; set; }

    public Participant() { }

    public Participant(string name, int age)
    {
        Name = name;
        Age = age;
    }
}