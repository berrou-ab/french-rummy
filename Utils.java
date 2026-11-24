public class Utils {
    private static int[] getTerminalSize() {
        try {
            Process process = new ProcessBuilder("sh", "-c", "stty size < /dev/tty")
            .redirectErrorStream(true).start();

            java.util.Scanner scanner = new java.util.Scanner(process.getInputStream());

            if (scanner.hasNextInt()) {
                int height = scanner.nextInt();
                int width = scanner.nextInt();
                scanner.close();
                process.waitFor();
                return new int[]{width, height};
            }

            scanner.close();
            process.waitFor();
        } catch (Exception ignored) {}

        // Fallback to environment variables
        int width = Integer.parseInt(System.getenv().getOrDefault("COLUMNS", "80"));
        int height = Integer.parseInt(System.getenv().getOrDefault("LINES", "24"));
        return new int[]{width, height};
    }
    public static int getWidth(){
        return getTerminalSize()[0];
    }
    public static int getHeight(){
        return getTerminalSize()[1];
    }
    public static void printLine(char c){
        System.out.println(String.valueOf(c).repeat(getWidth()));
    }
    public static void centerText(String s){
        int width = getWidth();
        int padding = Math.max(0, (width - s.length()) / 2);
        System.out.println(" ".repeat(padding) + s);
    }
}
