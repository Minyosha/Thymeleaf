package gb.Thymeleaf.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
public class IntegrationConfiguration {
    @Bean
    public MessageChannel messageChannelInput() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel messageChannelWriter() {
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "messageChannelInput", outputChannel = "messageChannelWriter")
    public GenericTransformer<String, String> myTransformer() {
        return text -> {return text;};
    }

    @Bean
    @ServiceActivator(inputChannel = "messageChannelWriter")
    public FileWritingMessageHandler myFileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("Output"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }

}
