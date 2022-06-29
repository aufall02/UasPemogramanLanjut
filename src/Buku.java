public class Buku{

    static String judulBuku;
    String kategoriBuku;
    String author;
//    long idCounter = 1;
    String idBuku;
    long idCounter = 1;

    public Buku(String judulBuku,String kategoriBuku, String author){
        this.judulBuku = judulBuku;
        this.kategoriBuku = kategoriBuku;
        this.author = author;
        this.idBuku =   cetakId();
//        this.idBuku = "0" + cetakId();
    }

    public Buku() {
    }

    //    private String cetakId() {
//        return idBuku = String.valueOf(idCounter+=1);
//    }
    public void updateDetail(String judulBuku, String kategoriBuku, String author){
        this.judulBuku = judulBuku;
        this.kategoriBuku = kategoriBuku;
        this.author = author;
    }
    public  void tampilkanDetailBuku(){
        System.out.println("ID \t\t\t: " + getIdBuku());
        System.out.println("Judul \t\t: " + getJudulBuku());
        System.out.println("Kategori \t: " + getKategoriBuku());
        System.out.println("Penulis \t: " + getAuthor());

    }

    /*
        Getter Setter
     */
    //membuat id buku
    private String cetakId() {
        return idBuku = String.valueOf(idCounter++);
    }
    public String getIdBuku() {
        return  idBuku;
    }



    public static String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public String getKategoriBuku() {
        return kategoriBuku;
    }

//    public void setKategoriBuku(String kategoriBuku) {
//        this.kategoriBuku = kategoriBuku;
//    }

    public String getAuthor() {
        return author;
    }

//    public void setAuthor(String author) {
//        this.author = author;
//    }
}
