import entertainment.controllers.ChannelController;
import entertainment.controllers.ContentController;
import entertainment.models.Channel;
import entertainment.models.Content;
import entertainment.views.Entertainment;

import java.io.IOException;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        System.out.println("Entertainment");
        Entertainment entertainment = new Entertainment();
        try {
            entertainment.start();
        } catch (IOException ioException) {
            System.out.println("Something went wrong!");
        }
    }

    public static void Example() {
        ChannelController channelController = new ChannelController();
        channelController.deleteChannel("235");
        Channel channel = new Channel("235", "Preguntas Incómodas");
        channelController.addChannel(channel);

        channel.setName("New channel name");
        channelController.updateChannel(channel);
        System.out.println(channelController.getChannels());

        ContentController contentController = new ContentController();

        Content content = new Content(
                "¿Por qué los RICOS juegan a SER POBRES? ¿Por qué los POBRES juegan a SER RICOS?",
                "¿Ser pobre es chic? Es lo que piensan algunos ricos. Hoy veremos cómo la pobreza es romantizada, pero a su vez, como las personas con menos poder adquisitivo intentan aparentar un nivel de vida mayor del que realmente pueden aspirar.",
                Duration.ofMinutes(30));
        contentController.addContent("235", content);
        String contentId = content.getId();

        content = new Content(
                "LATINOAMÉRICA, ¿ROJA y POBRE? | ¿Qué trae el retorno de la izquierda?",
                "Tras el triunfo de Gustavo Petro en Colombia, el mapa de LATAM aparece lleno de gobiernos de izquierda. López Obrador en México, Alberto Fernández en Argentina, Pedro Castillo en Perú, Gabriel Boric en Chile y próximamente, podría salir de la presidencia Jair Bolsonaro en Brasil. ¿Por qué ocurrió todo esto?",
                Duration.ofMinutes(60));
        contentController.addContent("235", content);

        content.setTitle(content.getTitle() + " Updated title");
        contentController.updateContent("235", content);

        content = new Content(
                "Content title",
                "Content summary",
                Duration.ofHours(12));
        contentController.addContent("235", content);
        contentController.deleteContent("235", contentId);

        channel = channelController.getChannelByNumber("235");
    }
}
