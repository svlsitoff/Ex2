public class Book {
    private  String Title;
    private String Autor;
    private String Rating;
    public Book(String titl,String Aut, String rtg){
        Title = titl;
        Autor = Aut;
        Rating  = rtg;
    }

    public String getTitle() {
        return Title;
    }

    public String getAutor() {
        return Autor;
    }

    public String getRating() {
        return Rating;
    }
}
