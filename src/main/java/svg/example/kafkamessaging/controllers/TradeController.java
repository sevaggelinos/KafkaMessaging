package svg.example.kafkamessaging.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import svg.example.kafkamessaging.model.Trade;
import svg.example.kafkamessaging.services.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
@Tag(name = "Kafka Producer", description = "Endpoints for sending trades to Kafka topics")
public class TradeController {

	private final TradeService tradeService;

	public TradeController(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	@Operation(summary = "a list of trades to Kafka", description = "Receives a list of trade objects and produces it to the 'svg-trade-requests' Kafka topic")
	@PostMapping("/sendListOfTrades")
	public String postListOfTrades(@RequestBody List<Trade> trades) {
		tradeService.sendTrades(trades);
		return "Trade sent to Kafka successfully!";
	}

	@Operation(summary = "Send a single trade to Kafka", description = "Receives a trade object and produces it to the 'svg-trade-requests' Kafka topic")
	@PostMapping("/sendTrade")
	public String postMessage(@RequestBody Trade trade) {
		tradeService.sendTrade(trade);
		return "Trade sent to Kafka successfully!";
	}
}