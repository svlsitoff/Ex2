import java.util.ArrayList;
import java.util.List;

public class Borrower {
    /*In the constructor, we initialize
     the fields, as well as an empty
      list of Book objects*/
    public Borrower(int id, String name, int age) {
        Id = id;
        Name = name;
        Age = age;
        booklist = new ArrayList<>();
    }

    private  int Id;
    private String Name;
    private int Age;
    private ArrayList<Book> booklist;

    /*When adding a book to the borrower's list,
     we determine the success of the
     operation, and upon successful
     completion of the method, return true*/
    public boolean AddBook(Book book){
        if(booklist.contains(book)){
            System.out.println("This book is already on hand");
            return false;
        }
        if(book.getRating().equalsIgnoreCase("adult")){
            if(Age>=18){
                booklist.add(book);
                return true;
            }
            if(Age<18){
                System.out.println("This book is for adults only :(");
                return false;
            }

        }
        if(booklist.size()>=2){
            System.out.println("The limit on the number of books on hand is exceeded");
            return false;
        }
        booklist.add(book);
        return true;
    }
    /* When deleting a book from
     the borrower's list, we check
      the success of the operation*/
    public boolean RemoveBook(Book book){
        if(booklist.contains(book)){
            booklist.remove(book);
            return true;
        }
        else return false;
    }
    public List<Book> GetBookList(){
        return booklist;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public int getAge() {
        return Age;
    }
}
