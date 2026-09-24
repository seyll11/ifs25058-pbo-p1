import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Baris 1-6: bobot tiap komponen
        int bobotPA = Integer.parseInt(sc.nextLine().trim());
        int bobotT = Integer.parseInt(sc.nextLine().trim());
        int bobotK = Integer.parseInt(sc.nextLine().trim());
        int bobotP = Integer.parseInt(sc.nextLine().trim());
        int bobotUTS = Integer.parseInt(sc.nextLine().trim());
        int bobotUAS = Integer.parseInt(sc.nextLine().trim());

        int totalBobot = bobotPA + bobotT + bobotK + bobotP + bobotUTS + bobotUAS;
        if (totalBobot != 100) {
            System.out.println("Total bobot harus 100");
            return;
        }

        // Bobot header per simbol (dipakai untuk kontribusi nilai akhir)
        Map<String, Integer> bobotHeader = new LinkedHashMap<>();
        bobotHeader.put("PA", bobotPA);
        bobotHeader.put("T", bobotT);
        bobotHeader.put("K", bobotK);
        bobotHeader.put("P", bobotP);
        bobotHeader.put("UTS", bobotUTS);
        bobotHeader.put("UAS", bobotUAS);

        // Akumulasi bobot & perolehan dari baris data komponen
        Map<String, Integer> totalKomponen = new LinkedHashMap<>();
        Map<String, Integer> perolehanKomponen = new LinkedHashMap<>();
        for (String s : bobotHeader.keySet()) {
            totalKomponen.put(s, 0);
            perolehanKomponen.put(s, 0);
        }

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.trim().equals("---")) break;

            String[] parts = line.split("\\|", -1);
            if (parts.length != 3) {
                System.out.println("Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai");
                continue;
            }

            String simbol = parts[0].trim();
            String bobotStr = parts[1].trim();
            String perolehanStr = parts[2].trim();

            int bobot;
            int perolehan;
            try {
                bobot = Integer.parseInt(bobotStr);
                perolehan = Integer.parseInt(perolehanStr);
            } catch (NumberFormatException e) {
                System.out.println("Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai");
                continue;
            }

            if (!bobotHeader.containsKey(simbol)) {
                System.out.println("Simbol tidak dikenal");
                continue;
            }

            // Clamp perolehan ke rentang [0, bobot]
            if (perolehan > bobot) perolehan = bobot;
            if (perolehan < 0) perolehan = 0;

            totalKomponen.put(simbol, totalKomponen.get(simbol) + bobot);
            perolehanKomponen.put(simbol, perolehanKomponen.get(simbol) + perolehan);
        }

        String[] urutanSimbol = {"PA", "T", "K", "P", "UTS", "UAS"};
        String[] label = {"Partisipatif", "Tugas", "Kuis", "Proyek", "UTS", "UAS"};

        double nilaiAkhir = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Perolehan Nilai:\n");
        for (int i = 0; i < urutanSimbol.length; i++) {
            String s = urutanSimbol[i];
            int total = totalKomponen.get(s);
            int perolehan = perolehanKomponen.get(s);

            int persen = (total == 0) ? 0 : (perolehan * 100) / total; // integer
            double kontribusi = (persen / 100.0) * bobotHeader.get(s); // double
            nilaiAkhir += kontribusi;

            sb.append(String.format(">> %s: %d/100 (%.2f/%d)\n",
                    label[i], persen, kontribusi, bobotHeader.get(s)));
        }

        String grade;
        if (nilaiAkhir >= 79.5) grade = "A";
        else if (nilaiAkhir >= 72) grade = "AB";
        else if (nilaiAkhir >= 64.5) grade = "B";
        else if (nilaiAkhir >= 56.5) grade = "BC";
        else if (nilaiAkhir >= 49.5) grade = "C";
        else if (nilaiAkhir >= 34) grade = "D";
        else grade = "E";

        System.out.print(sb);
        System.out.println();
        System.out.printf(">> Nilai Akhir: %.2f%n", nilaiAkhir);
        System.out.println(">> Grade: " + grade);
    }
}
