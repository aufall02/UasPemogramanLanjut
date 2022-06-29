
import java.util.ArrayList;
import java.util.Scanner;


enum InputBookType {
    JUDUL,
    KATAGORI,
    AUTHOR,
}


public class Admin extends User {
    private final Scanner input = new Scanner(System.in);
    private boolean aktivasi = true;

    Admin(String fullName, String userName, String password) {
        super(fullName, userName, password, "Admin");
    }

//    public Admin() {
//    }

    public void addNewBook(ArrayList<Buku> daftarbuku){
        System.out.print("Judul buku : ");
        String judulBuku = input.nextLine();
        System.out.print("Kategori buku : ");
        String kategoriBuku = input.nextLine();
        System.out.print("Author : ");
        String author = input.nextLine();


//        membuat objek buku untuk ditambahkan ke array list
        daftarbuku.add(new Buku(judulBuku,kategoriBuku,author));
        System.out.println("Berhasil menambahkan buku \"" + judulBuku + "\" ke data Perpustakaan.");
    }

    /*
        method update informasi buku
     */
    public void updateBookProperties(ArrayList<Buku> bookList) {
        aktivasi = true; // agar dapat mengakses menu
        while (aktivasi) {
            System.out.println("\n=== Menu Update Buku ===");
            System.out.println("Silahkan temukan buku yang akan dirubah.");
            System.out.println("1. Tampilkan seluruh buku");
            System.out.println("2. Tampilkan buku berdasarkan Abjad");
            System.out.println("3. Batal dan kembali");
            System.out.print("Masukkan Pilihan [1-3]: ");
            String userChoice = input.nextLine();
            switch (userChoice) {
                case "1" -> displayAllBooks(bookList);
                case "2" -> showByAlphabet(bookList);
                case "3" -> aktivasi = false;
                default -> System.out.println("Invalid Input!");
            }
        }
    }

    private void displayAllBooks(ArrayList<Buku> listBuku) {
        // menampilkan seluruh buku menggunakan forEach() dan lambda expression
        listBuku.forEach(buku -> {
            System.out.print("ID: " + buku.getIdBuku());
            System.out.println(" - Title: " + buku.getJudulBuku());
        });
        // user diminta memilih buku
        choosingBookToUpdate(listBuku);
    }

    private void showByAlphabet(ArrayList<Buku> listBuku) {
        boolean isAvailable = false;
        System.out.print("Masukkan huruf alphabet: ");
        String alphabet = input.nextLine();
        for (Buku buku: listBuku) {
            // mengecek apakah ada buku yang dimulai dengan huruf yang diinput user
            if (Buku.getJudulBuku().toLowerCase().charAt(0) == alphabet.toLowerCase().charAt(0)) {
                System.out.print("ID: " + buku.getIdBuku());
                System.out.println(" - Title: " + buku.getJudulBuku());
                isAvailable = true;
            }
        }

        // jika ada maka admin diminta memilih buku
        if (isAvailable) {
            choosingBookToUpdate(listBuku);
        } else {
            System.out.println("Buku tidak ditemukan.");
        }
    }

    private void choosingBookToUpdate(ArrayList<Buku> listBuku) {
        boolean isIdMatched = false;
        System.out.println("Info: input '0' untuk batal.");
        System.out.print("ID buku pilihan anda: ");
        String userChoice = input.nextLine();
        if (!userChoice.equals("0")) {
            for (Buku buku : listBuku) {
                // jika id yang diinputkan user == id buku
                if (userChoice.equals(buku.getIdBuku())) {
                    isIdMatched = true;
                    updateBook(buku);
                }
            }
        }

        if (!userChoice.equals("0") && !isIdMatched) {
            System.out.println("Invalid input");
        }
    }

    private void updateBook(Buku buku) {
        System.out.println("\nMenampilkan informasi sebelum diubah");
        buku.tampilkanDetailBuku();

        System.out.println("\n== [NOTE] Input '-' (strip) jika tidak ingin merubah. ==");
        // menerima input
        System.out.print("Judul Buku: ");
        String judulBuku = input.nextLine();
        System.out.print("Kategori: ");
        String kategori = input.nextLine();
        System.out.print("Penulis: ");
        String author = input.nextLine();


        System.out.println("Anda ingin mengubah informasi buku:");
        System.out.println("\"" + buku.getIdBuku() + "\"");
        System.out.print("Konfimasi [Y/N]: ");
        String userInput = input.nextLine();
        if (userInput.equals("Y") || userInput.equals("y")) {
            // jika user menginput Y atau y, maka buku akan di-update
            buku.updateDetail(
                    // melakukan check apakah input nya '-' jika iya maka tidak ada perubahan
                    checkInput(buku, judulBuku, InputBookType.valueOf("JUDUL")),
                    checkInput(buku, kategori, InputBookType.valueOf("KATAGORI")),
                    checkInput(buku, author, InputBookType.valueOf("AUTHOR"))
                    );
            System.out.println("Buku " + judulBuku + " berhasil diperbaharui");
            aktivasi = false;
        } else if (userInput.equals("N") || userInput.equals("n")) {
            System.out.println("Kembali ke menu sebelumnya.");
        }
    }

    private String checkInput(Buku buku, String inputUser, InputBookType type) {
        if (inputUser.equals("-")) {
            return switch (type) {
                case JUDUL -> buku.getJudulBuku();
                case KATAGORI -> buku.getKategoriBuku();
                case AUTHOR -> buku.getAuthor();
                default -> throw new IllegalStateException("Unexpected value: " + type);
            };
        }
        return inputUser;
    }


}
