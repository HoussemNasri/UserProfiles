package tech.houssemnasri.imagestorage.util;

public class Exceptions {
  public static <E extends Exception> void throwIfCond(boolean cond, E exception) throws E {
    if (cond) {
      throw exception;
    }
  }

  public static void throwIllegalArgumentIf(boolean cond, String message)
      throws IllegalArgumentException {
    if (cond) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void fThrowIllegalArgumentIf(
      boolean cond, String message, String format, Object... args) throws IllegalArgumentException {
    if (cond) {
      throw new IllegalArgumentException(String.format(format, args));
    }
  }
}
