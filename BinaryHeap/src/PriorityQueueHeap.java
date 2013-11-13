/**
 * File: PriorityQueueHeap.java
 * Description: A binary heap implementation of a Priority Queue
 * @author Anthony Turner
 *
 */
public class PriorityQueueHeap<E> {

	//Member variables
	private Object[] data;
	private Integer[]priority;
	private int current_size;

	/**
	 * Initialize a PriorityQueue with the given size
	 * Precondition: size is >= 0
	 * Postcondition: This priority queue is empty
	 * @param size the current size of this queue
	 * @throws IllegalArgumentException if the size is negative
	 */
	public PriorityQueueHeap(int size){

		if (size < 0 )
			throw new IllegalArgumentException("Invalid size. Must be a positive value");

		data = new Object[size];
		priority = new Integer[size];
		current_size = 0;

	}



	/**
	 * Adds a new item with the given priority to the Priority Queue
	 * @param item The data of the element to add
	 * @param selected_priority the element's priority
	 * @throws IllegalArgumentException if the priority is negative or is > the highest priority
	 */
	public void add(E item, int selected_priority){

		if( selected_priority < 0)
			throw new IllegalArgumentException("Illegal priority");


		if( current_size >= data.length){
			increaseArray();
		}

		int current_index = current_size;

		data[current_index] = item;
		priority[current_index] = selected_priority;
		current_size++;
		reheapUp(current_index);

	}

	private void increaseArray() {
		
		//Double the current array size
		Object newDataArr[] = new Object[data.length*2];
		Integer newPriorityArr[] = new Integer[data.length*2];
		
		for(int i = 0; i < data.length; i++){
			
			newDataArr[i] = data[i];
			newPriorityArr[i] = priority[i];
		}
		
		data = newDataArr;
		priority = newPriorityArr;

	}


	/*
	 * Swaps each child with its parent if the child's priority is > parent
	 */
	private void reheapUp(int index){

		if( index <= 0)
			return;

		int parent_of_index = getParentIndex(index);

		while( priority[index] > priority[parent_of_index] ){

			swap(index, parent_of_index );

			index = parent_of_index;
			parent_of_index = getParentIndex(index);

		}
	}
	
	
	
	/**
	 * Removes the highest priority item from this queue
	 */
	public Object remove(){

		int index = 0;
		Object removed_data = data[index];
		
		int last_index = current_size -1;
		//Swap the last element putting it at the root
		data[index] = data[last_index];
		priority[index] = priority[last_index];
		
		data[last_index] = null;
		priority[last_index] = null;
		current_size--;
		
		printArray();
		printHeap();
		reheapDownwards(index);

		return removed_data;
		

	}
	

	private void reheapDownwards(int index){

		while( true  ){

			int left_child_index = getLeftChildIndex(index);
			int right_child_index = getRightChildIndex(index);
			int greater_child_index = 0;
			
			if( right_child_index < current_size && priority[right_child_index] > priority[left_child_index] )
				greater_child_index = right_child_index;

			else if( left_child_index < current_size){
				
				greater_child_index = left_child_index;
			}else{
				
				return;
			}
			
			swap(index, greater_child_index );
		//	printArray();
			index = greater_child_index;
			printHeap();


		}
	}


	//Swaps two nodes
	private void swap(int index, int parent_index) {

		//Swap the child and parent's location
		Object child_node = data[index];//Get the child
		int node_priority = priority[index];

		data[index] = data[parent_index];//place the parent where the child was
		priority[index] = priority[parent_index];

		data[parent_index] = child_node;//place the child where the parent was
		priority[parent_index] = node_priority;

	}

	public Object getParent(int index){

		if( index <= 0 || index >= data.length )
			return null;

		return data[getParentIndex(index)];

	}

	public int getParentIndex(int index){

		return (index -1) /2;
	}

	public Object getLeftChild(int index){

		int left_child = getLeftChildIndex(index);
		if( left_child >= current_size)
			return null;

		return data[left_child];

	}

	public int getLeftChildIndex(int index){

		return 2*index + 1;
	}


	public Object getRightChild(int index){

		int right_child = getRightChildIndex(index);
		if( right_child >= current_size)
			return null;

		return data[right_child];

	}

	public int getRightChildIndex(int index){

		return 2*index + 2;
	}


	public void showHeap(){

		printArray();
		printHeap();

	}

	private void printArray(){

		//Array
		System.out.print("Heap array: ");

		for(int i=0; i < current_size; i++)
			if(priority[i] != null)
				System.out.print( priority[i] + " ");
			else
				System.out.print( "-- ");

		System.out.println();

	}

	public void printHeap(){

		// heap format
		int nBlanks = 32;
		int num_nodes = 1;
		int column = 0;
		int node_index = 0; 
		String dots = "=============================================";
		System.out.println(dots+dots);  

		while(node_index < current_size){

			if(column == 0)                  // first item in row?
				for(int k=0; k<nBlanks; k++)  // preceding blanks
					System.out.print(' ');
			
			// display item
			System.out.print(priority[node_index++]);

			if(node_index == current_size)
				continue;

			column++;
			if(column==num_nodes){        // end of level?
				
				nBlanks /= 2;                 // half the blanks
				num_nodes *= 2;             // twice the items
				column = 0;                   // start over on
				System.out.println();         //    new row
				System.out.println();         //    new row

			}
			else                             // next item on row
				for(int k=0; k<nBlanks*2-2; k++)
					System.out.print(' ');     // interim blanks
		}  // end for
		System.out.println("\n"+dots+dots); // dotted bottom line
	}


	private boolean hasRightChild(int right_child_index) {

		//ensure the index is within range
		if( right_child_index >= priority.length)
			return false;

		//Try retrieving the value at that index
		Integer value = priority[right_child_index];
		if ( value == null)
			return false;

		return true;
	}


	private boolean hasLeftChild(int left_child_index) {

		//ensure the index is within range
		if( left_child_index >= priority.length)
			return false;

		//Try retrieving the value at that index
		Integer value = priority[left_child_index];
		if ( value == null)
			return false;

		return true;
	}


	public BTNode<Integer> BuildTree(int index){


		BTNode<Integer> root = null;

		if( index >= data.length)
			return root;

		root = new BTNode<Integer>(priority[index]);

		BuildTree(getLeftChildIndex(index));
		BuildTree(index+1);
		BuildTree(getRightChildIndex(index));

		return root;

	}


}
