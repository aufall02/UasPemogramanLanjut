
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    // attribute
    private final ArrayList<Buku> libraryBooks = DummyData.generateBookList();  // mengisi data dari kelas DummyData
    private final ArrayList<daftarMember> memberList = new ArrayList<>();
    private final ArrayList<Admin> adminList = new ArrayList<>();
    private final ArrayList<Transaction> transactionList = new ArrayList<>();
    private final String libraryName;
    private Admin loggedInAdmin;    //  untuk menampung admin yang sedang login ke sistem
    private daftarMember memberLogin;   // untuk menampung member yang sedang login ke sistem
    private boolean isMenuActive = true;    // untuk perulangan menu

    // inisialisasi kelas scanner untuk Input User
    private final Scanner input = new Scanner(System.in);

    /*
        constructor
     */
    public Library(String libraryName) {
        this.libraryName = libraryName;
        init();
    }

    // inisialisasi ketika objek dibuat sekaligus menampilkan sapaan selamat datang
    private void init(){
        adminList.add(new Admin("aufal marom","aufal02","090602"));

        System.out.println("====================================");
        System.out.println("Selamat Datang di " + getLibraryName());
        System.out.println("====================================");
    }

    /*
        Method
     */
    // method adminMenu() akan di akses melalui class Login Manager, jika user login sebagai admin
    public void adminMenu() {
        System.out.println("Anda berhasil login sebagai admin.");
        isMenuActive = true;
        while (isMenuActive) {
            System.out.println("\nHalo " + getLoggedAdmin().getFullName());
            System.out.println("Menu Admin:");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Update Informasi Buku");
            System.out.println("3. Tampilkan Daftar Buku");
            System.out.println("4. Tampilkan Daftar Transaksi");
            System.out.println("5. LOGOUT");
            System.out.print("Masukkan Pilihan [1-5]: ");
            String userChoice = input.nextLine();
            switch (userChoice) {
                case "1" -> addNewBook();
                case "2" -> updateBookData();
                case "3" -> displayBookList();
                case "4" -> displayTransactionList();
                case "5" -> logout();
                default -> System.out.println("Invalid Input!");
            }
        } // akhir while loop
    } // akhir method



    /*
        method admin untuk menambahkan buku baru
        memanggil fungsi bawaan kelas admin yaitu addNewBook()
        dengan argumen list book yaitu getLibraryBook()
     */
    private void addNewBook() {
        getLoggedAdmin().addNewBook(getLibraryBooks());
    }

    private void updateBookData() {
        getLoggedAdmin().updateBookProperties(getLibraryBooks());
    }

    private void displayTransactionList() {
        System.out.println("\n===========================");
        System.out.println("Menampilkan Daftar Transaksi");
        System.out.println("============================");
        if (getTransactionList().isEmpty()) {
            System.out.println("Belum ada Transaksi.");
        } else {
            // menampilkan item pada ArrayList dengan forEach dan Lambda Expression
            getTransactionList().forEach(transaction -> {
                transaction.tampilkanDetailtraksaksi();
                System.out.println();
            });
        }
    }

    // method nampilin seluruh buku yang ada
    private void displayBookList() {
        System.out.println("\n===========================");
        System.out.println("Menampilkan Daftar Buku");
        System.out.println("============================");
        if (getLibraryBooks().isEmpty()) {
            System.out.println("Belum ada Buku.");
        } else {
            // menampilkan item pada ArrayList dengan forEach dan Lambda Expression
            getLibraryBooks().forEach(buku -> {
                buku.tampilkanDetailBuku();
                System.out.println();
            });
        }
    }

    /*
        method memberMenu() akan di akses melalui class Login Manager, jika user login sebagai member
     */
    public void memberMenu() {
        System.out.println("Anda berhasil login.");
        isMenuActive = true;
        while (isMenuActive) {
            System.out.println("\nHalo " + getMemberLogin().getFullName());
            System.out.println("Silahkan pilih Menu Member:");
            System.out.println("1. Tampilkan Daftar Buku");
            System.out.println("2. Menu Pinjam Buku");
            System.out.println("3. Kembalikan Buku");
            System.out.println("4. Riwayat Peminjaman");
            System.out.println("5. LOGOUT");
            System.out.print("Masukkan Pilihan [1-5]: ");
            String userChoice = input.nextLine();
            switch (userChoice) {
                case "1" -> displayBookList();
                case "2" -> bookBorrowing();
                case "3" -> bookReturning();
                case "4" -> displayUserBorrowingHistory();
                case "5" -> logout();
                default -> System.out.println("Invalid Input!");
            }
        } // akhir while loop
    } // akhir method

    /*
        method untuk member meminjam buku
        memanggil fungsi bawaan kelas LibraryMember yaitu borrowOneBook()
        dengan argumen list book -> getLibraryBook()
     */
    private void bookBorrowing() {

        getMemberLogin().borrowOneBook(getLibraryBooks());
        /*
            menambahkan transaksi dengan argumen: MemberLibrary, Aktivitas, Book
            jika buku yang dipinjam != null dan status ableToBorrow == true,
            maka tambah transaksi baru
         */
        if (getMemberLogin().getCurrentBorrowedBook() != null && getMemberLogin().isAbleToBorrow()) {
            getMemberLogin().setAbleToBorrow(false);   // member tidak bisa meminjam buku sebelum dikembalikan
//            Object buku;
            transactionList.add(new Transaction(getMemberLogin(),
                    "Peminjaman Buku",
                    getMemberLogin().getCurrentBorrowedBook()));
        }
    }

    // method untuk member mengembalikan
    private void bookReturning() {
        /*
            memanggil fungsi bawaan kelas Library Member yaitu returnBook
            fungsi tersebut akan mengembalikan nilai null atau nilai kelas Book
            variabel memberBorrowedBook digunakan untuk menampung buku yang dikembalikan
         */
        Buku memberBorrowedBook = getMemberLogin().kembalikanBuku();

        // jika buku yang dipinjam == null, berarti tidak ada buku yang dikembalikan
        if (memberBorrowedBook != null) {
            getMemberLogin().setAbleToBorrow(true); // member bisa kembali meminjam
            transactionList.add(new Transaction(
                    getMemberLogin(),
                    "Pengembalian Buku",
                    memberBorrowedBook)
            );
        }
    }

    // method untuk menampilkan riwayat peminjaman buku
    private void displayUserBorrowingHistory() {
        getMemberLogin().displayBorrowingHistory();
    }

    void logout() {
        setLoggedMember(null); // set ke null
        setLoggedAdmin(null); // set ke null
        LoginManager.setIsLogManagerActive(true); // mengaktifkan menu pada kelas LoginManager
        System.out.println("\nBerhasil logout.");
        isMenuActive = false;   // agar looping menu pada kelas Library berhenti
    }

    /*
        Getter Setter
     */
    public Admin getLoggedAdmin() {
        return loggedInAdmin;
    }

    public void setLoggedAdmin(Admin loggedInAdmin) {
        this.loggedInAdmin = loggedInAdmin;
    }

    public daftarMember getMemberLogin() {
        return memberLogin;
    }

    public void setLoggedMember(daftarMember loggedInMember) {
        this.memberLogin = loggedInMember;
    }

    public ArrayList<Buku> getLibraryBooks() {
        return libraryBooks;
    }

    public ArrayList<daftarMember> getMemberList() {
        return memberList;
    }

    public ArrayList<Admin> getAdminList() {
        return adminList;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public ArrayList<Transaction> getTransactionList() {
        return transactionList;
    }
}
