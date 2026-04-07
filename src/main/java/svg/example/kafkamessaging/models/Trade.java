package svg.example.kafkamessaging.models;

public record Trade(
		String tradeID,
		String tradeDate,
		String tradeTime,
		String securitySymbol,
		String ISIN,
		String side,
		Double volume,
		Double price,
		Double value,
		String traderCode,
		String clientID
) {}
