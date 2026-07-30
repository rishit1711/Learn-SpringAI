package com.Spring.Ai.Learning.Learn.SpringAI.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class TravelTool {
    // creating a tool and spring ai will add this tool into tool registry
    @Tool(description = "Get Weather Information about the City")
    public String getWeather(@ToolParam(description = "City name for which to get Information",required = true) String city){
        switch (city){
            case "Delhi":
                return "24 Degree Celcius(Sunny)";
            case "London":
                return "18 Degree Celcius(Windy)";
            case "Mumbai":
                return "21 Degree Celcius(Humid)";
            default:return "Could not Process";
        }

    }
}
