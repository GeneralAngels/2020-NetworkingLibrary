package com.ga2230.networking;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {

    private ArrayList<Node> slaves = new ArrayList<>();

    private HashMap<String, Command> commands = new HashMap<>();

    protected String id;

    protected HashMap<String, String> variables = new HashMap<>();

    protected Node(String id) {
        this.id = id;
    }

    protected void set(String name, String value) {
        variables.put(name, value);
    }

    protected String get(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        return null;
    }

    protected Node enslave(Node slave) {
        slaves.add(slave);
        return slave;
    }

    protected Command command(String name, Command command) {
        commands.put(name, command);
        return command;
    }

    protected ArrayList<Node> slaves() {
        return new ArrayList<>(slaves);
    }

    protected HashMap<String, Command> commands() {
        return new HashMap<>(commands);
    }

    public Node find(String name) {
        if (name.toLowerCase().equals("master") || name.toLowerCase().equals(id.toLowerCase())) {
            return this;
        } else {
            for (Node node : slaves) {
                Node found = node.find(name);
                if (found != null)
                    return found;
            }
        }
        return null;
    }

    public String execute(String command, String parameter) throws Exception {
        Command executable = commands.get(command);
        if (executable != null) {
            return executable.execute(parameter);
        }
        return null;
    }

    public interface Command {
        String execute(String parameter) throws Exception;
    }
}
