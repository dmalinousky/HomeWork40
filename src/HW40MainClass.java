import java.io.*;
import java.util.*;

public class HW40MainClass {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            // TASK 1
            System.out.println("Task #1");
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
            System.out.println("Provided word was found " + counter + " time(s).");
            System.out.println();


            // TASK 2
            System.out.println("Task #2");
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
            System.out.println();


            // TASK 3
            System.out.println("Task #3");
            // Collecting prohibited words
            System.out.println("Feel yourself a soviet censor! Let's ban some words! How many?");
            int bannedAmount = Integer.parseInt(reader.readLine());
            LinkedHashMap<String, Integer> bannedWords = new LinkedHashMap<>(bannedAmount);
            for (int i = 0; i < bannedAmount; i++) {
                System.out.println("Provide a word, comrade:");
                bannedWords.put(reader.readLine().toLowerCase(), 0);
            }
            // Writing the new text to another file
            output = new FileOutputStream(System.getProperty("user.dir") + File.separator + "Test2.txt");
            ArrayList<String> wordsWithoutBanned = new ArrayList<>(wordsFromFile);
            FileOutputStream finalOutput = output;
            bannedWords.entrySet().forEach(x -> {
                for (int i = 0; i < wordsWithoutBanned.size(); i++) {
                    if (x.getKey().equals(wordsWithoutBanned.get(i)) || x.getKey().equalsIgnoreCase(wordsWithoutBanned.get(i))) {
                        x.setValue(x.getValue() + 1);
                        wordsWithoutBanned.remove(i);
                    } else {
                        try {
                            finalOutput.write((wordsWithoutBanned.get(i) + " ").getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            });
            bannedWords.entrySet().forEach(x -> {
                System.out.println("\"" + x.getKey() + "\"" + " was found " + x.getValue() + " time(s).");
            });
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

