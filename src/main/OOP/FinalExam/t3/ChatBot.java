package OOP.FinalExam.t3;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.type.TypeReference;

public class ChatBot {
    private static final String CONFIG_FILE = "config.properties";
    private String serverUrl;
    private String botName;
    private Scanner scanner;
    private ObjectMapper objectMapper;
    
    public ChatBot() {
        scanner = new Scanner(System.in);
        objectMapper = new ObjectMapper();
        loadConfiguration();
    }
    
    private void loadConfiguration() {
        Properties props = new Properties();
        try {
            if (Files.exists(Paths.get(CONFIG_FILE))) {
                props.load(new FileInputStream(CONFIG_FILE));
            } else {
                // Create default config file
                props.setProperty("server.url", "max.ge/final/t3/11373849/index.php");
                props.setProperty("bot.name", "ChatBot");
                props.store(new FileOutputStream(CONFIG_FILE), "Chat Bot Configuration");
            }
            
            serverUrl = props.getProperty("server.url", "max.ge/final/t3/11373849/index.php");
            botName = props.getProperty("bot.name", "ChatBot");
            
            // Ensure URL has proper protocol
            if (!serverUrl.startsWith("http://") && !serverUrl.startsWith("https://")) {
                serverUrl = "https://" + serverUrl;
            }
            
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
            // Use default values
            serverUrl = "https://max.ge/final/t3/11373849/index.php";
            botName = "ChatBot";
        }
    }
    
    public void start() {
        System.out.println("=== " + botName + " Started ===");
        System.out.println("Server URL: " + serverUrl);
        System.out.println("Type 'help' for available commands or 'exit' to quit.\n");
        
        while (true) {
            System.out.print(botName + "> ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            
            processCommand(input);
        }
    }
    
    private void processCommand(String command) {
        try {
            switch (command.toLowerCase()) {
                case "help":
                    showHelp();
                    break;
                case "create":
                    createBlogPost();
                    break;
                case "view":
                    viewAllBlogPosts();
                    break;
                case "stats":
                    viewStatistics();
                    break;
                case "config":
                    showConfiguration();
                    break;
                default:
                    System.out.println("Unknown command. Type 'help' for available commands.");
            }
        } catch (Exception e) {
            System.err.println("Error executing command: " + e.getMessage());
        }
    }
    
    private void showHelp() {
        System.out.println("\n=== Available Commands ===");
        System.out.println("help     - Show this help message");
        System.out.println("create   - Create a new blog post");
        System.out.println("view     - View all blog posts");
        System.out.println("stats    - View general statistics");
        System.out.println("config   - Show current configuration");
        System.out.println("exit     - Exit the chat bot");
        System.out.println();
    }
    
    private void showConfiguration() {
        System.out.println("\n=== Current Configuration ===");
        System.out.println("Bot Name: " + botName);
        System.out.println("Server URL: " + serverUrl);
        System.out.println("Config File: " + CONFIG_FILE);
        System.out.println();
    }
    
    private void createBlogPost() {
        System.out.println("\n=== Create New Blog Post ===");
        System.out.print("Enter post title: ");
        String title = scanner.nextLine().trim();
        
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty!");
            return;
        }
        
        System.out.print("Enter post content: ");
        String content = scanner.nextLine().trim();
        
        if (content.isEmpty()) {
            System.out.println("Content cannot be empty!");
            return;
        }
        
        try {
            String jsonPayload = String.format(
                "{\"action\":\"create\",\"title\":\"%s\",\"content\":\"%s\"}", 
                escapeJson(title), escapeJson(content)
            );
            
            String response = sendPostRequest(jsonPayload);
            System.out.println("Server Response: " + response);
            
        } catch (Exception e) {
            System.err.println("Error creating blog post: " + e.getMessage());
        }
    }
    
    private void viewAllBlogPosts() {
        System.out.println("\n=== All Blog Posts ===");
        
        try {
            String jsonPayload = "{\"action\":\"view_all\"}";
            String response = sendPostRequest(jsonPayload);
            
            JsonNode jsonResponse = objectMapper.readTree(response);
            
            if (jsonResponse.has("posts") && jsonResponse.get("posts").isArray()) {
                JsonNode posts = jsonResponse.get("posts");
                
                if (posts.size() == 0) {
                    System.out.println("No blog posts found.");
                } else {
                    for (int i = 0; i < posts.size(); i++) {
                        JsonNode post = posts.get(i);
                        System.out.println("Post " + (i + 1) + ":");
                        System.out.println("  Title: " + post.get("title").asText());
                        System.out.println("  Content: " + post.get("content").asText());
                        if (post.has("date")) {
                            System.out.println("  Date: " + post.get("date").asText());
                        }
                        System.out.println();
                    }
                }
            } else {
                System.out.println("Response: " + response);
            }
            
        } catch (Exception e) {
            System.err.println("Error viewing blog posts: " + e.getMessage());
        }
    }
    
    private void viewStatistics() {
        System.out.println("\n=== Site Statistics ===");
        
        try {
            String jsonPayload = "{\"action\":\"stats\"}";
            String response = sendPostRequest(jsonPayload);
            
            JsonNode jsonResponse = objectMapper.readTree(response);
            
            if (jsonResponse.has("statistics")) {
                JsonNode stats = jsonResponse.get("statistics");
                System.out.println("Total Posts: " + stats.get("total_posts").asInt());
                System.out.println("Total Views: " + stats.get("total_views").asInt());
                System.out.println("Last Updated: " + stats.get("last_updated").asText());
            } else {
                System.out.println("Response: " + response);
            }
            
        } catch (Exception e) {
            System.err.println("Error viewing statistics: " + e.getMessage());
        }
    }
    
    private String sendPostRequest(String jsonPayload) throws IOException {
        URL url = new URL(serverUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", botName + "/1.0");
        connection.setDoOutput(true);
        
        // Send JSON payload
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        
        // Read response
        StringBuilder response = new StringBuilder();
        int responseCode = connection.getResponseCode();
        
        InputStream inputStream;
        if (responseCode >= 200 && responseCode < 300) {
            inputStream = connection.getInputStream();
        } else {
            inputStream = connection.getErrorStream();
        }
        
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
        }
        
        if (responseCode >= 400) {
            throw new IOException("HTTP Error " + responseCode + ": " + response.toString());
        }
        
        return response.toString();
    }
    
    private String escapeJson(String input) {
        return input.replace("\"", "\\\"")
                   .replace("\\", "\\\\")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
    
    public static void main(String[] args) {
        ChatBot bot = new ChatBot();
        bot.start();
    }
}
