package pl.lab.lab7;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HammingClearedTest {
  @Test
  public void whitespaceAndUnderscoreAreIgnored() {
    HammingCleared h = new HammingCleared();
    assertEquals(0, h.compare("a b_c", "abc"));
  }

  @Test
  public void tabsAndNewlinesAreIgnoredToo() {
    HammingCleared h = new HammingCleared();
    String s1 = "ab\tc\n_d";
    String s2 = "abcd";
    assertEquals(0, h.compare(s1, s2));
  }

  @Test
  public void realDifferenceAfterCleaningIsCounted() {
    HammingCleared h = new HammingCleared();
    assertEquals(1, h.compare("a x c", "a y c"));
  }

  @Test
  public void nullBecomesEmptyStringAndIsHandled() {
    HammingCleared h = new HammingCleared();
    assertEquals(3, h.compare(null, "abc"));
  }

  @Test
  public void nullVsNullBecomesZeroDistance() {
    HammingCleared h = new HammingCleared();
    assertEquals(0, h.compare(null, null));
  }
}
