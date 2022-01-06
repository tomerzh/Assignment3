
#ifndef CLIENT_KEYBOARDTHREAD_H
#define CLIENT_KEYBOARDTHREAD_H

#include "connectionHandler.h"

class keyboardThread {
    public:
        keyboardThread(ConnectionHandler &connectionHandler);
        void run();

    private:
        ConnectionHandler &handler;
        bool shouldTerminate; //shouldTerminate
};


#endif //CLIENT_KEYBOARDTHREAD_H
