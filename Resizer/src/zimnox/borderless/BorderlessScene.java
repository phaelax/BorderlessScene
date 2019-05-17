package zimnox.borderless;


import javafx.beans.value.ChangeListener;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.stage.StageStyle;







/**
 * Creates an undecorated scene with resize controls and drop shadow for JavaFX applications.
 * 
 * Based on the code from https://github.com/goxr3plus/FX-BorderlessScene
 * 
 * @author Phaelax
 * @version 2019.5.16
 *
 */
public class BorderlessScene extends Scene {
	
	// Amount from the edge that can trigger a resize
	private double borderSize = 8.0;
	
	private Vector2D oldPos = new Vector2D();
	private Vector2D oldSize = new Vector2D();
	private Vector2D mouseOffset = new Vector2D();
	
	// The node that will cast the shadow
	private Pane shadowPane = new Pane();
	
	// The root of the scene is added to this to keep it within the bounds of the shadow
	private AnchorPane contentAnchor = new AnchorPane();
		
	// Default drop shadow
	private DropShadow shadow = new DropShadow();
	
	
	// Resize controls
	private Pane paneTopLeft = new Pane();
	private Pane paneTop = new Pane();
	private Pane paneTopRight = new Pane();
	private Pane paneLeft = new Pane();
	private Pane paneRight = new Pane();
	private Pane paneBottomLeft = new Pane();
	private Pane paneBottomRight = new Pane();
	private Pane paneBottom = new Pane();
	
	private Stage stage = null;

	
	/**
	 * 
	 * @param stage
	 * @param root
	 */
	public BorderlessScene(Stage stage, Parent root) {
		super(new Pane());
		this.stage = stage;
		this.setRoot(init());
		this.setContent(root);
		
		stage.initStyle(StageStyle.TRANSPARENT);
		
		ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->{
			recalculateShadowClip();
		};
		
		stage.widthProperty().addListener(stageSizeListener);
		stage.heightProperty().addListener(stageSizeListener);
	}
	
	
	
	/**
	 * 
	 * @param stage
	 * @param root
	 * @param width
	 * @param height
	 */
	public BorderlessScene(Stage stage, Parent root, double width, double height) {
		super(new Pane(), width, height);
		this.stage = stage;
		this.setRoot(init());
		this.setContent(root);
		
		//stage.setMinWidth(width);
		//stage.setMinHeight(height);
		stage.initStyle(StageStyle.TRANSPARENT);
		
		ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->{
			recalculateShadowClip();
		};
		
		stage.widthProperty().addListener(stageSizeListener);
		stage.heightProperty().addListener(stageSizeListener);
		
		
	}
	

	

	
	/**
	 * Clips the node with the drop shadow. It allows the shadow to be seen while
	 * hiding the main body of the node. This is only noticeable when the content
	 * has a transparent background and prevents the shadow from blending through.
	 */
	private void recalculateShadowClip() {
		double w = stage.getWidth() - (shadow.getWidth()*2);
		double h = stage.getHeight() - shadow.getHeight()*2;
		
		Rectangle outer = new Rectangle(0, 0, stage.getWidth(), stage.getHeight());
		Rectangle inner = new Rectangle(shadow.getWidth(), shadow.getHeight(), w, h);
		Shape shadowArea = Shape.subtract(outer, inner);
		shadowPane.setClip(shadowArea);
	}
	
	

