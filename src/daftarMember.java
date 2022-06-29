import java.util.ArrayList;
import java.util.Scanner;

public class daftarMember extends User{
        private final ArrayList<Buku> borrowingHistory = new ArrayList<Buku>();
        private Buku currentBorrowedBook = null;
        private boolean menuActiveStatus = true;
        // atribut untuk pengecekan ketika peminjaman, agar tidak terjadi transaksi yang duplikasi
        private boolean isAbleToBorrow = true;
        // inisialisasi kelas scanner untuk Input User
        private Scanner input = new Scanner(System.in);

        // constructor kelas Member yang memanggil constructor kelas Parent nya
        public daftarMember(String fullName, String username, String password) {
            super(fullName, username, password, "Member");
        }

        public void borrowOneBook(ArrayList<Buku> listBuku) {
            // User dapat meminjam buku, jika tidak ada buku yang ia pinjam
            if (isAbleToBorrow()) {
                menuActiveStatus = true; // agar dapat mengakses menu
                while (menuActiveStatus) {
                    System.out.println("\n=== Menu Peminjaman Buku ===");
                    System.out.println("Silahkan pilih buku yang ingin anda pinjam terlebih dahulu.");
                    System.out.println("1. Tampilkan seluruh buku");
                    System.out.println("2. Tampilkan buku berdasarkan Abjad");
                    System.out.println("3. Batal dan kembali");
                    System.out.print("Masukkan Pilihan [1-3]: ");
                    String userChoice = input.nextLine();
                    switch (userChoice) {
                        case "1" -> displayAllBooks(listBuku);
                        case "2" -> showByAlphabet(listBuku);
                        case "3" -> menuActiveStatus = false;
                        default -> System.out.println("Invalid Input!");
                    }
                }
            } else {
                System.out.println("Setiap anggota hanya diperbolehkan meminjam 1 Buku dalam 1 waktu.");
                System.out.println("Anda sedang meminjam buku: \"" + this.currentBorrowedBook.getJudulBuku() + "\".");
                System.out.println("Harap kembalikan buku yang dipinjam terlebih dahulu sebelum meminjam kembali.");
            }
        }

        private void displayAllBooks(ArrayList<Buku> listBuku) {
            // menampilkan seluruh buku menggunakan forEach() dan lambda expression
            listBuku.forEach(buku -> {
                System.out.print("ID: " + buku.getIdBuku());
                System.out.println(" - Title: " + buku.getJudulBuku());
            });
            // user diminta memilih buku
            choosingBookToBorrow(listBuku);
        }

        private void showByAlphabet(ArrayList<Buku> listBuku) {
            boolean isAvailable = false;
            System.out.print("Masukkan huruf alphabet: ");
            String alphabet = input.nextLine();
            for (Buku buku : listBuku) {
                // mengecek apakah ada buku yang dimulai dengan huruf yang diinput user
                if (buku.getJudulBuku().toLowerCase().charAt(0) == alphabet.toLowerCase().charAt(0)) {
                    System.out.print("ID: " + buku.getIdBuku());
                    System.out.println(" - Title: " + buku.getJudulBuku());
                    isAvailable = true;
                }
            }

            // jika ada maka user diminta memilih buku
            if (isAvailable) {
                choosingBookToBorrow(listBuku);
            } else {
                System.out.println("Maaf Buku tidak tersedia.");
            }
        }

        private void choosingBookToBorrow(ArrayList<Buku> listBuku) {
            System.out.println("Info: input '0' untuk batal.");
            System.out.print("ID buku pilihan anda: ");
            String userChoice = input.nextLine();
            if (!userChoice.equals("0")) {
                listBuku.forEach(buku -> {
                    // jika id yang diinputkan user == id buku
                    if (userChoice.equals(buku.getIdBuku())) {
                        System.out.println("Anda ingin meminjam buku:");
                        System.out.println("\""+ buku.getJudulBuku() + "\"");
                        System.out.print("Konfimasi [Y/N]: ");
                        String userInput = input.nextLine();
                        if (userInput.equals("Y") || userInput.equals("y")) {
                            currentBorrowedBook = buku;
                            borrowingHistory.add(buku); // menambahkan riwayat peminjaman
                            System.out.println("Selamat anda berhasil meminjam buku \"" + buku.getJudulBuku() + "\"");
                            System.out.println("Batas waktu pengembalian adalah maks 30 hari setelah peminjaman.");
                            System.out.println("Terima kasih & Selamat Membaca.");
                            menuActiveStatus = false;
                        } else if (userInput.equals("N") || userInput.equals("n")){
                            System.out.println("Kembali ke menu sebelumnya.");
                        }
                    }
                });
            }

            // jika input user buka 0 dan buku yang dipinjam == null
            if (currentBorrowedBook == null && !userChoice.equals("0")) {
                System.out.println("Invalid input");
            }
        }


        public Buku kembalikanBuku() {
            Buku borrowedBook = getCurrentBorrowedBook();
            if (borrowedBook == null) {
                System.out.println("\nAnda sedang tidak meminjam buku, yuk pinjam buku.");
            } else {
                System.out.println("Anda ingin mengembalikan buku:");
                System.out.println(borrowedBook.getJudulBuku());
                System.out.print("Konfimasi [Y/N]: ");
                String userInput = input.nextLine();

                if (userInput.equals("Y") || userInput.equals("y")) {
                    setCurrentBorrowedBook(null);
                    System.out.println("Buku berhasil dikembalikan.");
                    System.out.println("Sekarang anda dapat meminjam buku lagi.");
                    return borrowedBook;

                } else if (userInput.equals("N") || userInput.equals("n")){
                    System.out.println("Pengembalian dibatalkan");
                } else {
                    System.out.println("Invalid Input!");
                }
            }
            return null;
        }


        public void displayBorrowingHistory() {
            if (getBorrowingHistory().isEmpty()) {
                System.out.println("\nBelum ada Peminjaman Buku yang anda lakukan.");
            } else {
                System.out.println("\nRiwayat Peminjaman Buku:");
                for (int i = 0; i < getBorrowingHistory().size(); i++) {
                    System.out.print((i + 1) + ". ");
                    System.out.println(getBorrowingHistory().get(i).getJudulBuku());
                }
            }
        }

        /*
            Getter Setter
         */
        public boolean isAbleToBorrow() {
            return isAbleToBorrow;
        }

        public void setAbleToBorrow(boolean ableToBorrow) {
            isAbleToBorrow = ableToBorrow;
        }

        public ArrayList<Buku> getBorrowingHistory() {
            return borrowingHistory;
        }

        public Buku getCurrentBorrowedBook() {
            return currentBorrowedBook;
        }

        public void setCurrentBorrowedBook(Buku currentBorrowedBook) {
            this.currentBorrowedBook = currentBorrowedBook;
        }
    }

