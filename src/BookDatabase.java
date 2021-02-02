import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookDatabase {
    private List<Book> books;
    private String path;
    public BookDatabase(String pth){
        books = new ArrayList<>();
        path = pth;
    }
   /*
	public void setBooks(List<Book> books) {
		this.books = books;
	}*/
	public boolean LoadData(){
        List<Book> temp = new ArrayList<>();
        try
        {
            FileInputStream fis=new FileInputStream(path);
            Scanner sc=new Scanner(fis);
            while(sc.hasNextLine())
            {
                String line = sc.nextLine();
                String[]sublines =line.split(",");
                if(sublines.length!=3) return false;

                String Title = sublines[0];
                String Autor = sublines[1];
                String Rating = sublines[2];

                if(Title.length()>0&&Autor.length()>0&&(Rating.equalsIgnoreCase("Adult")||Rating.equalsIgnoreCase("General")))
                    {
                      Book book = new Book(Title,Autor,Rating);
                      temp.add(book);
                    }


            }
            sc.close();
            if(temp.size()>0){
                books = temp;
                return true;
            }

        }
        catch(IOException e)
        {
            return false;
        }
        return false;
    }
	 public List<Book> getBooks() {
			return books;
		}



}
