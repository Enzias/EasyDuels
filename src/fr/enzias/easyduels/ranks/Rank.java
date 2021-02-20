package fr.enzias.easyduels.ranks;

import java.util.List;

public class Rank {

    private String name;
    private List<String> permissionNode;
    private List<String> rewardCommands;

    public Rank(String name, List<String> permissionNode, List<String> rewardCommands) {
        this.name = name;
        this.permissionNode = permissionNode;
        this.rewardCommands = rewardCommands;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPermissionNode() {
        return permissionNode;
    }

    public void setPermissionNode(List<String> permissionNode) {
        this.permissionNode = permissionNode;
    }

    public List<String> getRewardCommands() {
        return rewardCommands;
    }

    public void setRewardCommands(List<String> rewardCommands) {
        this.rewardCommands = rewardCommands;
    }

}
