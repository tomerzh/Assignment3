
#ifndef CLIENT_KEYBOARDTHREAD_H
#define CLIENT_KEYBOARDTHREAD_H

#include "connectionHandler.h"

class keyboardThread {
    public:
        keyboardThread(ConnectionHandler &connectionHandler, std::mutex &_mutex);
        void run();

    private:
        ConnectionHandler &handler;
        std::mutex &key;
        bool shouldTerminate; //shouldTerminate
};


#endif //CLIENT_KEYBOARDTHREAD_H
