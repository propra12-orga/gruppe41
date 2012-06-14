package bomberman.map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import bomberman.objects.terrain.Block;
import bomberman.objects.terrain.Rock;

/**
 * This class is used to parse an XML file to load a map.
 * 
 */
public class MapParse {
	private int currentTileX = 0, currentTileY = 0;
	private Map map;
	/**
	 * Changes the maps ground array.
	 * @param tiles - A string from the input file.
	 */
	private void fillTiles(String tiles) {
		for (char c : tiles.toCharArray()) {
			if (Character.isDigit(c)) {
				map.ground[currentTileY][currentTileX] = c - '0';

				if (++currentTileX > Map.TILES_COUNT_X - 1) {
					currentTileX = 0;
					currentTileY++;
				}
			}
		}
	}

	/**
	 * Generates indestructable walls along a row.
	 * 
	 * @param row
	 *            - Number of the row.
	 * @param blocks
	 *            - String in the input file, contains a '1' for every wall.
	 */
	private void addBlocks(int row, String blocks) {
		int x = 0;

		for (char c : blocks.toCharArray()) {
			if (Character.isDigit(c)) {
				if (c == '1')
					map.Add(new Block(map, x, row));
				x++;
			}
		}
	}

	/**
	 * This method randomly generates rocks on the whole map. There won't be any
	 * rocks placed onto spawn points or walls.
	 * 
	 * @param probability
	 *            - The chance to generate a rock at any position.
	 */
	private void generateRocks(int probability) {
		double p = (double) probability / 100;

		for (int x = 0; x < 17; x += 1) {
			if (x == 0 || x == 16) {
				for (int y = 2; y < 9; y += 1) {
					if (Math.random() < p)
						map.Add(new Rock(map, x, y));
				}
			} else if (x % 2 == 0) {
				for (int y = 0; y < 11; y += 1) {
					if (Math.random() < p)
						map.Add(new Rock(map, x, y));
				}
			} else if (x % 2 != 0) {
				for (int y = 0; y < 11; y += 2) {
					if (x == 1 || x == 15) {
						if (y < 8) {
							if (Math.random() < p)
								map.Add(new Rock(map, x, y + 2));
						}
					} else if (Math.random() < p)
						map.Add(new Rock(map, x, y));
				}
			}
		}
	}
	/**
	 * Removes rocks at positions that have to contain a wall.
	 * @param row - The number of the row to be changed.
	 * @param rocks - String from the input file.
	 */
	private void removeRocks(int row, String rocks) {
		int x = 0;

		for (char c : rocks.toCharArray()) {
			if (Character.isDigit(c)) {
				if (c == '1') {
					MapObject[] ol = map.getObjectsOnTile(x, row);
					for (MapObject o : ol) {
						map.Remove(o);
					}
				}
				x++;
			}
		}
	}
	/**
	 * Starts a complete map parse, calling up the other map parse methods.
	 * @param map - The map to be written.
	 * @param file - The input file (XML).
	 */
	public MapParse(final Map map, String file) {
		this.map = map;

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxparser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				String current;
				boolean blocks, rocks, rocks_clean, powerups;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					current = qName;

					switch (qName) {
					case "blocks":
						blocks = true;
						break;
					case "rocks":
						rocks = true;
						break;
					case "powerups":
						powerups = true;
						break;
					case "clean":
						if (rocks)
							rocks_clean = true;
						break;
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					current = "";

					switch (qName) {
					case "blocks":
						blocks = false;
						break;
					case "rocks":
						rocks = false;
						break;
					case "powerups":
						powerups = false;
						break;
					case "clean":
						if (rocks)
							rocks_clean = false;
						break;
					}
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
					String value = new String(ch, start, length);

					if (current == "tiles") {
						fillTiles(value);
					} else if (blocks && current.indexOf('_') > -1) {
						addBlocks(
								Integer.parseInt(current.substring(
										current.indexOf('_') + 1,
										current.length())) - 1, value);
					} else if (rocks) {
						if (current == "probability")
							generateRocks(Integer.parseInt(value.substring(0,
									value.length() - 1)));
						else if (rocks_clean && current.indexOf('_') > -1)
							removeRocks(
									Integer.parseInt(current.substring(
											current.indexOf('_') + 1,
											current.length())) - 1, value);
					} else if (powerups) {
						if (current == "droprate")
							map.droprate = (double) Integer.parseInt(value
									.substring(0, value.length() - 1)) / 100;
					}
				}
			};

			saxparser.parse(file, handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
