package pl.lab.lab7;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        String directory = args.length >= 1 ? args[0] : "examples";

        String[] extensions;
        if (args.length >= 2) {
            extensions = Arrays.copyOfRange(args, 1, args.length);
        } else {
            extensions = new String[] { ".txt", ".c" };
        }

        File dir = new File(directory);
        if (!dir.isDirectory()) {
            System.out.println("Directory not found: " + dir.getAbsolutePath());
            return;
        }

        List<File> files = findFiles(dir, extensions);

        if (files.size() < 2) {
            System.out.println("Not enough files in directory " + dir.getPath()
                    + " matching extensions " + String.join(", ", normalizeExtensions(extensions)));
            return;
        }

        Hamming hamming = new HammingCleared();
        CheckPlagiarism checker = new CheckPlagiarism(hamming);

        System.out.println("Threshold = " + CheckPlagiarism.threshold);
        System.out.println("Checking files for plagiarism in directory: " + dir.getPath());
        System.out.println("Extensions: " + String.join(", ", normalizeExtensions(extensions)));

        for (int i = 0; i < files.size(); i++) {
            for (int j = i + 1; j < files.size(); j++) {
                File f1 = files.get(i);
                File f2 = files.get(j);

                CheckPlagiarism.Result res = checker.CompareFiles(f1.getPath(), f2.getPath());

                System.out.printf(
                        "%s  <->  %s : avg=%.3f, identical=%d, plagiarism=%s%n",
                        f1.getName(),
                        f2.getName(),
                        res.getAverageMinimalDistance(),
                        res.getIdenticalLines(),
                        res.isPlagiarismDetected() ? "YES" : "NO");
            }
        }
    }

    static List<File> findFiles(File dir, String... extensions) {
        List<File> result = new ArrayList<>();
        if (dir == null || !dir.isDirectory()) {
            return result;
        }

        String[] normalized = normalizeExtensions(extensions);
        File[] list = dir.listFiles();
        if (list == null) {
            return result;
        }

        outer: for (File f : list) {
            if (!f.isFile()) {
                continue;
            }
            String name = f.getName();
            for (String ext : normalized) {
                if (name.endsWith(ext)) {
                    result.add(f);
                    continue outer;
                }
            }
        }

        return result;
    }

    private static String[] normalizeExtensions(String... extensions) {
        if (extensions == null || extensions.length == 0) {
            return new String[0];
        }
        String[] normalized = new String[extensions.length];
        for (int i = 0; i < extensions.length; i++) {
            String e = extensions[i];
            if (!e.startsWith(".")) {
                e = "." + e;
            }
            normalized[i] = e;
        }
        return normalized;
    }
}
