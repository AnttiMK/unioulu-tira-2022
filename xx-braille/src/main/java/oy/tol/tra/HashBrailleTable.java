package oy.tol.tra;

import java.util.Objects;

public class HashBrailleTable extends BrailleTable {

    /**
     * A char array that holds the braille symbols,
     * indexed by the ASCII value of the corresponding character.
     */
    private char[] brailleSymbols;

    /**
     * Initializes the table containing the braille symbols.
     * Considering the fact that the symbol table for this exercise uses nordic characters,
     * the table is initialized with the size of the extended ASCII table (256).
     */
    @Override
    protected void initializeTable() {
        brailleSymbols = new char[256];
    }

    @Override
    protected void addToTable(Character character, Character brailleSymbol) {
        Objects.requireNonNull(character, "Character cannot be null");
        Objects.requireNonNull(brailleSymbol, "Braille symbol cannot be null");

        // If the character's ASCII value is greater than 255, reallocate the array to fit the character.
        if (character >= brailleSymbols.length) {
            reallocate(character);
        }

        brailleSymbols[character] = brailleSymbol;
    }

    private void reallocate(int size) {
        char[] newBrailleSymbols = new char[size];
        System.arraycopy(brailleSymbols, 0, newBrailleSymbols, 0, brailleSymbols.length);
        brailleSymbols = newBrailleSymbols;
    }

    @Override
    public Character lookupBrailleSymbol(Character forCharacter) {
        Objects.requireNonNull(forCharacter, "The character cannot be null.");

        final char toFind = Character.toUpperCase(forCharacter);
        // If the character's ASCII value is greater than the size of the array,
        // can assume that the character is not in the table.
        if (toFind >= brailleSymbols.length) {
            return UNKNOWN_CHAR_OR_SYMBOL;
        }

        char brailleSymbol = brailleSymbols[toFind];
        if (brailleSymbol == 0) {
            return UNKNOWN_CHAR_OR_SYMBOL;
        }
        return brailleSymbol;
    }

    @Override
    public Character lookupCharacter(Character forBrailleSymbol) {
        Objects.requireNonNull(forBrailleSymbol, "The braille symbol cannot be null.");

        for (int i = 0; i < brailleSymbols.length; i++) {
            if (brailleSymbols[i] == forBrailleSymbol) {
                return (char) i;
            }
        }

        return UNKNOWN_CHAR_OR_SYMBOL;
    }

}
