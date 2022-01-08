
#ifndef CLIENT_KEYBOARDTHREAD_H
#define CLIENT_KEYBOARDTHREAD_H

#include "connectionHandler.h"

class keyboardThread {
    public:
        keyboardThread(ConnectionHandler &connectionHandler, std::mutex &_mutex, std::condition_variable &_conn);
        void run();
        void terminate();
        bool isShouldTerminated();

    private:
        ConnectionHandler &handler;
        std::mutex &key;
        std::condition_variable &conn;
        bool volatile shouldTerminate; //shouldTerminate
};


#endif //CLIENT_KEYBOARDTHREAD_H
