package org.example;

import org.example.entity.Author;
import org.example.entity.Book;
import org.example.entity.Loan;
import org.example.entity.User;
import org.example.enums.Genre;
import org.example.exceptions.BookDoesNotExistException;
import org.example.exceptions.LoanDoesNotAllowedException;
import org.example.exceptions.UserDoesNotExistException;
import org.example.managers.LoanManager;
import org.example.service.loan.LoanService;
import org.example.service.loan.LoanServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LibraryFactory {

    static List<Author> authors = Arrays.asList(
            new Author("Jan", "Kowalski"),
            new Author("Anna", "Nowak"),
            new Author("Piotr", "Wiśniewski"),
            new Author("Katarzyna", "Wójcik"),
            new Author("Tomasz", "Kowalczyk"),
            new Author("Michał", "Zieliński"),
            new Author("Ewa", "Szymańska"),
            new Author("Marcin", "Kamiński"),
            new Author("Agnieszka", "Lewandowska"),
            new Author("Robert", "Dąbrowski"));

    static List<User>  users;
    static List<Book> books;
    static{
        String[] firstNames = {"Heather", "Tracey", "April", "Bradley", "Jacob", "Tina", "Mindy", "Nicholas", "John", "Trevor"};
        String[] lastNames = {"Roberts", "Mayo", "Baldwin", "Hughes", "Lynch", "Gonzales", "Gibson", "Morrison", "King", "Barber"};
        users = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 13; i++) {
            users.add(new User(firstNames[random.nextInt(10)], lastNames[random.nextInt(10)]));
        }
    }
    static {
        String[] titles = {"Versatile Zero-Defect Algorithm", "Inverse Scalable Emulation", "Centralized Object-Oriented Attitude", "Reactive Global Product", "Cloned Local Core", "Persistent 4th-Generation Core", "Centralized Content-Based Intranet", "Vision-Oriented Global Matrices", "Up-Sized Content-Based Array", "Proactive Systematic Project", "Devolved Bottom-Line Methodology", "Persistent Optimal Solution", "Vision-Oriented Didactic Functionalities", "User-Centric Bottom-Line Support", "Phased Fresh-Thinking Methodology", "Fully-Configurable Directional Website", "Operative Disintermediate Capacity", "Monitored Real-Time Productivity", "Fundamental Holistic Installation", "Visionary 3rd-Generation Toolset", "Profound Value-Added Concept", "Visionary Responsive Functionalities", "User-Friendly Discrete Workforce", "Mandatory Neutral Concept", "Managed Regional Strategy", "Exclusive Encompassing Portal", "Expanded Web-Enabled Hardware", "Progressive Radical Capability", "Seamless Tangible Algorithm", "Front-Line Motivating Implementation", "Enterprise-Wide Full-Range Capability", "Reverse-Engineered Intermediate Paradigm", "Monitored User-Facing Adapter", "Business-Focused Executive Collaboration", "Focused Homogeneous Parallelism", "Fully-Configurable Empowering Monitoring", "Optional Eco-Centric Attitude", "Reduced Scalable Time-Frame", "Optimized Coherent Architecture", "Assimilated Systemic Info-Mediaries", "Persistent Multi-State Conglomeration", "Centralized Mobile Product", "Secured Human-Resource Initiative", "Sharable Client-Server Solution", "Synergistic Interactive Infrastructure", "Pre-Emptive 24-Hour Model", "Streamlined Modular Hierarchy", "Innovative Optimizing Structure", "Persistent Global Approach", "Multi-Tiered Even-Keeled Definition"};
           books = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            //geting random author
            Author author = authors.get(random.nextInt(authors.size()));
            //getting random title
            String title = titles[random.nextInt(50)];
            //getting random genres
            int x = random.nextInt(Genre.values().length);
            Genre genre = Genre.values()[x];
            //getting random date
            LocalDate date = generateRandomDate();
            //getting random amount of books;
            int ammout = random.nextInt(6)+1;


            books.add(new Book(author, title, genre, date, ammout));
        }
    }
    public static List<Book> getBooks(int number, List<Author> authors) {


        return books;
    }

    public static LocalDate generateRandomDate() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.now();

        Random random = new Random();

        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomDay = (long) (startEpochDay + random.nextDouble() * (endEpochDay - startEpochDay));

        return LocalDate.ofEpochDay(randomDay);
    }


    public static List<Author> generateAuthors() {

        return authors;
    }

    public static List<User> generateUsers(int number){

        return users;
    }


    public static List<Loan> generateLoans(List<User> users1, List<Book> books1) {
        List<Loan> loans = new ArrayList<>();
        Random random = new Random();
        Book book;
        User user;
        int bookId;
        int userId;
        try {

            for (int i = 0; i < users1.size(); i++) {
                bookId = random.nextInt(books1.size());
                userId = random.nextInt(users1.size());
                 book = books1.get(bookId);
                 user = users.get(userId);
                if (book.getQuantityOfAvailableBooks()>0) {
                    loans.add(new Loan(user, book));
                    user.addBook(book);
                    book.increaseRentedQuantity();
                } else {
                    throw new LoanDoesNotAllowedException("There is no avaible book with id: " + book.getId());
                }
            }

        }
        catch ( LoanDoesNotAllowedException e){
            System.out.println(e.getMessage());
        }
        return loans;
    }
}
