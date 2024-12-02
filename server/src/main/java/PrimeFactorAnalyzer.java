public class PrimeFactorAnalyzer {

    public int processNumber(String numberStr) {
        long number = Long.parseLong(numberStr);
        int numberOfPrimeFactors = countPrimeFactors(number);
        return isPrime(numberOfPrimeFactors) ? 1 : 0;
    }

    private int countPrimeFactors(long number) {
        int count = 0;

        while (number % 2 == 0) {
            count++;
            number /= 2;
        }

        for (long i = 3; i <= Math.sqrt(number); i += 2) {
            while (number % i == 0) {
                count++;
                number /= i;
            }
        }

        if (number > 2) {
            count++;
        }

        return count;
    }

    private boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number == 2) return true;
        if (number % 2 == 0) return false;
        
        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) return false;
        }
        
        return true;
   }
}