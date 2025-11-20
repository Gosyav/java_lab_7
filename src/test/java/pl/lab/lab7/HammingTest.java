package pl.lab.lab7;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HammingTest {

  @Test
  public void identicalStringsHaveZeroDistance() {
    Hamming h = new Hamming();
    assertEquals(0, h.compare("meditation", "meditation"));
  }

  @Test
  public void oneDifferentCharGivesDistanceOne() {
    Hamming h = new Hamming();
    assertEquals(1, h.compare("medication", "meditation"));
  }

  @Test
  public void differentLengthsAreCountedByTail() {
    Hamming h = new Hamming();
    assertEquals(3, h.compare("speed", "speeding"));
  }

  @Test
  public void bothEmptyStringsHaveZeroDistance() {
    Hamming h = new Hamming();
    assertEquals(0, h.compare("", ""));
  }

  @Test
  public void nonEmptyVsEmptyDistanceIsLength() {
    Hamming h = new Hamming();
    assertEquals(5, h.compare("hello", ""));
  }
}
