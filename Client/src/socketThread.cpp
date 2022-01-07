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

        cout << "opCode: " << opCode << endl;

        if (opCode == 9) { //Notification
            char typeBytes[2];
            handler.getBytes(typeBytes, 2);
            short type = bytesToShort(typeBytes);

            string notification;
            if(type == 0) {
                notification = "NOTIFICATION PM ";
            }
            else{
                notification = "NOTIFICATION Public ";
            }

            string postingUser;
            handler.getFrameAscii(postingUser, '\0');

            string content;
            handler.getFrameAscii(content, '\0');

            cout << notification + postingUser + " " + content.substr(1, content.length()) << endl;

            string end;
            handler.getFrameAscii(end, ';');
        }

        else if(opCode == 10){ //Ack
            char messageOp[2];
            handler.getBytes(messageOp, 2);
            short opMessage = bytesToShort(messageOp);

            cout << "messageOP: " << opMessage << endl;

            if(opMessage == 4){//ack for follow
                string userName;
                handler.getFrameAscii(userName, ';');
                cout << "ACK" << " " << std::string(std::to_string((int) opMessage))
                << " " << userName.substr(0, userName.length() - 1) << endl;
            }

            else if(opMessage == 8 || opMessage == 7){//ack for stat


                char ageByte[2];
                char numOfPostsByte[2];
                char numFollowersByte[2];
                char numFollowingsByte[2];

                handler.getBytes(ageByte, 2);
                short age = bytesToShort(ageByte);

                handler.getBytes(numOfPostsByte, 2);
                short numOfPosts = bytesToShort(numOfPostsByte);

                handler.getBytes(numFollowersByte, 2);
                short numFollowers = bytesToShort(numFollowersByte);

                handler.getBytes(numFollowingsByte, 2);
                short numFollowings = bytesToShort(numFollowingsByte);

                cout << "ACK" << " " << std::string(std::to_string((int) opMessage))  << " " << age << " "
                << numOfPosts << " " << numFollowers << " " << numFollowings << endl;

                string end;
                handler.getFrameAscii(end, ';');
            }

            else if(opMessage == 3){//ack for logout
                cout << "ACK" << " " << std::string(std::to_string((int) opMessage))  << endl;
                this->shouldTerminate = true;

                string end;
                handler.getFrameAscii(end, ';');
            }

            else{
                cout << "ACK" << " " << std::string(std::to_string((int) opMessage))  << endl;
                string end;
                handler.getFrameAscii(end, ';');
            }

        }

        else if(opCode == 11){ // error
            char messageOp[2];
            handler.getBytes(messageOp, 2);
            short opMessage = bytesToShort(messageOp);

            cout << "ERROR" << " " << std::string(std::to_string((int) opMessage))  << endl;

            string end;
            handler.getFrameAscii(end, ';');
        }

        else{
            cout << "no such opCode" << endl;
        }
    }
};