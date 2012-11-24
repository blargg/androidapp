package com.example.my.first.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

/**
 * This view displays a set of images and allows
 * the user to move the view around with guestures
 */
public class PlayAreaView extends View {
	// Static Debug String (TODO move this to a more global space?)
	private static final String DEBUG_TAG = "TOM:DEBUG";
	
	/**
	 * translate controls the offset of the View
	 */
	private Matrix translate;
	/**
	 * a bitmap of an image (this should get replaced with something better later
	 */
	private Bitmap droid;
	
	/**
	 * holds the gesture event manager
	 */
	private GestureDetector gestures;
	
	
	///////////// Variables for the Animation (swiping and such) /////////////////////
	private Matrix animateStart;
	private Interpolator animateInterpolator;
	private long startTime;
	private long endTime;
	private float totalAnimDx;
	private float totalAnimDy;
	
	public void onAnimateMove(float dx, float dy, long duration){
		animateStart = new Matrix(translate);
		animateInterpolator = new OvershootInterpolator();
		startTime = System.currentTimeMillis();
		endTime = startTime + duration;
		totalAnimDx = dx;
		totalAnimDy = dy;
		post(new Runnable(){
			//@Override
			public void run(){
				onAnimateStep();
			}
		});
	}
	
	private void onAnimateStep(){
		long curTime = System.currentTimeMillis();
		float percentTime = (float) (curTime - startTime) / (float) (endTime - startTime);
		float percentDistance = animateInterpolator.getInterpolation(percentTime);
		float curDx = percentDistance * totalAnimDx;
		float curDy = percentDistance * totalAnimDy;
		translate.set(animateStart);
		onMove(curDx, curDy);
		Log.v(DEBUG_TAG, "We're " + percentDistance + " of the way there!");
		if(percentTime < 1.0f){
			post(new Runnable(){
				//@Override
				public void run(){
					onAnimateStep();
				}
			});
		}
	}
	
	/**
	 * This private class is here because I was following a tutorial on GestureListener.
	 * The private class helps to show how to use the Gesture listener, but normally it
	 * would be easier and faster to have the outter class (PlayAreaView) to implement the
	 * GestureListener
	 * @author tom
	 *
	 */
	private class GestureListener implements GestureDetector.OnGestureListener,
			GestureDetector.OnDoubleTapListener{
		PlayAreaView view;
		
		public GestureListener(PlayAreaView view){
			this.view = view;
		}
		
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			Log.v(DEBUG_TAG, "onScroll");
			view.onMove(-distanceX, -distanceY);
			return true;
		}
		
		public boolean onDoubleTap(MotionEvent e){
			Log.v(DEBUG_TAG, "onDoubleTap");
			view.onResetLocation();
			return true;
		}
		
		public boolean onDown(MotionEvent e){
			Log.v(DEBUG_TAG, "onDown");
			return true;
		}

		public boolean onDoubleTapEvent(MotionEvent e) {
			// Ignore this event for now
			return true;
		}

		public boolean onSingleTapConfirmed(MotionEvent e) {
			// Ignore this
			return true;
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// velocity is in pixels per second
			Log.v(DEBUG_TAG, "onFling");
			final float distanceTimeFactor = 0.4f; // this adjusts the speed (i think) can change to whatever you want
			final float totalDx = (distanceTimeFactor * velocityX/2);
			final float totalDy = (distanceTimeFactor * velocityY/2);
			
			view.onAnimateMove(totalDx, totalDy, (long) (1000 * distanceTimeFactor));
			return true;
		}

		public void onLongPress(MotionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void onShowPress(MotionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public boolean onSingleTapUp(MotionEvent arg0) {
			// TODO Auto-generated method stub
			return false;
		}
	}
	
	public PlayAreaView(Context context)
	{
		super(context);
		translate = new Matrix();
		//gestures = new GestureDetector(PictureScroll.this, new GestureListener(this));
		gestures = new GestureDetector(context, new GestureListener(this));
		droid = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(droid, translate, null);
		//Matrix m = canvas.getMatrix(); // TODO: remove this. It is just for debugging right now
		Log.d(DEBUG_TAG, "Matrix: " + translate.toShortString());
		//Log.d(DEBUG_TAG, "Canvas: " + m.toShortString());

	}
	
	/**
	 * Moves the view by dx and dy
	 * @param dx X distance to move
	 * @param dy Y distance to move
	 */
	public void onMove(float dx, float dy){
		translate.postTranslate(dx,dy);
		invalidate(); // this means request a redraw right?
	}
	
	/**
	 * Resets the location of the image view back to the origin.
	 */
	public void onResetLocation(){
		translate.reset();
		invalidate();
	}
	
	public boolean onTouchEvent(MotionEvent event)
	{
		return gestures.onTouchEvent(event);
	}

}
