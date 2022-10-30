import java.util.*;
import java.util.stream.Collectors;

enum RomanNumeral {
    I(1), IV(4), V(5), IX(9), X(10),
    XL(40), L(50), XC(90), C(100);

    private final int value;

    RomanNumeral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static List<RomanNumeral> getReverseSortedValues() {
        return Arrays.stream(values())
                .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                .collect(Collectors.toList());
    }
}

public class Main {
    static HashMap<Character, Integer> romanMap = new HashMap<>();
    static{
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
    }

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(calc(input));
        scanner.close();
    }

    public static String calc(String input) throws Exception {
        String inp = input.replaceAll("\\s+", "");
        if (!isDecExpValid(inp) && !isRomanExpValid(inp))
            throw new Exception("Wrong expression.");
        String[] vars = inp.split("[-+*/]");
        char sign = inp.charAt(vars[0].length());
        if (isDecExpValid(inp))
            return String.valueOf(doDecCalc(vars, sign));
        else{
            return doRomanCalc(vars, sign);
        }
    }

    static int doDecCalc(String[] vars, char sign) throws Exception {
        int a = Integer.parseInt(vars[0]);
        int b = Integer.parseInt(vars[1]);
        if (sign == '+')
            return (a + b);
        if (sign == '-')
            return (a - b);
        if (sign == '*')
            return (a * b);
        else{
            if (b == 0)
                throw new Exception("Division by zero");
            return (a / b);
        }
    }

    static String doRomanCalc(String[] vars, char sign) throws Exception{
        int a = roman2arab(vars[0]);
        int b = roman2arab(vars[1]);
        if (sign == '+')
            return (arabic2Roman(a + b));
        if (sign == '*')
            return (arabic2Roman(a * b));
        if (sign == '-'){
            if (a - b <= 0)
                throw new Exception("roman result is 0 or less");
            else
                return (arabic2Roman(a - b));
        }
        else{
            if (b == 0)
                throw new Exception("Division by zero");
            return (arabic2Roman(a / b));
        }
    }

    static boolean isRomanExpValid(String romanExp){
        return romanExp.matches("(X|IX|IV|V|V?I{1,3})[-+*/](X|IX|IV|V|V?I{1,3})");
    }

    static boolean isDecExpValid(String romanExp){
        return romanExp.matches("([1-9]|10)[-+*/]([1-9]|10)");
    }

    static int roman2arab(String romanNum){
        int current, previous = 0, result = 0;

        for (int i = romanNum.length() - 1; i >= 0; i--){
            current = romanMap.get(romanNum.charAt(i));
            result = current < previous ? result - current : result + current;
            previous = current;
        }
        return result;
    }

    static String arabic2Roman(int number) {
        int i = 0;
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
        StringBuilder sb = new StringBuilder();
        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }
        return sb.toString();
    }
}

