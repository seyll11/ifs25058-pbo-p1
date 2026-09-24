import java.util.Scanner;

/**
 * Studi kasus 5: Jam Saya (perhitungan pergeseran jam tanpa API waktu Java).
 *
 * Input: jam awal "HH:MM", lalu perintah pergeseran menit ("+N" / "-N") hingga "---".
 * Setiap kali jam melewati batas 24 jam (maju atau mundur), Pergantian Hari bertambah.
 */
public class App {

    private static final int MENIT_PER_JAM = 60;
    private static final int MENIT_PER_HARI = 24 * MENIT_PER_JAM;
    private static final String PENANDA_SELESAI = "---";
    private static final String FORMAT_PERINTAH = "[+-]\\d+";

    /** Keadaan jam yang terus digeser. */
    private static class JamSaya {
        private final int jamAwal;
        private final int menitAwal;
        private long menitSekarang;
        private long totalGeser = 0;
        private long pergantianHari = 0;

        JamSaya(int jam, int menit) {
            this.jamAwal = jam;
            this.menitAwal = menit;
            this.menitSekarang = jam * MENIT_PER_JAM + menit;
        }

        void geser(long menit) {
            totalGeser += menit;
            menitSekarang += menit;
            pergantianHari += Math.abs(Math.floorDiv(menitSekarang, (long) MENIT_PER_HARI));
            menitSekarang = Math.floorMod(menitSekarang, (long) MENIT_PER_HARI);
        }

        int jamAkhir() {
            return (int) (menitSekarang / MENIT_PER_JAM);
        }

        int menitAkhir() {
            return (int) (menitSekarang % MENIT_PER_JAM);
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            JamSaya jam = bacaJamAwal(sc);
            if (jam == null) {
                System.out.println("Jam tidak valid");
                return;
            }

            bacaPerintah(sc, jam);
            tampilkanHasil(jam);
        }
    }

    // ------------------------------------------------------------------
    // Input & validasi
    // ------------------------------------------------------------------

    /** Membaca "HH:MM". Mengembalikan null jika format atau rentangnya salah. */
    private static JamSaya bacaJamAwal(Scanner sc) {
        if (!sc.hasNextLine()) {
            return null;
        }

        String[] bagian = sc.nextLine().trim().split(":", -1);
        if (bagian.length != 2) {
            return null;
        }

        try {
            int jam = Integer.parseInt(bagian[0].trim());
            int menit = Integer.parseInt(bagian[1].trim());
            boolean diLuarRentang = jam < 0 || jam > 23 || menit < 0 || menit > 59;
            return diLuarRentang ? null : new JamSaya(jam, menit);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static void bacaPerintah(Scanner sc, JamSaya jam) {
        while (sc.hasNextLine()) {
            String baris = sc.nextLine().trim();
            if (baris.equals(PENANDA_SELESAI)) {
                break;
            }
            if (baris.isEmpty()) {
                continue;
            }

            Long geser = parsePerintah(baris);
            if (geser == null) {
                System.out.println("Perintah tidak valid");
                continue;
            }
            jam.geser(geser);
        }
    }

    /** Mengubah "+N" / "-N" menjadi angka bertanda; null jika formatnya salah. */
    private static Long parsePerintah(String perintah) {
        if (!perintah.matches(FORMAT_PERINTAH)) {
            return null;
        }
        try {
            return Long.parseLong(perintah.substring(1)) * (perintah.charAt(0) == '-' ? -1 : 1);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // ------------------------------------------------------------------
    // Output
    // ------------------------------------------------------------------

    private static void tampilkanHasil(JamSaya jam) {
        System.out.printf("Jam Awal: %02d:%02d%n", jam.jamAwal, jam.menitAwal);
        System.out.printf("Jam Akhir: %02d:%02d%n", jam.jamAkhir(), jam.menitAkhir());
        System.out.println("Total Menit: " + formatTotalMenit(jam.totalGeser));
        System.out.println("Pergantian Hari: " + jam.pergantianHari);
    }

    private static String formatTotalMenit(long total) {
        return total > 0 ? "+" + total : String.valueOf(total);
    }
}
