package org.example;

import java.io.*;
import java.util.*;

public class ParseurRecursif {
    private List<String> tokens;
    private int position;
    private static List<String> articles;
    private static List<String> noms;
    private static List<String> verbes;

    public ParseurRecursif(String phrase) {
        this.tokens = Arrays.asList(phrase.split(" "));
        this.position = 0;
    }

    // Méthodes utilitaires
    private String currentToken() {
        return position < tokens.size() ? tokens.get(position) : null;
    }

    private void consume() {
        position++;
    }

    // Chargement des données depuis les fichiers
    private static List<String> loadListFromFile(String filePath) throws IOException {
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line.trim().toLowerCase());
            }
        }
        return list;
    }

    public boolean parsePhrase() {
        boolean isValid = parseSujet() && parseVerbe();
        if (isValid && currentToken() != null) {
            isValid = parseComplement();
        }
        return isValid && currentToken() == null;
    }

    private boolean parseSujet() {
        return parseArticle() && parseNom();
    }

    private boolean parseComplement() {
        return parseArticle() && parseNom();
    }

    private boolean parseArticle() {
        if (articles.contains(currentToken())) {
            consume();
            return true;
        }
        return false;
    }

    private boolean parseNom() {
        if (noms.contains(currentToken())) {
            consume();
            return true;
        }
        return false;
    }

    private boolean parseVerbe() {
        if (verbes.contains(currentToken())) {
            consume();
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            // Charger les listes depuis les fichiers
            articles = loadListFromFile("src/main/java/org/example/articles.txt");
            noms = loadListFromFile("src/main/java/org/example/noms.txt");
            verbes = loadListFromFile("src/main/java/org/example/verbes.txt");

            // Saisie des phrases directement dans le code
            List<String> phrases = Arrays.asList(
                    "le chien court",
                    "une souris mange",
                    "la téléphone sonne",
                    "la souris mange le fromage",
                    "un chat mange du poisson"
            );

            // Analyser chaque phrase
            for (String phrase : phrases) {
                System.out.println("Phrase: " + phrase);
                ParseurRecursif parseur = new ParseurRecursif(phrase.toLowerCase());

                if (parseur.parsePhrase()) {
                    System.out.println("Résultat: Phrase valide");
                } else {
                    System.out.println("Résultat: Phrase invalide");
                }
                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture des fichiers: " + e.getMessage());
        }
    }
}
