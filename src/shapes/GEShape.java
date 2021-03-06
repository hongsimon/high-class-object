package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Rectangle;
import utils.GEAnchorList;
import constants.GEConstants.EAnchorTypes;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public abstract class GEShape {
	protected Shape myShape;
	protected Point startP;
	protected boolean selected;
	protected GEAnchorList anchorList;
	protected EAnchorTypes selectedAnchor;
	protected Color lineColor, fillColor;
	protected AffineTransform affineTransform;
	
	public GEShape(Shape shape){
		this.myShape = shape;
		anchorList = null;
		selected = false;
		affineTransform = new AffineTransform();
	}
		
	public boolean isSelected() {
		return selected;
	}



	public Rectangle getBounds(){
		return myShape.getBounds();
	}
	
	public EAnchorTypes getSelectedAnchor() {
		return selectedAnchor;
	}


	public GEAnchorList getAnchorList() {
		return anchorList;
	}


	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}


	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}


	public void draw(Graphics2D g2D){
		if(fillColor != null){
			g2D.setColor(fillColor);
			g2D.fill(myShape);
		}
		if(lineColor != null){
			g2D.setColor(lineColor);
			g2D.draw(myShape);
		}
		if(selected == true){
			anchorList.setPosition(myShape.getBounds());
			anchorList.draw(g2D);
		}
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
		if(selected == true){
			anchorList = new GEAnchorList();
			anchorList.setPosition(myShape.getBounds());
		}else{
			anchorList = null;
		}
	}
	
	public boolean onShape(Point p){
		if(anchorList != null){
			selectedAnchor = anchorList.onAnchors(p);
			if(selectedAnchor != EAnchorTypes.NONE){
				return true;
			}
		}
		return myShape.intersects(new Rectangle(p.x, p.y, 2, 2));
	}
	
	public EAnchorTypes onAnchor(Point p){
		selectedAnchor = anchorList.onAnchors(p);
		return selectedAnchor;
	}
	
	public void resizeCoordinate(Point2D resizeFactor){
		affineTransform.setToScale(resizeFactor.getX(), resizeFactor.getY());
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	public void moveReverse(Point2D resizeAnchor){
		affineTransform.setToTranslation(-resizeAnchor.getX(), -resizeAnchor.getY());
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	public void move(Point2D resizeAnchor){
		affineTransform.setToTranslation(resizeAnchor.getX(), resizeAnchor.getY());
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	public void moveCoordinate(Point moveP){
		affineTransform.setToTranslation(moveP.getX(), moveP.getY());
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	abstract public void initDraw(Point startP);
	abstract public void setCoordinate(Point currentP);
	abstract public GEShape clone();
}
