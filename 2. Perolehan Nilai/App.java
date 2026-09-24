import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String jamAwalStr = sc.nextLine().trim();

        String[] parts = jamAwalStr.split(":");
        boolean valid = true;
        int jamAwal = 0, menitAwal = 0;

        if (parts.length != 2) {
            valid = false;
        } else {
            try {
                jamAwal = Integer.parseInt(parts[0].trim());
                menitAwal = Integer.parseInt(parts[1].trim());
                if (jamAwal < 0 || jamAwal > 23 || menitAwal < 0 || menitAwal > 59) {
                    valid = false;
                }
            } catch (NumberFormatException e) {
                valid = false;
            }
        }

        if (!valid) {
            System.out.println("Jam tidak valid");
            return;
        }

        int currentMinutes = jamAwal * 60 + menitAwal;
        int totalMenit = 0;
        int pergantianHari = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.equals("---")) break;
            if (line.isEmpty()) continue;

            if (line.matches("[+-]\\d+")) {
                int geser = Integer.parseInt(line);
                totalMenit += geser;
                currentMinutes += geser;

                // Normalisasi memakai modulo 24 jam (1440 menit)
                while (currentMinutes >= 1440) {
                    currentMinutes -= 1440;
                    pergantianHari++;
                }
                while (currentMinutes < 0) {
                    currentMinutes += 1440;
                    pergantianHari++;
                }
            } else {
                System.out.println("Perintah tidak valid");
            }
        }

        int jamAkhir = currentMinutes / 60;
        int menitAkhir = currentMinutes % 60;

        System.out.printf("Jam Awal: %02d:%02d%n", jamAwal, menitAwal);
        System.out.printf("Jam Akhir: %02d:%02d%n", jamAkhir, menitAkhir);

        if (totalMenit > 0) {
            System.out.println("Total Menit: +" + totalMenit);
        } else {
            System.out.println("Total Menit: " + totalMenit);
        }
        System.out.println("Pergantian Hari: " + pergantianHari);
    }
}
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String jamAwalStr = sc.nextLine().trim();

        String[] parts = jamAwalStr.split(":");
        boolean valid = true;
        int jamAwal = 0, menitAwal = 0;

        if (parts.length != 2) {
            valid = false;
        } else {
            try {
                jamAwal = Integer.parseInt(parts[0].trim());
                menitAwal = Integer.parseInt(parts[1].trim());
                if (jamAwal < 0 || jamAwal > 23 || menitAwal < 0 || menitAwal > 59) {
                    valid = false;
                }
            } catch (NumberFormatException e) {
                valid = false;
            }
        }

        if (!valid) {
            System.out.println("Jam tidak valid");
            return;
        }

        int currentMinutes = jamAwal * 60 + menitAwal;
        int totalMenit = 0;
        int pergantianHari = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.equals("---")) break;
            if (line.isEmpty()) continue;

            if (line.matches("[+-]\\d+")) {
                int geser = Integer.parseInt(line);
                totalMenit += geser;
                currentMinutes += geser;

                // Normalisasi memakai modulo 24 jam (1440 menit)
                while (currentMinutes >= 1440) {
                    currentMinutes -= 1440;
                    pergantianHari++;
                }
                while (currentMinutes < 0) {
                    currentMinutes += 1440;
                    pergantianHari++;
                }
            } else {
                System.out.println("Perintah tidak valid");
            }
        }

        int jamAkhir = currentMinutes / 60;
        int menitAkhir = currentMinutes % 60;

        System.out.printf("Jam Awal: %02d:%02d%n", jamAwal, menitAwal);
        System.out.printf("Jam Akhir: %02d:%02d%n", jamAkhir, menitAkhir);

        if (totalMenit > 0) {
            System.out.println("Total Menit: +" + totalMenit);
        } else {
            System.out.println("Total Menit: " + totalMenit);
        }
        System.out.println("Pergantian Hari: " + pergantianHari);
    }
}
