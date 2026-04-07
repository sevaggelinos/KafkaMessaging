package svg.example.kafkamessaging.models;

import tools.jackson.databind.ObjectMapper;

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
) {
	private static final ObjectMapper mapper = new ObjectMapper();
	public String toJson() {
		return mapper.writeValueAsString(this);
	}
}
