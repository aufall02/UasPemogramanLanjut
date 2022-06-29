import java.util.Scanner;

public class Data {
     String nama,alamat,nim,judul,author,jenis,kodeBuku;
    Scanner input =  new Scanner(System.in);

    public  void dataDiri(){
        System.out.print("Masukkan nama anda : ");
        nama = input.nextLine();
        System.out.println("Hallo "+nama+" Selamat datang di Perpustakaan universitas PGRI Kanjuruhan malang");
        System.out.println("================ lengkapi data diri anda ============= ");
        System.out.print("NIM : ");
        nim = input.nextLine();
        System.out.print("Alamat : ");
        alamat = input.nextLine();
    }
    public void menu(){
        System.out.println("============ silahkan Pilih menu ========");
        System.out.println("1. Meminjam Buku ");
        System.out.println("2. Mengembalikan buku ");
        var menu = input.nextLine();

        switch (menu){
            case "1" :
                System.out.print("masukkan judul buku: ");
                judul = input.nextLine();
                System.out.print("masukkan nama Author: ");
                author = input.nextLine();
                System.out.print("masukkan jenis buku: ");
                jenis = input.nextLine();
                System.out.print("masukkan kode buku: ");
                kodeBuku = input.nextLine();
                break;
            case "2":
                System.out.print("masukkan kode buku : ");
                kodeBuku = input.nextLine();
                System.out.print("masukkan judul buku : ");
                judul = input.nextLine();
                break;
            default:
                System.out.println("menu yang anda mmasukkan salah");

        }

    }
    public void output(){
        System.out.println("nama = "+nama);
        System.out.println("==== buku yang dipinjma ====");
        System.out.println("buku " + judul);
    }
}
