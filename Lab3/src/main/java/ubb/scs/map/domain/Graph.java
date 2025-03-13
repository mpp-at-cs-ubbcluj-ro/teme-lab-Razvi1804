package ubb.scs.map.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private final Map<Long, List<Long>> adjacencyList; // Lista de adiacență cu Long

    // Constructor
    public Graph() {
        adjacencyList = new HashMap<>(); // Utilizăm un HashMap pentru a stoca noduri de tip Long
    }

    // Metoda pentru adăugarea unei muchii în graf
    public void addEdge(Long src, Long dest) {
        adjacencyList.putIfAbsent(src, new ArrayList<>());
        adjacencyList.putIfAbsent(dest, new ArrayList<>());
        adjacencyList.get(src).add(dest);
        adjacencyList.get(dest).add(src); // Graf neorientat
    }

    // Metoda pentru DFS
    private void DFS(Long vertex, Map<Long, Boolean> visited, List<Long> component) {
        visited.put(vertex, true);
        component.add(vertex); // Adaugam nodul curent în componenta curentă

        // Explorăm toate nodurile adiacente
//        for (Long neighbor : adjacencyList.get(vertex)) {
//            if (!visited.get(neighbor)) {
//                DFS(neighbor, visited, component);
//            }
//        }
        adjacencyList.get(vertex).forEach(neighbor -> {
            if (!visited.get(neighbor)) {
                DFS(neighbor, visited, component);
            }
        });
    }

    // Metoda pentru găsirea tuturor componentelor conexe
    public List<List<Long>> findConnectedComponents() {
        Map<Long, Boolean> visited = new HashMap<>(); // Pentru a ține evidența nodurilor vizitate
        List<List<Long>> components = new ArrayList<>(); // Listă de componente conexe

        // Inițializăm toate nodurile ca nevizitate
        for (Long vertex : adjacencyList.keySet()) {
            visited.put(vertex, false);
        }

        // Iterăm peste toate nodurile
        for (Long v : adjacencyList.keySet()) {
            if (!visited.get(v)) {
                // Dacă nodul nu a fost vizitat, facem DFS pentru a găsi o nouă componentă conexă
                List<Long> component = new ArrayList<>();
                DFS(v, visited, component);
                components.add(component); // Adăugăm componenta în lista de componente
            }
        }

        return components; // Returnăm toate componentele conexe
    }
}