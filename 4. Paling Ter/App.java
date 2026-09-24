import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Studi kasus 4: Paling Ter (statistik sederhana dari deretan bilangan).
 *
 * Aturan seri:
 *   - Terbanyak / Jumlah Tertinggi : nilai yang lebih besar menang.
 *   - Tersedikit / Jumlah Terendah : nilai yang lebih kecil menang.
 */
public class App {

    private static final String PENANDA_SELESAI = "---";

    /** Satu nilai unik beserta frekuensi kemunculannya. */
    private static class Entri {
        private final int nilai;
        private final int frekuensi;

        Entri(int nilai, int frekuensi) {
            this.nilai = nilai;
            this.frekuensi = frekuensi;
        }

        long jumlah() {
            return (long) nilai * frekuensi;
        }
    }

    private static final Comparator<Entri> BERDASARKAN_FREKUENSI =
            Comparator.comparingInt((Entri e) -> e.frekuensi).thenComparingInt(e -> e.nilai);

    private static final Comparator<Entri> BERDASARKAN_JUMLAH =
            Comparator.comparingLong(Entri::jumlah).thenComparingInt(e -> e.nilai);

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            List<Integer> daftarNilai = bacaDaftarNilai(sc);

            // Tidak ada data -> tidak ada yang ditampilkan
            if (daftarNilai.isEmpty()) {
                return;
            }

            tampilkanStatistik(daftarNilai);
        }
    }

    // ------------------------------------------------------------------
    // Input
    // ------------------------------------------------------------------

    private static List<Integer> bacaDaftarNilai(Scanner sc) {
        List<Integer> daftar = new ArrayList<>();
        while (sc.hasNextLine()) {
            String baris = sc.nextLine().trim();
            if (baris.equals(PENANDA_SELESAI)) {
                break;
            }
            if (baris.isEmpty()) {
                continue;
            }
            try {
                daftar.add(Integer.parseInt(baris));
            } catch (NumberFormatException e) {
                System.out.println("Data tidak valid");
            }
        }
        return daftar;
    }

    // ------------------------------------------------------------------
    // Logika
    // ------------------------------------------------------------------

    private static List<Entri> hitungFrekuensi(List<Integer> daftarNilai) {
        Map<Integer, Integer> frekuensi = new LinkedHashMap<>();
        for (int nilai : daftarNilai) {
            frekuensi.merge(nilai, 1, Integer::sum);
        }

        List<Entri> hasil = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : frekuensi.entrySet()) {
            hasil.add(new Entri(e.getKey(), e.getValue()));
        }
        return hasil;
    }

    // ------------------------------------------------------------------
    // Output
    // ------------------------------------------------------------------

    private static void tampilkanStatistik(List<Integer> daftarNilai) {
        List<Entri> daftarEntri = hitungFrekuensi(daftarNilai);

        int tertinggi = Collections.max(daftarNilai);
        int terendah = Collections.min(daftarNilai);
        Entri terbanyak = Collections.max(daftarEntri, BERDASARKAN_FREKUENSI);
        Entri tersedikit = Collections.min(daftarEntri, BERDASARKAN_FREKUENSI);
        Entri jumlahTertinggi = Collections.max(daftarEntri, BERDASARKAN_JUMLAH);
        Entri jumlahTerendah = Collections.min(daftarEntri, BERDASARKAN_JUMLAH);

        System.out.println("Tertinggi: " + tertinggi);
        System.out.println("Terendah: " + terendah);
        System.out.println("Terbanyak: " + formatFrekuensi(terbanyak));
        System.out.println("Tersedikit: " + formatFrekuensi(tersedikit));
        System.out.println("Jumlah Tertinggi: " + formatJumlah(jumlahTertinggi));
        System.out.println("Jumlah Terendah: " + formatJumlah(jumlahTerendah));
    }

    private static String formatFrekuensi(Entri e) {
        return e.nilai + " (" + e.frekuensi + "x)";
    }

    private static String formatJumlah(Entri e) {
        return e.nilai + " * " + e.frekuensi + " = " + e.jumlah();
    }
}
