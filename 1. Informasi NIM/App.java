import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Studi kasus 1: Informasi NIM.
 * Format NIM: [3 karakter kode prodi][2 digit angkatan][3 digit urutan].
 */
public class App {

    private static final int PANJANG_NIM = 8;
    private static final int PANJANG_KODE_PRODI = 3;
    private static final int PANJANG_ANGKATAN = 2;
    private static final String AWALAN_TAHUN = "20";

    private static final Map<String, String> DAFTAR_PRODI = buatDaftarProdi();

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String nim = bacaBaris(sc);

            String pesanError = validasiNim(nim);
            if (pesanError != null) {
                System.out.println(pesanError);
                return;
            }

            tampilkanInformasi(nim);
        }
    }

    // ------------------------------------------------------------------
    // Input
    // ------------------------------------------------------------------

    private static String bacaBaris(Scanner sc) {
        return sc.hasNextLine() ? sc.nextLine().trim() : "";
    }

    // ------------------------------------------------------------------
    // Validasi
    // ------------------------------------------------------------------

    /** Mengembalikan pesan error, atau null jika NIM valid. */
    private static String validasiNim(String nim) {
        if (nim.length() != PANJANG_NIM) {
            return "NIM harus 8 karakter";
        }
        if (!DAFTAR_PRODI.containsKey(ambilKodeProdi(nim))) {
            return "Kode tidak tersedia";
        }
        if (!semuaDigit(nim.substring(PANJANG_KODE_PRODI))) {
            return "NIM tidak valid";
        }
        return null;
    }

    private static boolean semuaDigit(String teks) {
        for (int i = 0; i < teks.length(); i++) {
            char c = teks.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    // ------------------------------------------------------------------
    // Logika
    // ------------------------------------------------------------------

    private static String ambilKodeProdi(String nim) {
        return nim.substring(0, PANJANG_KODE_PRODI);
    }

    private static int ambilAngkatan(String nim) {
        int awal = PANJANG_KODE_PRODI;
        return Integer.parseInt(AWALAN_TAHUN + nim.substring(awal, awal + PANJANG_ANGKATAN));
    }

    private static int ambilUrutan(String nim) {
        return Integer.parseInt(nim.substring(PANJANG_KODE_PRODI + PANJANG_ANGKATAN));
    }

    // ------------------------------------------------------------------
    // Output
    // ------------------------------------------------------------------

    private static void tampilkanInformasi(String nim) {
        System.out.println("Informasi NIM " + nim + ": ");
        System.out.println(">> Program Studi: " + DAFTAR_PRODI.get(ambilKodeProdi(nim)));
        System.out.println(">> Angkatan: " + ambilAngkatan(nim));
        System.out.println(">> Urutan: " + ambilUrutan(nim));
    }

    // ------------------------------------------------------------------
    // Data
    // ------------------------------------------------------------------

    private static Map<String, String> buatDaftarProdi() {
        Map<String, String> prodi = new HashMap<>();
        prodi.put("11S", "Sarjana Informatika");
        prodi.put("12S", "Sarjana Sistem Informasi");
        prodi.put("13S", "Sarjana Teknik Elektro");
        prodi.put("21S", "Sarjana Manajemen Rekayasa");
        prodi.put("22S", "Sarjana Teknik Metalurgi");
        prodi.put("31S", "Sarjana Teknik Bioproses");
        prodi.put("32S", "Sarjana Bioteknologi");
        prodi.put("114", "Diploma 4 Teknologi Rekayasa Perangkat Lunak");
        prodi.put("113", "Diploma 3 Teknologi Informasi");
        prodi.put("133", "Diploma 3 Teknologi Komputer");
        return prodi;
    }
}
