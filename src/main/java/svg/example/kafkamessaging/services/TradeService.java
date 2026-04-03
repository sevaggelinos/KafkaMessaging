package svg.example.kafkamessaging.services;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import svg.example.kafkamessaging.model.Trade;

import java.util.List;

@Service
public class TradeService {

	private final KafkaTemplate<String, Trade> kafkaTemplate;
	private final String TOPIC = "svg-trade-requests";
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@PostConstruct
	public void init() {
		System.out.println("DEBUG: Kafka Servers -> " + bootstrapServers);
	}
	public TradeService(KafkaTemplate<String, Trade> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendTrade(Trade trade) {
		// Construct the key: tradeDate-tradeID
		String key = trade.tradeDate() + "-" + trade.tradeID();

		// Send to Kafka with Key and Value
		this.kafkaTemplate.send(TOPIC, key, trade);
	}

	public void sendTrades(List<Trade> trades) {
		for (Trade trade : trades) {
			sendTrade(trade);
		}
	}
}