
#ifndef CLIENT_KEYBOARDTHREAD_H
#define CLIENT_KEYBOARDTHREAD_H

#include "connectionHandler.h"
#include "socketThread.h"
#include <condition_variable>
#include <mutex>


class keyboardThread {
    public:
        keyboardThread(ConnectionHandler &connectionHandler, std::mutex &_mutex,
                       std::condition_variable &_conn, socketThread &_socket);
        void run();

    private:
        ConnectionHandler &handler;
        std::mutex &key;
        std::condition_variable &conn;
        socketThread &socket;
};


#endif //CLIENT_KEYBOARDTHREAD_H
