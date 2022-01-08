
#ifndef CLIENT_KEYBOARDTHREAD_H
#define CLIENT_KEYBOARDTHREAD_H

#include "connectionHandler.h"
#include "socketThread.h"

class keyboardThread {
    public:
        keyboardThread(ConnectionHandler &connectionHandler, std::mutex &_mutex,
                       std::condition_variable &_conn, socketThread &_socket);
        void run();
        void terminate();
        bool isShouldTerminated();

    private:
        ConnectionHandler &handler;
        std::mutex &key;
        std::condition_variable &conn;
        bool volatile shouldTerminate; //shouldTerminate
        socketThread &socket;
};


#endif //CLIENT_KEYBOARDTHREAD_H
