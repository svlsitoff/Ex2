import java.util.Scanner;

/*In the user interface class,
we define objects for storing
data, as well as methods that
 will change the states of these
  objects. Working with data
  objects will be done through
  the methods of the user
  interface (through reading
  data from the console)*/
public class UI {
	BorrowedDataBase base;
	BookDatabase bookDatabase;
	Scanner scanner = new Scanner(System.in);
	public void Start() {
		base = new BorrowedDataBase("Borrowers.txt");
		bookDatabase = new BookDatabase("books.txt");
		base.LoadData();
		bookDatabase.LoadData();
		while (MainMenu()) ;
	}

	/*Main menu of the user interface.
	 This method reads information
	 from the console input and calls
	  the corresponding method
	  to work with the data.*/
	public boolean MainMenu() {

		System.out.println("Welcome to the My Library");
		System.out.println("====================");
		System.out.println("(1) Register New Borrower");
		System.out.println("(2) Manage Borrower");
		System.out.println("(3) List All Borrowers");
		System.out.println("(4) Display Help");
		System.out.println("(5) Exit Library");
		System.out.println("Choose an option: ");
		String choisStr = scanner.nextLine();
		if(tryParseInt(choisStr)) {
			int chois = Integer.parseInt(choisStr);
			switch (chois) {
				case 1:
					while(Register());
						return true;


				case 2:
						Borrower borrower = BorrowerFind();
						if(borrower!=null){
							while(BorowerMenu(borrower));
						}else {
							System.out.println("The specified borrower could not be found");
						}
					return true;


				case 3:
					for (Borrower itm : base.getBorrowers()) {
						System.out.println(itm.getId()+" "+itm.getAge()+" "+itm.getName());
					}
					return true;

				case 4:
					System.out.println("To register a new borrower, enter the name followed by the borrower's age.\n" +
							"To select a borrower, select 3 from the main menu and indicate the name and age of the borrower.\n" +
							"In order to indicate which book the borrower should add, indicate the book number from the list.\n" +
							"To delete a book, select the book number from the list. \n"+
							"To exit the application press 5 in the main menu\n");
					return true;

				case 5:
					return false;


				default:

					break;
			}
		}

		return true;

	}
	private boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	/*In this method, we create a borrower
	 object and add it to the list
	  of similar objects. After adding
	   the object, a method is called
	    that saves these lists in a file*/
	private boolean Register() {
		System.out.println("Enter the following data for registration\n or enter end to exit");
		System.out.println("Name");
		String nameString = scanner.nextLine();
		if(nameString.equalsIgnoreCase("end")) {
			System.out.println("Exiting the registration menu");
			return false;
		}
		System.out.println("Age:");
		String ageString = scanner.nextLine();
		if(!nameString.isEmpty()&&tryParseInt(ageString))
		{
			int age = Integer.parseInt(ageString);
			if(age<6){
				System.out.println("Incorrect value for age. The minimum value is 6 years");
				return false;
			}
			int id = GetId(base);

				Borrower borrower = new Borrower(id, nameString, age);
				base.AddBorrower(borrower);
				base.SaveData();
				System.out.println("A new borrower is registered");
				return false;
		}else {
			System.out.println("The entered data is not correct");
			return true;
		}

	}
	/*This method generate borrower Id*/
	private int GetId(BorrowedDataBase base) {
		int needId=0;
		int[] Ids = new int[100];
		for(int i=0;i<Ids.length;i++) {
			Ids[i]=i;
		}
		if(base.getBorrowers().size()<Ids.length-1) {

			for (int i : Ids)
			{
				int findedId = i;
				boolean unic = true;

				for (Borrower item : base.getBorrowers())
				{
					if(findedId==item.getId()) unic = false;
				}
				if(unic==false)continue;

				if(unic) {
					needId = findedId;
					return needId;
				}
			}
		}else {
			return 0;
		}
		return 0;


	}
	/*This method will return the borrower object when reading data from the console*/
	private Borrower BorrowerFind() {

		Borrower borrower;

		System.out.println("Enter the borrower's name :\t");
		String namestr = scanner.nextLine();

		System.out.println("Enter the borrower's Age:\t");

		String agestr = scanner.nextLine();
		if(!namestr.isEmpty()&&tryParseInt(agestr)) {
			int age = Integer.parseInt(agestr);
			if(age>0) {
				for(Borrower item : base.getBorrowers()) {
					if(item.getAge()==age && item.getName().equals(namestr)){
						borrower = item;
						return borrower;
					}



				}

			}else {
				System.out.println("Failed to find borrower");
			}

		}else {
			System.out.println("The entered data is not correct");
		}
		return null;
	}
	/*this method works with the list of books for the borrower through console input.*/
	private boolean BorowerMenu(Borrower borrower) {

		    System.out.println("Select an option:");
			System.out.println("====================");
			System.out.println("(1) Borrow a Book");
			System.out.println("(2) Return a Book");
			System.out.println("(3) List Borrowed Books");
			System.out.println("(4) Return to Main Menu");
			System.out.println("Choose an option: ");
			String choisStr="";
			if(!scanner.hasNextLine()){
				scanner.nextLine();
				choisStr = scanner.nextLine();
			}else {
				choisStr= scanner.nextLine();
			}

			if(tryParseInt(choisStr)) {
				int chois = Integer.parseInt(choisStr);
				switch (chois) {
					case 1:
						Book book = BorrowBook();
						if(book!=null&&borrower.AddBook(book)==true) {
							base.SaveData();
							System.out.println("Book added");
							return true;
						}else {
							return true;
						}

					case 2:
						Book bookReturn = ReturnBook(borrower);
						if(bookReturn!=null&&borrower.RemoveBook(bookReturn)==true) {
							System.out.println("Book returned to lybrary");
							base.SaveData();
							return true;
						}else {
							return true;
						}

					case 3:
						if(borrower.GetBookList().size()>0){
							for(Book itemBook : borrower.GetBookList())
							{
								System.out.println(itemBook.getTitle()+" "+itemBook.getAutor()+" "+itemBook.getRating());
							}
						}else {
							System.out.println("Borrower has no books");
						}

						return true;



					case 4:

						return false;




					default:

						break;



			}
		}
		return false;
	}
	/*This method returns the object of the requested book*/
	private Book BorrowBook() {
		System.out.println("List of books available");
		for(int i=0;i<bookDatabase.getBooks().size();i++) {
			Book item = bookDatabase.getBooks().get(i);
			System.out.println(i+" "+item.getAutor()+" "+item.getTitle()+" "+item.getRating());
		}
		System.out.println("Specify the book number from the list ");

		String numString = "";
		if(!scanner.hasNextLine()){
			scanner.nextLine();
			numString = scanner.nextLine();
		}else {
			numString = scanner.nextLine();
		}
		if(tryParseInt(numString)) {
			int num = Integer.parseInt(numString);
			if(num>=0 && num<bookDatabase.getBooks().size()) {
				Book book = bookDatabase.getBooks().get(num);
				return book;
			}


		}else {
			System.out.println("The entered data is not correct");

			return null;
		}

		return null;

	}
	/*This method returns the book object that the borrower has for deletion*/
	private Book ReturnBook(Borrower borrower) {
		if(borrower.GetBookList().size()==0){
			System.out.println("No books on hand");
			return null;

		}
		System.out.println("List of books available on borrower's hands");
		for(int i=0;i<borrower.GetBookList().size();i++) {
			Book item = borrower.GetBookList().get(i);
			System.out.println(i+" "+item.getAutor()+" "+item.getTitle()+" "+item.getRating());
		}
		System.out.println("Specify the book number which need to return");
		Scanner scanner = new Scanner(System.in);
		String numString = scanner.nextLine();
		if(tryParseInt(numString)) {
			int num = Integer.parseInt(numString);
			if(num>=0 && num<borrower.GetBookList().size()) {
				Book book = borrower.GetBookList().get(num);
				return book;
			}


		}else {
			System.out.println("The entered data is not correct");
			return null;
		}
		return null;

	}
	 

}
