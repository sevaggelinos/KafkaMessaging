package svg.example.kafkamessaging.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import svg.example.kafkamessaging.model.Trade;
import svg.example.kafkamessaging.services.TradeService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TradeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TradeService tradeService;

    @InjectMocks
    private TradeController tradeController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeMethod
    public void setUp() {
        // Initialize Mockito annotations (@Mock and @InjectMocks)
        MockitoAnnotations.openMocks(this);

        // Standalone setup: Test only the controller without loading the full Spring Context
        this.mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
    }

    @Test
    public void testPostTrade() throws Exception {
        Trade trade = new Trade("1", "2023-10-27", "10:00", "AAPL", "US0378331005",
                "BUY", 100.0, 150.0, 15000.0, "TRD1", "CLI1");

        // We don't need doNothing() because void is the default behavior for mocks

        mockMvc.perform(post("/api/trades/sendTrade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trade)))
                .andExpect(status().isOk())
                .andExpect(content().string("Trade sent to Kafka successfully!"));

        verify(tradeService, times(1)).sendTrade(any(Trade.class));
    }

    @Test
    public void testPostListOfTrades() throws Exception {
        Trade trade1 = new Trade("1", "2023-10-27", "10:00", "AAPL", "US0378331005", "BUY", 100.0, 150.0, 15000.0, "TRD1", "CLI1");
        List<Trade> trades = List.of(trade1);

        mockMvc.perform(post("/api/trades/sendListOfTrades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trades)))
                .andExpect(status().isOk())
                .andExpect(content().string("Trade sent to Kafka successfully!"));

        verify(tradeService).sendTrades(anyList());
    }
}