import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GuessTheMovie {
    public static void main(String[] args) throws Exception {
        // movie list - array list of string
        File file = new File("movies.txt");
        Scanner fileScanner = new Scanner(file);

        List<String> movieList = new ArrayList<>();
        while (fileScanner.hasNextLine()) {
            String movie = fileScanner.nextLine();
            movieList.add(movie);
        }

        // choose a movie randomly, cast it, and map its letters
        int randomNumber = ((int) (Math.random() * 100)) % movieList.size();
        String movieSelected = movieList.get(randomNumber);
        System.out.println("(" + movieSelected + ")");
        List<Character> movieCasted = new ArrayList<>();
        Map<Character, List<Integer>> map = new HashMap<>();
        int toWin = 0;
        for (int i = 0; i < movieSelected.length(); i++){
            if (movieSelected.charAt(i) == ' ') {
                movieCasted.add(i, ' ');
                addToMap(map, i, ' ');
            } else {
                movieCasted.add(i, '_');
                addToMap(map, i, movieSelected.charAt(i));
                toWin++;
            }
        }

        // guess the movie
        int totalGuess = 10;
        int remainingGuess = 10;
        Set<Character> rightGuessesSet = new HashSet<>();
        Set<Character> wrongGuessesSet = new HashSet<>();
        while (remainingGuess > 0){
            System.out.println("You are guessing: " + movieCasted);
            System.out.println("You have guessed (" + wrongGuessesSet.size() + ") wrong letters: " + wrongGuessesSet.toString());
            System.out.println("Guess a letter: ");
            Scanner input = new Scanner(System.in);
            char guess = input.nextLine().charAt(0);

            if (map.containsKey(guess)){
                if (rightGuessesSet.contains(guess)){
                    continue;
                } else {
                    List<Integer> toFill = map.get(guess);
                    for (Integer integer : toFill) {
                        movieCasted.set(integer, guess);
                        toWin--;
                    }
                    rightGuessesSet.add(guess);
                }
            } else {
                wrongGuessesSet.add(guess);
                remainingGuess = totalGuess - wrongGuessesSet.size();
            }

            if (toWin == 0){
                System.out.println("You Win!");
                System.out.println("You have guessed '" + movieSelected + "' correctly.");
                break;
            }
        }
        if (toWin > 0){
            System.out.println("You fail!");
            System.out.println("The movie is: " + movieSelected);
        }
    }

    private static void addToMap(Map<Character, List<Integer>> map, int i, char a){
        if (!map.containsKey(a)){
            map.put(a, new ArrayList<>());
        }
        map.get(a).add(i);
    }
}
