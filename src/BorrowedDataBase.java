import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BorrowedDataBase {
    private List<Borrower> borrowers;

    private String path;
    public BorrowedDataBase(String pth){
        borrowers = new ArrayList<>();
        path = pth;
    }
    public boolean LoadData(){
        List<Borrower> temp = new ArrayList<>();
        try
        {
            FileInputStream fis=new FileInputStream(path);
            Scanner sc=new Scanner(fis);
            while(sc.hasNextLine())
            {
                String line = sc.nextLine();
                String[]sublines =line.split(",");
                if(sublines.length==3||sublines.length==6||sublines.length==9)
                {

                    String name = sublines[0];
                    String IdStr = sublines[1];
                    String AgeStr = sublines[2];
                    int id = 0;
                    int age = 0;
                    if(tryParseInt(IdStr)&&tryParseInt(AgeStr)){
                        id = Integer.parseInt(IdStr);
                        age = Integer.parseInt(AgeStr);
                    }else return false;
                    if(id>=0&&age>0){
                        Borrower borrower = new Borrower(id,name,age);
                        if(sublines.length==3){
                            temp.add(borrower);
                            continue;
                        }
                        if(sublines.length == 6 ){

                            String Title = sublines[3];
                            String Author = sublines[4];
                            String Rating = sublines[5];
                            Book book = new Book(Title,Author,Rating);
                            borrower.AddBook(book);
                            temp.add(borrower);
                        }
                        if(sublines.length == 9 ){

                            String Title = sublines[3];
                            String Author = sublines[4];
                            String Rating = sublines[5];
                            Book book1 = new Book(Title,Author,Rating);
                            borrower.AddBook(book1);

                            Title = sublines[6];
                            Author = sublines[7];
                            Rating = sublines[8];
                            Book book2 = new Book(Title,Author,Rating);
                            borrower.AddBook(book2);
                            temp.add(borrower);
                        }
                    }
                }

            }
            sc.close();
            if (temp.size()>0){
                borrowers=temp;
                return true;
            }else {
                return false;
            }

        }
        catch(IOException e)
        {
           return false;
        }
    }
   private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public void AddBorrower(Borrower borrower){
        borrowers.add(borrower);

    }
    public boolean SaveData()  {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for(Borrower borrower : borrowers)
            {
                String temp = "";
                temp  = borrower.getName()+","+borrower.getId()+","+borrower.getAge()+",";
                for(Book book : borrower.GetBookList()){
                    temp+=book.getTitle()+","+book.getAutor()+","+book.getRating()+",";
                }
                temp = temp.substring(0, temp.length() - 1);
                writer.write(temp);
                writer.newLine();
            }

            writer.close();
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public List<Borrower> getBorrowers() {
        return borrowers;
    }
}
