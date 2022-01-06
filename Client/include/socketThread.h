//
// Created by spl on 06/01/2022.
//

#ifndef CLIENT_SOCKETTHREAD_H
#define CLIENT_SOCKETTHREAD_H

#include "connectionHandler.h"

class socketThread {
private:
    ConnectionHandler &handler;
    bool shouldTerminate;

public:
    socketThread(ConnectionHandler &connectionHandler);
    void run();
};


#endif //CLIENT_SOCKETTHREAD_H
