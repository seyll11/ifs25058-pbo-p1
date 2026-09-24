import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

/**
 * Studi kasus 2: Perolehan Nilai.
 *
 * Alur input:
 *   1. Enam baris bobot akhir (PA, T, K, P, UTS, UAS) yang totalnya harus 100.
 *   2. Baris data "Simbol|Bobot|Perolehan" hingga baris "---".
 *
 * Acuan bobot:
 *   - Persentase komponen dihitung dari total bobot & perolehan pada baris data.
 *   - Kontribusi ke nilai akhir dihitung dari bobot akhir (baris 1-6).
 */
public class App {

    private static final int TOTAL_BOBOT_AKHIR = 100;
    private static final String PENANDA_SELESAI = "---";
    private static final String PESAN_FORMAT_SALAH =
            "Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai";

    private static final String[] SIMBOL = {"PA", "T", "K", "P", "UTS", "UAS"};
    private static final String[] NAMA = {"Partisipatif", "Tugas", "Kuis", "Proyek", "UTS", "UAS"};

    /** Satu komponen penilaian beserta akumulasi data yang masuk. */
    private static class Komponen {
        private final String nama;
        private final int bobotAkhir;
        private long totalBobot = 0;
        private long totalPerolehan = 0;

        Komponen(String nama, int bobotAkhir) {
            this.nama = nama;
            this.bobotAkhir = bobotAkhir;
        }

        void tambahData(int bobot, int perolehan) {
            int perolehanValid = Math.max(0, Math.min(perolehan, bobot));
            totalBobot += bobot;
            totalPerolehan += perolehanValid;
        }

        int persentase() {
            return totalBobot == 0 ? 0 : (int) ((totalPerolehan * 100) / totalBobot);
        }

        double kontribusi() {
            return bulatkan2Desimal(persentase() / 100.0 * bobotAkhir);
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Map<String, Komponen> komponen = bacaBobotAkhir(sc);
            if (komponen == null) {
                System.out.println("Bobot tidak valid");
                return;
            }
            if (totalBobotAkhir(komponen) != TOTAL_BOBOT_AKHIR) {
                System.out.println("Total bobot harus 100");
                return;
            }

            bacaDataKomponen(sc, komponen);

            double nilaiAkhir = hitungNilaiAkhir(komponen);
            tampilkanHasil(komponen, nilaiAkhir);
        }
    }

    // ------------------------------------------------------------------
    // Input
    // ------------------------------------------------------------------

    /** Membaca 6 bobot akhir. Mengembalikan null jika ada baris yang tidak valid. */
    private static Map<String, Komponen> bacaBobotAkhir(Scanner sc) {
        Map<String, Komponen> hasil = new LinkedHashMap<>();
        for (int i = 0; i < SIMBOL.length; i++) {
            if (!sc.hasNextLine()) {
                return null;
            }
            Integer bobot = parseInteger(sc.nextLine());
            if (bobot == null || bobot < 0) {
                return null;
            }
            hasil.put(SIMBOL[i], new Komponen(NAMA[i], bobot));
        }
        return hasil;
    }

    private static void bacaDataKomponen(Scanner sc, Map<String, Komponen> komponen) {
        while (sc.hasNextLine()) {
            String baris = sc.nextLine();
            if (baris.trim().equals(PENANDA_SELESAI)) {
                break;
            }
            prosesBarisData(baris, komponen);
        }
    }

    private static void prosesBarisData(String baris, Map<String, Komponen> komponen) {
        String[] bagian = baris.split("\\|", -1);
        if (bagian.length != 3) {
            System.out.println(PESAN_FORMAT_SALAH);
            return;
        }

        String simbol = bagian[0].trim();
        Integer bobot = parseInteger(bagian[1]);
        Integer perolehan = parseInteger(bagian[2]);
        if (bobot == null || perolehan == null || bobot < 0) {
            System.out.println(PESAN_FORMAT_SALAH);
            return;
        }

        Komponen target = komponen.get(simbol);
        if (target == null) {
            System.out.println("Simbol tidak dikenal");
            return;
        }
        target.tambahData(bobot, perolehan);
    }

    /** Mengubah teks menjadi Integer; null jika bukan bilangan bulat. */
    private static Integer parseInteger(String teks) {
        try {
            return Integer.parseInt(teks.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // ------------------------------------------------------------------
    // Logika
    // ------------------------------------------------------------------

    private static int totalBobotAkhir(Map<String, Komponen> komponen) {
        int total = 0;
        for (Komponen k : komponen.values()) {
            total += k.bobotAkhir;
        }
        return total;
    }

    private static double hitungNilaiAkhir(Map<String, Komponen> komponen) {
        double total = 0;
        for (Komponen k : komponen.values()) {
            total += k.kontribusi();
        }
        // Dibulatkan agar galat floating-point (mis. 56.9999999) tidak memengaruhi grade.
        return bulatkan2Desimal(total);
    }

    private static double bulatkan2Desimal(double nilai) {
        return Math.round(nilai * 100) / 100.0;
    }

    private static String tentukanGrade(double nilai) {
        if (nilai >= 79.5) return "A";
        if (nilai >= 72) return "AB";
        if (nilai >= 64.5) return "B";
        if (nilai >= 57) return "BC";
        if (nilai >= 49.5) return "C";
        if (nilai >= 34) return "D";
        return "E";
    }

    // ------------------------------------------------------------------
    // Output
    // ------------------------------------------------------------------

    private static void tampilkanHasil(Map<String, Komponen> komponen, double nilaiAkhir) {
        System.out.println("Perolehan Nilai:");
        for (Komponen k : komponen.values()) {
            System.out.printf(Locale.US, ">> %s: %d/100 (%.2f/%d)%n",
                    k.nama, k.persentase(), k.kontribusi(), k.bobotAkhir);
        }
        System.out.println();
        System.out.printf(Locale.US, ">> Nilai Akhir: %.2f%n", nilaiAkhir);
        System.out.println(">> Grade: " + tentukanGrade(nilaiAkhir));
    }
}
