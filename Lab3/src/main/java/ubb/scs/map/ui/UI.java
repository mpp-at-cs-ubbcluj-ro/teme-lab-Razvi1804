package ubb.scs.map.ui;

import java.util.Scanner;

import ubb.scs.map.service.Service;
import ubb.scs.map.service.ServiceException;

public class UI {
    private final Service service;

    public UI(Service service) {
        this.service = service;
    }

    private void addUser(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter firstName: ");
        String firstName = sc.next();
        System.out.print("Enter lastName: ");
        String lastName = sc.next();
        try{
            service.addUser(firstName, lastName);
            System.out.println("Utilizatorul a fost adaugat cu succes!");
        }
        catch(ServiceException e){
            System.out.println(e.getMessage());
        }

    }

    private void deleteUser(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter userId: ");
        Long userId = sc.nextLong();
        try{
            service.removeUser(userId);
            System.out.println("Utilizatorul a fost sters cu succes!");
        }
        catch(ServiceException e){
            System.out.println(e.getMessage());
        }
    }

    private void addFriendship(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter userId1: ");
        Long userId1 = sc.nextLong();
        System.out.print("Enter userId2: ");
        Long userId2 = sc.nextLong();
        try{
            service.addFriendship(userId1, userId2);
            System.out.println("Prietenia a fost adaugata cu succes!");
        }
        catch(ServiceException e){
            System.out.println(e.getMessage());
        }
    }

    private void deleteFriendship(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter userId1: ");
        Long userId1 = sc.nextLong();
        System.out.print("Enter userId2: ");
        Long userId2 = sc.nextLong();
        try{
            service.removeFriendship(userId1, userId2);
            System.out.println("Prietenia a fost stearsa cu succes!");
        }
        catch(ServiceException e){
            System.out.println(e.getMessage());
        }
    }

    private void nrComunitati(){
        System.out.println("Nr de comunitati: " + service.numarComunitati());
    }

    private void ceaMaiSociabila(){
        System.out.println("Cea mai sociabila comunitate:");
//        for(Utilizator u : service.ceaMaiSociabila()){
//            System.out.println(u);
//        }
        service.ceaMaiSociabila().forEach(System.out::println);
    }

    public void run(){
        System.out.println("adduser -> adauga user( are nevoie de prenume, nume, id)\n" +
                "    deleteuser -> sterge user( are nevoie id)\n" +
                "    addfriend -> creeaza prietenie ( are nevoie de id user1 si id user2)\n" +
                "    deletefriend -> sterge prietennie ( are nevoie de id user1 si id user2)\n" +
                "    nrcomunitati -> afiseaza nr de comunitati\n" +
                "    ceamaisciabila -> afiseaza user din cea mai sociabila comunitate ");
        Scanner sc = new Scanner(System.in);
        String option = "";
        while(!option.equals("exit")){
            option = sc.nextLine();
            switch(option){
                case "adduser"->addUser();
                case "deleteuser"->deleteUser();
                case "addfriend"->addFriendship();
                case "deletefriend"->deleteFriendship();
                case "nrcomunitati"->nrComunitati();
                case "ceamaisocial"->ceaMaiSociabila();
                case "exit"-> System.out.println("Exit");
                default -> System.out.println("adduser -> adauga user( are nevoie de prenume, nume, id)\n" +
                        "    deleteuser -> sterge user( are nevoie id)\n" +
                        "    addfriend -> creeaza prietenie ( are nevoie de id user1 si id user2)\n" +
                        "    deletefriend -> sterge prietennie ( are nevoie de id user1 si id user2)\n" +
                        "    nrcomunitati -> afiseaza nr de comunitati\n" +
                        "    ceamaisciabila -> afiseaza user din cea mai sociabila comunitate ");
            }
        }
    }
}