	/**
	 * 
	 * @return
	 */
	private StackPane init() {
		AnchorPane resizeAnchor = new AnchorPane();
		resizeAnchor.setId("resize-anchor");
		// Resize nodes are the top-most layer in a stack pane.
		// Must call this to allow mouse events on the underlying layers
		resizeAnchor.setPickOnBounds(false);


		// top-left
		paneTopLeft.setCursor(Cursor.NW_RESIZE);
		paneTopLeft.setPrefSize(borderSize, borderSize);
		
		// top
		paneTop.setCursor(Cursor.N_RESIZE);
		paneTop.setPrefHeight(borderSize);
		
		// top-right
		paneTopRight.setCursor(Cursor.NE_RESIZE);
		paneTopRight.setPrefSize(borderSize, borderSize);
		
		// left
		paneLeft.setCursor(Cursor.W_RESIZE);
		paneLeft.setPrefWidth(borderSize);
		
		// right
		paneRight.setCursor(Cursor.E_RESIZE);
		paneRight.setPrefWidth(borderSize);
		
		// bottom-left
		paneBottomLeft.setCursor(Cursor.SW_RESIZE);
		paneBottomLeft.setPrefSize(borderSize, borderSize);
		
		// bottom
		paneBottom.setCursor(Cursor.S_RESIZE);
		paneBottom.setPrefHeight(borderSize);
		
		// bottom-right
		paneBottomRight.setCursor(Cursor.SE_RESIZE);
		paneBottomRight.setPrefSize(borderSize, borderSize);
		
		/*
		paneRight.setStyle("-fx-background-color:rgba(255,0,0,0.5);");
		paneLeft.setStyle("-fx-background-color:rgba(255,0,0,0.5);");
		paneTopRight.setStyle("-fx-background-color:rgba(255,0,0,0.5);");
		paneTopLeft.setStyle("-fx-background-color:rgba(255,0,0,0.5);");
		paneBottomRight.setStyle("-fx-background-color:rgba(255,0,0,0.5);");
		paneBottomLeft.setStyle("-fx-background-color:rgba(255,0,0,0.5);");
		paneTop.setStyle("-fx-background-color:rgba(255,0,0,0.5);");
		paneBottom.setStyle("-fx-background-color:rgba(255,0,0,0.5);");
		*/
		
		// Assign resize listeners
		setResizeControl(paneTop, "top");
		setResizeControl(paneRight, "right");
		setResizeControl(paneBottom, "bottom");
		setResizeControl(paneLeft, "left");
		setResizeControl(paneTopRight, "top-right");
		setResizeControl(paneTopLeft, "top-left");
		setResizeControl(paneBottomRight, "bottom-right");
		setResizeControl(paneBottomLeft, "bottom-left");
		
		// Add all the resize nodes to the anchor layer
		resizeAnchor.getChildren().addAll(paneTopLeft, paneTop, paneTopRight, paneLeft, paneRight, paneBottomLeft, paneBottom, paneBottomRight);
		
		
		
		// Creates a layered window:  the shadow is the bottom layer, then the content, and resizing nodes on top		
		StackPane root = new StackPane();
		root.setStyle("-fx-background-color:rgba(0,0,0,0.0);");
		root.getChildren().add(0, shadowPane);
		root.getChildren().add(1, contentAnchor);
		root.getChildren().add(2, resizeAnchor);
		
		
		// Apply default drop shadow
		setShadow(shadow);
		
		// Hide the scene's background 
		this.setFill(Color.TRANSPARENT);
		
		// The stackpane is the real content set for this scene
		return root;
	}
	

	
	
	/**
	 * 
	 * @param shadow
	 */
	public void setShadow(DropShadow shadow) {
		this.shadow = shadow;
		
		shadowPane.setEffect(shadow);
		
		// Since this pane has no children, must set a background color for it to be visible. 
		// Must set insets to see the drop shadow
		shadowPane.setStyle("-fx-background-color:white;-fx-background-insets:"+shadow.getHeight()+" "+shadow.getWidth()+" "+shadow.getHeight()+" "+shadow.getWidth()+";");
		

		// Update assigned content node to fit within the shadow
		if (contentAnchor.getChildren().size() > 0) {
			Node node = contentAnchor.getChildren().get(0);
			AnchorPane.setTopAnchor(node, shadow.getHeight());
			AnchorPane.setRightAnchor(node, shadow.getWidth());
			AnchorPane.setBottomAnchor(node, shadow.getHeight());
			AnchorPane.setLeftAnchor(node, shadow.getWidth());
		}
		
		
		// Whenever the content's anchor points change, the resize nodes must be
		// recalculated to match
		double size = borderSize/2;
		double width = shadow.getWidth()-size;
		double height = shadow.getHeight()-size;
		

		// top-left
		AnchorPane.setTopAnchor(paneTopLeft, height);
		AnchorPane.setLeftAnchor(paneTopLeft, width); 
		
		// top
		AnchorPane.setTopAnchor(paneTop, height);
		AnchorPane.setLeftAnchor(paneTop, width+borderSize);
		AnchorPane.setRightAnchor(paneTop, width+borderSize);
		
		// top-right
		AnchorPane.setTopAnchor(paneTopRight, height);
		AnchorPane.setRightAnchor(paneTopRight, width);
		
		// left
		AnchorPane.setTopAnchor(paneLeft, height+borderSize);
		AnchorPane.setBottomAnchor(paneLeft, height+borderSize);
		AnchorPane.setLeftAnchor(paneLeft, width);
		
		
		// right
		AnchorPane.setTopAnchor(paneRight, height+borderSize);
		AnchorPane.setBottomAnchor(paneRight, height+borderSize);
		AnchorPane.setRightAnchor(paneRight, width);
		
		// bottom-left
		AnchorPane.setBottomAnchor(paneBottomLeft, height);
		AnchorPane.setLeftAnchor(paneBottomLeft, width);
		
		// bottom
		AnchorPane.setBottomAnchor(paneBottom, height);
		AnchorPane.setLeftAnchor(paneBottom, width+borderSize);
		AnchorPane.setRightAnchor(paneBottom, width+borderSize);
		
		// bottom-right
		AnchorPane.setBottomAnchor(paneBottomRight, height);
		AnchorPane.setRightAnchor(paneBottomRight, width);
		
		
		recalculateShadowClip();
	}
	
	
	
	
	
