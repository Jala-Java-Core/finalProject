package org.example;

import java.util.HashMap;

public class ChannelSystem {

    private HashMap<String, Channel> channels;

    public ChannelSystem(){
        this.channels = new HashMap<String, Channel>();
    }

    public HashMap<String, Channel> getChannels(){
        return this.channels;
    }

    public void addChannel(Channel channel){
        if (channel == null){
            throw new IllegalArgumentException("Channel must not be null");
        }
        this.channels.put(channel.getName(),channel);
    }

    public void removeChannel(String channelName){
        if (channelName == null){
            throw new IllegalArgumentException("Channel Name must not be null");
        }
        if (channelName == ""){
            throw new IllegalArgumentException("Channel Name must not be empty");
        }
        if (!this.channels.containsKey(channelName)) {
            throw new IllegalArgumentException("Channel does not exist");
        }
        this.channels.remove(channelName);
    }

    public void editChannel(Channel channel){
        if (channel == null){
            throw new IllegalArgumentException("Channel must not be null");
        }
        if (!this.channels.containsKey(channel.getName())) {
            throw new IllegalArgumentException("Channel Name does not exist");
        }
        this.channels.replace(channel.getName(), channel);
    }

    public Channel getChannel(String channelName){
        if (channelName == null){
            throw new IllegalArgumentException("Channel Name must not be null");
        }
        if (channelName == ""){
            throw new IllegalArgumentException("Channel Name must not be empty");
        }
        if (!this.channels.containsKey(channelName)) {
            throw new IllegalArgumentException("Channel does not exist");
        }
        return this.channels.get(channelName);
    }
}
