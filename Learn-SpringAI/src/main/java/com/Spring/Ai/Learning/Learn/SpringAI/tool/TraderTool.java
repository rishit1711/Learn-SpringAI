package com.Spring.Ai.Learning.Learn.SpringAI.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class TraderTool {

    @Tool(description = "Return stock price of the given stock")
    public double getStockPrice(
            @ToolParam(description = "Stock name", required = true)
            String stock) {

        return 190;
    }

    @Tool(description = "Buy stock with given quantity")
    public String buyStock(
            @ToolParam(description = "Stock name", required = true)
            String stock,

            @ToolParam(description = "Quantity of shares", required = true)
            int quantity) {

        return "Bought " + quantity + " shares of " + stock;
    }

}