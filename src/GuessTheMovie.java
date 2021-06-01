import java.io.File;
import java.util.*;

public class GuessTheMovie {
    public static void main(String[] args) throws Exception {
        MovieList list = new MovieList();
        list.loadMovieList();
        Game game = new Game(list.movieList);
        game.chooseMovie(list.movieList);

        // guess the movie
        int totalGuess = 10;
        int remainingGuess = 10;
        Set<Character> rightGuessesSet = new HashSet<>();
        Set<Character> wrongGuessesSet = new HashSet<>();
        while (remainingGuess > 0){
            System.out.println("You are guessing: " + game.movieCasted);
            System.out.println("You have guessed (" + wrongGuessesSet.size() + ") wrong letters: " + wrongGuessesSet.toString());
            System.out.println("Guess a letter: ");
            Scanner input = new Scanner(System.in);
            char guess = input.nextLine().charAt(0);

            if (game.map.containsKey(guess)){
                if (rightGuessesSet.contains(guess)){
                    continue;
                } else {
                    List<Integer> toFill = game.map.get(guess);
                    for (Integer integer : toFill) {
                        game.movieCasted.set(integer, guess);
                        game.toWin--;
                    }
                    rightGuessesSet.add(guess);
                }
            } else {
                wrongGuessesSet.add(guess);
                remainingGuess = totalGuess - wrongGuessesSet.size();
            }

            if (game.toWin == 0){
                System.out.println("You Win!");
                System.out.println("You have guessed '" + game.movieSelected + "' correctly.");
                break;
            }
        }
        if (game.toWin > 0){
            System.out.println("You fail!");
            System.out.println("The movie is: " + game.movieSelected);
        }
    }


}

class MovieList{
    List<String> movieList;

    MovieList(){
        movieList = new ArrayList<>();
    }

    void loadMovieList() throws Exception{
        // movie list - array list of string
        File file = new File("movies.txt");
        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            String movie = fileScanner.nextLine();
            movieList.add(movie);
        }
    }
}

class Game{
    int randomNumber;
    String movieSelected;
    List<Character> movieCasted;
    Map<Character, List<Integer>> map;
    int toWin;

    Game(List<String> movieList){
        randomNumber = ((int) (Math.random() * 100)) % movieList.size();
        movieSelected = movieList.get(randomNumber);
        movieCasted = new ArrayList<>();
        map = new HashMap<>();
        toWin = 0;
    }

    // choose a movie randomly, cast it, and map its letters
    void chooseMovie(List<String> movieList){
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
    }

    void addToMap(Map<Character, List<Integer>> map, int i, char a){
        if (!map.containsKey(a)){
            map.put(a, new ArrayList<>());
        }
        map.get(a).add(i);
    }
}
