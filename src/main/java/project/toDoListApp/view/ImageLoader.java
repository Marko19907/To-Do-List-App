package project.toDoListApp.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class ImageLoader represents an image loader
 * It is responsible for loading, storing and returning images from the disk
 */
public class ImageLoader {
  private final HashMap<String, ImageView> images;
  private final Logger logger;

  public ImageLoader() {
    this.logger = Logger.getLogger(this.getClass().toString());
    this.images = new HashMap<>();
  }

  /**
   * Returns an ImageView of the given image
   *
   * @param imageName The image name corresponding to the image to return,
   *                  can not be blank or null
   * @return ImageView of the given image name or,
   * null if the image can not be found or loaded or,
   * null if the given String is blank or null
   */
  public ImageView getImage(String imageName) {
    ImageView toReturn = null;
    if (imageName != null) {
      if (!imageName.isBlank()) {
        if (!this.images.containsKey(imageName)) {
          this.loadImage(imageName);
        }
        toReturn = this.images.get(imageName);
      }
    }
    return toReturn;
  }

  /**
   * Loads an image with the given name into memory
   *
   * @param imageName The image name corresponding to the image to load,
   *                  can not be blank or null
   */
  private void loadImage(String imageName) {
    if (imageName != null) {
      if (!imageName.isBlank()) {
        String imageLocation = imageName + ".png";

        try {
          Image image = new Image(imageLocation);
          ImageView imageView = new ImageView(image);
          imageView.setPreserveRatio(true);

          this.images.put(imageName, imageView);
        } catch (IllegalArgumentException e) {
          String message = "Image " + imageLocation + " could not be loaded!";
          this.logger.log(Level.INFO, message);
        }
      }
    }
  }
}