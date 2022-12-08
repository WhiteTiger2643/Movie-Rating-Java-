import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        var movieCatalog = new MovieCatalog();
        movieCatalog.MainMenu();
    }
}

class MovieCatalog {
    private Scanner input = new Scanner(System.in);
    String userName = "admin";
    String passWord = "password";

    //movies object stores all the movies in the system.
    private ArrayList<Movie> movies = new ArrayList<>();

    //selectedMovie object is use for searching/adding comments/liking comments
    private Movie selectedMovie;

    //MainMenu will be shown to the user when the program runs
    public void MainMenu() {
        int option;
        do {
            System.out.print("1:AdminMenu\n2:UserMenu\n3:Display all movies\n4:Exit\nEnter: ");
            option = input.nextInt();
            switch (option) {
                case 1:
                    // verify the admin login
                    System.out.print("Please login first:\nUsername: ");
                    String username = input.next();
                    System.out.print("Password: ");
                    String password = input.next();
                    if (username.equals(userName) && password.equals(passWord)) {
                        // if login correct go to the admin menu
                        AdminMenu();
                    } else {
                        System.out.print("Login Failed.");
                        break;
                    }
                    break;

                case 2:
                    UserMenu();
                    break;

                case 3:
                    DisplayAllMovies();
                    break;

                case 4:
                    // confirm they want to exit and then exit
                    System.out.print("Do you really want to exit?\n1:Yes\n2:No\nEnter:");
                    int confirmation = input.nextInt();
                    input.nextLine();
                    if (confirmation == 1) {
                        return;
                    }
            }
        } while (option > 0 && option < 5);
    }

    //AdminMenu renders the Admin functionalities
    public void AdminMenu() {
        int option;
        do {
            System.out.print("WELCOME ADMIN!\nWhat do you want to do?\n1:Add a movie\n2:Update a movie\n3:Delete a movie\n4:Return to main menu\nEnter: ");
            option = input.nextInt();
            input.nextLine(); // Consume newline left-over
            switch (option) {
                case 1:
                    AddMovie();
                    break;
                case 2:
                    System.out.print("Update movie");
                    break;
                case 3:
                    DeleteMovie();
                    break;
            }
        } while (option > 0 && option < 4);
    }

    //UserMenu renders the User functionalities
    public void UserMenu() {
        int option;
        do {
            System.out.print("1:Search a movie\n2:Rate a movie\n3:Add comments about a movie\n4:Like a comment about a movie\n5:Return to main menu\nEnter: ");
            option = input.nextInt();
            input.nextLine();
            switch (option) {
                case 1:
                    SearchMovie();
                    break;
                case 2:
                    RateMovie();
                    break;
                case 3:
                    AddComments();
                    break;
                case 4:
                    LikeComment();
                    break;
            }
        } while (option < 5 && option > 0);
    }

    //RateMovie is used for rating a movie
    public void RateMovie() {
        if (selectedMovie == null) {
            SearchMovie();
        }
        System.out.println("Selected movie: " + selectedMovie.title);
        System.out.println("Enter your rating 1-10: ");
        int rate = input.nextInt();
        input.nextLine();
        Movie foundMovie = new Movie();
        for (Movie m : movies) {
            if (m.title.equalsIgnoreCase(selectedMovie.title)) {
                foundMovie = m;
                break;
            }
        }
        foundMovie.userRating += rate;
        foundMovie.timesRated += 1;
    }

    //AddComments is used for adding a comment to a movie
    public void AddComments() {
        if (selectedMovie == null) {
            SearchMovie();
        }
        System.out.println("Selected movie: " + selectedMovie.title);
        System.out.print("Add your comment: ");
        String userComment = input.nextLine();
        Comment commentObject = new Comment();
        Movie foundMovie = new Movie();
        for (Movie m : movies) {
            if (m.title.equalsIgnoreCase(selectedMovie.title)) {
                foundMovie = m;
                break;
            }
        }
        commentObject.text = userComment;
        foundMovie.comments.add(commentObject);
    }

    //DisplayAllMovies will display all the movies stored in the system
    public void DisplayAllMovies() {
        if (movies.size() < 1) {
            System.out.println("No movie in the list");
        } else {
            System.out.println("List of Movies:\n");
            for (Movie movie : movies) {
                // Print all elements of ArrayList
                System.out.println(movie + "\n");
            }
        }
    }

