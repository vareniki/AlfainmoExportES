package com.alfainmo.util;

import java.io.IOException;

public interface ICSVPrint {

    void changeDelimiter(char newDelimiter);

    void changeQuote(char newQuote);

    void setAutoFlush(boolean autoFlush);

    boolean checkError();

    void println(String value);

    void writeln(String value) throws IOException;

    void println();

    void writeln() throws IOException;

    void println(String[] values);

    void writeln(String[] values) throws IOException;

    void println(String[][] values);

    void writeln(String[][] values) throws IOException;

    void printlnComment(String comment);

    void writelnComment(String comment) throws IOException;

    void print(String value);

    void write(String value) throws IOException;

    void flush() throws IOException;

    void close() throws IOException;

    void print(String[] values);

    void write(String[] values) throws IOException;

    void setAlwaysQuote(boolean alwaysQuote);
}
