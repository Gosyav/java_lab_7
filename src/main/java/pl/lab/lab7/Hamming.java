package pl.lab.lab7;

public class Hamming {
  public int compare(String a, String b) {
    if (a == null || b == null) {
      throw new IllegalArgumentException("Strings cannot be null");
    }

    if (a.isEmpty() && b.isEmpty()) {
      return 0;
    }

    int minLen = Math.min(a.length(), b.length());
    int diff = 0;

    for (int i = 0; i < minLen; i++) {
      if (a.charAt(i) != b.charAt(i)) {
        diff++;
      }
    }

    diff += Math.abs(a.length() - b.length());

    return diff;
  }
}
