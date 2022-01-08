//
// Created by spl on 06/01/2022.
//

#ifndef CLIENT_SOCKETTHREAD_H
#define CLIENT_SOCKETTHREAD_H

#include "connectionHandler.h"

class socketThread {
private:
    ConnectionHandler &handler;
    std::mutex &key;
    bool shouldTerminate;

public:
    socketThread(ConnectionHandler &connectionHandler, std::mutex &_mutex);
    void run();
};


#endif //CLIENT_SOCKETTHREAD_H
