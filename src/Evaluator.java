import java.util.LinkedList;
import java.util.List;

public class Evaluator {


    public static int calculate(String expr) {
        // Convertim l'string d'entrada en una llista de tokens
        Token[] tokens = Token.getTokens(expr);
        // Efectua el procediment per convertir la llista de tokens en notació RPN
        List<Token> cua = new LinkedList<>();
        LinkedList<Token> operantes = new LinkedList<>();

        for (int i = 0; i < tokens.length; i++) {
            // C
            if (tokens[i].getTtype() == Token.Toktype.NUMBER) {
                cua.add(tokens[i]);
            } else if (tokens[i].getTtype() == Token.Toktype.OP) {
                if (!operantes.isEmpty()) {
                    if (CompareTo(tokens[i].getTk(), operantes.peek().getTk())) {
                        operantes.push(tokens[i]);
                    } else {
                        cua.add(operantes.pop());
                        if (!operantes.isEmpty() && !CompareTo(tokens[i].getTk(), operantes.peek().getTk())) {
                            cua.add(operantes.pop());
                        }
                        operantes.push(tokens[i]);
                    }
                } else {
                    operantes.push(tokens[i]);
                }
            } else if (tokens[i].getTk() == '(') {
                operantes.push(tokens[i]);
            } else if (tokens[i].getTk() == ')') {
                while (operantes.peek().getTk() != '(') {
                    cua.add(operantes.pop());
                }
                operantes.pop();
            }
        }
        while (!operantes.isEmpty()) {
            cua.add(operantes.pop());
        }
        Token[] operacio = new Token[cua.size()];
        cua.toArray(operacio);
        System.out.print(cua);

        // Finalment, crida a calcRPN amb la nova llista de tokens i torna el resultat
        return calcRPN(operacio);
    }

    public static int calcRPN(Token[] list) {
        LinkedList<Token> lista = new LinkedList<>();
        for (int i = 0; i < list.length; i++) {
            while (list[i].getTtype() == Token.Toktype.NUMBER) {
                lista.push(list[i]);
                if (i == list.length - 1) {
                    break;
                } else {
                    i++;
                }
            }
            Token n2 = lista.pop();
            Token n1 = lista.pop();

            if (list[i].getTk() == '+') {
                lista.push(suma(n1, n2));
            } else if (list[i].getTk() == '-') {
                lista.push(resta(n1, n2));
            } else if (list[i].getTk() == '*') {
                lista.push(multiplicacio(n1, n2));
            } else if (list[i].getTk() == '/') {
                lista.push(divisio(n1, n2));
            }
        }
        System.out.println(lista.peek());
        return lista.poll().getValue();
    }

    public static Token suma(Token a, Token b) {
        int r = a.getValue();
        int r2 = b.getValue();
        Token resultat = Token.tokNumber(r + r2);
        return resultat;
    }

    public static Token resta(Token a, Token b) {

        int r = a.getValue();
        int r2 = b.getValue();
        Token resultat = Token.tokNumber(r - r2);
        return resultat;
    }

    public static Token multiplicacio(Token a, Token b) {
        int r = a.getValue();
        int r2 = b.getValue();
        Token resultat = Token.tokNumber(r * r2);
        return resultat;
    }

    private static Token divisio(Token a, Token b) {
        int r = a.getValue();
        int r2 = b.getValue();
        Token resultat = Token.tokNumber(r / r2);
        return resultat;
    }

    private static boolean CompareTo(char op1, char op2) {
        int operante1 = Priority(op1);
        int operante2 = Priority(op2);

        return operante1 > operante2;
    }

    private static int Priority(char op) {
        int priority = 0;
        switch (op) {
            case '*':
                priority = 2;
                break;
            case '/':
                priority = 2;
                break;
            case '+':
                priority = 1;
                break;
            case '-':
                priority = 1;
                break;
        }
        return priority;
    }


}
