package ru.mirea.kozharinov.practice7.socketconnection;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketUtils {

    @NonNull
    @Contract("_ -> new")
    public static BufferedReader getReader(@NonNull Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @NonNull
    @Contract("_ -> new")
    public static PrintWriter getWriter(@NonNull Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }
}
