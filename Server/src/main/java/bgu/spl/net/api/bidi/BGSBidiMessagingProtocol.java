package bgu.spl.net.api.bidi;

import bgu.spl.net.api.bidi.commands.*;

import java.util.HashMap;

public class BGSBidiMessagingProtocol<T> implements BidiMessagingProtocol<T> {

    private boolean shouldTerminate = false;
    private int connectionId;
    private Connections<T> connections;

    private HashMap<Short, Command> opcodeToCommand;
    private Message currMessage;
    private Command currCommand;

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
            currMessage = (Message) message;
            currCommand = opcodeToCommand.get(currMessage.getOpCode());
            currCommand.process(currMessage);
        }
    }

    @Override
    public boolean shouldTerminate() {
        return false;
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
