import java.util.ArrayList;
import java.util.List;

public class BinaryTree extends Maze {
	
	private boolean _animate = false;
	private float _delay = 0.0f;
	
	//
	// Standard Constructors
	//
	public BinaryTree() {
		super();
		initialize();
	}
	public BinaryTree(int w,int h) {
		super(w,h);
		initialize();
	}
	public BinaryTree(int w,int h,long seed) {
		super(w,h,seed);
		initialize(); 
	}
	
	// 
	// Animating Constructor(s)
	//
	public BinaryTree(int w,int h,boolean animate,float delay) {
		super(w,h);
		_animate = animate;
		_delay = delay;
		initialize();
	}
	
	public BinaryTree(int w,int h,boolean animate,float delay,long seed) {
		super(w,h,seed);
		_animate = animate;
		_delay = delay;
		initialize();
	}
	
	private void initialize() {
		if ( !_animate ) {
			carvePassages();
		}
	}
	
	/***
	 * Walk down the maze, cell-by-cell, carving a maze using the binary tree algorithm.
	 * 
	 * Because we walk down the maze, cell-by-cell, in a linear fashion, this 
	 * algorithm is amenable to animation. Animated version is implemented in the 
	 * overridden draw() method below. 
	 */
	private void carvePassages() {
		for ( int y=0; y < _h; ++y ) {
			for ( int x=0; x < _w; ++x ) {
				if ( _animate ) {
					// draw (using animations)
					draw(true);
					
					// try to sleep the thread for _delay seconds
					try {
						Thread.sleep((long)(_delay*1000));
					} catch ( InterruptedException ex) { 
						//pass by exception 
					}
				}
					
				// update the list of directories 
				List<Integer> dirs = new ArrayList<Integer>();
				if ( y > 0 ) dirs.add(new Integer(Maze.N));
				if ( x > 0 ) dirs.add(new Integer(Maze.W));
				
				// recurse, if necessary 
				if ( dirs.size() > 0 ) { 
					int dir = ((Integer)dirs.get(_random.nextInt(dirs.size()))).intValue();
					if ( dir != 0 ) {
						int dx = x + Maze.DX(dir);
						int dy = y + Maze.DY(dir);
						_grid[y][x] |= dir;
						_grid[dy][dx] |= Maze.OPPOSITE(dir);
					}
				}
			}
		}
		
		// make one final call to "update" to display the last cell
		if ( _animate ) {
			draw(true);
		}
	}
	
	/***
	 * Method only needs to be overridden if we are animating.
	 * 
	 * If we are drawing the maze statically, defer to superclass. 
	 * 
	 * @param update
	 */
	public void draw(boolean update) {
		if ( update || !_animate ) {
			System.out.println((char)27+"[H");
			if ( !_animate ) {
				System.out.println((char)27+"[2J");
			}
			super.draw();
		} else {
			System.out.println((char)27+"[2J");
			carvePassages();
		}
	}
	
	// Need to stub out this method, otherwise it won't get called
	// (i.e., java does not support "default method arguments" like 
	// some other OO scripting languages do). 
	public void draw() {
		draw(false);
	}
}