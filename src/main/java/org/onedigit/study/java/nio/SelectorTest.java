package org.onedigit.study.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SelectorTest
{
    static int port = 5566;
    static Selector selector = null;
    
    public static void main(String[] args)
    {
        try {
            selector = Selector.open();
            
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            
            System.out.println("server socket channel: " + serverSocketChannel);
            serverSocketChannel.configureBlocking(false);
            SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            
            while (true) {
                selector.select();
                System.out.println("accept ready: " + key.isAcceptable());
                SocketChannel socketChannel = serverSocketChannel.accept();
                System.out.println("Connected with: " + socketChannel);
            }
            
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                selector.close();
            } catch (IOException ex) {
            }
        }
    }
}
