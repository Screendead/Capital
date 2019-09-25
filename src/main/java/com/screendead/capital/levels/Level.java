package com.screendead.capital.levels;

import com.screendead.capital.graphics.Mesh;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.screendead.capital.Main.load;

public class Level {
    private String name;
    private Brick[] tiles;
    private Mesh mesh;

    private int width, height;

    public Level(String name, Brick[] tiles, int width, int height) {
        this.name = name;
        this.tiles = tiles;
        this.width = width;
        this.height = height;

        mesh = Mesh.generate(this.tiles);
    }

    public static Level generate(String name) {
        JSONArray levels = new JSONObject(readFile("data/level.json")).getJSONArray("levels");
        JSONObject level = null;
        for (int i = 0; i < levels.length(); i++) {
            if (levels.getJSONObject(i).getString("name").equals(name)) level = levels.getJSONObject(i);
        }
        if (level == null) throw new RuntimeException(String.format("No level with the name \"%s\" found.", name));

        JSONArray tiles_json = level.getJSONArray("layout");
        JSONArray[] rows_json = new JSONArray[tiles_json.length()];
        for (int i = 0; i < tiles_json.length(); i++) {
            rows_json[i] = tiles_json.getJSONArray(i);
        }

        int width = rows_json[0].length(),
                height = rows_json.length;

        Brick[] tiles = new Brick[rows_json.length * width];
        for (int i = 0; i < rows_json.length; i++) {
            for (int j = 0; j < rows_json[i].length(); j++) {
                tiles[i * width + j] = Brick.generate(Bricks.values()[rows_json[i].getInt(j)], j - (float) width / 2.0f, i - (float) height / 2.0f);
            }
        }

        return new Level(name, tiles, width, height);
    }

    public void render() {
        this.mesh.render();
    }

    /**
     * Parse a level string from a file
     * @param filename The name of the file (without a path)
     * @return A parsed string representing the JSON in the file
     */
    private static String readFile(String filename) {
        StringBuilder source = new StringBuilder();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new InputStreamReader(load(filename).openStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                source.append(line);
                source.append("\n");
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getClass() + ": " + e.getMessage());
        }

        return source.toString();
    }
}
