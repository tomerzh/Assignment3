#include <iostream>
#include <stdlib.h>
#include <thread>
#include "../include/connectionHandler.h"
#include "../include/socketThread.h"
#include "../include/keyboardThread.h"
#include <condition_variable>
#include <mutex>

using boost::asio::ip::tcp;

/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/
int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);

    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }

    std::mutex key;
    std::condition_variable conn;

    socketThread sock = socketThread(connectionHandler, key, conn);
    keyboardThread keyboard = keyboardThread(connectionHandler, key, conn, sock);



    std::thread th1(&socketThread::run, &sock);
    std::thread th2(&keyboardThread::run, &keyboard);

    th2.join();
    th1.join();
    return 0;
}