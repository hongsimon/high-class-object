package shapes;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.Rectangle;

public class GELine extends GEShape{
	
	public GELine(){
		super(new Line2D.Double());
	}

	public void initDraw(Point startP){
		this.startP = startP;
	}

	public void setCoordinate(Point currentP){
		Line2D tempLine = (Line2D)myShape;
		tempLine.setLine(startP.x, startP.y, currentP.x,  currentP.y);
		if(anchorList != null){
			anchorList.setPosition(myShape.getBounds());
		}
	}
	
	public boolean contains(Point p){
		Line2D tempLine = (Line2D)myShape;
		Rectangle tempRectangle = new Rectangle();
		tempRectangle.setFrameFromDiagonal(tempLine.getP1(), tempLine.getP2());
		return tempRectangle.contains(p);
	}

	public GEShape clone() {
		return new GELine();
	}
}