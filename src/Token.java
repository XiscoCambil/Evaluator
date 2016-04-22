import java.util.ArrayList;
import java.util.List;

public class Token {
    enum Toktype {
        NUMBER, OP, PAREN
    }

    public char getTk() {
        return tk;
    }

    public Toktype getTtype() {
        return ttype;
    }

    public int getValue() {
        return value;
    }

    // Pensa a implementar els "getters" d'aquests atributs
    private Toktype ttype;
    private int value;
    private char tk;

    // Constructor privat. Evita que es puguin construir objectes Token externament
    private Token() {
    }

    // Torna un token de tipus "NUMBER"
    static Token tokNumber(int value) {
        Token t = new Token();
        t.ttype = Toktype.NUMBER;
        t.value = value;
        return t;
    }

    // Torna un token de tipus "OP"
    static Token tokOp(char c) {
        Token t = new Token();
        t.ttype = Toktype.OP;
        t.tk = c;
        return t;
    }

    // Torna un token de tipus "PAREN"
    static Token tokParen(char c) {
        Token t = new Token();
        t.ttype = Toktype.PAREN;
        t.tk = c;
        return t;
    }

    // Mostra un token (conversió a String)
    public String toString() {
        return "" + this.ttype + this.value + this.tk;
    }

    // Mètode equals. Comprova si dos objectes Token són iguals
    public boolean equals(Object o) {
        Token token = (Token) o;
        if (token.ttype != this.ttype) return false;
        if (token.ttype == Toktype.NUMBER) return token.value == this.value;
        if (token.ttype == Toktype.OP) return token.tk == this.tk;
        if (token.ttype == Toktype.PAREN) return token.tk == this.tk;
        return false;
    }

    // A partir d'un String, torna una llista de tokens
    public static Token[] getTokens(String expr) {
        List<Token> T = new ArrayList<Token>();
        for (int i = 0; i < expr.length(); i++) {
            String numero = "";
            while (expr.charAt(i) >= 48 && expr.charAt(i) <= 57) {
                numero += expr.charAt(i);
                if (i == expr.length() - 1) {
                    break;
                } else {
                    i++;
                }
            }
            if (numero.length() > 0) {
                T.add(tokNumber(Integer.parseInt(numero)));
            }
            if (expr.charAt(i) == 43 || expr.charAt(i) == 45 || expr.charAt(i) == 42 || expr.charAt(i) == 47) {
                T.add(tokOp(expr.charAt(i)));
            } else if (expr.charAt(i) == 40 || expr.charAt(i) == 41) {
                T.add(tokParen(expr.charAt(i)));
            }
        }
        Token[] Token = new Token[T.size()];
        T.toArray(Token);
        return Token;
    }

}
