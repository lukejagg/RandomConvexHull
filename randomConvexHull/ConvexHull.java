package randomConvexHull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Random;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;
import javafx.beans.property.IntegerProperty;
public class ConvexHull extends Application
{
	int seed = 0;
	void addSeed() {
		seed++;
	}
	@Override
	public void start(Stage stage) 
	{
		int width = 850;
		int height = 850;
		int dotCount = 10;
		Group root = new Group();
	    	Scene s = new Scene(root, width, height, Color.BLACK);
	    	final Canvas canvas = new Canvas(width, height);
	    GraphicsContext gc = canvas.getGraphicsContext2D();
	    	root.getChildren().add(canvas);
	    	stage.initStyle(StageStyle.UNDECORATED);
	    	stage.setTitle("CONVEX");
	    	stage.setScene(s);
	    	stage.setResizable(false);
	    	stage.show();
	    	canvas.setFocusTraversable(true);
	    	canvas.setOnMousePressed(e -> 
	    	{
	    		for (int n = 0; n < 1; n++) {
	    		long start = System.nanoTime();
		    	Random rnd = new Random();
		    	addSeed();
		    	rnd.setSeed(seed);
		    	ArrayList<Point2D> dots = new ArrayList<Point2D>();
		    	for (int i = 0; i < dotCount; i++) 
		    	{
//		    		double r = Math.pow(rnd.nextDouble(),0.5)*width/2;
//		    		double t = rnd.nextDouble()*2*Math.PI;
//		    		double t = rnd.nextDouble()*Math.PI*2;
//		    		dots.add(new Point2D(r*Math.cos(t)+width/2, r*Math.sin(t)+height/2));
		    		
//		    		dots.add(new Point2D(r*Math.cos(i/(dotCount/2.0)*Math.PI)+width/2, r*Math.sin(i/(dotCount/2.0)*Math.PI)+height/2));
		    		
//		    		dots.add(new Point2D(rnd.nextDouble()*width/2+width/4,rnd.nextDouble()*height/2+width/4));
		    		
		    		dots.add(new Point2D(rnd.nextDouble()*width,rnd.nextDouble()*height));
		    		
//		    		dots.add(new Point2D(width/2+400*Math.cos(seed/10.0+i*2*Math.PI/dotCount), width/2+400*Math.sin(seed/10.0+i*2*Math.PI/dotCount)));
		    	}
		    	Set<Point2D> removeDoubles = new HashSet<Point2D>();
		    	removeDoubles.addAll(dots);
		    	dots.clear();
		    	dots.addAll(removeDoubles);
		    	gc.clearRect(0, 0, width, height);
		    	gc.setFill(Color.WHITE);
		    	ArrayList<Point2D> finishPoints = new ArrayList<Point2D>();
		    	double m = 0;
		    	double b = 0;
		    	Point2D left = new Point2D(dots.get(0).getX(),dots.get(0).getY());
		    	Point2D top = left;
		    	Point2D right = left;
		    	Point2D bottom = left;
		    	for (int i = 0; i < dots.size(); i++) 
		    	{
		    		if (dots.get(i).getX() < left.getX()) 
		    		{
		    			left = dots.get(i);
		    		}
		    		if (dots.get(i).getX() > right.getX()) 
		    		{
		    			right = dots.get(i);
		    		}
		    		if (dots.get(i).getY() < bottom.getY()) 
		    		{
		    			bottom = dots.get(i);
		    		}
		    		if (dots.get(i).getY() > top.getY()) 
		    		{
		    			top = dots.get(i);
		    		}
		    	}
		    	
		    double m1 = (left.getY()-top.getY())/(left.getX()-top.getX());
		    double b1 = left.getY();
		    double m2 = (top.getY()-right.getY())/(top.getX()-right.getX());
		    double b2 = top.getY();
		    double m3 = (right.getY()-bottom.getY())/(right.getX()-bottom.getX());
		    double b3 = right.getY();
		    double m4 = (bottom.getY()-left.getY())/(bottom.getX()-left.getX());
		    double b4 = bottom.getY();
		    	
		    	finishPoints.add(left);
		    	for (int i = 0; i < 4; i++) 
		    	{
		    	Point2D r1 = left;
		    	Point2D r2 = top;
		    	if (i == 1) {
		    		r1 = top;
		    		r2 = right;
		    	}
		    	else if (i == 2) {
		    		r1 = right;
		    		r2 = bottom;
		    	}
		    	else if (i == 3) {
		    		r1 = bottom;
		    		r2 = left;
		    	}
		    	m = (r1.getY() - r2.getY())/(r1.getX()-r2.getX());
		    	b = r1.getY();
		    	Point2D pnt = new Point2D(r1.getX(), r1.getY());
		    	for (Point2D p : dots) 
		    	{
		    		gc.fillRoundRect(p.getX()-1, p.getY()-1, 2, 2, 2, 2);
//		    		for (Point2D t : dots)
//		    		{
//		    			if (p==t)
//		    				continue;
//		    			gc.strokeLine(p.getX(), p.getY(), t.getX(), t.getY());
//		    		}
		    	}
		    	while (true)
		    	{
		    		int amount = 0;
		    		double angle = -1000;
		    		Point2D savePoint = pnt;
			    	for (Point2D p : dots) 
			    	{
			    		if (p==r1)
			    			continue;
			    		if (p==r2)
			    			continue;
			    		if (p==pnt)
			    			continue;
			    		if (i < 2) {
			    			if (p.getY() < m*(p.getX()-pnt.getX()) + b)
				    			continue;
			    			if (p.getX() < pnt.getX())
			    				continue;
			    			if (i==0 && p.getY() < pnt.getY())
			    				continue;
			    			else if (i==1 && p.getY() > pnt.getY())
			    				continue;
			    		}
			    		else {
			    			if (p.getY() > m*(p.getX()-pnt.getX()) + b)
				    			continue;
			    			if (p.getX() > pnt.getX())
			    				continue;
			    			if (i==2 && p.getY() > pnt.getY())
			    				continue;
			    			else if (i==3 && p.getY() < pnt.getY())
			    				continue;
			    		}
			    		double a = Math.acos((p.getX()-pnt.getX())/Math.hypot(p.getX()-pnt.getX(), p.getY()-pnt.getY()));
			    		amount++;
			    		switch(i) 
			    		{
			    		case 0:
				    		if (a > angle) {
				    			angle = a;
				    			savePoint = p;
				    		}
				    		break;
			    		case 1:
			    			if (-a > angle) {
				    			angle = -a;
				    			savePoint = p;
				    		}
			    			break;
			    		case 2:
				    		if (-a > angle) {
				    			angle = -a;
				    			savePoint = p;
				    		}
				    		break;
			    		case 3:
			    			if (a > angle) {
				    			angle = a;
				    			savePoint = p;
				    		}
			    			break;
			    		}
			    	}
			    	if (amount == 0)
			    		break;
			    	pnt = savePoint;
			    	if (pnt==r2)
			    		break;
			    	if (angle > -180) {
			    		m = (pnt.getY() - r2.getY())/(pnt.getX()-r2.getX());
			    		b = pnt.getY();
			    		finishPoints.add(pnt);
			    	}
			    	else
			    		break;
		    	}
		    	finishPoints.add(r2);
		    	}
		    	gc.setStroke(Color.LIME);
		    	System.out.println((System.nanoTime()-start) / 1000000000.0 + " seconds to analyze.");
		    	for (int i = 0; i < finishPoints.size(); i++) 
		    	{
		    		if (i < finishPoints.size()-1)
		    		{
		    			if (finishPoints.get(i) != finishPoints.get(i+1))
		    				gc.strokeLine(finishPoints.get(i).getX(), finishPoints.get(i).getY(), finishPoints.get(i+1).getX(), finishPoints.get(i+1).getY());
		    		}
		    		else {
		    			gc.strokeLine(finishPoints.get(i).getX(), finishPoints.get(i).getY(), finishPoints.get(0).getX(), finishPoints.get(0).getY());
		    		}
		    	}
//		    	System.out.println((System.nanoTime()-start) / 1000000000.0 + " seconds to analyze and render.");
	    		}
	    	});
	}
	public static void main(String[] args) 
	{
		launch(args);
	}	
}