
#include "../include/keyboardThread.h"
#include "regex"
#include "../include/connectionHandler.h"
#include <boost/algorithm/string.hpp>

using boost::asio::ip::tcp;


keyboardThread::keyboardThread(ConnectionHandler &connectionHandler, std::mutex &_mutex,
                               std::condition_variable &_conn, socketThread &_socket) :
                               handler(connectionHandler), key(_mutex), conn(_conn), socket(_socket) {}

using namespace std;

static void shortToBytes(short num, char* bytesArr) // from assi
{
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}

void keyboardThread::run() {
    while (!socket.isShouldTerminated()) {
        const short bufsize = 1024;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
        std::string line(buf);

        std::vector<std::string> words;
        boost::split(words, line, boost::is_any_of(" "));
        string currWord = words.front();

        char *currOpcode = new char[2];
        char *endline = new char[1];
        endline[0] = ';';
        bool toSendEndline = true;

        if (!words.empty()) {
            if (currWord == "REGISTER") {
                shortToBytes((short) 1, currOpcode);
                handler.sendBytes(currOpcode, 2);
                handler.sendFrameAscii(words.at(1), '\0'); //userName
                handler.sendFrameAscii(words.at(2), '\0'); //password
                handler.sendFrameAscii(words.at(3), '\0'); //birthday
            }
            else if (currWord == "LOGIN") {
                shortToBytes((short) 2, currOpcode);
                handler.sendBytes(currOpcode, 2);
                handler.sendFrameAscii(words.at(1), '\0'); //userName
                handler.sendFrameAscii(words.at(2), '\0'); //password
                char *captcha = new char[1];
                if(words.at(3) == "1"){
                    captcha[0] = 1;
                    handler.sendBytes(captcha,1);
                }
                else {
                    captcha[0] = 0;
                    handler.sendBytes(captcha,1);
                }
                delete[] captcha;
            }
            else if ((currWord == "LOGOUT")) {
                shortToBytes((short) 3, currOpcode);
                handler.sendBytes(currOpcode, 2);
                //shouldTerminate = true;
            }
            else if ((currWord == "FOLLOW")){
                shortToBytes((short) 4, currOpcode);
                handler.sendBytes(currOpcode, 2);
                char *follow = new char[2];
                if (words.at(1) == "0") {
                    shortToBytes((short) 0, follow);
                }
                else {
                    shortToBytes((short) 1, follow);
                }
                handler.sendBytes(follow, 2);
                handler.sendFrameAscii(words.at(2), '\0'); //username
            }
            else if ((currWord == "POST")){
                shortToBytes((short) 5, currOpcode);
                handler.sendBytes(currOpcode, 2);
                string content = "";
                for(int i = 1; i < words.size(); i++) {
                    content.append(words.at(i));
                    content.append(" ");
                }
                handler.sendFrameAscii(content, '\0'); //content
            }
            else if ((currWord == "PM")){
                shortToBytes((short) 6, currOpcode);
                handler.sendBytes(currOpcode, 2);
                handler.sendFrameAscii(words.at(1), '\0'); //userName
                string content = "";
                for(int i = 2; i < words.size(); i++) {
                    content.append(words.at(i));
                    content.append(" ");
                }
                handler.sendFrameAscii(content, '\0'); //content
            }
            else if ((currWord == "LOGSTAT")){
                shortToBytes((short) 7, currOpcode);
                handler.sendBytes(currOpcode, 2);
            }
            else if ((currWord == "STAT")){
                shortToBytes((short) 8, currOpcode);
                handler.sendBytes(currOpcode, 2);
                handler.sendFrameAscii(words.at(1), '\0'); //username list
            }
            else if ((currWord == "BLOCK")){
                shortToBytes((short) 12, currOpcode);
                handler.sendBytes(currOpcode, 2);
                handler.sendFrameAscii(words.at(1), '\0'); //userName
            }
            else {
                toSendEndline = false;
                cout << "No such Command!" << endl;
            }
        }
        else {
            toSendEndline = false;
            cout << "Empty input!" << endl;
        }

        if(toSendEndline) {
            handler.sendBytes(endline,1);
        }
        delete[] currOpcode;
        delete[] endline;

        std::unique_lock<std::mutex> lock(key);
        conn.wait(lock);
    }
}