	/**
	 * Set's the visible node for this scene
	 * @param node
	 */
	public void setContent(Node node) {
		
		// If a node was already assigned as the content, remove it
		if (contentAnchor.getChildren().size() > 0) {
			contentAnchor.getChildren().remove(0);
		}
		
		contentAnchor.getChildren().add(0, node);
		
		// Update assigned content node to fit within the shadow
		AnchorPane.setTopAnchor(node, shadow.getHeight());
		AnchorPane.setRightAnchor(node, shadow.getWidth());
		AnchorPane.setBottomAnchor(node, shadow.getHeight());
		AnchorPane.setLeftAnchor(node, shadow.getWidth());
	}
	
	


	/**
	 * Applies listeners to the node for moving the window around the desktop.
	 * In other words, the node to act as the window's title bar
	 * @param node
	 */
	public void setDragControl(Node node) {
		node.setOnMousePressed(e -> {
			if (e.isPrimaryButtonDown()) {
				mouseOffset.x  = e.getScreenX() - stage.getX();
				mouseOffset.y  = e.getScreenY() - stage.getY();
			}
		});
		
		node.setOnMouseDragged(e -> {
			if (e.isPrimaryButtonDown()) {
				stage.setX(e.getScreenX() - mouseOffset.x);
				stage.setY(e.getScreenY() - mouseOffset.y);
			}
		});
	}
	
	
	
	/**
	 * 
	 * @param pane
	 * @param direction
	 */
	private void setResizeControl(Pane pane, String direction) {


		
		pane.setOnMouseDragged(e -> {
			if (e.isPrimaryButtonDown()) {
				
				if (direction.endsWith("left")) {
					double width = stage.getWidth() - e.getScreenX() + stage.getX() + shadow.getWidth();
					if (width >= stage.getMinWidth()) {
						stage.setWidth(width);
						stage.setX(e.getScreenX()-shadow.getWidth());
					}
				}
				
				if (direction.endsWith("right")) {
					double width = e.getScreenX() - oldPos.x + shadow.getWidth();
					if (width >= stage.getMinWidth()) {
						stage.setWidth(width);
					}
				}
				
				
				if (direction.startsWith("bottom")) {
					double height = e.getScreenY() - oldPos.y + shadow.getHeight();
					if (height >= stage.getMinHeight()) {
						stage.setHeight(height);
					}
				}
				
				if (direction.startsWith("top")) {
					double height = stage.getHeight() - e.getScreenY() + stage.getY() + shadow.getHeight();
					if (height >= stage.getMinHeight()) {
						stage.setHeight(height);
						stage.setY(e.getScreenY()-shadow.getHeight());
					}
				}
				
			}
		});
		
		
		pane.setOnMousePressed(e -> {
			if (e.isPrimaryButtonDown()) {
				oldPos.x  = stage.getX();
				oldPos.y  = stage.getY();
				oldSize.x = stage.getWidth();
				oldSize.y = stage.getHeight();
			}
		});
		
	}
	

	
	/**
	 * Simple inner class, not needed for anything outside of BorderlessScene
	 * @author Phaelax
	 *
	 */
	protected class Vector2D {
		double x;
		double y;
	}

	

}
