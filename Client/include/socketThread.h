//
// Created by spl on 06/01/2022.
//

#ifndef CLIENT_SOCKETTHREAD_H
#define CLIENT_SOCKETTHREAD_H

#include "connectionHandler.h"
#include "keyboardThread.h"

class socketThread {
private:
    ConnectionHandler &handler;
    std::mutex &key;
    std::condition_variable &conn;
    bool shouldTerminate;
    keyboardThread keyboard;

public:
    socketThread(ConnectionHandler &connectionHandler, std::mutex &_mutex,
                 std::condition_variable &_conn, keyboardThread &_keyboard);
    void run();
};


#endif //CLIENT_SOCKETTHREAD_H
