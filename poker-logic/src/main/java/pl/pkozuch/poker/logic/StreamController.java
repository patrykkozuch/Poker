package pl.pkozuch.poker.logic;

import java.io.*;

public class StreamController {

    private final BufferedReader in;
    private final BufferedWriter out;


    public StreamController(InputStream in, OutputStream out) {
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new BufferedWriter(new OutputStreamWriter(out));
    }

    public void sendWithNewLine(String message) {
        try {
            send(message);
            out.newLine();
            out.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void send(String message) {
        try {
            out.write(message);
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String readLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String readLines() {
        try {
            StringBuilder message = new StringBuilder();
            while (in.ready()) {
                message.append(in.readLine());
                message.append(System.getProperty("line.separator"));
            }
            return message.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isInReady() {
        try {
            return in.ready();
        } catch (Exception e) {
            return false;
        }
    }
}
