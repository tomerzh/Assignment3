package bgu.spl.net.api.bidi;

import bgu.spl.net.api.bidi.commands.*;

import java.util.HashMap;

public class BGSBidiMessagingProtocol<T extends Message> implements BidiMessagingProtocol<T> {

    private boolean shouldTerminate = false;
    private int connectionId;
    private Connections<T> connections;

    private HashMap<Short, Command> opcodeToCommand;
    private Message currMessage;
    private Command currCommand;

    private short ackOpcode = 10;
    private short errorOpcode = 11;

    public BGSBidiMessagingProtocol(){
        opcodeToCommand = new HashMap<>();
    }

    @Override
    public void start(int connectionId, Connections<T> connections) {
        this.connectionId = connectionId;
        this.connections = connections;
        initOpCodes();
    }

    @Override
    public void process(T message) {
        if(message != null){
            currMessage = message;
            currCommand = opcodeToCommand.get(currMessage.getOpCode());
            if(currCommand != null){
                boolean processCompleted = currCommand.process(currMessage, connectionId, connections);
                if(!processCompleted){ //error occurred
                    currCommand = opcodeToCommand.get(errorOpcode);
                    currCommand.process(currMessage, connectionId, connections);
                }
                else { //ack
                    currCommand = opcodeToCommand.get(ackOpcode);
                    currCommand.process(currMessage, connectionId, connections);
                }
            }
            else {
                currCommand = opcodeToCommand.get(errorOpcode);
                currCommand.process(currMessage, connectionId, connections);
            }
        }
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    public void terminate(){
        shouldTerminate = true;
    }

    private void initOpCodes(){
        opcodeToCommand.put((short) 1, new RegisterCommand());
        opcodeToCommand.put((short) 2, new LoginCommand());
        opcodeToCommand.put((short) 3, new LogoutCommand());
        opcodeToCommand.put((short) 4, new FollowCommand());
        opcodeToCommand.put((short) 5, new PostCommand());
        opcodeToCommand.put((short) 6, new PMCommand());
        opcodeToCommand.put((short) 7, new LogstatCommand());
        opcodeToCommand.put((short) 8, new StatCommand());
        opcodeToCommand.put((short) 9, new NotificationCommand());
        opcodeToCommand.put((short) 10, new AckCommand());
        opcodeToCommand.put((short) 11, new ErrorCommand());
        opcodeToCommand.put((short) 12, new BlockCommand());
    }
}
