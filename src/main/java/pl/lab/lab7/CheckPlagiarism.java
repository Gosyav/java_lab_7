package pl.lab.lab7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckPlagiarism {
  public static double threshold = 20.0;

  private final Hamming hamming;

  public CheckPlagiarism(Hamming hamming) {
    this.hamming = hamming;
  }

  public static class Result {
    private final double averageMinimalDistance;
    private final int identicalLines;
    private final boolean plagiarismDetected;

    public Result(double averageMinimalDistance, int identicalLines, boolean plagiarismDetected) {
      this.averageMinimalDistance = averageMinimalDistance;
      this.identicalLines = identicalLines;
      this.plagiarismDetected = plagiarismDetected;
    }

    public double getAverageMinimalDistance() {
      return averageMinimalDistance;
    }

    public int getIdenticalLines() {
      return identicalLines;
    }

    public boolean isPlagiarismDetected() {
      return plagiarismDetected;
    }
  }

  public Result CompareFiles(String path1, String path2) throws IOException {
    List<String> lines1 = readNonEmptyLines(path1);
    List<String> lines2 = readNonEmptyLines(path2);

    if (lines1.size() < lines2.size()) {
      List<String> tmp = lines1;
      lines1 = lines2;
      lines2 = tmp;
    }

    if (lines1.isEmpty() || lines2.isEmpty()) {
      return new Result(Double.POSITIVE_INFINITY, 0, false);
    }

    int identical = 0;
    int sumMinDistances = 0;

    for (String line1 : lines1) {
      int minDist = Integer.MAX_VALUE;

      for (String line2 : lines2) {
        int d = hamming.compare(line1, line2);

        if (d < minDist) {
          minDist = d;
        }
      }

      if (minDist == 0) {
        identical++;
      }

      sumMinDistances += minDist;
    }

    double average = (double) sumMinDistances / lines1.size();
    boolean plag = average < threshold;

    return new Result(average, identical, plag);
  }

  private List<String> readNonEmptyLines(String path) throws IOException {
    List<String> result = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line;

      while ((line = br.readLine()) != null) {
        if (!line.trim().isEmpty()) {
          result.add(line);
        }
      }
    }

    return result;
  }
}
