/*
 *      @(#)MIPmapApp.java 1.2 01/06/18
 *
 * Copyright (c) 1996-2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

/*
 
 */

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 * MIPmapApp creates a single plane with texture mapping.
 */
public class MIPmapApp extends Applet {

    BranchGroup createScene() {
      int imageWidth, imageHeight, imageLevel;
      final int SMALLEST = 1;
      BranchGroup objRoot = new BranchGroup();

      Transform3D transform = new Transform3D();

      QuadArray plane = new QuadArray(4, GeometryArray.COORDINATES
                                        | GeometryArray.TEXTURE_COORDINATE_2);

      Point3f p = new Point3f(-1.0f,  1.0f,  0.0f);
      plane.setCoordinate(0, p);
      p.set(-1.0f, -1.0f,  0.0f);
      plane.setCoordinate(1, p);
      p.set(1.0f, -1.0f,  0.0f);
      plane.setCoordinate(2, p);
      p.set(1.0f,  1.0f,  0.0f);
      plane.setCoordinate(3, p);

      TexCoord2f q = new TexCoord2f( 0.0f,  1.0f);
      plane.setTextureCoordinate( 0, 0, q);
      q.set(0.0f, 0.0f);
      plane.setTextureCoordinate( 0, 1, q);
      q.set(1.0f, 0.0f);
      plane.setTextureCoordinate( 0, 2, q);
      q.set(1.0f, 1.0f);
      plane.setTextureCoordinate( 0, 3, q);

      Appearance appear = new Appearance();

      String filename = "stripe.gif";
      System.out.println("loading image: "+filename);
      TextureLoader loader = new TextureLoader(filename, TextureLoader.GENERATE_MIPMAP, this);
      ImageComponent2D image = loader.getImage();

      if(image == null) {
            System.out.println("load failed for texture: "+filename);
      }

      imageWidth = image.getWidth();
      imageHeight = image.getHeight();

      Texture2D texture = new Texture2D(Texture.MULTI_LEVEL_MIPMAP, Texture.RGBA,
                                        imageWidth, imageHeight);
      imageLevel = 0;

      System.out.println("set image level: "+imageLevel+"  width: "+imageWidth
                        +"  height: "+imageHeight);
      texture.setImage(imageLevel, image);
      while (imageWidth > SMALLEST || imageWidth > SMALLEST){
          imageLevel++;
          if (imageWidth > SMALLEST) imageWidth /= 2;
          if (imageHeight > SMALLEST) imageHeight /= 2;
          System.out.print("get image level: "+imageLevel+"  width: "+imageWidth
                            +"  height: "+imageHeight+" ... ");
          image = loader.getScaledImage(imageWidth, imageHeight);

          System.out.println("set image");
          texture.setImage(imageLevel, image);
      }

      texture.setMagFilter(Texture.BASE_LEVEL_POINT);
      texture.setMinFilter(Texture.MULTI_LEVEL_POINT);

      //texture.setEnable(false);

      appear.setTexture(texture);

      appear.setTransparencyAttributes(
           new TransparencyAttributes(TransparencyAttributes.FASTEST, 0.1f));

      Shape3D planeObj = new Shape3D(plane, appear);
      objRoot.addChild(planeObj);

      Background background = new Background();
      background.setColor(1.0f, 1.0f, 1.0f);
      background.setApplicationBounds(new BoundingSphere());
      objRoot.addChild(background);

      return objRoot;
  }

  public MIPmapApp (){
    setLayout(new BorderLayout());
    GraphicsConfiguration config =
       SimpleUniverse.getPreferredConfiguration();

    Canvas3D canvas3D = new Canvas3D(config);
    add("Center", canvas3D);
    NewTextureLoader.setImageObserver(canvas3D);

    canvas3D.setStereoEnable(false);

    SimpleUniverse u = new SimpleUniverse(canvas3D);

    // This will move the ViewPlatform back a bit so the
    // objects in the scene can be viewed.
    u.getViewingPlatform().setNominalViewingTransform();

    u.addBranchGraph(createScene());
  }
  
  public static void main(String argv[])
  {
    System.out.print("MIPmapApp.java \n- ");
    System.out.println("The simpliest example of using multiple level texture mapping.\n");
    System.out.println("This is a simple example progam from The Java 3D API Tutorial.");
    System.out.println("The Java 3D Tutorial is available on the web at:");
    System.out.println("http://java.sun.com/products/java-media/3D/collateral ");

    new MainFrame(new MIPmapApp(), 256, 256);
  }
}

