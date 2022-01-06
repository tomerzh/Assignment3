//
// Created by spl on 06/01/2022.
//

#include "../include/socketThread.h"

using namespace std;

socketThread::socketThread(ConnectionHandler &connectionHandler) : handler(
        connectionHandler){shouldTerminate=false;}

static short bytesToShort(char* bytesArr)
{
    short result = (short)((bytesArr[0] & 0xff) << 8);
    result += (short)(bytesArr[1] & 0xff);
    return result;
}

void socketThread::run() {
    while(!shouldTerminate){
        char opBytes[2];
        handler.getBytes(opBytes, 2);
        short opCode = bytesToShort(opBytes);

        if (opCode == 9) { //Notification
            char typeBytes[1];
            handler.getBytes(typeBytes, 1);
            short type = bytesToShort(typeBytes);
            string notification;
            if(type == 0){
                notification = "NOTIFICATION PM ";
            }

            else{
                notification = "NOTIFICATION Public ";
            }

            string postingUser;
            handler.getFrameAscii(postingUser, '\0');

            string content;
            handler.getFrameAscii(content, '\0');

            cout << notification + postingUser + " " + content << endl;
        }

        else if(opCode == 10){ //Ack
            char messageOp[2];
            handler.getBytes(messageOp, 2);
            short opMessage = bytesToShort(messageOp);

            if(opMessage == 4){//ack for follow
                string userName;
                handler.getFrameAscii(userName, ';');
                cout << "ACK" << " " << std::string(std::to_string((int) opMessage))  << " " << userName << endl;
            }

            else if(opMessage == 8){//ack for stat
                char ageByte[2];
                char numOfPostsByte[2];
                char numFollowersByte[2];
                char numFollowingsByte[2];

                handler.getBytes(ageByte, 2);
                short age = bytesToShort(messageOp);

                handler.getBytes(numOfPostsByte, 2);
                short numOfPosts = bytesToShort(messageOp);

                handler.getBytes(numFollowersByte, 2);
                short numFollowers = bytesToShort(messageOp);

                handler.getBytes(numFollowingsByte, 2);
                short numFollowings = bytesToShort(messageOp);

                cout << "ACK" << " " << std::string(std::to_string((int) opMessage))  << " " << age << " "
                << numOfPosts << " " << numFollowers << " " << numFollowings << endl;
            }

            else if(opMessage == 2){//ack for logout
                cout << "ACK" << " " << std::string(std::to_string((int) opMessage))  << endl;
                this->shouldTerminate = true;
            }

            else{
                cout << "ACK" << " " << std::string(std::to_string((int) opMessage))  << endl;
            }

        }

        else if(opCode == 11){ // error
            char messageOp[2];
            handler.getBytes(messageOp, 2);
            short opMessage = bytesToShort(messageOp);

            cout << "ERROR" << " " << std::string(std::to_string((int) opMessage))  << endl;
        }
    }
};