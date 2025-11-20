package pl.lab.lab7;

import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        File dir = new File(".");

        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files == null || files.length < 2) {
            System.out.println("Not enough .txt files in the current directory to perform plagiarism check");
            return;
        }

        Hamming hamming = new HammingCleared();
        CheckPlagiarism checker = new CheckPlagiarism(hamming);
        CheckPlagiarism.threshold = 20.0;

        System.out.println("Checking files for plagiarism:");
        for (int i = 0; i < files.length; i++) {
            for (int j = i + 1; j < files.length; j++) {
                CheckPlagiarism.Result res = checker.CompareFiles(files[i].getPath(), files[j].getPath());

                System.out.printf(
                        "%s  <->  %s : avg=%.3f, identical=%d, plagiarism=%s%n",
                        files[i].getName(),
                        files[j].getName(),
                        res.getAverageMinimalDistance(),
                        res.getIdenticalLines(),
                        res.isPlagiarismDetected() ? "YES" : "NO");
            }
        }
    }
}
