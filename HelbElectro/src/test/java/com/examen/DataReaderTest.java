package com.examen;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*; // Static pour pouvoir utilisé les méthodes sans instancié


public class DataReaderTest {

    @Test
    public void testValidCharacters() {

        DataReader dataReader = new DataReader(new ArrayList<>());

        // Je récupere le fichier statiquement depuis mon componentController

        assertDoesNotThrow(() -> { testDataFileCharacters(ComponentController.fileName);});

    }

    // On utilise cette méthode pour la partie testing
    public void testDataFileCharacters(String fileName) throws IOException {
        List<Character> validChars = Arrays.asList(

                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', // -> Nombres autorisés
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', // Lettres Miniscule autorisées
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', // Lettres Majuscule autorisées
                'N', 'O', 'P', 'Q', 'R',  'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '%', ',',  // -> Caract speciaux autorisés

                '\n' // Passage à la ligne autorisé evidemment
        );

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int charCode;
            while((charCode = reader.read()) != -1) { // Lire jusqu' à la fin du fichier
                char currentChar = (char) charCode;
                if (!validChars.contains(currentChar)) {
                    throw new IllegalArgumentException("Valeur aberrantes trouvées !: " + currentChar);
                }
            }
        }
    }
}
