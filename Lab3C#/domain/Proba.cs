﻿namespace Lab3.domain;

public class Proba : Entity<long>
{
    public string Distanta { get; set; }
    public string Stil { get; set; }

    public Proba() { }

    public Proba(string distanta, string stil)
    {
        Distanta = distanta;
        Stil = stil;
    }
}