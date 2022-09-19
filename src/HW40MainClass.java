import java.io.*;
import java.util.*;

public class HW40MainClass {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            // TASK 1
            // Path to file
            System.out.println(System.getProperty("user.dir") + File.separator + "Test1.txt\nRequired file path is above (just copy that):");
            String path = reader.readLine();
            input = new FileInputStream(path);
            // Starting to search a word
            System.out.println("Now enter a word for search:");
            String wordToSearch = reader.readLine();
            // Deleting redundant symbols
            ArrayList<Character> specials = new ArrayList<>();
            specials.add(';'); specials.add(':'); specials.add('-');
            specials.add('.'); specials.add(','); specials.add('?');
            specials.add('!'); specials.add('('); specials.add(')');
            specials.add('['); specials.add(']');
            // Collecting chars from original file
            ArrayList<Character> charsFromFile = new ArrayList<>();
            while (input.available() > 0) {
                char value = (char) input.read();
                if (!(specials.contains(value))) {
                    charsFromFile.add(value);
                }
            }
            // Collecting words (String) from original file
            ArrayList<String> wordsFromFile = new ArrayList<>();
            String buffer = "";
            int counter = 0;
            for (int i = 0; i < charsFromFile.size(); i++) {
                if (charsFromFile.get(i) == ' ') {
                    if (!buffer.equals("")) {
                        if (buffer.equalsIgnoreCase(wordToSearch)) {
                            counter++;
                        }
                        wordsFromFile.add(buffer);
                        buffer = "";
                    }
                } else {
                    buffer += String.valueOf(charsFromFile.get(i));
                    if (i == charsFromFile.size() - 1) {
                        wordsFromFile.add(buffer);
                    }
                }
            }
            // Showing & counting
            System.out.println(wordsFromFile);
            System.out.println("Provided word was found " + counter + " times.");


            // TASK 2
            // Getting a new word as substitution and inserting it
            System.out.println("Enter a word which will substitute the one you had provided earlier:");
            String wordForChange = reader.readLine();
            ArrayList<String> wordsWithSubstitution = new ArrayList<>(wordsFromFile);
            for (int i = 0; i < wordsWithSubstitution.size(); i++) {
                if (wordsWithSubstitution.get(i).equalsIgnoreCase(wordToSearch)) {
                    wordsWithSubstitution.remove(i);
                    wordsWithSubstitution.add(i, wordForChange);
                }
            }
            System.out.println(wordsWithSubstitution);


            // TASK 3
            // Collecting prohibited words
            System.out.println("Feel yourself a soviet censor! Let's ban some words! How many?");
            int bannedAmount = Integer.parseInt(reader.readLine());
            ArrayList<String> bannedWords = new ArrayList<>(bannedAmount);
            for (int i = 0; i < bannedAmount; i++) {
                System.out.println("Provide a word, comrade:");
                bannedWords.add(reader.readLine());
            }
            // Writing the new text to another file
            output = new FileOutputStream(System.getProperty("user.dir") + File.separator + "Test2.txt");
            ArrayList<String> wordsWithoutBanned = new ArrayList<>(wordsFromFile);
            for (int i = 0; i < wordsWithoutBanned.size(); i++) {
                for (int j = 0; j < bannedWords.size(); j++) {
                    if (wordsWithoutBanned.get(i).equals(bannedWords.get(j)) || wordsWithoutBanned.get(i).equalsIgnoreCase(bannedWords.get(j))) {
                        wordsWithoutBanned.remove(i);
                        i = 0;
                    }
                }
                output.write(((wordsWithoutBanned.get(i)) + " ").getBytes());
            }
            System.out.println(wordsWithoutBanned);
            // Catching exceptions & closing file streams
        } catch (IOException exception) {}
        finally {
            try {
                input.close();
                output.close();
            } catch (IOException exception) {}
        }
    }
}