    //AddMovie will be used to add a movie to the system
    public void AddMovie() {
        boolean flag = true;
        while (flag) {
            Movie movie = new Movie();
            System.out.print("Enter title of the movie: ");
            movie.title = input.nextLine();
            System.out.print("Enter description of the movie: ");
            movie.description = input.nextLine();
            System.out.print("Enter name of the first actor: ");
            movie.actor1 = input.nextLine();
            System.out.print("Enter name of the second actor: ");
            movie.actor2 = input.nextLine();
            System.out.print("Enter name of the director: ");
            movie.director = input.nextLine();
            System.out.print("Enter name of the producer: ");
            movie.producer = input.nextLine();
            System.out.print("Enter name of the studio: ");
            movie.studio = input.nextLine();
            System.out.print("Enter release year: ");
            movie.releaseYear = input.nextInt();
            System.out.print("Enter MPAA rating: ");
            movie.rate = input.next();
            System.out.print("Enter gross box office: ");
            movie.grossBox = input.nextInt();
            movies.add(movie);
            System.out.print("Movie has been added.\nDo you want to add a new movie?\n1:Yes\n2:No\nEnter: ");
            int option = input.nextInt();
            input.nextLine();
            switch (option) {
                case 1:
                    continue;
                case 2:
                    flag = false;
                    break;
            }
        }
    }

    //SearchMovie will be used to search a movie in the system
    public void SearchMovie() {
        ArrayList<Movie> searchedMovie = new ArrayList<>();
        System.out.print("Enter title of the movie to search: ");
        String movieName = input.nextLine();
        movieName = movieName.replaceAll("\\s", "").toLowerCase();
        for (int i = 0; i < movies.size(); i++) {
            if ((movies.get(i).title.replaceAll("\\s", "").toLowerCase().equals(movieName))) {
                searchedMovie.add((movies.get(i)));
            }
        }

        if (searchedMovie.size() == 0) {
            System.out.print("No movie found!");
        } else if (searchedMovie.size() > 1) {
            int incrementer = 1;
            System.out.println("Which movie you want to select?");
            for (int i = 0; i < searchedMovie.size(); i++) {
                System.out.println(incrementer + ": " + searchedMovie.get(i).title);
                incrementer++;
            }
            System.out.print("Enter: ");
            int option = input.nextInt();
            input.nextLine();
            System.out.println("Movie Details:\n" + searchedMovie.get(option - 1));
            selectedMovie = searchedMovie.get(option - 1);
        } else {
            selectedMovie = searchedMovie.get(0);
            System.out.println("Movie Details:\n" + selectedMovie);
        }
    }

    //DeleteMovie will be used to delete a movie from the system
    public void DeleteMovie() {
        int incrementer = 1;
        for (Movie m : movies
        ) {
            System.out.println(incrementer + ": " + m.title);
            incrementer++;
        }
        System.out.print("Which movie you want to delete?\nEnter movie number: ");
        int movieNumber = input.nextInt();
        movies.remove(movieNumber - 1);
        System.out.println("Movie has been deleted.");
    }

    public void LikeComment() {
        if (selectedMovie == null) {
            SearchMovie();
        }
        if (selectedMovie.comments.isEmpty()) {
            System.out.println("No comments found.");
            return;
        }
        int incrementer = 1;
        System.out.println("Selected movie: " + selectedMovie.title);
        System.out.println("Select a comments to like: ");
        for (Comment comment : selectedMovie.comments) {
            System.out.println(incrementer + ": " + comment.text);
        }
        int option = input.nextInt();
        selectedMovie.comments.get(option - 1).numLikes++;
    }
}

//Class use to create a Movie object
class Movie {
    public String title;
    public String description;
    public String actor1;
    public String actor2;
    public String director;
    public String producer;
    public String studio;
    public int releaseYear;
    public String rate;
    public int grossBox;
    public float userRating = 0;
    public int timesRated;


    public ArrayList<Comment> comments = new ArrayList<>();

    //this function will return the details of a movie
    public String toString() {
        return "Title: " + title + "\nDescription: " + description + "\nActor_1: " + actor1 + "\nActor_2: " + actor2
                + "\nDirector: " + director + "\nProducer: " + producer + "\nStudio: " + studio + " " + releaseYear
                + "\nGrossBox: " + grossBox + "\nMPAA Rating: " + rate + "\nUser Ratings: " + getUserRating() +
                "\nComments: " + DisplayComments();
    }

    //this function will return the user rating about a movie
    public float getUserRating() {
        if (timesRated > 0) {
            return userRating / timesRated;
        }
        return 0;
    }

    //this function will display all the comments in the movie details
    public List<String> DisplayComments() {
        return comments.stream().map(comment -> {
            return comment.text + " (+" + comment.numLikes + ")";
        }).collect(Collectors.toList());
    }


}

//Class use to create a comment object
class Comment {
    public String text;
    public int numLikes;
}