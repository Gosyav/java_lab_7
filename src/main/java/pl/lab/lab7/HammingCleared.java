package pl.lab.lab7;

public class HammingCleared extends Hamming {
  private String clear(String s) {
    if (s == null) {
      return "";
    }

    StringBuilder sb = new StringBuilder();

    for (char c : s.toCharArray()) {
      if (!Character.isWhitespace(c) && c != '_') {
        sb.append(c);
      }
    }

    return sb.toString();
  }

  @Override
  public int compare(String a, String b) {
    String clearedA = clear(a);
    String clearedB = clear(b);
    return super.compare(clearedA, clearedB);
  }
}
