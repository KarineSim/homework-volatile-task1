import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static AtomicInteger length3 = new AtomicInteger(0);
    private static AtomicInteger length4 = new AtomicInteger(0);
    private static AtomicInteger length5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindrome = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    addText(text);
                }
            }
        });

        Thread sameLetter = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {
                    addText(text);
                }
            }
        });

        Thread lettersInAscendingOrder = new Thread(() -> {
            for (String text : texts) {
                if (isInAscendingOrder(text)) {
                    addText(text);
                }
            }
        });

        palindrome.start();
        sameLetter.start();
        lettersInAscendingOrder.start();

        palindrome.join();
        sameLetter.join();
        lettersInAscendingOrder.join();

        System.out.println("Красивых слов с длиной 3: " + length3 + " шт.");
        System.out.println("Красивых слов с длиной 4: " + length4 + " шт.");
        System.out.println("Красивых слов с длиной 5: " + length5 + " шт.");
    }

    public static boolean isInAscendingOrder(String text) {
        for (int i = text.length() - 1; i > 0; i--) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSameLetter(String text) {
        for (int i = text.length() - 1; i > 0; i--) {
            if (text.charAt(i) != text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindrome(String text) {
        boolean palindrome = true;
        StringBuilder reverse = new StringBuilder(text).reverse();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != reverse.charAt(i)) {
                palindrome = false;
                break;
            }
        }
        return palindrome;
    }

    public static void addText(String text) {
        switch (text.length()) {
            case 3:
                length3.incrementAndGet();
                break;
            case 4:
                length4.incrementAndGet();
                break;
            case 5:
                length5.incrementAndGet();
                break;
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}