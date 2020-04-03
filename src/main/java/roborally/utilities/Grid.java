package roborally.utilities;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Base64Coder;
import org.jetbrains.annotations.NotNull;
import roborally.utilities.enums.LayerName;
import roborally.utilities.enums.TileName;
import roborally.utilities.tiledtranslator.TiledTranslator;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * This class reads through a tmx map encoded with Base64 and compressed with zlib.
 * For each layer in the tmx map found by hitting a line that includes the field
 * "layer id" it decodes and decompresses the input to make a map of the name
 * of the layer with another map as value. The value is a map that consists
 * of GridPoint2 positions and TileNames of the tiles included in the specific layer.
 * <p>
 * To get a perspective of what the class makes, use the method
 * {@link #printGrid} to display all the layers with all the tiles and positions.
 */
public class Grid {
	private Map<LayerName, Map<GridPoint2, TileName>> gridLayers;
	private TiledTranslator tiledTranslator;
	private int width;
	private int height;

	public Grid(String mapTMX) {
		gridLayers = new HashMap<>();
		tiledTranslator = new TiledTranslator();
		String line;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(mapTMX)));
			while (!(line = br.readLine().strip()).equals("</map>"))
				if (line.contains("layer id")) {
					if (height == 0 || width == 0)
						setWidthHeight(line);

					int nameStartIndex = ("name".length() + 2) + line.indexOf("name");
					int nameEndIndex = line.indexOf("width") - 2;
					String layerNameString = (line.substring(nameStartIndex, nameEndIndex));
					br.readLine();
					String gridLayerEncoded = br.readLine().strip();

					//System.out.println("Added layer '" + layerNameString + "' to the grid");

					for (LayerName layerName : LayerName.values()) {
						if (layerName.getLayerString().toLowerCase().equals(layerNameString.toLowerCase())) {
							makeNewGridLayer(layerName, gridLayerEncoded);
						}
					}
				}
			br.close();
		} catch (IOException | DataFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Decompresses zlib compressed data.
	 *
	 * @param data the bytes of the layer read from the tmx map (decoded).
	 * @return Array with the decoded values.
	 */
	@NotNull
	private static byte[] decompress(byte[] data) throws IOException, DataFormatException {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!inflater.finished()) {
			int count = inflater.inflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		outputStream.close();
		return outputStream.toByteArray();
	}

	public Map<GridPoint2, TileName> getGridLayer(LayerName layerName) {
		return gridLayers.get(layerName);
	}

	public TileName getTileNameFromLayerPos(LayerName layerName, GridPoint2 pos) {
		return getGridLayer(layerName).get(pos);
	}

	public void putTileInLayer(LayerName layerName, TileName tileName, GridPoint2 pos) {
		getGridLayer(layerName).put(pos, tileName);
	}

	// Prints all the placed tiles in the tmx map, the tileNames along with the positions.
	public void printGrid() {
		for (LayerName layerName : gridLayers.keySet()) {
			System.out.println("Grid Layer: " + layerName);
			for (GridPoint2 tilePos : gridLayers.get(layerName).keySet())
				System.out.print(gridLayers.get(layerName).get(tilePos) + " -> " + tilePos + " ");
			System.out.println();
			System.out.println();
		}
	}

	/**
	 * Makes a new Layer with a map of GridPoint positions and TileNames.
	 *
	 * @param layerName layerName of the layer that is being made.
	 * @param gridLayer compressed and encoded String of the layer.
	 * @throws IOException         if an I/O error occurs.
	 * @throws DataFormatException if the format is not a byte format.
	 */
	private void makeNewGridLayer(LayerName layerName, String gridLayer) throws IOException, DataFormatException {
		gridLayers.put(layerName, new HashMap<>());
		byte[] bytes = Base64Coder.decode(gridLayer);
		bytes = decompress(bytes);
		int x = 0;
		int y = 0;
		for (int j = 0; j < bytes.length; j += 4) {
			if (bytes[j] != 0) {
				gridLayers.get(layerName).put(new GridPoint2(x, height - y - 1),
						tiledTranslator.getTileName(bytes[j] & 0xFF));
			}
			x++;
			x = x % width;
			if (x == 0) {
				y++;
			}
		}
	}

	/**
	 * Sets the width and the height of the map (all layers share the same size).
	 */
	private void setWidthHeight(@NotNull String string) {
		int widthEndIdx = string.indexOf("width") + "width".length() + 1;
		String width = string.substring(widthEndIdx + 1, widthEndIdx + 3);
		this.width = Integer.parseInt(width);
		int heightEndIdx = string.indexOf("height") + "height".length() + 1;
		String height = string.substring(heightEndIdx + 1, heightEndIdx + 3);
		this.height = Integer.parseInt(height);
	}
}
