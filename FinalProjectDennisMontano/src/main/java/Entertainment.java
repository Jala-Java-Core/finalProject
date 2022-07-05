import java.util.HashMap;

public class Entertainment {

    private HashMap<String, Channel> channels = new HashMap<String, Channel>();

    public Entertainment() {

    }

    public void addChannel(String name) {
        if(!channels.containsKey(name)) {
            channels.put(name, new Channel(name));
        }
    }

    public void addChannel(Channel channel) {
        if(!channels.containsKey(channel.getChannelName())) {
            channels.put(channel.getChannelName(), channel);
        }
    }

    public void deleteChannel(String name) {
        if(channels.containsKey(name)) {
            channels.remove(name, new Channel(name));
        }
    }

    public Channel getChannel(String name) {
        if(channels.containsKey(name)) {
            return channels.get(name);
        }
        return null;
    }
}
