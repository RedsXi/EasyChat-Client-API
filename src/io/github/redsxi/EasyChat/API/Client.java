package io.github.redsxi.EasyChat.API;

import io.github.redsxi.InternalException;

public class Client {
    private static final String format = "&&&&&%{CMD_NUM}@@@@@%{ARG}&&&&&"; //Example for BAD_REQUEST:&&&&&300@@@@@245123123&&&&&

    public enum ReceiveCommand {
        OK, // 100
        SERVER_INTERNAL_ERROR, // 200
        NO_DO, // 201
        BAD_REQUEST, // 300
        NO_LOGIN // 301
    }

    public enum SendCommand {
        GET_RECENT_EVENT, // 400
        SEND_EVENT, // 401
        REGISTER_ACCOUNT, //402
        LOGIN, //403
        LOGOUT //404
    }

    public static String commandToString(SendCommand Command,String Argument) {
        String result = format;
        int CommandInt;

        CommandInt = switch(Command) {
            case GET_RECENT_EVENT -> 400;
            case SEND_EVENT -> 401;
            case REGISTER_ACCOUNT -> 402;
            case LOGIN -> 403;
            case LOGOUT -> 404;
        };

        result = result.replace("%{CMD_NUM}",String.valueOf(CommandInt)).replace("%{ARG}",Argument);

        return result;
    }

    public ReceiveCommand getCommandFromText(String text) throws Exception{
        ReceiveCommand rc = null;

        int cmd = Integer.parseInt(text.replace("&&&&&","").split("@@@@@")[0],10);

        rc = switch (cmd) {
            case 100 -> ReceiveCommand.OK;
            case 200 -> ReceiveCommand.SERVER_INTERNAL_ERROR;
            case 300 -> ReceiveCommand.BAD_REQUEST;
            case 301 -> ReceiveCommand.NO_LOGIN;
            case 201 -> ReceiveCommand.NO_DO;
            default -> throw new InternalException("Bad request");
        };

        return rc;
    }

    public String getArgumentFromText(String text) {
        return text.replace("&&&&&","").split("@@@@@")[1];
    }
}
